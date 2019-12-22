package com.lsy.java.test.advance;

/**
 * 抽象类
 * Created by lisiyu on 2019/2/15.
 */
abstract class AbstractClass {
    abstract void abstractMethod();
    void concreteMethod() { // concrete methods are still allowed in abstract classes
        System.out.println("This is a concrete method.");
    }
}
