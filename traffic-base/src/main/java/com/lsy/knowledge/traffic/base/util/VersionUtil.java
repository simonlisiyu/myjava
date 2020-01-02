package com.lsy.knowledge.traffic.base.util;

import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

// 处理version相关
public class VersionUtil {

	private FileSystem fs;

	private String roadNetPath = "/user/its_bi/flow_data/road_net/";

	private String mapTypeVersionPath = "/user/its_bi/warehouse/its.db/maptypeversion/maptypeversion.txt";

	/*
	 * 设置RoadNetPath
	 */
	public VersionUtil setRoadNetPath(String path) {
		this.roadNetPath = path;
		return this;
	}

	/*
	 * 设置文件系统
	 */
	public VersionUtil setFileSystem(FileSystem fs) {
		this.fs = fs;
		return this;
	}

	/*
	 * 设置maptypeversion的路径
	 */
	public VersionUtil setMapTypeVersionPath(String path) {
		this.mapTypeVersionPath = path;
		return this;
	}

	public String[] getOkVersions(String file)
	{
		FSDataInputStream in = null;
		List<String> ret = new ArrayList<String>();
		try {
			in = fs.open(new Path(file));
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String line = br.readLine();
			while (line != null) {
				ret.add(line);
				line = br.readLine();
			}
			in.close();
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
		String[] strarray = new String[ret.size()];
		ret.toArray(strarray);

		return strarray;
	}

	/*
	 * 获取符合的version
	 */
	public String getAvaiableVersion(String year, String month, String day) {

		// get ok version
		String[] okVersions = this.getOkVersions(this.roadNetPath + "/versions");
		try {
			// parse maptypeversions
			FSDataInputStream in = this.fs.open(new Path(this.mapTypeVersionPath));
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String line = br.readLine();
			line = br.readLine();
			while (line != null) {
				String[] tmps = line.split("\t");
				String time = tmps[0];
				String version = tmps[1];
				if (time.equals(year + "-" + month + "-" + day + " 00:00:00")) {
					return this.nearestVersion(version, okVersions);
				}
				line = br.readLine();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return this.nearestVersion(year + month + day + "00", okVersions);
	}

	/*
	 * 根据version，获取最近的版本
	 */
	public String nearestVersion (String version, String[] okVersions) {
		// 判断是否在这个okVersions里面
		for (String okVersion : okVersions) {
			if (okVersion.equals(version)) {
				return version;
			}
		}

		// 如果没有的话使用最近版本
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhh");
		Calendar cal = Calendar.getInstance();

		long min = cal.getTimeInMillis();
		String minVersion = "";
		try {
			cal.setTime(sdf.parse(version));

			for (String okVersion : okVersions) {

				Calendar caltmp = Calendar.getInstance();
				caltmp.setTime(sdf.parse(okVersion));
				caltmp.getTimeInMillis();

				long distance = Math.abs(caltmp.getTimeInMillis() - cal.getTimeInMillis());
				if (min > distance) {
					min = distance;
					minVersion = okVersion;
				}
			}

		} catch (ParseException e) {
			e.printStackTrace();
			return "";
		}

		return minVersion;
	}
}
