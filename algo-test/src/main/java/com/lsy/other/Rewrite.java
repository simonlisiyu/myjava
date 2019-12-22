package com.lsy.other;

import org.apache.commons.lang3.StringUtils;

import java.io.*;

/**
 * Created by lisiyu on 2019/8/16.
 */
public class Rewrite {

    public static void main(String[] args){
        String input = "/Users/zhangfan/log";
        String output = "/Users/zhangfan/out";
        try {
            rewrite(input,output);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 有一个文本文件backup.txt，写个程序把这个文件每行行尾的三个字符都写入到新的文件里面
     * (可以考虑开启多线程来优化,用LineNumberReader来实现)
     *
     * @param input
     *  ：原始文件路径
     * @param output
     *  ：输入文件路径
     * @return
     */
    public static void rewrite(String input,String output) throws Exception {
        if(StringUtils.isEmpty(input) || StringUtils.isEmpty(output)){
            return;
        }
        File fin = new File(input);
        BufferedReader br = new BufferedReader(new FileReader(fin));

        String line = null;
        BufferedWriter bw = new BufferedWriter(
                new OutputStreamWriter(
                        new FileOutputStream(new File(output)), "UTF-8"));
        try{
            while ((line = br.readLine()) != null) {
                if((line.length()-3) <= 0){
                    bw.write(line);
                    bw.newLine();
                    continue;
                }
                bw.write(line.substring(line.length()-3));
                bw.newLine();
            }
        } finally {
            bw.close();
        }
    }

}
