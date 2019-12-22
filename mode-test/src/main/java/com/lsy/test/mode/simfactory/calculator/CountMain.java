package com.lsy.test.mode.simfactory.calculator;

import com.lsy.test.mode.simfactory.calculator.oper.Operator;

/**
 * 简单工厂实现计算器
 * 1.界面逻辑和运算逻辑分离、灵活性好（工厂封装）
 * 2.操作类可维护可扩展（运算类继承）
 * 3.操作判断类可复用（方法多态）
 * Created by lisiyu on 2019/11/20.
 */
public class CountMain {

    public static void main(String[] args) {
        Operator operator = OperationFactory.createOperator("*", 1.2, 5.5);
        if(operator == null){
            System.out.println("param may be wrong!");
        } else {
            System.out.println("result is "+operator.calculate());
        }
    }
}
