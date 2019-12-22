package com.lsy.java.test.base;

/**
 * Created by lisiyu on 2019/1/30.
 */
public class IfElseCondition {
    public static void main(String[] args) {
        int a = 3;
        int b = 3;

        //常规
        if (a == b) {
            // We already know this part
        } else {
            // a and b are not equal... :/
        }

        //省略大括号，但是只管后面的一行语句
        if (a == b) System.out.println("Yeah!");
        else System.out.println("Ohhh...");

        if (a == b)
            System.out.println("Another line Wow!");
        else
            System.out.println("Double rainbow!");

        //写在一行替代if的方式就是使用操作符 ?
        int result = a == 4 ? 1 : 8;
    }
}
