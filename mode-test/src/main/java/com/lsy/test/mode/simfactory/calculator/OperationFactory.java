package com.lsy.test.mode.simfactory.calculator;

import com.lsy.test.mode.simfactory.calculator.oper.Operator;
import com.lsy.test.mode.simfactory.calculator.oper.OperatorSum;

/**
 * Created by lisiyu on 2019/11/20.
 */
public class OperationFactory {
    public static Operator createOperator(String oper, double numA, double numB){
        Operator operator = null;

        switch (oper){
            case "+":
                operator = new OperatorSum(numA, numB);
                break;
            case "-":
                operator = new OperatorSum(numA, numB);
                break;
            case "*":
                operator = new OperatorSum(numA, numB);
                break;
            case "/":
                operator = new OperatorSum(numA, numB);
                break;
            default:
                break;
        }

        return operator;
    }
}
