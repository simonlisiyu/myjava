package com.lsy.knowledge.traffic.struct

/*
CREATE EXTERNAL TABLE `dwm_car_flow_baseindex_d` (
    `driver_id` string   COMMENT '司机id',
    `point_timestamp` int COMMENT '第一个轨迹点时间戳，和司机id结合是唯一索引',
    `time_slice` string COMMENT '分属的时间片,格式10:00',
    `duration` float COMMENT '通行时间',
    `traffic_distance` float COMMENT '通行距离',
    `before_stop_count` int COMMENT '路口前停车次数',
    `after_stop_count` int COMMENT '路口后停车次数',
    `before_max_stop_distance` float COMMENT '离路口最大停车距离',
    `speed` float COMMENT '行驶速度'
)
COMMENT '车辆基础指标表'
PARTITIONED BY (
  `city_id` int COMMENT '城市id',
   `flow_type` string COMMENT 'flow类型例如路口junction／路段section',
  `dt` string COMMENT '分区日期'

)
 */
case class CarFlowBaseIndex(
                      driver_id : String,
                      point_timestamp: Int,
                      time_slice : String,
                      duration: Float,
                      traffic_distance: Float,
                      before_stop_count: Int,
                      after_stop_count: Int,
                      before_max_stop_distance: Float,
                      speed: Float,
                      city_id: Int,
                      flow_type: String,
                      dt : String
                      )


case class CarFlowBaseIndex2(
                             driver_id : String,
                             point_timestamp: Int,
                             time_slice : String,
                             duration: Float,
                             traffic_distance: Float,
                             before_stop_count: Int,
                             after_stop_count: Int,
                             before_max_stop_distance: Float,
                             speed: Float,
                             city_id: Int,
                             junction_id: String,
                             flow_type: String,
                             dt : String
                           )