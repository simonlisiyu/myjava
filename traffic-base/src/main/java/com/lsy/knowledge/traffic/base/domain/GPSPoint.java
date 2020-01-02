package com.lsy.knowledge.traffic.base.domain;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GPSPoint implements Serializable {

	private static final long serialVersionUID = 8865542149641555474L;
	
	public Double timestamp = 0.0; // 时间戳
	public Double lng = 0.0; // 经度
	public Double lat = 0.0;  // 纬度
	public String linkid = ""; // 当前所在的linkid
	public GPSPoint realPoint = null; // 有可能这个轨迹并没有纪录这个linkid, 那么就存在realPoint, 有打点的这个就为null
	public Double distance = 0.0; // 已经通过距离
	public List<String> realLinks = new ArrayList<String>();
	public List<GPSPoint> crossLinkPoints = new ArrayList<GPSPoint>(); // 记录这个点之后的真实Link的Point记录

	public int type = 0; // 类型，0 普通节点／1 crosslink／2 截止加入的点，比如为了250/30加入的点
	public double speed = 0.0;  // 速度
	
	public int realPointIndex = 0;
	public String raw = ""; // 原始轨迹点
	
	// 这个点之前的虚拟点
	public List<GPSPoint> viaPoints = null;
	public GPSPoint pre = null;
	public GPSPoint next = null;
	
	@Override
    public String toString() {
        StringBuilder sb = new StringBuilder("");
        sb.append("timestamp:" + this.timestamp.intValue() + ",linkid:" + this.linkid + ",distance:" + this.distance +",type:" + this.type);
        return sb.toString();
    }
	
	/*
	 * 转换成LinkPoint
	 */
	public Flow.LinkPoint toLinkPoint() {
		Flow flow = new Flow();
		return flow.new LinkPoint(this.linkid, this.distance);
	}
	
	/*
	 * 标记这个轨迹点是crossLink
	 */
	public void markIsCrossLink() {
		this.type = 1;
	}
	
	public void markIsBoundAdd() {
		this.type = 2;
	}
	
	/*
	 * 确认这个轨迹点是不是crosslink
	 */
	public boolean isCrossLink() {
		return this.type == 1;
	}
	
	public boolean isBoundAdd() {
		return this.type == 1;
	}

	/*
	 * 过滤points中的真实的Point
	 */
	public static List<GPSPoint> filterRealPoint(List<GPSPoint> points) {
		List<GPSPoint> ret = new ArrayList<GPSPoint>();
		for (GPSPoint tmp : points) {
			if (false == tmp.isVirtualPoint()) {
				ret.add(tmp);
			}
		}
		return ret;
	}

	// 将gps中的point转化为GPSPoint点，有可能是一堆纪录
	public static List<GPSPoint> ParseGPSPoint(String point) {
		String tmp[] = null;
		tmp = point.split(",");
		
		List<GPSPoint> points = new ArrayList<GPSPoint>();
		// 真实point点完成
		GPSPoint realPoint = new GPSPoint();
		realPoint.timestamp = Double.parseDouble(tmp[0]);
		realPoint.lng = Double.parseDouble(tmp[1]);
		realPoint.lat = Double.parseDouble(tmp[2]);
		realPoint.distance = Double.parseDouble(tmp[4]);
		realPoint.speed = Double.parseDouble(tmp[8]);
		String tmpArr[] = tmp[3].split("\\|");
		List<String> links = new ArrayList<String>();
		//如果虚拟节点中有等于结尾节点的，就去掉这个虚拟节点
		String lastId = tmpArr[tmpArr.length-1];
		
		for (int i=0; i<tmpArr.length; i++) {
			if((i != tmpArr.length-1) && tmpArr[i].equals(lastId)) {
				continue;
			}
			links.add(tmpArr[i]);
		}
		realPoint.linkid = links.get(links.size() - 1);
		
		// 如果有多个点
		if (links.size() > 1) {
			int i = 0;
			for(i = 0; i < links.size() - 1; i++) {
				GPSPoint viaPoint = new GPSPoint();
				viaPoint.linkid = links.get(i);
				viaPoint.realPoint = realPoint;
				viaPoint.lat = realPoint.lat;
				viaPoint.lng = realPoint.lng;
				viaPoint.timestamp = realPoint.timestamp;
				viaPoint.speed = realPoint.speed;
				viaPoint.realLinks.addAll(links.subList(i, links.size()));
				
				points.add(viaPoint);
			}
		}
		points.add(realPoint);
		return points;
	}
	
	// 判断是否是虚拟点
	public boolean isVirtualPoint() {
		return this.realPoint != null;
	}
	
	// 获取真实的Point
	public GPSPoint realPoint() {
		if (this.realPoint != null) {
			return this.realPoint;
		}
		return this;
	}
	
	public void addCrossLinkPoint(GPSPoint point) {
		this.crossLinkPoints.add(point);
	}
	
	@Override
	public boolean equals(Object obj) {
	    if (obj == null) {
	        return false;
	    }
	    if (!GPSPoint.class.isAssignableFrom(obj.getClass())) {
	        return false;
	    }
	    final GPSPoint other = (GPSPoint) obj;
	    if (this.timestamp.equals(other.timestamp) && this.linkid.equals(other.linkid) && this.distance.equals(other.distance)) {
	    	return true;
	    }
	    return false;
	}
}
