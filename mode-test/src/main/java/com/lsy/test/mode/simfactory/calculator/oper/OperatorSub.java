package com.lsy.test.mode.simfactory.calculator.oper;

/**
 * Created by lisiyu on 2019/11/20.
 */
public class OperatorSub extends Operator {

    public OperatorSub(double numA, double numB) {
        super(numA, numB);
    }

    public double calculate() {
        return getNumA() - getNumB();
    }
}
