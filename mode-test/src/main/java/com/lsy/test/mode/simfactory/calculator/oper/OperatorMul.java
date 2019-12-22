package com.lsy.test.mode.simfactory.calculator.oper;

/**
 * Created by lisiyu on 2019/11/20.
 */
public class OperatorMul extends Operator {

    public OperatorMul(double numA, double numB) {
        super(numA, numB);
    }

    public double calculate() {
        return getNumA() * getNumB();
    }
}
