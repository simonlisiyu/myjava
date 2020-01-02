package com.lsy.knowledge.traffic.base.util;



import com.lsy.knowledge.traffic.base.domain.GPSPoint;
import com.lsy.knowledge.traffic.base.domain.Link;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GPSPointUtil {
	
	public class ParseResult {
		public String userId;
		public List<GPSPoint> points;
		public List<GPSPoint> realPoints;
	}
	
	/*
	 * 没有用户id，只有轨迹信息的解析
	 */
	public static GPSPointUtil.ParseResult parseData(String[] values, Map<String, Boolean> crossLinksHash) {
		// 记录节点，过滤掉crossLinks
		List<GPSPoint> points = new ArrayList<GPSPoint>(); // 过滤了crossLinks的节点
		List<GPSPoint> realPoints = new ArrayList<GPSPoint>(); // 真实的节点

		int realPointsIndex = 0;
		GPSPoint lastPoint = null;
		List<GPSPoint> viaPoints = new ArrayList<GPSPoint>();
		for (String point : values) {
			List<GPSPoint> links = GPSPoint.ParseGPSPoint(point);
			for (GPSPoint p : links) {
				// 如果是真实节点
				if (p.isVirtualPoint() == false) {
					p.realPointIndex = realPointsIndex;
					p.viaPoints = viaPoints;
					p.raw = point;

					realPoints.add(p);
					realPointsIndex++;
					viaPoints = new ArrayList<GPSPoint>();
				} else {
					viaPoints.add(p);
				}

				if (!crossLinksHash.containsKey(p.linkid)) {
					points.add(p);
				} else {
					if (lastPoint != null) {
						p.markIsCrossLink();
						lastPoint.addCrossLinkPoint(p);
						continue;
					}
				}
				lastPoint = p;
			}
		}

		// 对realPoints进行前后指针的设置
		for (int i = 0; i < realPoints.size() - 1; i++) {
			realPoints.get(i).next = realPoints.get(i+1);
		}
		for (int i = 1; i < realPoints.size(); i++) {
			realPoints.get(i).pre = realPoints.get(i-1);
		}

		GPSPointUtil util = new GPSPointUtil();

		ParseResult result = util.new ParseResult();
		result.userId = "";
		result.points = points;
		result.realPoints = realPoints;
		return result;
	}

	/*
	 * 原始完整轨迹信息的解析
	 */
	public static GPSPointUtil.ParseResult parseData(String value, Map<String, Boolean> crossLinksHash) {
		String[] record = value.toString().split(" |\t");
		
		// 长度必须为3
		if (record.length != 3) {
			System.out.println("illegel data, record.length error:  " + record.length);
			return null;
		}
		
		String userId = record[0];
		
		// 记录节点，过滤掉crossLinks
		String[] values =  record[2].split(";");
		ParseResult result = GPSPointUtil.parseData(values, crossLinksHash);
		result.userId = userId;
		return result;
	}
	
	
	/*
	 * 根据前后两个实体点获取两个点之间的距离
	 */
	public static Double getDistanceByRealPoint(GPSPoint pre, GPSPoint current, Map<String, Link> linkAttrs) throws Exception {
		if (pre.linkid.equals(current.linkid)) {
			return current.distance - pre.distance;
		}
		
		List<String> linkIds = new ArrayList<String>();
		
		String linkId = pre.linkid;
		if (linkAttrs.containsKey(linkId) == false) {
			throw new Exception("linkAttrs does not contain " + linkId);
		}
		
		Link link = linkAttrs.get(linkId);
		linkIds.add(linkId);
		
		Double distance = 0.0;
		distance += Double.parseDouble(link.getLength()) - pre.distance;
		
		// 对每个current的虚拟节点
		if (current.viaPoints != null) {
			for (GPSPoint viaPoint: current.viaPoints) {
				linkId = viaPoint.linkid;
				if (linkIds.contains(linkId)) {
					continue;
				}
				if (linkAttrs.containsKey(linkId) == false) {
					throw new Exception("linkAttrs does not contain " + linkId);
				}
				
				distance += Double.parseDouble(linkAttrs.get(viaPoint.linkid).getLength());
				linkIds.add (linkId);
			}
		}
		
		// 对最后一个长度
		linkId = current.linkid;
		if (linkAttrs.containsKey(linkId) == false) {
			throw new Exception("linkAttrs does not contain " + linkId);
		}
		distance += current.distance;
		return distance; 
	}
	
	/*
	 * 根据前后两个实体点获取两个点之间的速度
	 */
	public static Double getSpeedByRealPoint(GPSPoint pre, GPSPoint current, Map<String, Link> linkAttrs) throws Exception {
		double dis = GPSPointUtil.getDistanceByRealPoint(pre, current, linkAttrs);
		double time = current.timestamp - pre.timestamp;
		return dis/time;
	}
}
