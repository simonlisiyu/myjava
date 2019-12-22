package com.lsy.java.test.object;


import com.alibaba.fastjson.JSON;

public class ResponseData {
	private Integer status = 0;
	private String msg = "";
	private String rid = "";
	private Object data = "";
	private long total = 1;

	public ResponseData() {
	}

	public ResponseData(Object data) {
		this.status = 1;
		this.data = data;
	}

	public ResponseData(Integer status, String msg, String rid, Object data, long total) {
		this.status = status;
		this.msg = msg;
		this.rid = rid;
		this.data = data;
		this.total = total;
	}

	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	public long getTotal() {
		return total;
	}
	public void setTotal(long total) {
		this.total = total;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getRid() {
		return rid;
	}

	public void setRid(String rid) {
		this.rid = rid;
	}

	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}
}
