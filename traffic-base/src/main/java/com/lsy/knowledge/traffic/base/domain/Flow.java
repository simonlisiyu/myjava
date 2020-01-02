/**
 * 
 */
package com.lsy.knowledge.traffic.base.domain;


import com.lsy.knowledge.traffic.base.util.*;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author lsy
 * 2017年2月13日
 */
public class Flow implements Serializable {

	private static final long serialVersionUID = -292742739389010967L;
	
	private String junctionid = "";
	private String linkid1 = "";
	private String linkid2 = "";
	//从停车点前截取tailLen长度的轨迹
	private String tailLen = "";
	private String type = "";
	private String length = "";
	private String linkids = "";
	private String direction = "";
	private String turn = "";
	private String stage = "";
	private Map<String,Link> links = new HashMap<String, Link>();
	
	// 从其他数据衍生出来的数据
	private double freeSpeed;
	
	/*
	 * 代表在flow上的一个点, 用路网的信息来代表
	 */
	public class LinkPoint implements Serializable {
		
		//private static final long serialVersionUID = -292742739389010967L;
		
		// 代表这个点所在的linkid
		String linkId;
		
		// 代表这个点所在linkid的距离
		double distance;
		
		/*
		 * 构造函数
		 */
		public LinkPoint(String linkId, double distance) {
			this.linkId = linkId;
			this.distance = distance;
		}
	}
	
	public Flow() {
		super();
	}
	
	public Flow(String s) {
		super();
		// 5119250 73760791    73760780    true    370 73760791,73760781,73760780  -90.000000  4   1   0   [{\"linkid\":73760791,\"len\":\"159\",\"snode\":5115353,\"enode\":5144752},{\"linkid\":73760781,\"len\":\"62\",\"snode\":5115354,\"enode\":5115353},{\"linkid\":73760780,\"snode\":5132116,\"enode\":5147996,\"next_linkids\":\"73759881,73882580,74062341,\",\"len\":149}]
		String[] tmps = s.split("\t");
		this.junctionid = tmps[0];
		this.linkid1 = tmps[1];
		this.linkid2 = tmps[2];
		this.type = tmps[3];
		this.length = tmps[4];
		this.linkids = tmps[5].replaceAll("[|]", "");
		this.direction = tmps[7];
		this.turn = tmps[8];
		this.stage = tmps[9];
		this.links = FlowUtil.getLinkAttrs(tmps[10]);
	}
	
	public Flow(String junctionid, String linkid1, String linkid2, String type,
                String length, String linkids, String direction, String turn, String stage, Map<String,Link> links) {
		super();
		this.junctionid = junctionid;
		this.linkid1 = linkid1;
		this.linkid2 = linkid2;
		this.type = type;
		this.length = length;
		this.linkids = linkids;
		this.direction = direction;
		this.turn = turn;
		this.stage = stage;
		this.links = links;
	}

	public Flow Copy() {
		return new Flow(this.junctionid,this.linkid1,this.linkid2,this.type, this.length, this.linkids, this.direction, this.turn, this.stage, this.links);
	}

	public String getJunctionid() {
		return junctionid;
	}
	
	public String getLinkid1() {
		return linkid1;
	}
	
	public String getLinkid2() {
		return linkid2;
	}
	
	public String getType(){
		return type;
	}
	
	public String getTailLen() {
		return tailLen;
	}
	
	public String getLength() {
		return length;
	}
	
	public String getLinkids() {
		return linkids;
	}
	
	public void setLinkids(String linkids) {
		this.linkids = linkids;
	}
	
	public String getDirection() {
		return direction;
	}
	
	public String getTurn() {
		return turn;
	}

	public String getStage() {
		return stage;
	}

	public Map<String, Link> getLinkAttr() {
		return links;
	}

	public void setLinkAttrs(Map<String, Link> links) {
		this.links = links;
	}
	
	public void setFreeSpeed(FreeUtil freeUtil) {
		String key = this.getStopLinkId() + "@" + this.getLinkid2();
		this.freeSpeed = freeUtil.getSpeedByLspeed(key);
	}
	
	public void setFreeSpeed(double freeSpeed) {
		this.freeSpeed = freeSpeed;
	}
	
	/**
	 * @param tailLen the tailLen to set
	 */
	public void setTailLen(String tailLen) {
		this.tailLen = tailLen;
	}

	public String getStopLinkId() {
		return this.getStopPoint().linkId;
	}
	
	/*
	 * 获取倒数第二个linkid
	 */
	public String getLastSecondLinkId() {
		String ids[] = this.linkids.split(",");
		return ids[ids.length-2];
	}


	/*
	 * 获取crossLink的个数
	 */
	public int getCrossLinkCount() {
		String ids[] = this.linkids.split(",");
		int count = 0;
		for (String id : ids) {
			if (this.getLink(id).isCrossLink()) {
				count++;
			}
		}
		return count;
	}
	
	
	/*
	 * 获取link
	 */
	public Link getLink(String linkId) {
		return this.links.get(linkId);
	}

	/*
	 * 获取上一个linkId
	 */
	public String preLinkId(String linkId) {
		String[] links = this.getAllLinks(this.getLinkid1(), this.getLinkid2());
		for (int i = 1; i < links.length; i++) {
			if (links[i].equals(linkId)) {
				return links[i-1];
			}
		}
		return null;
	}
	
	/*
	 * 获取下一个linkId
	 */
	public String nextLinkId(String linkId) {
		String[] links = this.getAllLinks(this.getLinkid1(), this.getLinkid2());
		for (int i = 0; i < links.length - 1; i++) {
			if (links[i].equals(linkId)) {
				return links[i+1];
			}
		}
		return null;
	}

	/*
	 * 调整轨迹点, 往前面250米增加一个轨迹点，往后面30米增加一个轨迹点，去掉所有的虚拟节点
	 */
	public List<GPSPoint> reorgPointsMainRoad(List<GPSPoint> points, List<GPSPoint> realPoints) throws Exception {
		// 从匹配的点拿到真实的点的轨迹
		int index1 = points.get(0).realPoint().realPointIndex;
		int index2 = points.get(points.size() - 1).realPoint().realPointIndex;
		if (points.get(points.size() - 1).isVirtualPoint()) {
			index2 = index2 - 1;
		}
		if (index1 > index2) {
			throw new Exception("index1 > index2");
		}

		List<GPSPoint> allRealPoints = realPoints.subList(index1, index2 + 1);

		// 防御：
		// 1 这个轨迹每个间隔不能超过10s，说明这个轨迹可以用
		double timeThreadHold = 10;
		double speedThreadHold = 30;
		for (int i = 1; i < allRealPoints.size(); i++) {
			if (allRealPoints.get(i).timestamp - allRealPoints.get(i-1).timestamp > timeThreadHold) {
				throw new Exception("timeThreadHold not ok: " + allRealPoints.get(i).timestamp.intValue());
			}

			// 如果两个点link是同一个，但是后一个distance小于前一个，说明有倒车现象，或者是路网映射问题，这种给去掉
			if (allRealPoints.get(i).linkid.equals(allRealPoints.get(i-1).linkid) &&
					allRealPoints.get(i).distance < allRealPoints.get(i-1).distance) {
				throw new Exception("reversing car" + allRealPoints.get(i).timestamp.toString());
			}

			double sp = GPSPointUtil.getSpeedByRealPoint(allRealPoints.get(i-1), allRealPoints.get(i), this.getLinkAttr());
			if (sp > speedThreadHold) {
				throw new Exception("speedThreadHold not ok: " + "real: " + sp + "\t"  + allRealPoints.get(i).timestamp.intValue());
			}
		}

		double dis = 0.0;
		double speed = 0.0;

		// 操作4: 最后一个link取30米，可以做个虚拟点
		// 判断最后一个link的长度
		LinkPoint endPoint = new LinkPoint(this.getLinkid2(), this.length(this.getLinkid2()));
		if (endPoint.distance > 30.0) {
			endPoint.distance = 30.0;
		}
		int left = this.prePoint(allRealPoints, endPoint.linkId, endPoint.distance);
		if (left == -1) {
			throw new Exception("end no left");
		}
		GPSPoint lpoint = allRealPoints.get(left);
		if (lpoint.next == null) {
			throw new Exception("end next is null");
		}
		GPSPoint rpoint = lpoint.next;
		if (rpoint.timestamp - lpoint.timestamp > timeThreadHold) {
			throw new Exception("end timeThreadHold not ok");
		}

		GPSPoint tailPoint = null;
		Double incrSec = 0.0;
		dis = this.getLinkDistance(lpoint.toLinkPoint(), endPoint);
		speed = GPSPointUtil.getSpeedByRealPoint(lpoint, rpoint, this.getLinkAttr());
		if (speed > speedThreadHold) {
			throw new Exception("end speedThreadHold not ok");
		}

		incrSec = dis/speed;
		if (incrSec > 0 && speed != 0) {
			tailPoint = new GPSPoint();
			tailPoint.linkid = endPoint.linkId;
			tailPoint.distance = endPoint.distance;
			tailPoint.timestamp = lpoint.timestamp + incrSec;
			tailPoint.speed = speed;
			tailPoint.markIsBoundAdd();
		}

		// 操作5：第一个link开始点之后取30米
		LinkPoint startPoint = null;
		Link firstLink = this.getLink(this.getLinkid1());
		if (Integer.parseInt(firstLink.getLength()) < 30) {
			startPoint = new LinkPoint(this.getLinkid1(), Integer.parseInt(firstLink.getLength()));
		} else {
			startPoint = new LinkPoint(this.getLinkid1(), 30);
		}
		// 获取这个的后一个节点
		int right2 = this.nextPoint(allRealPoints, startPoint);
		if (right2 == -1) {
			throw new Exception("start right2 not ok");
		}
		GPSPoint rpoint2 = allRealPoints.get(right2);
		if (rpoint2.pre == null) {
			throw new Exception("start right2 pre is null");
		}
		GPSPoint lpoint2 = rpoint2.pre;
		if (rpoint2.timestamp - lpoint2.timestamp > timeThreadHold) {
			throw new Exception("start timeThreadHold not ok");
		}

		dis = this.getLinkDistance(startPoint, allRealPoints.get(right2).toLinkPoint());
		speed = GPSPointUtil.getSpeedByRealPoint(lpoint2, rpoint2, this.getLinkAttr());
		if (speed > speedThreadHold) {
			throw new Exception("start speed not ok");
		}

		incrSec = dis/speed;
		GPSPoint headPoint = null;
		if (incrSec > 0 && speed != 0) {
			// 需要增加一个GPSPoint
			headPoint = new GPSPoint();
			headPoint.timestamp = rpoint2.timestamp - incrSec;
			headPoint.linkid = startPoint.linkId;
			headPoint.distance = startPoint.distance;
			headPoint.speed = speed;
			headPoint.markIsBoundAdd();
		}

		// 整理输出
		List<GPSPoint> ret = new ArrayList<GPSPoint>();
		if (headPoint != null) {
			ret.add(headPoint);
		}
		for (GPSPoint p : allRealPoints.subList(right2, left + 1)) {
			ret.add(p);
		}
		if (tailPoint != null) {
			ret.add(tailPoint);
		}
		return ret;
	}

	public List<GPSPoint> reorgPointsMainRoadReal(List<GPSPoint> points, List<GPSPoint> realPoints) throws Exception {
		// 从匹配的点拿到真实的点的轨迹
		int index1 = points.get(0).realPoint().realPointIndex;
		int index2 = points.get(points.size() - 1).realPoint().realPointIndex;
		if (points.get(points.size() - 1).isVirtualPoint()) {
			index2 = index2 - 1;
		}
		if (index1 > index2) {
			throw new Exception("index1 > index2");
		}

		List<GPSPoint> allRealPoints = realPoints.subList(index1, index2 + 1);

		// 防御：
		// 1 这个轨迹每个间隔不能超过10s，说明这个轨迹可以用
		double timeThreadHold = 10;
		double speedThreadHold = 30;
		for (int i = 1; i < allRealPoints.size(); i++) {
			if (allRealPoints.get(i).timestamp - allRealPoints.get(i-1).timestamp > timeThreadHold) {
				//throw new Exception("timeThreadHold not ok: " + allRealPoints.get(i).timestamp.intValue());
			}

			// 如果两个点link是同一个，但是后一个distance小于前一个，说明有倒车现象，或者是路网映射问题，这种给去掉
			if (allRealPoints.get(i).linkid.equals(allRealPoints.get(i-1).linkid) &&
					allRealPoints.get(i).distance < allRealPoints.get(i-1).distance) {
				throw new Exception("reversing car" + allRealPoints.get(i).timestamp.toString());
			}

			double sp = GPSPointUtil.getSpeedByRealPoint(allRealPoints.get(i-1), allRealPoints.get(i), this.getLinkAttr());
			if (sp > speedThreadHold) {
				// throw new Exception("speedThreadHold not ok: " + "real: " + sp + "\t"  + allRealPoints.get(i).timestamp.intValue());
			}
		}

		return allRealPoints;
	}

	public List<GPSPoint> reorgPointsCrossRoadReal(List<GPSPoint> points, List<GPSPoint> realPoints) throws Exception {
		// 从匹配的点拿到真实的点的轨迹
		int index1 = points.get(0).realPoint().realPointIndex;
		int index2 = points.get(points.size() - 1).realPoint().realPointIndex;
		if (points.get(points.size() - 1).isVirtualPoint()) {
			index2 = index2 - 1;
		}
		if (index1 > index2) {
			throw new Exception("index1 > index2");
		}

		List<GPSPoint> allRealPoints = realPoints.subList(index1, index2 + 1);

		// 防御：
		// 1 这个轨迹每个间隔不能超过10s，说明这个轨迹可以用
		double timeThreadHold = 10;
		double speedThreadHold = 30;
		for (int i = 1; i < allRealPoints.size(); i++) {
			if (allRealPoints.get(i).timestamp - allRealPoints.get(i-1).timestamp > timeThreadHold) {
				throw new Exception("timeThreadHold not ok: " + allRealPoints.get(i).timestamp.intValue());
			}

			// 如果两个点link是同一个，但是后一个distance小于前一个，说明有倒车现象，或者是路网映射问题，这种给去掉
			if (allRealPoints.get(i).linkid.equals(allRealPoints.get(i-1).linkid) &&
					allRealPoints.get(i).distance < allRealPoints.get(i-1).distance) {
				throw new Exception("reversing car" + allRealPoints.get(i).timestamp.toString());
			}

			double sp = GPSPointUtil.getSpeedByRealPoint(allRealPoints.get(i-1), allRealPoints.get(i), this.getLinkAttr());
			if (sp > speedThreadHold) {
				throw new Exception("speedThreadHold not ok: " + "real: " + sp + "\t"  + allRealPoints.get(i).timestamp.intValue());
			}
		}

		double dis = 0.0;
		double speed = 0.0;

		// 操作4: 最后一个link取30米，可以做个虚拟点
		// 判断最后一个link的长度
		LinkPoint endPoint = new LinkPoint(this.getLinkid2(), this.length(this.getLinkid2()));
		if (endPoint.distance > 30.0) {
			endPoint.distance = 30.0;
		}
		int left = this.prePoint(allRealPoints, endPoint.linkId, endPoint.distance);
		if (left == -1) {
			throw new Exception("end no left");
		}
		GPSPoint lpoint = allRealPoints.get(left);
		if (lpoint.next == null) {
			throw new Exception("end next is null");
		}
		GPSPoint rpoint = lpoint.next;
		if (rpoint.timestamp - lpoint.timestamp > timeThreadHold) {
			throw new Exception("end timeThreadHold not ok");
		}

		GPSPoint tailPoint = null;
		Double incrSec = 0.0;
		dis = this.getLinkDistance(lpoint.toLinkPoint(), endPoint);
		speed = GPSPointUtil.getSpeedByRealPoint(lpoint, rpoint, this.getLinkAttr());
		if (speed > speedThreadHold) {
			throw new Exception("end speedThreadHold not ok");
		}

		incrSec = dis/speed;
		if (incrSec > 0 && speed != 0) {
			tailPoint = new GPSPoint();
			tailPoint.linkid = endPoint.linkId;
			tailPoint.distance = endPoint.distance;
			tailPoint.timestamp = lpoint.timestamp + incrSec;
			tailPoint.speed = speed;
			tailPoint.markIsBoundAdd();
		}

		// 操作5: crossLink之前取250米，可以做个点
		// 获取stopLinkId
		LinkPoint stopPoint = this.getStopPoint();
		if (stopPoint == null) {
			throw new Exception("start stopPoint not ok");
		}
		// 获取距离stopLinkId截止位置前250米所在的linkid和距离
		LinkPoint startPoint = this.getPrePointByDistance(stopPoint, 250);
		// 如果没有的话，就使用第一个linkid和0
		if (startPoint == null) {
			startPoint = new LinkPoint(this.getLinkid1(), 0);
		}
		// 获取这个的后一个节点
		int right2 = this.nextPoint(allRealPoints, startPoint);
		if (right2 == -1) {
			throw new Exception("start right2 not ok");
		}
		GPSPoint rpoint2 = allRealPoints.get(right2);
		if (rpoint2.pre == null) {
			throw new Exception("start right2 pre is null");
		}
		GPSPoint lpoint2 = rpoint2.pre;
		if (rpoint2.timestamp - lpoint2.timestamp > timeThreadHold) {
			throw new Exception("start timeThreadHold not ok");
		}

		dis = this.getLinkDistance(startPoint, allRealPoints.get(right2).toLinkPoint());
		speed = GPSPointUtil.getSpeedByRealPoint(lpoint2, rpoint2, this.getLinkAttr());
		if (speed > speedThreadHold) {
			throw new Exception("start speed not ok\t realspeed is:" + speed + "\t prePoint:" + lpoint2.toString() + "\t startPoint:" + rpoint2.toString());
		}

		incrSec = dis/speed;
		GPSPoint headPoint = null;
		if (incrSec > 0 && speed != 0) {
			// 需要增加一个GPSPoint
			headPoint = new GPSPoint();
			headPoint.timestamp = rpoint2.timestamp - incrSec;
			headPoint.linkid = startPoint.linkId;
			headPoint.distance = startPoint.distance;
			headPoint.speed = speed;
			headPoint.markIsBoundAdd();
		}

		// 整理输出
		List<GPSPoint> ret = new ArrayList<GPSPoint>();
		if (headPoint != null) {
			ret.add(headPoint);
		}
		for (GPSPoint p : allRealPoints.subList(right2, left + 1)) {
			ret.add(p);
		}
		if (tailPoint != null) {
			ret.add(tailPoint);
		}
		return ret;
	}


	/*
	 * 调整轨迹点, 往前面250米增加一个轨迹点，往后面30米增加一个轨迹点，去掉所有的虚拟节点
	 */
	public List<GPSPoint> reorgPoints(List<GPSPoint> points, List<GPSPoint> realPoints) throws Exception {
		// 从匹配的点拿到真实的点的轨迹
		int index1 = points.get(0).realPoint().realPointIndex;
		int index2 = points.get(points.size() - 1).realPoint().realPointIndex;
		if (points.get(points.size() - 1).isVirtualPoint()) {
			index2 = index2 - 1;
		}
		if (index1 > index2) {
			throw new Exception("index1 > index2");
		}
		
		List<GPSPoint> allRealPoints = realPoints.subList(index1, index2 + 1);
		
		// 防御：
		// 1 这个轨迹每个间隔不能超过10s，说明这个轨迹可以用
		double timeThreadHold = 10;
		double speedThreadHold = 30;
		for (int i = 1; i < allRealPoints.size(); i++) {
			if (allRealPoints.get(i).timestamp - allRealPoints.get(i-1).timestamp > timeThreadHold) {
				throw new Exception("timeThreadHold not ok: " + allRealPoints.get(i).timestamp.intValue());
			}
			
			// 如果两个点link是同一个，但是后一个distance小于前一个，说明有倒车现象，或者是路网映射问题，这种给去掉
			if (allRealPoints.get(i).linkid.equals(allRealPoints.get(i-1).linkid) &&
					allRealPoints.get(i).distance < allRealPoints.get(i-1).distance) {
				throw new Exception("reversing car" + allRealPoints.get(i).timestamp.toString());
			}
			
			double sp = GPSPointUtil.getSpeedByRealPoint(allRealPoints.get(i-1), allRealPoints.get(i), this.getLinkAttr());
			if (sp > speedThreadHold) {
				throw new Exception("speedThreadHold not ok: " + "real: " + sp + "\t"  + allRealPoints.get(i).timestamp.intValue());
			}
		}
		
		double dis = 0.0;
		double speed = 0.0;
		
		// 操作4: 最后一个link取30米，可以做个虚拟点
		// 判断最后一个link的长度
		LinkPoint endPoint = new LinkPoint(this.getLinkid2(), this.length(this.getLinkid2()));
		if (endPoint.distance > 30.0) {
			endPoint.distance = 30.0;
		}
		int left = this.prePoint(allRealPoints, endPoint.linkId, endPoint.distance);
		if (left == -1) {
			throw new Exception("end no left");
		}
		GPSPoint lpoint = allRealPoints.get(left);
		if (lpoint.next == null) {
			throw new Exception("end next is null");
		}
		GPSPoint rpoint = lpoint.next;
		if (rpoint.timestamp - lpoint.timestamp > timeThreadHold) {
			throw new Exception("end timeThreadHold not ok");
		}
		
		GPSPoint tailPoint = null;
		Double incrSec = 0.0;
		dis = this.getLinkDistance(lpoint.toLinkPoint(), endPoint);
		speed = GPSPointUtil.getSpeedByRealPoint(lpoint, rpoint, this.getLinkAttr());
		if (speed > speedThreadHold) {
			throw new Exception("end speedThreadHold not ok");
		}
		
		incrSec = dis/speed;
		if (incrSec > 0 && speed != 0) {
			tailPoint = new GPSPoint();
			tailPoint.linkid = endPoint.linkId;
			tailPoint.distance = endPoint.distance;
			tailPoint.timestamp = lpoint.timestamp + incrSec;
			tailPoint.speed = speed;
			tailPoint.markIsBoundAdd();
		}
		
		// 操作5: crossLink之前取250米，可以做个点
		// 获取stopLinkId
		LinkPoint stopPoint = this.getStopPoint();
		if (stopPoint == null) {
			throw new Exception("start stopPoint not ok");
		}
		// 获取距离stopLinkId截止位置前250米所在的linkid和距离
		LinkPoint startPoint = this.getPrePointByDistance(stopPoint, 250);
		// 如果没有的话，就使用第一个linkid和0
		if (startPoint == null) {
			startPoint = new LinkPoint(this.getLinkid1(), 0);
		}
		// 获取这个的后一个节点
		int right2 = this.nextPoint(allRealPoints, startPoint);
		if (right2 == -1) {
			throw new Exception("start right2 not ok");
		}
		GPSPoint rpoint2 = allRealPoints.get(right2);
		if (rpoint2.pre == null) {
			throw new Exception("start right2 pre is null");
		}
		GPSPoint lpoint2 = rpoint2.pre;
		if (rpoint2.timestamp - lpoint2.timestamp > timeThreadHold) {
			throw new Exception("start timeThreadHold not ok");
		}
		
		dis = this.getLinkDistance(startPoint, allRealPoints.get(right2).toLinkPoint());
		speed = GPSPointUtil.getSpeedByRealPoint(lpoint2, rpoint2, this.getLinkAttr());
		if (speed > speedThreadHold) {
			throw new Exception("start speed not ok\t realspeed is:" + speed + "\t prePoint:" + lpoint2.toString() + "\t startPoint:" + rpoint2.toString());
		}
		
		incrSec = dis/speed;
		GPSPoint headPoint = null;
		if (incrSec > 0 && speed != 0) {
			// 需要增加一个GPSPoint
			headPoint = new GPSPoint();
			headPoint.timestamp = rpoint2.timestamp - incrSec;
			headPoint.linkid = startPoint.linkId;
			headPoint.distance = startPoint.distance;
			headPoint.speed = speed;
			headPoint.markIsBoundAdd();
		}
		
		// 整理输出
		List<GPSPoint> ret = new ArrayList<GPSPoint>();
		if (headPoint != null) {
			ret.add(headPoint);
		}
		for (GPSPoint p : allRealPoints.subList(right2, left + 1)) {
			ret.add(p);
		}
		if (tailPoint != null) {
			ret.add(tailPoint);
		}
		return ret;
	}

	public List<GPSPoint> reorgPointsCustom(List<GPSPoint> points, List<GPSPoint> realPoints) throws Exception {
		// 从匹配的点拿到真实的点的轨迹
		int index1 = points.get(0).realPoint().realPointIndex;
		int index2 = points.get(points.size() - 1).realPoint().realPointIndex;
		if (points.get(points.size() - 1).isVirtualPoint()) {
			index2 = index2 - 1;
		}
		if (index1 > index2) {
			throw new Exception("index1 > index2");
		}

		List<GPSPoint> allRealPoints = realPoints.subList(index1, index2 + 1);
		
		LinkPoint stopPoint = getUserSetStopPoint();
		if (stopPoint == null) {
			throw new Exception("start stopPoint not ok");
		}
		// 获取距离stopLinkId截止位置前250米所在的linkid和距离
		LinkPoint startPoint = this.getPrePointByDistance(stopPoint, Double.valueOf(getTailLen()));
		// 如果没有的话，就使用第一个linkid和0
		if (startPoint == null) {
			startPoint = new LinkPoint(this.getLinkid1(), 0);
		}
		// 获取这个的后一个节点
		int right2 = this.nextPoint(allRealPoints, startPoint);
		if (right2 == -1) {
			throw new Exception("start right2 not ok");
		}
		GPSPoint rpoint2 = allRealPoints.get(right2);
		if (rpoint2.pre == null) {
			throw new Exception("start right2 pre is null");
		}
		GPSPoint lpoint2 = rpoint2.pre;
		
		double timeThreadHold = 10;
		double speedThreadHold = 30;
		
		if (rpoint2.timestamp - lpoint2.timestamp > timeThreadHold) {
			throw new Exception("start timeThreadHold not ok");
		}
		
		double dis = this.getLinkDistance(startPoint, allRealPoints.get(right2).toLinkPoint());
		double speed = GPSPointUtil.getSpeedByRealPoint(lpoint2, rpoint2, this.getLinkAttr());
		if (speed > speedThreadHold) {
			throw new Exception("start speed not ok\t realspeed is:" + speed + "\t prePoint:" + lpoint2.toString() + "\t startPoint:" + rpoint2.toString());
		}
		
		double incrSec = dis/speed;
		GPSPoint headPoint = null;
		if (incrSec > 0 && speed != 0) {
			// 需要增加一个GPSPoint
			headPoint = new GPSPoint();
			headPoint.timestamp = rpoint2.timestamp - incrSec;
			headPoint.linkid = startPoint.linkId;
			headPoint.distance = startPoint.distance;
			headPoint.speed = speed;
			headPoint.markIsBoundAdd();
		}
		
		// 整理输出
		List<GPSPoint> ret = new ArrayList<GPSPoint>();
		if (headPoint != null) {
			ret.add(headPoint);
		}
		for (GPSPoint p : allRealPoints.subList(right2,allRealPoints.size() - 1)) {
			ret.add(p);
		}
		return ret;
	}

	public List<GPSPoint> reorgPointsMatch(List<GPSPoint> points, List<GPSPoint> realPoints) throws Exception {
		// 从匹配的点拿到真实的点的轨迹
		int index1 = points.get(0).realPoint().realPointIndex;
		int index2 = points.get(points.size() - 1).realPoint().realPointIndex;
		if (points.get(points.size() - 1).isVirtualPoint()) {
			index2 = index2 - 1;
		}
		if (index1 > index2) {
			throw new Exception("index1 > index2");
		}

		List<GPSPoint> allRealPoints = realPoints.subList(index1, index2 + 1);
		return allRealPoints;
	}



	public int prePoint(List<GPSPoint> points, LinkPoint point) {
		String linkId = point.linkId;
		double distance = point.distance;
		return this.prePoint(points, linkId, distance);
	}
	
	/*
	 * 获取这个flow上的某个点的下一个节点的index，如果没有则为－1, 如果point有这个点，返回最后一个出现的
	 */
	public int prePoint(List<GPSPoint> points, String linkid, double distance) {
		// 如果这个points有linkid的点
		int firstIndex = this.firstOccur(points, linkid);
		if (firstIndex != -1) {
			// 顺着找上一个
			int index = firstIndex;
			if (points.get(index).distance >= distance) {
				return index - 1;
			}
			// 顺着找下一个
			while(true) {
				if (index >= points.size()) {
					return index-1;
				}
				
				if (points.get(index).linkid.equals(linkid) && points.get(index).distance < distance) {
					index = index+1;
					continue;
				} else {
					return index - 1;
				}
			}
		}
		
		// 如果这个points没有linkid的点
		String preLinkId = this.preLinkId(linkid);
		if (preLinkId == null) {
			return -1;
		}
		return prePoint(points, preLinkId, this.length(preLinkId));
	}
	
	/*
	 * 获取这个flow上的某个点的下一个节点的index，如果没有则为－1
	 */
	public int nextPoint(List<GPSPoint> points, LinkPoint point) {
		String linkId = point.linkId;
		double distance = point.distance;
		return this.nextPoint(points, linkId, distance);
	}
	
	/*
	 * 获取这个flow上的某个点的下一个节点的index，如果没有则为－1, 如果points中有这个点了，取第一个出现的
	 */
	public int nextPoint(List<GPSPoint> points, String linkid, double distance) {
		// 如果这个points有linkid的点
		int firstIndex = this.firstOccur(points, linkid);
		if (firstIndex != -1) {
			// 顺着找下一个
			int index = firstIndex;
			if (points.get(index).distance > distance) {
				return index;
			} 
			// 顺着找下一个
			while(true) {
				if (index >= points.size()) {
					return -1; // 没有
				}
				
				if (!points.get(index).linkid.equals(linkid) || points.get(index).distance > distance) {
					break;
				}
				index = index + 1;
			}
			return index;
		}
		
		// 如果这个points没有linkid的点
		String nextLinkId = this.nextLinkId(linkid);
		if (nextLinkId == null) {
			return -1;
		}
		return nextPoint(points, nextLinkId, 0);
	}
	
	// 获取第一个出现linkid的下标
	private int firstOccur(List<GPSPoint> points, String linkid) {
		for (int i = 0; i < points.size(); i++) {
			if (points.get(i).linkid.equals(linkid)) {
				return i;
			}
		}
		return -1;
	}
	
	/*
	 * 获取停车线所在的linkid，也就是crossLink之前的那个linkid
	 */
	public LinkPoint getStopPoint() {
		String[] links = this.getAllLinks(this.getLinkid1(), this.getLinkid2());
		// 获取倒数第二个
		String lastSecond = links[links.length-2];
		if (this.getLink(lastSecond).isCrossLink() == false) {
			return new LinkPoint(lastSecond, this.length(lastSecond));
		}
		
		for (int i = links.length - 2; i >= 0; i--) {
			String linkId = links[i];
			if (this.getLink(linkId).isCrossLink() == false) {
				return new LinkPoint(linkId, this.length(linkId));
			}
		}
		return null;
	}

	/*
	获取最后一个link的点
	 */
	public LinkPoint getLastLinkPoint() {
		String[] links = this.getAllLinks(this.getLinkid1(), this.getLinkid2());
		String lastLink = links[links.length-1];
		return new LinkPoint(lastLink, 0);
	}
	
	public LinkPoint getUserSetStopPoint(){
		String[] linkIds = this.getLinkids().split(",");
		return new LinkPoint(linkIds[linkIds.length - 1], this.length(linkIds[linkIds.length - 1]));
	}
	
	/*
	 * 获取结束点之前的距离distance的点
	 */
	public LinkPoint getPrePointByDistance(LinkPoint endPoint, double distance) {
		
		if (endPoint.distance > distance) {
			return new LinkPoint(endPoint.linkId, endPoint.distance - distance);
		}
		distance = distance - endPoint.distance;
		LinkPoint p = endPoint;
		while(this.preLinkId(p.linkId) != null) {
			String linkId = this.preLinkId(p.linkId);
			if (this.length(linkId) > distance) {
				return new LinkPoint(linkId, this.length(linkId) - distance);
			} else {
				distance = distance - this.length(linkId);
				p = new LinkPoint(linkId, 0);
				continue;
			}
		}
		return null;
	}
	
	// 判断这段估计是否符合这个flow, 可能做适量裁剪，使用裁剪后的轨迹
	public List<GPSPoint> matchPoint(List<GPSPoint> points) {
		
		int start = 0;
		int end = points.size() - 1;
		
		/*
		 * 如果第一个节点是虚的点
		 * 对应的真实点必须在当前flow，这个轨迹才算
		 */
		GPSPoint first = points.get(start);
		if (first.isVirtualPoint()) {
			if (!this.links.containsKey(first.realPoint().linkid)) {
				return null;
			}
		}
		
		/*
		 * 如果最后一个点是虚的点
		 * 前面必须有一个实的节点
		 */
		GPSPoint last = points.get(end);
		if (last.isVirtualPoint()) {
			//看前面是否有非虚
			int tag = end - 1;
			if (tag == start) {
				return null;
			}
			while(tag > start) {
				if (!points.get(tag).linkid.equals(last.linkid)) {
					// TODO: 感觉这里还可以分几种情况，不过直接忽略比较好
					return null;
				}
				
				if (points.get(tag).linkid.equals(last.linkid) 
						&& (points.get(tag).isVirtualPoint() == false)) {
					return points.subList(start, tag + 1);
				}
				
				tag--;
			}
		}
		
		return points.subList(start, end + 1);
		
	}

	public void checkMaxStopTimeExceed(Map<String, Integer> cycles, List<GPSPoint> points) throws Exception {
		// 规则4: 单次最大停车时间不能超过周期的0.9
		// 获取推算出来的周期时间
		double start = points.get(0).realPoint().timestamp;
		SimpleDateFormat format =  new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
		String d = format.format((long)start * 1000);
		Date date = null;
		date = format.parse(d);

		String hour = String.format("%02d", date.getHours());
		String min  = String.format("%02d", (date.getMinutes() > 30 ? 30: 0));

		int cycle = CycleUtil.getCycle(cycles, this.junctionid, hour + ":" + min);

		if(this.maxStopTime(points) > cycle * 0.9) {
			throw new Exception("max stop time exceed:" + "real:" + this.maxStopTime(points) + " expect:" + cycle * 0.9 );
		}
	}

	public void checkMaxStopTimeExceed(CycleUtil cycleUtil, List<GPSPoint> points) throws Exception {
		// 规则4: 单次最大停车时间不能超过周期的0.9
		// 获取推算出来的周期时间
		double start = points.get(0).realPoint().timestamp;
		SimpleDateFormat format =  new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
		String d = format.format((long)start * 1000);
		Date date = null;
		date = format.parse(d);

		String hour = String.format("%02d", date.getHours());
		String min  = String.format("%02d", (date.getMinutes() > 30 ? 30: 0));
		
		int cycle = cycleUtil.getCycle(this.junctionid, hour + ":" + min);
		
		if(this.maxStopTime(points) > cycle * 0.9) {
			throw new Exception("max stop time exceed:" + "real:" + this.maxStopTime(points) + " expect:" + cycle * 0.9 );
		}
	}
	
	// 判断这个flow是否可用
	public void checkUseable() throws Exception {
		// 规则: flow的crossLink的个数不能超过4个
		if (this.getCrossLinkCount() > 6) {
			throw new Exception("flow rule: crosslink exceed");
		}
	}

	public void checkPointUseable(List<GPSPoint> points) throws Exception {
		// 规则1: 任意两个点中间打点时间不超过10s
		for (int i = 1; i< points.size(); i++) {
			if ((points.get(i).timestamp - points.get(i-1).timestamp) >= 10) {
				throw new Exception("flow rule: timestamp exceed 10");
			}
		}

		// 规则3: 时间不能小于0
		Double duration = points.get(points.size()-1).realPoint().timestamp - points.get(0).realPoint().timestamp;
		if (duration <= 0) {
			throw new Exception("flow rule: timestamp lte 0");
		}

		/*
		// 规则4: 单次最大停车时间不能超过周期的0.9
		int cycle = 200;
		// 获取推算出来的周期时间
		if(this.maxStopTime(points) > cycle * 0.9) {
			return false;
		}
		*/
	}
	
	/*
	 * 单次最大停车时间
	 */
	public double maxStopTime(List<GPSPoint> points) {
		// 过滤掉虚拟节点
		List<GPSPoint> realPoints = new ArrayList<GPSPoint>();
		for (GPSPoint p : points) {
			if (p.isVirtualPoint() == false) {
				realPoints.add(p);
			}
		}
		
		double maxTime = 0;
		double time = 0;
		for (int i = 1; i < realPoints.size(); i++) {
			if ((realPoints.get(i).linkid.equals(realPoints.get(i-1).linkid)) 
					&& (realPoints.get(i).distance.equals(realPoints.get(i-1).distance))) {
				time = time + realPoints.get(i).timestamp - realPoints.get(i-1).timestamp;
			} else {
				if (time > maxTime) {
					maxTime = time;
				}
				time = 0;
			}
		}
		if (time > maxTime) {
			maxTime = time;
		}
		
		return maxTime;
	}
	
	// 判断这段轨迹是否有停止
	public boolean hasStop(List<GPSPoint> points) {
		int i;
		GPSPoint lastPoint = new GPSPoint();
		for (i = 0; i < points.size(); i++) {
			/*
			 * 判断是否有停止
			 * 1 两个点都在同一个link上
			 * 2 两个点的距离小于2（3s都走不到2米）
			 */
			if (lastPoint.linkid.equals(points.get(i).linkid)  
					&& (points.get(i).distance - lastPoint.distance < 2)) {
				return true;
			}
			lastPoint = points.get(i);
		}
		return false;
	}
	
	/*
	 * 判断是否有这个link
	 */
	public boolean hasLink(String linkid) {
		return this.links.containsKey(linkid);
	}
	
	/*
	 * 根据起点linkid和终点linkid获取经过这些的link, 包含startLink和endLink
	 */
	public String[] getAllLinks(String startLink, String endLink)
	{
		boolean flag = false;
		List<String> links = new ArrayList<String>();
		String[] linkIds = this.getLinkids().split(",");
		for (int i = 0; i < linkIds.length; i++) {
			if (linkIds[i].equals(startLink)) {
				flag = true;
			}
			if (flag == true) {
				links.add(linkIds[i]);
			}
			if (linkIds[i].equals(endLink)) {
				flag = false;
				if (!links.contains(linkIds[i])) {
					links.add(linkIds[i]);
				}
				break;
			}
		}
		String[] ret = new String[links.size()];
		ret = links.toArray(ret);
		return ret;
	}
	
	// 获取某个link的长度
	public double length(String linkid) {
		if (!this.links.containsKey(linkid)) {
			System.out.println(this.linkid1 + "@" + this.linkid2 + " does not contain " + linkid);
			return 0.0;
		}
		return Double.parseDouble(this.links.get(linkid).getLength());
	}
	
	/*
	 * 获取两个link之间的距离
	 */
	public double getLinkDistance(String startLink, String endLink) {
		String[] links = this.getAllLinks(startLink, endLink);
		double distance = 0.0;
		for (String linkid : links) {
			distance = distance + this.length(linkid);
		}
		return distance;
	}

	/*
	 * 获取两个point的距离
	 */
	public double getLinkDistance(LinkPoint startPoint, LinkPoint endPoint) {
		return getLinkDistance(startPoint.linkId, startPoint.distance, endPoint.linkId, endPoint.distance);
	}

	
	public double getLinkDistance(String startLink, double startDistance, String endLink, double endDistance) {
		double distance = this.getLinkDistance(startLink, endLink);
		distance = distance - startDistance;
		distance = distance - this.length(endLink) + endDistance;
		return distance;
	}
	
	/*
	 * 使用另外一种方式计算points的长度，要求，points上的点都在这个flow里面
	 */
	public double gpsLength2(List<GPSPoint> points) {
		// 计算这个轨迹经过了哪些links
		GPSPoint first = points.get(0).realPoint();
		GPSPoint last = points.get(points.size() - 1).realPoint();
		if (this.hasLink(last.linkid) == false) {
			return 0.0;
		}
		
		String[] links = this.getAllLinks(first.linkid, last.linkid);
		double length = 0.0;
		int i = 0;
		for (i = 0; i < links.length - 1; i++) {
			length = length + this.length(links[i]);
		}
		// 加上尾部
		length = length + last.distance;
		// 去掉头部
		length = length - first.distance;
		return length;
	}
	
	public double gpsStopLength(GPSPoint point) {
		// 计算这个轨迹经过了哪些links
		GPSPoint first = point.realPoint();
		
		String[] links = this.getAllLinks(first.linkid, this.getLastSecondLinkId());
		double length = 0.0;
		int i = 0;
		for (i = 0; i < links.length; i++) {
			length = length + this.length(links[i]);
		}
		// 去掉头部
		length = length - first.distance;
		return length;
	}

	/*
	 * 根据GPSPoint获取指标,有tti值
	 */
	public IndexUtil.IndexResult getIndexResult(List<GPSPoint> linkPoints) {
		IndexUtil util = new IndexUtil();
		IndexUtil.IndexResult result = util.new IndexResult();
		
		LinkPoint stopLinkPoint = this.getStopPoint();
		LinkPoint lastLinkPoint = this.getLastLinkPoint();
		int preIndex = this.prePoint(linkPoints, stopLinkPoint.linkId, stopLinkPoint.distance);
		if (preIndex == -1) {
			return null;
		}

		int afterIndex = this.prePoint(linkPoints, lastLinkPoint.linkId, 0);
		if (afterIndex == -1) {
			return null;
		}
		
		result.beforeCount = this.StopCount(linkPoints.subList(0, preIndex + 1));
		result.afterCount = this.StopCount(linkPoints.subList(afterIndex, linkPoints.size()));
		if (result.beforeCount == 0) {
			result.beforeLength = 0.0;
		} else {
			result.beforeLength = this.StopLength(linkPoints);
		}
		// 计算时间
		result.duration =  linkPoints.get(linkPoints.size()-1).timestamp - linkPoints.get(0).timestamp;
		result.length = this.getLinkDistance(linkPoints.get(0).toLinkPoint(), linkPoints.get(linkPoints.size()-1).toLinkPoint());
		result.speed = result.length / result.duration;
		
		result.tti = result.duration / (result.length / this.freeSpeed);
		result.delay = result.duration - result.length / this.freeSpeed;
		if(result.delay < 0) {
			result.delay = 0;
		}
		result.free_speed = this.freeSpeed;
		
		return result;
	}

	/*
	 * 计算停车次数
	 */
	public int StopCount(List<GPSPoint> linkPoints)
	{
		int count = 0;
		boolean isNew = true;
		GPSPoint lastStopPoint = linkPoints.get(0);
		for(int i = 1; i < linkPoints.size(); i++) {
			GPSPoint curPoint = linkPoints.get(i);
			if (curPoint.isVirtualPoint() == true) {
				continue;
			}
			
			if (curPoint.isBoundAdd() == true) {
				continue;
			}

			GPSPoint lastPoint = linkPoints.get(i - 1);
			Double speed = this.getLinkDistance(lastPoint.toLinkPoint(), curPoint.toLinkPoint()) / (curPoint.timestamp - lastPoint.timestamp);

			// 如果什么都还没开始
			if (isNew == true) {
				// 如果出现了一次停止
				if (speed < 0.666) {
					// 两次停车之间的速度必须大于0.25才能算
					Double durationSpeed = this.getLinkDistance(lastStopPoint.toLinkPoint(), lastPoint.toLinkPoint()) / (lastPoint.timestamp - lastStopPoint.timestamp);
					if (durationSpeed > 2.5 || count == 0) {
						count++;
						isNew = false;
					}
				}
			} else {
				if (speed >= 0.666) {
					isNew = true;
					lastStopPoint = curPoint;
				}
			}
		}
		return count;
	}
	
	/*
	 * 计算最大停车距离
	 */
	public Double StopLength(List<GPSPoint> linkPoints)
	{
		LinkPoint stopLinkPoint = this.getStopPoint();
		
		for(int i = 1; i < linkPoints.size(); i++) {
			GPSPoint curPoint = linkPoints.get(i);
			GPSPoint lastPoint = linkPoints.get(i-1);
			if (curPoint.isVirtualPoint() == true) {
				continue;
			}
			
			if (curPoint.isBoundAdd() == true) {
				continue;
			}

			Double speed = this.getLinkDistance(lastPoint.toLinkPoint(), curPoint.toLinkPoint()) / (curPoint.timestamp - lastPoint.timestamp);

			// 如果出现了一次停止, 速度小于2/3米
			if (speed < 0.666) {
				return this.getLinkDistance(curPoint.toLinkPoint(), stopLinkPoint);
			}
		}
		return 0.0;
	}
	
}
