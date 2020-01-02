package com.lsy.knowledge.traffic.base.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/*
 * 分析自由流的文件
 */
public class FreeUtil implements Serializable {
	
	private static final long serialVersionUID = -5782696486838602217L;

	HashMap<String, Free> lfrees = null; // 这个的key是link_lsecond@link2
	
	/*
	 * 表示一个自由流节点
	 */
	public class Free implements Serializable {

		private static final long serialVersionUID = -7368919334959410374L;
		
		private String stopLinkId;  // 进路口的最后一个link
		private String linkId2; // 出路口的第一个link（也是最后一个link）
		private Double speed; // 速度
		private int count; // 统计个数
		private String junctionId; //路口id
		
		public Free(String stopLinkId, String linkId2, Double speed, int count, String junctionId) {
			this.stopLinkId = stopLinkId;
			this.linkId2 = linkId2;
			this.speed = speed;
			this.count = count;
			this.junctionId = junctionId;
		}
		
		public Double getSpeed()
		{
			return this.speed;
		}
		
		public Integer getCount()
		{
			return this.count;
		}
		
		public String getStopLinkId()
		{
			return this.stopLinkId;
		}
		
		public String getLinkId2() {
			return linkId2;
		}

		public String getJunctionId() {
			return junctionId;
		}
		
		/*
		 * 判断：
		 * 1 count是大于20
		 * 2 speed大于6小于20
		 */
		public boolean isAvaiable(){
			if (this.speed > 25 || this.speed < 6) {
				return false;
			}
			return true;
		}
	}
	
	/*
	 * 解析输入文件导出
	 */
	public FreeUtil() {
		this.lfrees = new HashMap<String, Free>();
	}
	
	/*
	 * 解析输入文件加入到map中
	 */
	public void parseFreeFile(InputStream in) {
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String line = br.readLine();
			String tmps[] = null;
			while (line != null) {
				tmps = line.split("\t");
				
				String[] links = tmps[0].split("@");
				String stopLinkId = links[0];
				String linkId2 = links[1];
				Double speed = Double.parseDouble(tmps[1]);
				Integer count = Integer.parseInt(tmps[2]);
				String junctionId = tmps[3];
				
				Free free = new Free(stopLinkId, linkId2, speed, count, junctionId);
				this.lfrees.put(tmps[0], free);
				line = br.readLine();
			}
			System.out.println("free条数一共有：" + this.lfrees.size());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}


	public void parseFreeRealTime(InputStream in) {
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String line = br.readLine();
			String tmps[] = null;
			Integer c = 0;
			while (line != null) {
				tmps = line.split("\t");

				String junctionId = tmps[0];
				String stopLinkId = tmps[1];
				String linkId2 = tmps[2];
				Double speed = Double.parseDouble(tmps[5]);
				Integer count = Integer.parseInt(tmps[4]);

				Free free = new Free(stopLinkId, linkId2, speed, count, junctionId);
				this.lfrees.put(stopLinkId + "@" + linkId2, free);
				line = br.readLine();
				c++;
			}
			System.out.println("free条数一共有：" + c);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	
	/*
	 * 根据停车线前和后的linkId的组合获取自由流速度
	 */
	public Double getSpeedByLspeed(String key) {
		if (this.lfrees.containsKey(key)) {
			Free free = this.lfrees.get(key);
			if (free.isAvaiable()) {
				return free.getSpeed();
			}
		}
		return 15.0;
	}
	
	/*
	 * 获取free
	 */
	public Free getFree(String stopLinkId, String linkId2) {
		String key = stopLinkId + "@" + linkId2;
		if (!this.lfrees.containsKey(key)) {
			return null;
		}
		return this.lfrees.get(key);
	}
	
	
	/*
	 * 更新Free
	 */
	public void SetFree(String stopLinkId, String linkId2, double speed, int count, String junctionId) {
		String key = stopLinkId + "@" + linkId2;
		if (this.getFree(stopLinkId, linkId2) == null) {
			Free free = new Free(stopLinkId, linkId2, speed, count, junctionId);
			this.lfrees.put(key, free);
			return;
		}
		
		boolean useOld = true;
		Free oldFree = this.getFree(stopLinkId, linkId2);
		if(count > 50 || oldFree.getCount() < count) {
			useOld = false;
		}
		if (useOld == false) {
			Free free = new Free(stopLinkId, linkId2, speed, count, junctionId);
			this.lfrees.put(key, free);
			return;
		}
	}
	
	public Map<String, Free> getFrees() {
		return this.lfrees;
	}
	
}
