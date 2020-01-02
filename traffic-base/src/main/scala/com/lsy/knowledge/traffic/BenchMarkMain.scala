package com.lsy.knowledge.traffic

import com.mysql.jdbc.Driver
import org.apache.spark.sql._
import org.apache.spark.rdd.RDD

/*
这个程序用于计算干线的benchmark
 */
case class DetailInfo(      // case class 用于 rdd 里面mainroad类型使用
                       linkid1: String,
                       linkid2: String,
                       duration: Float,
                       length:Float,
                       userId: String,
                       timestamp: Int,
                       beforeCount: Int,
                       afterCount: Int,
                       beforeLength: Float,
                       speed: Float,
                       tti: Float,
                       delay: Float,
                       freeSpeed: Float,
                       hour: String
                     )

//流量信息类型定义
case class FlowInfo(cityid: String, junctionid: String, linkid1: String, linkid2: String, direction: String, turn: String, linkidLsecond: String)

object BenchMarkMain {

  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder().appName(s"BenchMarkMain").enableHiveSupport().getOrCreate()

    val sc = spark.sparkContext

    import spark.implicits._

    // 获取前面30天的detail信息
    var citys = args(0)     // xxx,xx,xx
    var date = args(1)    //
    var daynum = args(2)
    // 判断参数是否正确
    if (citys.isEmpty || date.isEmpty || daynum.isEmpty) {
      System.exit(1)
    }

    citys.split(",").foreach(city => loadCityData(city, date, daynum))

    def loadCityData(city: String, date: String, daynum: String) = {
      import java.text.SimpleDateFormat
      import java.util.Calendar
      val sdf = new SimpleDateFormat("yyyyMMdd")
      val ecal = java.util.Calendar.getInstance
      ecal.setTime(sdf.parse(date))

      var rdd: RDD[DetailInfo] = sc.emptyRDD[DetailInfo]
      for (i <- 0 to daynum.toInt) {
        import java.util.Calendar
        ecal.add(Calendar.DATE, -1)

        import java.text.SimpleDateFormat
        val year = new SimpleDateFormat("yyyy").format(ecal.getTime)
        val month = new SimpleDateFormat("MM").format(ecal.getTime)
        val day = new SimpleDateFormat("dd").format(ecal.getTime)

        // 获取每天的mainroad数据
        var file = "/user/its_bi/bgd_delay_index_mainroad/" + year + "/" + month + "/" + day + "/" + city + "_detail-r-00000"
        val hadoopConf = new org.apache.hadoop.conf.Configuration()
        val fs = org.apache.hadoop.fs.FileSystem.get(hadoopConf)
        val exists = fs.exists(new org.apache.hadoop.fs.Path(file))

        if (exists) {
          var trdd = sc.textFile(file).map(_.split("\t"))
            .map(
              p => DetailInfo(
                p(0).split("@")(0),
                p(0).split("@")(1),
                p(1).trim.toFloat,
                p(2).trim.toFloat,
                p(3).trim,
                p(4).trim.toInt,
                p(5).trim.toInt,
                p(6).trim.toInt,
                p(7).trim.toFloat,
                p(8).trim.toFloat,
                p(9).trim.toFloat,
                p(10).trim.toFloat,
                p(11).trim.toFloat,
                getHour(p(4).toInt))
            )
          rdd = rdd union trdd    //合并每天的数据
          println("读取文件:%s".format(file))
        }
      }
      println("rdd个数：%s".format(rdd.count))

      // 获取flow文件，使用最新的flow版本，进行join操作
      var file = "/user/its_bi/flow_data/mainflow/2017082514/mainflow_%s".format(city)
      var frdd = sc.textFile(file).map(_.split("\t")).map(f => FlowInfo(
        city,
        f(0),
        f(1),
        f(2),
        f(7),
        f(8),
        getLslink(f(5))
      ))

      var details = rdd.toDF()
      details.registerTempTable("details")

      var flows = frdd.toDF()
      flows.registerTempTable("flows")

      val df = spark.sql(
        "select " +
          "linkid1 as detaillink1, linkid2 as detaillink2, hour, count(1) as count, " +
          "percentile_approx(duration, 0.95) as duration, " +
          "percentile_approx(delay, 0.95) as delay, " +
          "percentile_approx(speed, 0.95) as speed, " +
          "percentile_approx(beforeLength, 0.95) as before_length, " +
          "percentile_approx(tti, 0.95) as tti " +
          "from details group by linkid1, linkid2, hour"
      )

      var retdf = df.join(flows, flows("linkid1") === df("detaillink1") && flows("linkid2") === df("detaillink2"), "inner")
      retdf.show()
      println("最终rdd个数：%s".format(retdf.count))

      val prop = new java.util.Properties
      val url = "jdbc:mysql://100.69.238.94:4008/its"
      val table = "flow_benchmark_mainroad"
      val user = "its_rw"
      val password = "iTsITs_Rw@0912"
      prop.setProperty("driver", "com.mysql.jdbc.Driver")
      prop.setProperty("user", user)
      prop.setProperty("password", password)


      // 先清空数据库中的数据
      import java.sql.{Connection, DriverManager, ResultSet}
      classOf[Driver]
      val conn = DriverManager.getConnection(url, user, password)
      val statement = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE)
      try {
        var prep = conn.prepareStatement("DELETE FROM %s WHERE cityid=%s".format(table, city))
        prep.execute()
      } catch {
        case e: Exception => e.printStackTrace()
      } finally {
        conn.close()
      }

      retdf.withColumnRenamed("linkidLsecond", "linkid_lsecond").select(
        "cityid", "junctionid", "hour", "count", "duration", "direction", "turn", "linkid2", "linkid1", "delay", "before_length",
        "tti", "speed", "linkid_lsecond"
      ).write.mode("append").jdbc(url, table, prop)
    }


    //details.rdd.map(r => r.mkString("\t")).saveAsTextFile("/user/its_bi/yejiafeng/retdf")
    //retdf.insertIntoJDBC()
  }

  def getHour(timestamp: Int) : String = {
    import java.text.SimpleDateFormat

    val format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    val d = format.format(timestamp.toLong * 1000)
    val date = format.parse(d)

    var hour = "%02d".format(date.getHours)
    val minten = (date.getMinutes / 10).toInt * 10
    val min = "00"
    "%s:%s".format(hour, min)
  }

  def getLslink(linkids: String) : String = {
    val links = linkids.split(",")
    links(links.size-2)
  }

}
