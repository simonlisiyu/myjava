package com.lsy.knowledge.traffic.base.util;



import com.lsy.knowledge.traffic.base.domain.Link;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LinkUtil {
	
	/*
	 * 根据输入文件获取links的映射
	 */
	public static HashMap<String, Link> getLinkAttrs(InputStream in) {
		HashMap<String, Link> links = new HashMap<String, Link>();
		
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String line = br.readLine();
			line = br.readLine();
			while (line != null) {
				String[] tmps = line.split("\t");
				String linkId = tmps[0];
				String length = tmps[3];
				String snode = tmps[9];
				String enode = tmps[10];
				Link link = new Link(linkId, length, snode, enode);
				links.put(linkId, link);
				line = br.readLine();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return links;
	}

	/* 
	 * 根据输入文件获取links
	 */
	public static List<String> getLinks(InputStream in) {
		List<String> links = new ArrayList<String>();
		
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String line = br.readLine();
			while (line != null) {
				String[] tmps = line.split(",");
				for (String link : tmps) {
					links.add(link);
				}
				line = br.readLine();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return links;
	}
	
	
}
