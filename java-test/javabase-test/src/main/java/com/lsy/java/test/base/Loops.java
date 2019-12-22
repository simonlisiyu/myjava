package com.lsy.java.test.base;

/**
 * 循环
 * Created by lisiyu on 2019/2/14.
 */
public class Loops {

    public static void main(String[] args) {
        for (int i = 0; i < 3; i++) {}

        int i = 0;
        for (;i < 5;) {
            System.out.println(i++);
        }

        while (i < 5) {}

        do {

        } while(i < 5);

        for (i = 0; i < 5; i++) {
            if (i >= 3) {
                break;
            }
            System.out.println("Yuhu");
            if (i >= 1) {
                continue;
            }
            System.out.println("Tata");
        }
    }
}
