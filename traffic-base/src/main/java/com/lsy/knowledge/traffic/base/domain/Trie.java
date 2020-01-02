package com.lsy.knowledge.traffic.base.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/*
 * Trie树
 */
public class Trie implements Serializable {

	private static final long serialVersionUID = 5017396586492724808L;
	private Node root;
	
	public Node getRoot()
	{
		return this.root;
	}
	
	public class Node implements Serializable {

		private static final long serialVersionUID = 624991289393346342L;

		/*
		 * 当前节点的下面节点
		 */
		private List<Node> childs;
		
		/*
		 * 当前节点代表的linkId
		 */
		private String linkId;
		
		/*
		 * 当前节点的flow数据，如果不是叶子节点，就不会有flow，这个为空
		 */
		private Flow flow;
		
		/*
		 * 判断是否是叶子节点
		 */
		public boolean isLeaf()
		{
			return this.flow != null;
		}
		
		/*
		 * 创建一个非叶子节点的node
		 */
		public Node(String linkId)
		{
			this.linkId = linkId;
			this.childs = new ArrayList<Node>();
		}
		
		/*
		 * 创建一个叶子节点的node
		 */
		public Node(String linkId, Flow flow)
		{
			this.linkId = linkId;
			this.flow = flow;
			this.childs = new ArrayList<Node>();
		}
		
		/*
		 * 将数据node打印出来
		 */
		public void append(Node node) 
		{
			this.childs.add(node);
		}
		
		public String getLinkId()
		{
			return this.linkId;
		}
		
		public List<Node> getChilds()
		{
			return this.childs;
		}
		
		public Flow getFlow()
		{
			return this.flow;
		}
		
		/*
		 * 判断一个linkId是否是这个node的子节点
		 */
		public Node getChild(String linkId)
		{
			for(Node node : this.childs) {
				if (linkId.equals(node.linkId)) {
					return node;
				}
			}
			return null;
		}
	}
	
	/*
	 * 初始化
	 */
	public Trie()
	{
		this.root = new Node("");
	}
	
	// 插入Trie树
	public void insert(Flow flow)
	{
		Node cur = this.root;
		List<String> linkIds = new ArrayList<String>(Arrays.asList(flow.getLinkids().split(",")));
		String lastLinkId = linkIds.get(linkIds.size() - 1);
		
		for (String linkId : linkIds) {
			
			Node child = cur.getChild(linkId);
			// 已经有了这个子树了
			if (child != null) {
				cur = child;
				continue;
			}
			
			// 还没这个子树，加上
			if(linkId.equals(lastLinkId)) {
				child = new Node(linkId, flow);
			} else {
				child = new Node(linkId);
			}
			cur.append(child);
			cur = child;
		}
	}
	
	
	public class SearchResult {
		
		public Flow flow;
		public List<GPSPoint> points;
			
		/*
		 * 根据points调整flow
		 */
		public SearchResult adjustFlow(Map<String, Link> linkAttrs, Map<String, Boolean> crossLinks) {
			List<String> linkIds = new ArrayList<String>();
			
			for (GPSPoint point: points) {
				
				if (!linkIds.contains(point.linkid)) {
					linkIds.add(point.linkid);
				}
				
				if (point.linkid.equals(this.flow.getLinkid2())) {
					break;
				}
				
				if (point.crossLinkPoints != null) {
					for (GPSPoint p: point.crossLinkPoints) {
						if (!linkIds.contains(p.linkid)) {
							linkIds.add(p.linkid);
						}
					}
				}
			}

			this.flow = this.flow.Copy();

			// 这里得到了linkIds
			this.flow.setLinkids(String.join(",", linkIds));
			this.flow.setLinkAttrs(linkAttrs);
			return this;
		}
	}
	
	/*
	 * 搜索起点为flow的起点的GPSPoint轨迹对应的flow
	 */
	public SearchResult searchBegin(List<GPSPoint> points)
	{
		GPSPoint first = points.get(0);
		
		// 判断起点是否在trie树第一级
		Node curNode = this.root.getChild(first.linkid);
		if (curNode == null) {
			return null;
		}
		
		SearchResult result = new SearchResult();
		
		int start = 0;
		int end = start + 1;
		// 查找结尾的end
		for (end = start + 1; end < points.size(); end++) {
			
			// 如果end和前一个相同，则跳过，去掉重复的，这样只有获取第一个不同的。
			if (points.get(end).linkid.equals(points.get(end-1).linkid)) {
				continue;
			}
			
			// 判断是否是符合flow的子孩子
			Node child = curNode.getChild(points.get(end).linkid);
			if(child == null) {
				return null;
			}
			
			curNode = child;
			if (curNode.isLeaf()) {
				// flow就找出来了
				result.flow = curNode.getFlow();
				break;
			}
		}
		
		// 这个是必要的，因为有可能轨迹都遍历完了还没有
		if (result.flow == null) {
			return null;
		}
		
		// end再延生到同样的link的最后一个，最后一个轨迹点一定是真实轨迹点
		while(end < points.size() - 1) {
			if (points.get(end).linkid.equals(points.get(end+1).linkid)) {
				end = end+1;
			} else {
				break;
			}
		}
		result.points = points.subList(start, end + 1);
		
		return result;
	}
	
	/*
	 * 非贪婪模式，取第一个点就可以
	 */
	public SearchResult nonGreedySearchBegin(List<GPSPoint> points)
	{
		GPSPoint first = points.get(0);
		
		// 判断起点是否在trie树第一级
		Node curNode = this.root.getChild(first.linkid);
		if (curNode == null) {
			return null;
		}
		
		SearchResult result = new SearchResult();
		
		int start = 0;
		int end = start + 1;
		// 查找结尾的end
		for (end = start + 1; end < points.size(); end++) {
			
			// 如果end和前一个相同，则跳过，去掉重复的，这样只有获取第一个不同的。
			if (points.get(end).linkid.equals(points.get(end-1).linkid)) {
				continue;
			}
			
			// 判断是否是符合flow的子孩子
			Node child = curNode.getChild(points.get(end).linkid);
			if(child == null) {
				return null;
			}
			
			curNode = child;
			if (curNode.isLeaf()) {
				// flow就找出来了
				result.flow = curNode.getFlow();
				break;
			}
		}
		
		// 这个是必要的，因为有可能轨迹都遍历完了还没有
		if (result.flow == null) {
			return null;
		}
		result.points = points.subList(start, end + 1);
		return result;
	}
}
