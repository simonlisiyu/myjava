package com.lsy.java.test.base;

/**
 * Created by lisiyu on 2019/2/15.
 */
public class TheStringBuilder {

    // 和 String 类不同的是，StringBuffer 和 StringBuilder 类的对象能够被多次的修改，并且不产生新的未使用对象。
    // 由于 StringBuilder 相较于 StringBuffer 有速度优势，所以多数情况下建议使用 StringBuilder 类。
    // 然而在应用程序要求线程安全的情况下，则必须使用 StringBuffer 类。
    public static void main(String args[]){
        StringBuilder stringBuilder = new StringBuilder("go:");
        stringBuilder.append("dddd");
        stringBuilder.append("ffff");
        stringBuilder.append("eee");
        System.out.println(stringBuilder);

        StringBuffer sBuffer = new StringBuffer("123：");
        sBuffer.append("111");
        sBuffer.append(".222");
        sBuffer.append(".333");
        System.out.println(sBuffer);
    }
}
