- [订单轨迹时空查询](#订单轨迹时空查询)
- [订单轨迹时间范围查询](#订单轨迹时间范围查询)
- [订单轨迹空间范围查询](#订单轨迹空间范围查询)
- [订单轨迹时间范围空间范围查询](#订单轨迹时间范围空间范围查询)


## 订单轨迹查询接口
### url:
/api/traj/traj

## 订单轨迹时空查询
获取指定link空间条件+ts时间条件，查询订单轨迹详细
#### uri:
/traj/list
#### metord:
GET/POST
#### 请求格式
application/x-www-form-urlencoded; charset=UTF-8
#### 请求参数
| 参数名称  | 是否必选 |  类型  |     描述       |
|-------|------|----|------------|
| linkid | 是 | String | 74544301-74544301,74544301 (';'且关系，','或关系，'-'且关系,优先级‘-’ > ',' > ';' )|
| start | 是 | int | 1522717200|
| end | 是 | int | 1522728000|
| size | 否 | int | 2，defaultValue="1000" |
| from | 否 | int |指定返回订单开始位置, defaultValue="0" |
| index | 否 | String | default:3,8,9； 全部：all； [index说明](http://wiki.intra.lsy.com/pages/viewpage.action?pageId=116565675)|

####  响应参数
| 参数名称  |  类型  |     描述       |
|------|----|------------|
| stauts | Int | 错误码，1: 成功，0: 失败 |
| msg | String | 错误信息 |
| data | JsonArray | 返回数据list |
| total | int | 符合条件的总数 |
| data.pointList | Array | [[timestamp,[linkids],distance],[...],[...]] |
| data.orderInfo | Array | [order_id,driver_id,s_lon,s_lat,e_lon,e_lat,s_ts,e_ts] |


####  示例
#####  输入
```bash
curl "http://100.69.238.158:8001/api/traj/traj/traj/list?linkid=720654241,720654250,720654261,720654270,720654281,720654570,720654590,720654611,720655150,720655151,720655160,720655161,720655170,720655171,720655180&start=1522717200&end=1522728000&size=2" 
```

#####  输出
```bash
{
    "msg":"",
    "total":706,
    "data":[
        {
            "pointList":
             [
             [
                    1522716504,
                    [
                        3716131
                    ],
                    24
                ],[],...
                ],
            "orderInfo":[
                "c740e92f19851203100671",
                "013ee9d730e502efd06698974946f360",
                116.35352,
                39.9297,
                116.28471,
                40.04434,
                1522716504,
                1522718878
            ]
        },
        {
            "pointList":Array[341],
            "orderInfo":[
                "b58168e927666923100671",
                "185080b6dd27e6a36a327c842817306f",
                116.28458,
                40.04398,
                116.32192,
                40.03663,
                1522726023,
                1522727045
            ]
        }
    ],
    "rid":"",
    "status":1
}
```

## 订单轨迹时间范围查询
获取指定时间条件（日期范围、时段范围、工作日范围），空间条件（linkids），查询订单轨迹详细
#### uri:
/path/list/condition
#### metord:
GET/POST
#### 请求格式
application/x-www-form-urlencoded; charset=UTF-8
#### 请求参数
| 参数名称  | 是否必选 |  类型  |     描述       |
|-------|------|----|------------|
| linkid | 是 | String | 74544301-74544301,74544301 (';'且关系，','或关系，'-'且关系)|
| start | 是 | int | 1522717200|
| end | 是 | int | 1522728000|
| date_range | 否 | String | 优先','或关系，之后'-'至关系：如"20180401-20180402,20180403"|
| hour_range | 否 | String | defaultValue="0-24"|
| weekday_range | 否 | String | 工作日, defaultValue="1,2,3,4,5"|
| size | 否 | int | 5, defaultValue="1000" |
| from | 否 | int |指定返回订单开始位置, defaultValue="0" |
| index | 否 | String | default:3,8,9； 全部：all； [index说明](http://wiki.intra.lsy.com/pages/viewpage.action?pageId=116565675)|


####  响应参数
| 参数名称  |  类型  |     描述       |
|------|----|------------|
| stauts | Int | 错误码，1: 成功，0: 失败 |
| msg | String | 错误信息 |
| data | JsonArray | 返回数据list |
| total | int | 符合条件的总数 |
| data.pointList | Array | [[timestamp,[linkids],distance],[...],[...]] |
| data.orderInfo | Array | [order_id,driver_id,s_lon,s_lat,e_lon,e_lat,s_ts,e_ts] |

####  示例
#####  输入
```bash
curl "http://100.69.238.158:8001/api/traj/traj/traj/list/condition?linkid=720654241,720654250,720654261,720654270,720654281,720654290,720654570,720654590,720654611,720655150,720655151,720655160,720655161,720655170,720655171,720655180,720655181&date_range=20180401-20180403,20180409&hour_range=7-9,17-19&weekday_range=1,2,3,4,5&from=0&size=5"```
```

#####  输出
```bash
{
    "msg":"",
    "total":1,
    "data":[
        {
            "pointList":[
                    1522715675,
                    [
                        317413031
                    ],
                    17,
                    116.23512
                ], ...[ ]
                ],
            "orderInfo":[
                "c8f86cda82738892100671",
                "9841b5a859fa9774c8dcd418fbbcce12",
                116.23523,
                40.0854,
                116.28465,
                40.04455,
                1522715674,
                1522717213
            ]
        }
    ],
    "rid":"",
    "status":1
}
```

## 订单轨迹空间范围查询
获取指定gps + ts时间条件，查询订单轨迹详细
#### uri:
/path/list/gps
#### metord:
GET/POST
#### 请求格式
application/x-www-form-urlencoded; charset=UTF-8
#### 请求参数
| 参数名称  | 是否必选 |  类型  |     描述       |
|-------|------|----|------------|
| polygon_range | 是 | String | 116.28445-40.04330,116.28454-40.04404 |
| city_id | 是 | String | 1 |
| start | 是 | int | 1522717200|
| end | 是 | int | 1522728000|
| size | 否 | int | 5, defaultValue="1000"|
| from | 否 | int |指定返回订单开始位置, defaultValue="0" |
| index | 否 | String | default:3,8,9； 全部：all； [index说明](http://wiki.intra.lsy.com/pages/viewpage.action?pageId=116565675)|

####  响应参数
| 参数名称  |  类型  |     描述       |
|------|----|------------|
| stauts | Int | 错误码，1: 成功，0: 失败 |
| msg | String | 错误信息 |
| data | JsonArray | 返回数据list |
| total | int | 符合条件的总数 |
| data.pointList | Array | [[timestamp,[linkids],distance],[...],[...]] |
| data.orderInfo | Array | [order_id,driver_id,s_lon,s_lat,e_lon,e_lat,s_ts,e_ts] |


####  示例
#####  输入
```bash
curl "http://100.69.238.158:8001/api/traj/traj/traj/list/gps?polygon_range=116.28445-40.04330,116.28454-40.04404,116.28457-40.04447,116.28463-40.04483,116.28463-40.04510,116.28449-40.04545,116.28469-40.04549,116.28477-40.04533,116.28484-40.04504,116.28481-40.04476,116.28474-40.04412,116.28476-40.04339,116.28478-40.04334&city_id=1&start=1522717200&end=1522728000&from=0&size=2"
```

#####  输出
```bash
{
    "msg":"",
    "total":706,
    "data":[
        {
            "pointList":[
                [
                    1522716504,
                    [
                        3716131
                    ],
                    24
                ],...[]
                ],
            "orderInfo":[
                "c740e92f19851203100671",
                "013ee9d730e502efd06698974946f360",
                116.35352,
                39.9297,
                116.28471,
                40.04434,
                1522716504,
                1522718878
            ]
        },
        {
            "pointList":[
                    1522726023,
                    [
                        720654590
                    ],
                    1
                ],...,[]
                ],
            "orderInfo":[
                "b58168e927666923100671",
                "185080b6dd27e6a36a327c842817306f",
                116.28458,
                40.04398,
                116.32192,
                40.03663,
                1522726023,
                1522727045
            ]
        }
    ],
    "rid":"",
    "status":1
}
```

## 订单轨迹时间范围空间范围查询
获取指定时间条件（日期范围、时段范围、工作日范围），空间条件（gps多边形），查询订单轨迹详细
#### uri:
/path/list/gps/condition
#### metord:
GET/POST
#### 请求格式
application/x-www-form-urlencoded; charset=UTF-8
#### 请求参数
| 参数名称  | 是否必选 |  类型  |     描述       |
|-------|------|----|------------|
| polygon_range | 是 | String | 116.28445-40.04330,116.28454-40.04404 |
| city_id | 是 | String | 1 |
| date_range | 否 | String | 优先','或关系，之后'-'至关系：如"20180401-20180402,20180403"|
| hour_range | 否 | String | defaultValue="0-24"|
| weekday_range | 否 | String | 工作日|
| size | 否 | int | 5, defaultValue="1000"|
| from | 否 | int |指定返回订单开始位置, defaultValue="0" |
| index | 否 | String | default:3,8,9； 全部：all； [index说明](http://wiki.intra.lsy.com/pages/viewpage.action?pageId=116565675)|

####  响应参数
| 参数名称  |  类型  |     描述       |
|------|----|------------|
| stauts | Int | 错误码，1: 成功，0: 失败 |
| msg | String | 错误信息 |
| data | JsonArray | 返回数据list |
| total | int | 符合条件的总数 |
| data.pointList | Array | [[timestamp,[linkids],distance],[...],[...]] |
| data.orderInfo | Array | [order_id,driver_id,s_lon,s_lat,e_lon,e_lat,s_ts,e_ts] |


####  示例
#####  输入
```bash
curl "http://100.90.164.32:8086/api/traj/traj/list/gps/condition?polygon_range=116.28445-40.04330,116.28454-40.04404,116.28457-40.04447,116.28463-40.04483,116.28463-40.04510,116.28449-40.04545,116.28469-40.04549,116.28477-40.04533,116.28484-40.04504,116.28481-40.04476,116.28474-40.04412,116.28476-40.04339,116.28478-40.04334&date_range=20180401-20180403,20180409&city_id=1&hour_range=7-9,17-19&weekday_range=1,2,3,4,5&from=0&size=5" 
```

#####  输出
```bash
{
    "msg":"",
    "total":706,
    "data":[
        {
            "pointList":[
                Array[3],
                [
                    1522716505,
                    [
                        3716131
                    ],
                    24
                ],...,
                ],
            "orderInfo":[
                "c740e92f19851203100671",
                "013ee9d730e502efd06698974946f360",
                116.35352,
                39.9297,
                116.28471,
                40.04434,
                1522716504,
                1522718878
            ]
        },
        {
            "pointList":[
                [
                    1522726023,
                    [
                        720654590
                    ],
                    1
                ],...,
                ],
            "orderInfo":[
                "b58168e927666923100671",
                "185080b6dd27e6a36a327c842817306f",
                116.28458,
                40.04398,
                116.32192,
                40.03663,
                1522726023,
                1522727045
            ]
        }
    ],
    "rid":"",
    "status":1
}
```
