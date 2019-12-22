- [list instance信息](#list_instance信息)
- [kv查询instance信息](#kv查询instance信息)

## zbs接口
### url:
/api/resource/zbs

### list_instance信息
获取指定region，所有云硬盘信息
#### url:
/instance/list
#### metord:
GET
#### 请求格式

#### 请求参数
| 参数名称  | 是否必选 |  类型  |     描述       |
|-------|------|----|------------|
| region | 是 | String | region(cn-north-1等)|
| updateBegin | 否 | String |查询update的开始时间timestamp（ms），默认不填为全查|
| updateEnd | 否 | String | 查询update的截止时间timestamp（ms），默认不填为全查|
| start | 否 | String | 分页查询的start position，默认为0|
| length | 否 | String | 分页查询的长度，默认为10000|
####  响应参数
| 参数名称  |  类型  |     描述       |
|------|----|------------|
| stauts | Int | 错误码，0: 成功，-1: 失败 |
| message | String | 错误信息 |
| data | JsonArray | 返回数据list |
| size | int | 符合条件的总数 |
| data.id | String | instance id |
| data.volume_name | String | instance display name |
| data.size | long | size in bytes |
| data.resize_size | long | datastore_version_id |
| data.pool_id | String | 内部资源池 ID，pool-ebs0000000 表示是 EBS 盘，否则为 ZBS 盘 |
| data.volume_type_name | String | hdd 或 ssd |


####  示例
#####  输入
```bash
curl "http://x.x.x.x:8081/api/resource/zbs/instance/list?region=south-1&length=1&updateBegin=1516235261000&updateEnd=1516268261000" > test.log
```
#####  输出
```bash
{
	"status": 0,
	"message": "success to get data to mysql",
	"data": [{
		"id": "xx-xx-46e7-8082-xxx",
		"volume_name": "xxx",
		"size": 107374182400,
		"resize_size": 0,
		"pool_id": "pool-ebsxx",
		"volume_type_name": "ssd",
		"az_name": "gz",
		"action": "",
		"tenant_id": "xxx",
		"version": 0,
		"bootable": false,
		"enable": true,
		"snapshot_id": "",
		"created_at": 1502474515000,
		"updated_at": 1516251293000,
		"deleted_at": null,
		"status": 2,
		"iops": 20000,
		"throughput": 100,
		"description": "",
		"host_ip": "172.27.37.58",
		"instance_uuid": "xx-1207-48d3-8ee2-xx",
		"instance_type": "",
		"device_name": "nbd",
		"attach_mode": "rw",
		"attach_time": 1515604206000,
		"attend_status": 34,
		"snapshot_names": null
	}],
	"size": 7
}



```

### kv查询instance信息
通过指定的key和value，查询instance详请
#### url:
/instance/get
#### metord:
GET
#### 请求格式

#### 请求参数
| 参数名称  | 是否必选 |  类型  |     描述       |
|-------|------|----|------------|
| region | 是 | String | region(cn-north-1等)|
| key | 是 | String |查询的key|
| value | 是 | String | 查询的value，可以为jsonarray(["xx","yy"])|
| start | 否 | String | 分页查询的start position，默认为0|
| length | 否 | String | 分页查询的长度，默认为10|
####  响应参数
| 参数名称  |  类型  |     描述       |
|------|----|------------|
| stauts | Int | 错误码，0: 成功，-1: 失败 |
| message | String | 错误信息 |
| data | JsonArray | 返回数据list |
| size | int | 符合条件的总数 |
| data.id | String | instance id |
| data.volume_name | String | instance display name |
| data.size | long | size in bytes |
| data.resize_size | long | datastore_version_id |
| data.pool_id | String | 内部资源池 ID，pool-ebs0000000 表示是 EBS 盘，否则为 ZBS 盘 |
| data.volume_type_name | String | hdd 或 ssd |

####  示例
#####  输入
```bash
curl "http://xx.xx.xx.xx:8081/api/resource/zbs/instance/get?value=xx-3a05-46e7-8082-xx&region=south-1&key=id" > test.log
```
#####  输出
```bash
{
	"status": 0,
	"message": "success to get data to mysql",
	"data": [{
		"id": "",
		...
	}],
	"size": 1
}

```

