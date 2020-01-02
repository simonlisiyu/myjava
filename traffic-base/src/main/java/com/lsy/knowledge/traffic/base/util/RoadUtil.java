package com.lsy.knowledge.traffic.base.util;

import com.lsy.knowledge.traffic.base.domain.Link;
import com.lsy.knowledge.traffic.base.domain.Trie;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * 代表路网版本
 */
public class RoadUtil implements Serializable {

	private static final long serialVersionUID = -8970773787285938164L;
	
	private String roadNetPath = "/user/its_bi/flow_data/road_net/";
	
	private String mapTypeVersionPath = "/user/its_bi/warehouse/its.db/maptypeversion/maptypeversion.txt";
	
	// 设置flow的路径，如果不设置，默认road_net下面的文件
	private String flowPath = "";
	
	private FileSystem fs;

	// 设置对应的路网版本
	private String version;

	// 对应的城市
	private String city;

	// 当前版本的crossLink
	private Map<String, Boolean> crossLinksHash;

	// 当前版本的linkAttrs
	private Map<String, Link> linkAttrs;

	// 当前版本的全城flow构造的trie树
	private Trie trie;

	/*
	设置当前版本
	 */
	public RoadUtil setVersion(String version) {
		this.version = version;
		return this;
	}

	/*
	设置当前城市
	 */
	public RoadUtil setCity(String city) {
		this.city = city;
		return this;
	}

	/*
	 * 设置RoadNetPath
	 */
	public RoadUtil setRoadNetPath(String path) {
		this.roadNetPath = path;
		return this;
	}
	
	/*
	 * 设置文件系统
	 */
	public RoadUtil setFileSystem(FileSystem fs) {
		this.fs = fs;
		return this;
	}
	
	/*
	 * 设置maptypeversion的路径
	 */
	public RoadUtil setMapTypeVersionPath(String path) {
		this.mapTypeVersionPath = path;
		return this;
	}
	
	/*
	 * 设置flow path
	 */
	public RoadUtil setFlowPath(String path) {
		this.flowPath = path;
		return this;
	}
	
	/*
	 * 获取crossLinkHash
	 */
	public Map<String, Boolean> getCrossLinkHash() throws Exception {
		if (this.crossLinksHash != null) {
			return this.crossLinksHash;
		}

		// 计算crossLinks
		Map<String, Boolean> crossLinksHash = new HashMap<String, Boolean>();
		// /user/its_bi/flow_data/road_net/2017080317/cross_link/crosslink_99
		String linkFile = this.roadNetPath + "/" + version + "/cross_link/crosslink_" + city;
		if (false == this.fs.isFile(new Path(linkFile))) {
			throw new Exception("no cross.linkFile version:" + linkFile);
		}
		FSDataInputStream in = fs.open(new Path(linkFile));
		System.out.println("load cross.link file:" + linkFile);
		List<String> links = LinkUtil.getLinks(in);
		for(String linkId : links) {
			crossLinksHash.put(linkId, true);
		}
		
		in.close();
		this.crossLinksHash = crossLinksHash;
		System.out.println("cross links num:" + crossLinksHash.size());
		return crossLinksHash;
	}
	
	/*
	 * 获取crossLinkHash
	 */
	public Trie getFlowTrie(String flowType) throws Exception {
		if (this.trie != null) {
			return this.trie;
		}
		// 计算flow
		Map<String, Link> linkAttrs = this.getLinkAttrs();
		
		// /user/its_bi/flow_data/road_net/2017080317/all_city_flow/flow_city_99_hdfs
		String flowFile = this.roadNetPath + "/" + version + "/flow_city/flow_city_" + city + "_hdfs";
		if (this.flowPath.equals("") == false) {
			flowFile = this.flowPath;
		}
		FSDataInputStream in = this.fs.open(new Path(flowFile));
		System.out.println("load flow file:" + flowFile);
		Trie trie = null;
		if (flowType.equals("crossroad") || flowType.equals("mainroad")) {
			trie = FlowUtil.getTrieFromFile(in, crossLinksHash, linkAttrs);
		} else if (flowType.equals("custom") || flowType.equals("match")) {
			trie = FlowUtil.getTrieFromCustomFile(in, crossLinksHash, linkAttrs);
		}

		in.close();
		this.trie = trie;
		return trie;
	}
	
	/*
	 * 获取这个城市的所有Link信息
	 */
	public Map<String, Link> getLinkAttrs() throws Exception {
		if (this.linkAttrs != null) {
			return this.linkAttrs;
		}

		String linkAttrFile = this.roadNetPath + "/" + version + "/link_attr/link_attr_" + city;
		if (false == this.fs.isFile(new Path(linkAttrFile))) {
			throw new Exception("no linkFile version");
		}
		FSDataInputStream linkAttrIn = null;
		linkAttrIn = fs.open(new Path(linkAttrFile));
		System.out.println("load attr.link file:" + linkAttrFile);
		Map<String, Link> linkAttrs = LinkUtil.getLinkAttrs(linkAttrIn);
		linkAttrIn.close();
		this.linkAttrs = linkAttrs;
		return linkAttrs;
	}

	public RealTimeUtil toRealTimeUtil()
	{
		RealTimeUtil util = new RealTimeUtil();
		util.setCrossLinksHash(this.crossLinksHash);
		util.setLinkAttrs(this.linkAttrs);
		util.setTrie(this.trie);
		return util;
	}

}
