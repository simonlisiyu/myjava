package com.lsy.knowledge.traffic.base.util;



import com.lsy.knowledge.traffic.base.domain.Flow;
import com.lsy.knowledge.traffic.base.domain.GPSPoint;

import java.io.Serializable;
import java.util.List;

/*
 * 这个类用于计算索引
 */
public class IndexUtil implements Serializable {

	private static final long serialVersionUID = -8386836093463972505L;

	
	public class IndexResult implements Serializable {

		private static final long serialVersionUID = -5561024481794874543L;
		// 停车次数
		public int beforeCount = 0;
		// 溢流停车次数
		public int afterCount = 0;
		// 停车距离
		public Double beforeLength = 0.0;
		// 过车时间
		public Double duration = 0.0;
		// 实际过车长度
		public double length = 0.0;
		// tti的值
		public double tti = 0.0;
		// 速度
		public double speed = 0.0;
		// 延误时间
		public double delay = 0.0;
		// 自由流速度
		public double free_speed = 0.0;
		
		/*
		 * 判断这个计算是否可行, 可行返回null，不行返回错误信息
		 */
		public void checkAvaiable() throws Exception
		{
			// 规则1: 速度计算大于25了，这个轨迹不能用了
			if (this.speed > 30) {
				throw new Exception("rule: speed > 30");
			}
			// 规则2: 延误时间大于15分钟，这个轨迹也是有问题的
			if (this.delay > 700) {
				throw new Exception("rule: delay > 700");
			}
			// 规则3: 速度小于等于0，这个轨迹不能用
			if (this.speed <= 0) {
				throw new Exception("rule: speed < 0");
			}

			if (Double.isNaN(this.speed)) {
				throw new Exception("rule: speed is Nan");
			}

			if (Double.isNaN(this.tti)) {
				throw new Exception("rule: tti is Nan");
			}

		}
		
	}
	
	/*
	 * 根据GPSPoint获取指标,有tti值
	 */
	public IndexResult getIndexResult(List<GPSPoint> linkPoints, Flow flow, double free_speed)	{
		IndexResult result = this.getIndexResult(linkPoints, flow);
		result.tti = result.duration / (result.length / free_speed);
		result.delay = result.duration - result.length / free_speed;
		result.free_speed = free_speed;
		
		return result;
	}
	
	/*
	 * 根据GPSPoint获取指标
	 */
	public IndexResult getIndexResult(List<GPSPoint> linkPoints, Flow flow)	{
		IndexResult result = new IndexResult();
		
		// 中止link的前一个就是我们的停止线link
		String lastLinkId = flow.getLinkid2();
		int stopI = 0;
		for (int i = linkPoints.size() - 1; i >= 0; i--) {
			if (false == linkPoints.get(i).linkid.equals(lastLinkId)) {
				stopI = i;
				break;
			}
		}
		
		result.beforeCount = this.StopCount(linkPoints.subList(0, stopI + 1));
		List<GPSPoint> links  = linkPoints.get(stopI).crossLinkPoints;
		links.addAll(linkPoints.subList(stopI + 1, linkPoints.size()));
		result.afterCount = this.StopCount(links);
		if (result.beforeCount == 0) {
			result.beforeLength = 0.0;
		} else {
			result.beforeLength = this.StopLength(linkPoints, flow);
		}
		// 计算时间
		result.duration =  linkPoints.get(linkPoints.size()-1).realPoint().timestamp - linkPoints.get(0).realPoint().timestamp;
		result.length = flow.gpsLength2(linkPoints);
		result.speed = result.length / result.duration;
		
		return result;
	}
	
	
	/*
	 * 计算停车次数
	 */
	public int StopCount(List<GPSPoint> linkPoints)
	{
		int count = 0;
		GPSPoint lastPoint = null;
		boolean isNew = true;
		for(int i = 0; i < linkPoints.size(); i++) {
			GPSPoint curPoint = linkPoints.get(i);
			if (curPoint.isVirtualPoint() == true) {
				continue;
			}
			
			if (lastPoint == null) {
				lastPoint = curPoint;
				isNew = true;
				continue;
			}
			
			// 如果什么都还没开始
			if (isNew == true) {
				// 如果出现了一次停止
				if (curPoint.linkid.equals(lastPoint.linkid) && (curPoint.distance - lastPoint.distance < 2)) {
					count++;
					isNew = false;
					lastPoint = curPoint;
					continue;
				} else {
					lastPoint = curPoint;
					continue;
				}
			} else {
				if ((false == curPoint.linkid.equals(lastPoint.linkid)) || (curPoint.distance - lastPoint.distance > 2)) {
					isNew = true;
					lastPoint = curPoint;
					continue;
				} else {
					lastPoint = curPoint;
					continue;
				}
			}
		}
		return count;
	}
	
	/*
	 * 计算最大停车距离
	 */
	public Double StopLength(List<GPSPoint> linkPoints, Flow flow)
	{
		List<GPSPoint> realPoints = GPSPoint.filterRealPoint(linkPoints);
		
		for(int i = 1; i < realPoints.size(); i++) {
			GPSPoint curPoint = realPoints.get(i);
			GPSPoint lastPoint = realPoints.get(i-1);
			
			// 如果出现了一次停止
			if (curPoint.linkid.equals(lastPoint.linkid) && (curPoint.distance - lastPoint.distance < 2)) {
				// 计算停车点到终点的距离
				return flow.gpsStopLength(curPoint);
			}
		}
		return 0.0;
	}
}
