- [list_order_id等信息](#list_order_id等信息)
- [get_traj详细信息](#get_traj详细信息)

## 订单轨迹基础接口
### url:
/api/base

## list_order_id等信息
获取指定link条件+时间条件，所有order_id等信息
#### uri:
/order/list
#### metord:
GET/POST
#### 请求格式
application/x-www-form-urlencoded; charset=UTF-8
#### 请求参数
| 参数名称  | 是否必选 |  类型  |     描述       |
|-------|------|----|------------|
| linkid | 是 | String | 74544301-74544301,74544301 (','或关系，'-'且关系)|
| start | 是 | int | 1522717200|
| end | 是 | int | 1522728000|
| size | 否 | int | 2 |
| type | 否 | int | 0: Origin, 1: Destination, -1: Pass |


####  响应参数
| 参数名称  |  类型  |     描述       |
|------|----|------------|
| stauts | Int | 错误码，1: 成功，0: 失败 |
| msg | String | 错误信息 |
| data | JsonArray | 返回数据list |
| total | int | 符合条件的总数 |
| data.linkId | String | link id |
| data.timeStampHour | String | timestamp hour |
| data.orderIds | JsonArray | order id array |  
| data.timeStamps | JsonArray | order id timestamp array |  
| data.orderODFlag | JsonArray | order id od array (0=O,1=D,-1=pass) |

####  示例
#####  输入
```bash
curl "http://100.69.238.158:8001/api/traj/base/order/list?linkid=720654261&start=1522717200&end=1522724400" 
```

#####  输出
```bash
{
    "msg":"",
    "total":6,
    "data":[
        {
            "linkId":"e0a871cb720654590",
            "orderIds":[
                "0c37417115868292100671",
                "aea0983d77778803100671"
            ],
            "orderODFlag":[
                1,
                -1
            ],
            "timeStampHour":"1522717200",
            "timeStamps":[
                "1522719877",
                "1522719095"
            ]
        },
        {
            "linkId":"e0a871cb720654590",
            "orderIds":Array[434],
            "orderODFlag":Array[434],
            "timeStampHour":"1522720800",
            "timeStamps":Array[434]
        },
        Object{...},
        Object{...},
        Object{...},
        Object{...}
    ],
    "rid":"",
    "status":1
}
```

## get_traj详细信息
获取指定order_id的详细轨迹信息
#### uri:
/traj/get
#### metord:
GET/POST
#### 请求格式
application/x-www-form-urlencoded; charset=UTF-8
#### 请求参数
| 参数名称  | 是否必选 |  类型  |     描述       |
|-------|------|----|------------|
| orderid | 是 | String | order id (','或关系)|
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
curl "http://100.69.238.158:8001/api/traj/base/traj/get?orderid=c8f86cda82738892100671&index=3,8,9,10,11"
```

#####  输出
```bash
{
    "msg":"",
    "total":1,
    "data":[
        {
            "pointList":[
                [
                    1522715675,
                    [
                        317413031
                    ],
                    17,
                    116.23512
                ],[]... 
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