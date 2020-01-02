package com.lsy.knowledge.traffic.struct

case class CarMatchFlowTrail (
                        driver_id: String,
                        road_net_version : String,
                        point_timestamp : Int,
                        previous_trail_point: Trail,
                        next_trail_point: Trail,
                        trails: List[Trail],
                        //adjust_trails: List[AdjustTrail],
                        start_link_id: String,
                        last_second_link_id: String,
                        end_link_id: String,
                        city_id: Integer,
                        flow_type: String,
                        dt: String
                        )