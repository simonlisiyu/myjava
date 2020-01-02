/**
 * 
 */
package com.lsy.knowledge.traffic.base.domain;

import java.io.Serializable;

/**
 * @author lsy
 * 2017年2月20日
 */
public class Link implements Serializable {

	private static final long serialVersionUID = -6017217064499461746L;
	private String linkid = "";
	private String length = "";
	private String snode = "";
	private String enode ="";
	
	private int type = 0; // 0 正常link/ 1 crosslink 
	
	public void markCrossLink() {
		this.type = 1;
	}
	
	public boolean isCrossLink() {
		return this.type == 1;
	}
	
	/**
	 * @param linkid
	 * @param length
	 * @param snode
	 * @param enode
	 */
	public Link(String linkid, String length, String snode, String enode) {
		super();
		this.linkid = linkid;
		this.length = length;
		this.snode = snode;
		this.enode = enode;
	}
	/**
	 * @return the linkid
	 */
	public String getLinkid() {
		return linkid;
	}
	/**
	 * @param linkid the linkid to set
	 */
	public void setLinkid(String linkid) {
		this.linkid = linkid;
	}
	/**
	 * @return the length
	 */
	public String getLength() {
		return length;
	}
	/**
	 * @param length the length to set
	 */
	public void setLength(String length) {
		this.length = length;
	}
	/**
	 * @return the snode
	 */
	public String getSnode() {
		return snode;
	}
	/**
	 * @param snode the snode to set
	 */
	public void setSnode(String snode) {
		this.snode = snode;
	}
	/**
	 * @return the enode
	 */
	public String getEnode() {
		return enode;
	}
	/**
	 * @param enode the enode to set
	 */
	public void setEnode(String enode) {
		this.enode = enode;
	}
	
	public Link(String linkAttr) {
		String[] tmps = linkAttr.split("\t");
		String linkId = tmps[0];
		String length = tmps[3];
		String snode = tmps[9];
		String enode = tmps[10];
		this.linkid = linkId;
		this.length = length;
		this.snode = snode;
		this.enode = enode;
	}
}
