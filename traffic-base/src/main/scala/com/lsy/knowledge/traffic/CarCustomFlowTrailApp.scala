package com.lsy.knowledge.traffic

import java.text.SimpleDateFormat
import java.util
import java.util.Calendar

import com.lsy.knowledge.traffic.base.domain.{Flow, GPSPoint, Link, Trie}
import com.lsy.knowledge.traffic.base.util.{CycleUtil, GPSPointUtil, RoadUtil, VersionUtil}
import com.lsy.knowledge.traffic.struct._
import org.apache.spark.SparkContext
import org.apache.spark.sql.{Row, SparkSession}

import scala.util.control.Breaks.{break, breakable}


/*
 生成匹配轨迹，数据入库：dwd_car_order_trail_di
 */
object CarCustomFlowTrailApp {

  def main(args: Array[String]): Unit = {

    if (args.length < 3) {
      System.err.println("Usage: CarMatchFlowTrailApp <city_id> <flow_content> <date(yyyyMMdd)> <output_folder>")
      System.exit(1)
    }

    // 输入参数
    val cityId = args(0) // "2"
    val flow_content = args(1)  // "1:128971541,128971751,128971741,148465901,148465900;2:14276780,148465890,148465900,128971740"
    val flow_type = "custom"
    val date = args(2) // "20180105"
    val output_folder = args(3) // /user/its_bi/yejianfeng/custom_output

    // 校验参数
    CommonUtil.checkOutputSave(output_folder, "/user/its_bi/yejianfeng/")

    // spark session生成
    val spark = SparkSession.builder().enableHiveSupport().appName(s"CarCustomFlowTrailApp-${date}-${cityId}").getOrCreate()
    val sc: SparkContext = spark.sparkContext

    val year = date.substring(0, 4)
    val month = date.substring(4, 6)
    val day = date.substring(6, 8)
    val dt = s"${year}-${month}-${day}"

    // 轨迹匹配，生成dwd_car_match_flow_trail_di需要的数据
    // 获取当天的路网版本
    import org.apache.hadoop.conf.Configuration
    val conf: Configuration = new Configuration

    val roadNet = "/user/its_bi/flow_data/road_net/"
    import org.apache.hadoop.fs.FileSystem
    val fs = FileSystem.get(conf)

    //生成Map<string,trie>
    var trieMap = Map[String, Trie]()
    var crossLinksHashMap = Map[String, java.util.Map[java.lang.String, java.lang.Boolean]]()
    var cityLinksMap = Map[String, java.util.Map[java.lang.String, Link]]()
    var cyclesMap = Map[String, java.util.Map[java.lang.String, java.lang.Integer]]()

    val versionUtil = new VersionUtil()
    versionUtil.setFileSystem(FileSystem.get(conf)).setRoadNetPath(roadNet)
    val version = versionUtil.getAvaiableVersion(year, month, day)
    println(s"=====load version: ${version}======")

    val roadUtil = new RoadUtil()
    roadUtil.setRoadNetPath(roadNet).setFileSystem(fs)
    roadUtil.setCity(cityId).setVersion(version)

    val linkAttrs = roadUtil.getLinkAttrs()
    val crossLinksHash = roadUtil.getCrossLinkHash
    val trie = getTrieFromCustomString(flow_content, crossLinksHash, linkAttrs)

    val cityLinks = roadUtil.getLinkAttrs
    val cycleUtil = new CycleUtil(cityId)
    cycleUtil.setFileSystem(fs).loadCycleFile(year, month, day)

    val bTrie = sc.broadcast[Trie](trie)
    val bCrossLinkHash = sc.broadcast[java.util.Map[java.lang.String, java.lang.Boolean]](crossLinksHash)
    val bCityLinks = sc.broadcast[java.util.Map[java.lang.String, Link]](cityLinks)
    val bCycle = sc.broadcast[java.util.Map[java.lang.String, java.lang.Integer]](cycleUtil.cycles)

    val bVersion = sc.broadcast[String](version)

    // 读取 dwd_car_order_trail_di 表数据
    spark.sql(s"use its_bi")
    val initPartitionCount = if (cityId == "1" || cityId == "11" || cityId == "17") 1000 else 100
    var carOrderTrailDf = spark.sql(s"SELECT * FROM dwd_car_order_trail_di WHERE city_id=${cityId} AND dt='${year}-${month}-${day}'").repartition(initPartitionCount)

    // 最终的两个list存储在这里
    import spark.implicits._
    var exceptionCount = 0

    val matchRdd = carOrderTrailDf.map(row => {

      var lastTimeStamp = 0

      var carMatchFlowTrailLists = List[CarMatchFlowTrail]()
      var carFlowBaseIndexLists = List[CarFlowBaseIndex2]()

      val userId = row.getAs[String]("driver_id")
      val roadVersion = bVersion.value

      val trails = row.getSeq[Row](2)

      val sb = new StringBuffer()
      sb.append(userId)
      sb.append("\t")
      sb.append(roadVersion)
      sb.append("\t")

      trails.foreach(item => {
        sb.append(item.mkString(","))
        sb.append(";")
      })
      val crossLinksHash = bCrossLinkHash.value
      val trie = bTrie.value
      val cityLinks = bCityLinks.value
      val cycles = bCycle.value

      val parseResult = GPSPointUtil.parseData(sb.toString, crossLinksHash)

      val points: java.util.List[GPSPoint] = parseResult.points
      // 用于匹配的GPSPoint
      val realPoints: java.util.List[GPSPoint] = parseResult.realPoints // 用于计算真实点的GPSPoint
      val counter: Int = points.size
      val offset = counter - 2

      for (i <- 0 to offset) {
        breakable {
          if (i > 0 && points.get(i).linkid == points.get(i - 1).linkid) {
            break()
          }
          else {
            // 先匹配
            val result = trie.searchBegin(points.subList(i, counter))
            if (result == null) break()

            try {
              // 匹配之后使用轨迹调整flow，会把cityLinks放在flow的attr中
              val lastSecondLink = result.flow.getLastSecondLinkId
              val flowInfo = result.adjustFlow(cityLinks, crossLinksHash).flow

              val linkPoints = flowInfo.reorgPointsMatch(result.points, realPoints)

              flowInfo.checkUseable()
              flowInfo.checkMaxStopTimeExceed(cycles, linkPoints)
              val start = linkPoints.get(0).realPoint().timestamp

              val format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
              val d = format.format(start.toLong * 1000)
              val cal = Calendar.getInstance
              cal.setTime(format.parse(d))

              val hour = "%02d".format(cal.get(Calendar.HOUR_OF_DAY))
              val minint = if (cal.get(Calendar.MINUTE) > 30) {
                30
              }
              else {
                0
              }
              val min = "%02d".format(minint)

              val indexResult = flowInfo.getIndexResult(linkPoints)
              if (indexResult == null) throw new Exception("indexResult is null")
              indexResult.checkAvaiable()

              var firstTimeStamp = linkPoints.get(0).timestamp.intValue
              if (linkPoints.get(0).isBoundAdd) firstTimeStamp = linkPoints.get(1).timestamp.intValue
              if (lastTimeStamp == firstTimeStamp) {
                firstTimeStamp = firstTimeStamp + 1
              }
              lastTimeStamp = firstTimeStamp

              // 获取匹配的轨迹在realPoint里面的前后截止位置
              val firstRealPointIndex = points.get(i).realPoint().realPointIndex
              val lastRealPointIndex = points.get(i + result.points.size() - 1).realPoint().realPointIndex
              var preRealPointStr: String = ""
              if (firstRealPointIndex - 1 > 0) {
                preRealPointStr = realPoints.get(firstRealPointIndex - 1).raw
              }
              var nextRealPointStr: String = ""
              if (lastRealPointIndex + 1 < realPoints.size()) {
                nextRealPointStr = realPoints.get(lastRealPointIndex + 1).raw
              }

              import scala.collection.JavaConversions._
              val matchTrails: List[Trail] = realPoints.subList(firstRealPointIndex, lastRealPointIndex).map(point =>
                StructUtil.convertStringToTrail(point.raw)
              ).flatMap(item => item).toList

              val adjustTrail = linkPoints.map(point => AdjustTrail(point.timestamp.toInt, point.linkid, point.distance.toInt)).toList

              // 构造CarMatchFlowTrail 和 CarFlowBaseIndex结构
              val preRealPoint = StructUtil.convertStringToTrail(preRealPointStr).getOrElse[Trail](null)
              val nextRealPoint = StructUtil.convertStringToTrail(nextRealPointStr).getOrElse[Trail](null)

              val matchFlowTrail = CarMatchFlowTrail(
                userId,
                roadVersion,
                firstTimeStamp,
                preRealPoint,
                nextRealPoint,
                matchTrails,
                //adjustTrail,
                flowInfo.getLinkid1,
                lastSecondLink,
                flowInfo.getLinkid2,
                cityId.toInt,
                flow_type,
                dt
              )

              val carFlowBaseIndex = CarFlowBaseIndex2(
                userId,
                firstTimeStamp,
                s"${hour}:${min}",
                indexResult.duration.toFloat,
                indexResult.length.toFloat,
                indexResult.beforeCount,
                indexResult.afterCount,
                indexResult.beforeLength.toFloat,
                indexResult.speed.toFloat,
                cityId.toInt,
                flowInfo.getJunctionid,
                flow_type,
                dt
              )

              carMatchFlowTrailLists = matchFlowTrail :: carMatchFlowTrailLists
              carFlowBaseIndexLists = carFlowBaseIndex :: carFlowBaseIndexLists
            }
            catch {
              case e: java.lang.UnsupportedOperationException => e.printStackTrace()
              case e: Exception => {
                exceptionCount = exceptionCount + 1
                //println("exception caught: " + e)
                break()
              }
            }

          }
        }
      }

      (carMatchFlowTrailLists, carFlowBaseIndexLists)
    })

    matchRdd.flatMap(item => item._2).rdd.repartition(1).saveAsTextFile(output_folder + "/carMatchFlowTrailLists")

    //matchRdd.flatMap(item => item._2).toDF().repartition(1).write.option("timestampFormat", "yyyy/MM/dd HH:mm:ss ZZ").mode(SaveMode.Overwrite).csv(output_folder + "/carMatchFlowTrailLists")
    //matchRdd.flatMap(item => item._2).toDF().repartition(1).write.mode(SaveMode.Overwrite).text()

    println("all task end")
    spark.stop()
    System.exit(0)
  }

  def getTrieFromCustomString(context: String, crossLinks: util.Map[String, java.lang.Boolean], linkAttrs: util.Map[String, Link]): Trie = {
    val trie: Trie = new Trie
    try {
      val lines: Array[String] = context.split(";")
      var count: Int = 0
      for (line <- lines) {
        val t: Array[String] = line.split(":")
        if (t.length != 2) throw new Exception("format error")
        val realLinkIds: util.List[String] = new util.ArrayList[String]
        for (id <- t(1).split(",")) {
          if (!crossLinks.containsKey(id)) realLinkIds.add(id)
        }
        t(1) = String.join(",", realLinkIds)
        val id: String = t(0)
        val linkIdArrs: Array[String] = t(1).split(",")
        val junctionId: String = id
        val linkid1: String = linkIdArrs(0)
        val linkid2: String = linkIdArrs(linkIdArrs.length - 1)
        val `type`: String = "true"
        val length: String = "0"
        val linkids: String = t(1)
        val direction: String = "0"
        val turn: String = "0"
        val stage: String = "0"
        val links: util.Map[String, Link] = new util.HashMap[String, Link]
        for (l <- linkIdArrs) {
          if (false == linkAttrs.containsKey(l)) throw new Exception("linkAttr does not contain link: " + l)
          links.put(l, linkAttrs.get(l))
        }
        val f: Flow = new Flow(junctionId, linkid1, linkid2, `type`, length, linkids, direction, turn, stage, links)
        //Flow截取的长度
        trie.insert(f)
        count += 1
      }
      System.out.println("flow条数一共有：" + count)
    } catch {
      case ex: Exception =>
        ex.printStackTrace()
    }
    trie
  }
}



