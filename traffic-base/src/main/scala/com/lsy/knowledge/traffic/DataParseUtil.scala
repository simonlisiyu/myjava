package com.lsy.knowledge.traffic

// 路口流经的车辆基本数据
case class JunctionDriverBasicData(cityId: Int,
                                   junctionId: BigInt,
                                   startLink: BigInt,
                                   endLink: BigInt,
                                   direction: Int,
                                   turn: Int,
                                   hourMinute: String,
                                   duration: String,
                                   distance: BigDecimal,
                                   driverId: String,
                                   firstPointTimeStamp: BigInt,
                                   beforeStopCarCount: Int,
                                   afterStopCarCount: Int,
                                   beforeStopLength: BigDecimal,
                                   speed: BigDecimal,
                                   lastSecondLink: BigInt
                                  )

case class JunctionFreeSpeed(junctionId: BigInt,
                             startLink: BigInt,
                             endLink: BigInt,
                             cityId: Int,
                             sampleCount: BigInt,
                             freeSpeed: BigDecimal
                            )

// tti 交通通行指数
case class CrossroadIndexOne(junctionId: BigInt,
                             driverId: String,
                             startLink: BigInt,
                             endLink: BigInt,
                             cityId: Int,
                             direction: Int,
                             turn: Int,
                             firstPointTimeStamp: BigInt,
                             hourMinute: String,
                             duration: BigDecimal,
                             distance: BigDecimal,
                             freeSpeed: BigDecimal,
                             delayTime: BigDecimal,
                             tti: BigDecimal
                            )

case class CrossroadIndexTwo(date: String,
                             junctionId: BigInt,
                             hourMinute: String,
                             count: Int,
                             durationSum: BigDecimal,
                             direction: Int,
                             turn: Int,
                             endLink: BigInt,
                             startLink: BigInt,
                             lengthSum: BigDecimal,
                             freeSpeed: BigDecimal,
                             delayTimeSum: BigDecimal,
                             beforeStopCarCountSum: Int,
                             afterStopCarCountSum: Int,
                             beforeStopLengthSum: BigDecimal,
                             beforeStopCarSum: Int,
                             afterStopCarSum: Int,
                             tti: BigDecimal,
                             speed: BigDecimal,
                             lastSecondLink: BigInt,
                             weight: BigDecimal,
                             Delay: BigDecimal
                            )

case class CrossroadIndexTrust(junctionId: BigInt,
                               hourMinute: String,
                               direction: Int,
                               turn: Int,
                               endLink: BigInt,
                               startLink: BigInt,
                               lastSecondLink: BigInt,
                               delayStandard: String, // 有可能为null，但是实际上后续不参与运算，所以使用String
                               durationAvg: BigDecimal,
                               trust: Int
                              )

object DataParseUtil {

  implicit def stringToInt(tempString: String): Int = Integer.parseInt(tempString)

  implicit def stringToBigInt(tempString: String): BigInt = BigInt(tempString)

  implicit def stringToBigDecimal(tempString: String): BigDecimal = BigDecimal(tempString)

  def parseCrossroadIndexOneData(line: String): Option[CrossroadIndexOne] = {
    line.split("\t") match {
      case Array(junctionId,
      driverId,
      startLink,
      endLink,
      cityId,
      direction,
      turn,
      firstPointTimeStamp,
      hourMinute,
      duration,
      distance,
      freeSpeed,
      delayTime,
      tti) => try {
        Some(CrossroadIndexOne(junctionId,
          driverId,
          startLink,
          endLink,
          cityId,
          direction,
          turn,
          firstPointTimeStamp,
          hourMinute,
          duration,
          distance,
          freeSpeed,
          delayTime,
          tti
        ))
      } catch {
        case _: Exception => {
          println(s"CrossroadIndexOne format is not right after split with tab: ${line} ")
          None
        }
      }
      case _ => {
        println(s"CrossroadIndexOne format is not right after split with tab: ${line} ")
        None
      }
    }
  }

  def parseJunctionFreeSpeedData(line: String): Option[JunctionFreeSpeed] = {
    line.split("\t") match {
      case Array(junctionId,
      startLink,
      endLink,
      cityId,
      sampleCount,
      freeSpeed
      ) => try {
        Some(JunctionFreeSpeed(junctionId, startLink, endLink, cityId, sampleCount, freeSpeed))
      } catch {
        case _: Exception => {
          println(s"JunctionFreeSpeedData format is not right after split with tab: ${line} ")
          None
        }
      }

      case _ => {
        println(s"JunctionFreeSpeedData format is not right after split with tab: ${line} ")
        None
      }
    }
  }

  def parseJunctionDriverBasicData(line: String): Option[JunctionDriverBasicData] = {
    line.split("\t") match {
      case Array(cityId,
      junctionId,
      startLink,
      endLink,
      direction,
      turn,
      hourMinute,
      duration,
      distance,
      driverId,
      firstPointTimeStamp,
      beforeStopCarCount,
      afterStopCarCount,
      beforeStopLength,
      speed,
      lastSecondLink,
      points
      ) =>
        try {
          Some(
            JunctionDriverBasicData(
              cityId,
              junctionId,
              startLink,
              endLink,
              direction,
              turn,
              hourMinute,
              duration,
              distance,
              driverId,
              firstPointTimeStamp,
              beforeStopCarCount,
              afterStopCarCount,
              beforeStopLength,
              speed,
              lastSecondLink
            ))
        } catch {
          case _: Exception => {
            println(s"JunctionDriverBasicData format is not right after split with tab: ${line}")
            None
          }
        }

      case _ => {
        println(s"JunctionDriverBasicData format is not right after split with tab: ${line}")
        None
      }
    }
  }

  def parseCrossroadIndexTwoData(line: String): Option[CrossroadIndexTwo] = {
    line.split("\t") match {
      case Array(date,
      junctionId,
      hourMinute,
      count,
      durationSum,
      direction,
      turn,
      endLink,
      startLink,
      lengthSum,
      freeSpeed,
      delayTimeSum,
      beforeStopCarCountSum,
      afterStopCarCountSum,
      beforeStopLengthSum,
      beforeStopCarSum,
      afterStopCarSum,
      tti,
      speed,
      lastSecondLink,
      weight,
      Delay) => try {
        Some(CrossroadIndexTwo(date,
          junctionId,
          hourMinute,
          count,
          durationSum,
          direction,
          turn,
          endLink,
          startLink,
          lengthSum,
          freeSpeed,
          delayTimeSum,
          beforeStopCarCountSum,
          afterStopCarCountSum,
          beforeStopLengthSum,
          beforeStopCarSum,
          afterStopCarSum,
          tti,
          speed,
          lastSecondLink,
          weight,
          Delay
        ))
      } catch {
        case _: Exception => {
          println(s"CrossroadIndexTwo format is not right after split with tab: ${line} ")
          None
        }
      }
      case _ => {
        println(s"CrossroadIndexTwo format is not right after split with tab: ${line} ")
        None
      }
    }
  }

  def parseCrossroadIndexTrust(line: String): Option[CrossroadIndexTrust] = {
    line.split("\t") match {
      case Array(junctionId,
      hourMinute,
      direction,
      turn,
      endLink,
      startLink,
      lastSecondLink,
      delayStandard,
      durationAvg,
      trust) => try {
        Some(CrossroadIndexTrust(junctionId,
          hourMinute,
          direction,
          turn,
          endLink,
          startLink,
          lastSecondLink,
          delayStandard,
          durationAvg,
          trust))
      } catch {
        case _: Exception => {
          println(s"CrossroadIndexTrust format is not right after split with tab: ${line} ")
          None
        }
      }
      case _ => {
        println(s"CrossroadIndexTrust format is not right after split with tab: ${line} ")
        None
      }
    }
  }





  def parseCityOrderGps(line: String): Option[CrossroadIndexTrust] = {
    line.split("\t") match {
      case Array(junctionId,
      hourMinute,
      direction,
      turn,
      endLink,
      startLink,
      lastSecondLink,
      delayStandard,
      durationAvg,
      trust) => try {
        Some(CrossroadIndexTrust(junctionId,
          hourMinute,
          direction,
          turn,
          endLink,
          startLink,
          lastSecondLink,
          delayStandard,
          durationAvg,
          trust))
      } catch {
        case _: Exception => {
          println(s"CrossroadIndexTrust format is not right after split with tab: ${line} ")
          None
        }
      }
      case _ => {
        println(s"CrossroadIndexTrust format is not right after split with tab: ${line} ")
        None
      }
    }
  }

}
