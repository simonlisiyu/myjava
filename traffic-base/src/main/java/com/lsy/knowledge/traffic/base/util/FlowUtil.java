/**
 * 
 */
package com.lsy.knowledge.traffic.base.util;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lsy.knowledge.traffic.base.domain.Flow;
import com.lsy.knowledge.traffic.base.domain.Link;
import com.lsy.knowledge.traffic.base.domain.Trie;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lsy 2017年2月20日
 */
public class FlowUtil implements Serializable {

	private static final long serialVersionUID = 8846034016279744162L;

	public static HashMap<String, Flow> getFlowFromFile(InputStream in) {
		HashMap<String, Flow> flows = new HashMap<String, Flow>();
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String line = br.readLine();
			String flow[] = null;
			while (line != null) {
				flow = line.split("\t");
				if (flow.length != 11) {
					System.out.println("illegal flow record, the length is "
							+ flow.length);
				} else if (flow[3].equals("true") || flow[3].equals("1")) {
					String key = flow[1] + "@" + flow[2];
					if (flows.containsKey(key)) {
						//System.out.println("已经存在这个flow了：" + line);
					}
					
					flows.put(key, new Flow(flow[0], flow[1], flow[2], flow[3],
									flow[4], flow[5].replaceAll("[|]", ""),
									flow[7], flow[8], flow[9],
									getLinkAttrs(flow[10])));
				}
				line = br.readLine();
			}
			System.out.println("flow条数一共有：" + flow.length);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return flows;
	}
	
	/*
	 * 根据文件获取Trie树；
	 */
	public static Trie getTrieFromFile(InputStream in, Map<String, Boolean> crossLinks) {
		Trie trie = new Trie();
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String line = br.readLine();
			String flow[] = null;
			int count = 0;
			while (line != null) {
				flow = line.split("\t");
				if (flow.length != 11) {
					System.out.println("illegal flow record, the length is "+ flow.length);
				} else if (flow[3].equals("true") || flow[3].equals("1")) {
					String linkIdStr = flow[5].replaceAll("[|]", "");
					String[] linkIds = linkIdStr.split(",");
					List<String> realLinkIds = new ArrayList<String>();
					for (String id : linkIds) {
						if (!crossLinks.containsKey(id)) {
							realLinkIds.add(id);
						}
					}
					flow[5] = String.join(",", realLinkIds);
 					Flow f = new Flow(flow[0], flow[1], flow[2], flow[3],
							flow[4], flow[5].replaceAll("[|]", ""),
							flow[7], flow[8], flow[9],
							getLinkAttrs(flow[10]));
					
 					if(f.getLinkAttr() == null) {
 						System.out.println(f.getLinkid1() + "@" + f.getLinkid2() + " is null linkAttr");
 					}
					trie.insert(f);
					count++;
				}
				line = br.readLine();
			}
			System.out.println("flow条数一共有：" + count);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return trie;
	}
	
	public static Trie getTrieFromFile(InputStream in, Map<String, Boolean> crossLinks, Map<String, Link> linkAttrs) {
		Trie trie = new Trie();
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String line = br.readLine();
				String flow[] = null;
			int count = 0;
			while (line != null) {
				//String newString = new String(line.toCharArray());
				flow = line.split("\t");
				String flow0 = new String(flow[0].toCharArray());
				String flow1 = new String(flow[1].toCharArray());
				String flow2 = new String(flow[2].toCharArray());
				String flow3 = new String(flow[3].toCharArray());
				String flow4 = new String(flow[4].toCharArray());
				String flow5 = new String(flow[5].toCharArray());
				String flow6 = new String(flow[6].toCharArray());
				String flow7 = new String(flow[7].toCharArray());
				String flow8 = new String(flow[8].toCharArray());
				String flow9 = new String(flow[9].toCharArray());
				if (flow3.equals("true") || flow3.equals("1")) {
					String linkIdStr = flow5.replaceAll("[|]", "");
					String[] linkIds = linkIdStr.split(",");
					List<String> realLinkIds = new ArrayList<String>();
					for (String id : linkIds) {
						if (!crossLinks.containsKey(id)) {
							realLinkIds.add(id);
						}
					}
					flow[5] = String.join(",", realLinkIds);
					String[] tmp = new String[realLinkIds.size()];
					realLinkIds.toArray(tmp);

 					Flow f = new Flow(flow0, flow1, flow2, flow3,
 							flow4, flow5,
 							flow7, flow8, flow9,
							getLinkAttrs(tmp, linkAttrs));
					
 					if(f.getLinkAttr() == null) {
 						System.out.println(f.getLinkid1() + "@" + f.getLinkid2() + " is null linkAttr");
 					}
					trie.insert(f);
					count++;
				}
				line = br.readLine();
			}
			br.close();
			System.out.println("flow条数一共有：" + count);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		Runtime run = Runtime.getRuntime();
		run.gc();
		return trie;
	}

	public static Trie getTrieFromCustomString(String context, Map<String, Boolean> crossLinks, Map<String, Link> linkAttrs) {
		Trie trie = new Trie();
		try {
			String[] lines = context.split(";");
			int count = 0;
			for(String line: lines) {
				String[] t = line.split(":");
				if (t.length != 3) {
					throw new Exception("format error");
				}

				List<String> realLinkIds = new ArrayList<String>();
				for (String id: t[1].split(",")) {
					if (!crossLinks.containsKey(id)) {
						realLinkIds.add(id);
					}
				}
				t[1] = String.join(",", realLinkIds);

				String id = t[0];
				String[] linkIdArrs = t[1].split(",");
				String junctionId = id;
				String linkid1 = linkIdArrs[0];
				String linkid2 = linkIdArrs[linkIdArrs.length-1];
				String type = "true";
				String length = "0";
				String linkids = t[1];
				String direction = "0";
				String turn = "0";
				String stage = "0";

				Map<String,Link> links = new HashMap<String, Link>();
				for (String l: linkIdArrs) {
					if (false == linkAttrs.containsKey(l)) {
						throw new Exception("linkAttr does not contain link: " + l);
					}
					links.put(l, linkAttrs.get(l));
				}
				Flow f = new Flow(junctionId, linkid1, linkid2, type, length, linkids, direction, turn, stage, links);
				//Flow截取的长度
				f.setTailLen(t[2]);

				trie.insert(f);
				count++;
			}

			System.out.println("flow条数一共有：" + count);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return trie;
	}

	/*
	 * 根据简易的文件获取Trie树
	 */
	public static Trie getTrieFromCustomFile(InputStream in, Map<String, Boolean> crossLinks, Map<String, Link> linkAttrs) {
		Trie trie = new Trie();
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String line = br.readLine();
			int count = 0;
			while (line != null) {
				String[] t = line.split("\t");
				if (t.length != 3) {
					throw new Exception("format error");
				}
				
				List<String> realLinkIds = new ArrayList<String>();
				for (String id: t[1].split(",")) {
					if (!crossLinks.containsKey(id)) {
						realLinkIds.add(id);
					}
				}
				t[1] = String.join(",", realLinkIds);
				
				String id = t[0];
				String[] linkIdArrs = t[1].split(",");
				String junctionId = id;
				String linkid1 = linkIdArrs[0];
				String linkid2 = linkIdArrs[linkIdArrs.length-1];
				String type = "true";
				String length = "0";
				String linkids = t[1];
				String direction = "0";
				String turn = "0";
				String stage = "0";
				
				Map<String,Link> links = new HashMap<String, Link>();
				for (String l: linkIdArrs) {
					if (false == linkAttrs.containsKey(l)) {
						throw new Exception("linkAttr does not contain link: " + l);
					}
					links.put(l, linkAttrs.get(l));
				}
				Flow f = new Flow(junctionId, linkid1, linkid2, type, length, linkids, direction, turn, stage, links);
				//Flow截取的长度
				f.setTailLen(t[2]);
				
				trie.insert(f);
				count++;
				line = br.readLine();
			}
			System.out.println("flow条数一共有：" + count);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return trie;
	}
	
	public static Map<String, Link> getLinkAttrs(String attrString) {
		Map<String, Link> links = new HashMap<String, Link>();
		if(!attrString.equals("null")){
			JSONArray array = JSONArray.parseArray(attrString);
			for (int i = 0 ; i < array.size(); i ++) {
				JSONObject jo = array.getJSONObject(i);
				links.put("" + jo.getLong("linkid"),
						new Link("" + jo.get("linkid"), "" + jo.get("len"), "", "" ));
			}
		}
		return links;
	}
	
	public static Map<String, Link> getLinkAttrs(String[] linkIds, Map<String, Link> linkAttrs) {
		Map<String, Link> links = new HashMap<String, Link>();
		for (String linkId : linkIds) {
			links.put(linkId, linkAttrs.get(linkId));
		}
		return links;
	}

}
