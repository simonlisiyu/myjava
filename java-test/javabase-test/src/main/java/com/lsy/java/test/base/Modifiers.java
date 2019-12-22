package com.lsy.java.test.base;

/**
 * 修饰符
 * Created by lisiyu on 2019/2/15.
 */
public class Modifiers {

    private boolean myFlag;
    static final double weeks = 9.5;
    protected static final int BOXWIDTH = 42;
    public static void main(String[] arguments) {
        // 方法体
    }

    /**
     *
     default (即缺省，什么也不写）: 在同一包内可见，不使用任何修饰符。使用对象：类、接口、变量、方法。

     private : 在同一类内可见。使用对象：变量、方法。 注意：不能修饰类（外部类）

     public : 对所有类可见。使用对象：类、接口、变量、方法

     protected : 对同一包内的类和所有子类可见。使用对象：变量、方法。 注意：不能修饰类（外部类）。
     */
}
