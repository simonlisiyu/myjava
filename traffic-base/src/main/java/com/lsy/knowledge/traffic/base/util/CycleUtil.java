package com.lsy.knowledge.traffic.base.util;

import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class CycleUtil implements Serializable {

	private static final long serialVersionUID = -6017217064499461747L;

	private String city;
	
	private FileSystem fs;
	
	public Map<String, Integer> cycles;  // 这里的key是junction@hour，比如123@09:00
	
	private String file = null;
	
	public CycleUtil(String city) {
		this.city = city;
	}
	
	public CycleUtil SetFile (String file) {
		this.file = file;
		return this;
	}
	
	/*
	 * 设置文件系统
	 */
	public CycleUtil setFileSystem(FileSystem fs) {
		this.fs = fs;
		return this;
	}
	
	/*
	 * 读取cycle
	 */
	public void loadCycleFile(String year, String month, String day) throws IOException, ParseException {
		// 获取读取这天的前一天的
		SimpleDateFormat format =  new SimpleDateFormat("yyyyMMdd");
		Calendar cal = Calendar.getInstance();
		cal.set(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day));
		// 周一和周六我就不用前天的数据
		if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
			cal.add(Calendar.DAY_OF_YEAR, -3);
		} else if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
			cal.add(Calendar.DAY_OF_YEAR, -7);
		} else {
			cal.add(Calendar.DAY_OF_YEAR, -1);
		}
		
		String date = format.format(cal.getTime());
		
		String file = "/user/its_bi/cycle_estimation_i/runall/" + this.city + "/5_" + date + "/cycle/result.txt";
		if (this.file != null) {
			file = this.file;
		}
		if (!this.fs.exists(new Path(file))) {
			return;
		}
		System.out.println("load cycle file:" + file);
		
		this.cycles = new HashMap<String, Integer>();
		BufferedReader br = new BufferedReader(new InputStreamReader(this.fs.open(new Path(file))));
		String line = br.readLine();
		int count = 0;
		while (line != null) {
			String[] tmps = line.split(" ");
			String junctionId = tmps[1];
			String timeDuration = tmps[2];
			Integer cycle = Integer.parseInt(tmps[3]);
			String[] times = timeDuration.split(":");
			String hour = times[0] + ":" + times[1];
			
			String key = junctionId + "@" + hour;
			this.cycles.put(key, cycle);
			count++;
			line = br.readLine();
		}
		System.out.println("load cycle lines:" + count);
	}

	/*
	这个函数是为了可以使用broadcase临时使用
	 */
	public static Integer getCycle(Map<String, Integer> cycles, String junctionId, String hour) {
		String key = junctionId + "@" + hour;
		if (cycles != null && cycles.containsKey(key)) {
			return cycles.get(key);
		}
		return 200;
	}
	
	/*
	 * 获取cycle
	 */
	public Integer getCycle(String junctionId, String hour) {
		String key = junctionId + "@" + hour;
		if (this.cycles != null && this.cycles.containsKey(key)) {
			return this.cycles.get(key);
		}
		return 200;
	}
}
