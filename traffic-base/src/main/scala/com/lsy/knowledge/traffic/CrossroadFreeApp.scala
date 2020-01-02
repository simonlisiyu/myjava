package com.lsy.knowledge.traffic

import java.text.SimpleDateFormat
import java.util.{Calendar, Date}

import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.fs.{FileSystem, Path}
import org.apache.spark.sql.{SaveMode, SparkSession, functions}
import org.apache.spark.rdd.RDD

/*
city + detail
String detail = flowInfo.getJunctionid() + "\t" + flowInfo.getLinkid1() + "\t" + flowInfo.getLinkid2() + "\t"
    + flowInfo.getDirection() + "\t" + flowInfo.getTurn() + "\t"
    + hour + ":" + min + "\t" + indexResult.duration + "\t" + indexResult.length + "\t" + userId + "\t"
    + firstTimeStamp + "\t" + indexResult.beforeCount + "\t"
    + indexResult.afterCount + "\t" + indexResult.beforeLength + "\t" + indexResult.speed + "\t" + pointstrace;

 */
case class BaseIndex(
                    city : String,
                    junctionId : String,
                    linkid1 : String,
                    linkid2 : String,
                    direction: Int,
                    turn : Int,
                    hour: String,
                    duration: Float,
                    length: Float,
                    userId: String,
                    timestamp: Int,
                    beforeCount: Int,
                    afterCount: Int,
                    beforeLength: Float,
                    speed: Float
                    )

/**
  * 这个程序用来计算自由流速度
  */
object CrossroadFreeApp {

  def main(args: Array[String]): Unit = {

    if (args.length < 4) {
      System.err.println("Usage: App <crossroad_base_speed_path>" +
        " <city_ids> <date> <output_path> ")
      System.exit(1)
    }

    // 读取参数
    var crossroadBasePath = args(0)   //hdfs file path
    val cityIds = args(1).split(",").toSet  //xxx,xxx,xxx
    var date = args(2).toString       // yyyyMMDD
    var output = args(3)            // /user/its_bi/estimate_v3/xxxxx
    CommonUtil.checkOutput(output)

    val spark = SparkSession.builder().appName(s"Crossroadfree-${date}").enableHiveSupport().getOrCreate()


    cityIds.foreach(city => {
      // 读取base数据
      import spark.implicits._
      // 生成rdd
      // 根据date获取上周日的数据,这样保持一周更新一次
      val lastDate = lastWeekenDate(date);
      val dates = getDates(lastDate, 28)
      val regexDate = dates.mkString("{", ",", "}")
      val appendInputPaths = s"/${city}/${regexDate}/*_detail*" // /bj/{20180128,20180127,20180126,20180125,20180124,20180123,20180122,20180121,20180120,20180119,20180118,20180117,20180116,20180115,20180114,20180113,20180112,20180111,20180110,20180109,20180108,20180107,20180106,20180105,20180104,20180103,20180102,20180101}/*_detail*
      // 加载匹配路口司机轨迹数据并注册到临时表
      val junctionDriverBasicDataLines = spark.read.textFile(crossroadBasePath + appendInputPaths).repartition(100)

      // 记录异常解析数据

      //val junctionDriverBasicDataLose = junctionDriverBasicDataLines
      //  .filter(DataParseUtil.parseJunctionDriverBasicData(_).isEmpty)
      // junctionDriverBasicDataLose.repartition(10).write.mode(SaveMode.Overwrite).text(s"${output}_crossroad_basic_data_miss/${city}/${date}/")

      val junctionDriverBasicData = junctionDriverBasicDataLines
        .map(item => Tuple1(DataParseUtil.parseJunctionDriverBasicData(item)))  // hdfs data to object
        .filter(!_._1.isEmpty)    // not empty
        .map(_._1.get)

      val frees = junctionDriverBasicData.filter(item => {    // 只拿上午0-6点，并且没有停车的数据
        item.hourMinute >= "00:00" && item.hourMinute <= "06:00" && item.beforeStopCarCount == 0 && item.afterStopCarCount == 0
      })

      frees.createOrReplaceTempView("frees")    // 生成sql视图 frees

      val resold = spark.sql(   // 拿到中位数的speed在 6-25 之间的数据
        "select " +
          "junctionId, startLink, endLink, cityId, count(1) as count, " +
          "percentile_approx(speed, 0.5) as free_speed " +
          "from frees group by startLink, endLink, junctionId, cityId having free_speed >=6 and free_speed <=25"
      )

      resold.map(row => row.mkString("\t"))
        .toDF().repartition(1).write.mode(SaveMode.Overwrite)
        .text(s"${output}/${city}/${date}/")
    })

    spark.stop()
  }

  def lastWeekenDate(date: String) :String = {
    val c = Calendar.getInstance()
    val sf = new SimpleDateFormat("yyyyMMdd")
    val d = sf.parse(date)
    c.setTime(d)
    if (c.get(Calendar.DAY_OF_WEEK).equals(1)) {
      c.add(Calendar.DATE, -7)
    } else {
      c.add(Calendar.DATE, 0 - c.get(Calendar.DAY_OF_WEEK) + 1)
    }
    sf.format(c.getTime)
  }

  def getDates(date: String, num :Int) :Array[String] = {
    val c = Calendar.getInstance()
    val sf = new SimpleDateFormat("yyyyMMdd")
    val d = sf.parse(date)
    c.setTime(d)
    val buf = scala.collection.mutable.ArrayBuffer.empty[String]
    buf += sf.format(c.getTime)

    for (i <- 1 to num-1) {
      c.add(Calendar.DATE, -1)
      buf += sf.format(c.getTime)
    }
    buf.toArray
  }
}
