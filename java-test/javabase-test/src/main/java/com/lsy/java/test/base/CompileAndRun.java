package com.lsy.java.test.base;

/**
 * Created by lisiyu on 2019/2/14.
 */
public class CompileAndRun {
    public static void main(String[] args) {
        for (int i = 0; i < args.length; i++) {
            System.out.println(args[i]);
        }
    }

    /**
     * javac Arguments.java
     * java Arguments arg0 arg1 arg2
     */
}
