package com.lsy.knowledge.traffic.struct

/*
  轨迹信息
 */
case class Trail (point_timestamp:Int,
             src_longitude: Float,
             src_latitude: Float,
             linkids: String,
             distance: Int,
             proj_longitude: Float,
             proj_latitude: Float,
             certainty: Float,
             line_speed: Float,
             line_direction: Float,
             pre_last_link_id: String
            )
{
  override def toString: String = {
    s"${point_timestamp},${src_longitude},${src_latitude},${linkids},${distance},${proj_longitude},${proj_latitude},${certainty},${line_speed},${line_direction},${pre_last_link_id}"
  }
}
