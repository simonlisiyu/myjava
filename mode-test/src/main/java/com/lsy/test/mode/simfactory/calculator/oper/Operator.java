package com.lsy.test.mode.simfactory.calculator.oper;

/**
 * 运算符抽象类
 * Created by lisiyu on 2019/11/20.
 */
public abstract class Operator {
    private double numA;
    private double numB;

    public Operator(double numA, double numB) {
        this.numA = numA;
        this.numB = numB;
    }

    public double getNumA() {
        return numA;
    }

    public void setNumA(double numA) {
        this.numA = numA;
    }

    public double getNumB() {
        return numB;
    }

    public void setNumB(double numB) {
        this.numB = numB;
    }

    public abstract double calculate();
}
