package com.lsy.knowledge.traffic.struct

object StructUtil {

  def convertStringToTrail(point: String): Option[Trail] = {
    val items = point.split(",")
    if (items.size != 11) {
      None
    }
    if (point == "") {
      None
    } else {
      Some(Trail(items(0).toInt,
        items(1).toFloat,
        items(2).toFloat,
        items(3).toString,
        items(4).toInt,
        items(5).toFloat,
        items(6).toFloat,
        items(7).toFloat,
        items(8).toFloat,
        items(9).toFloat,
        items(10).toString
      ))
    }
  }
}
