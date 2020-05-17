package com.lsy.test.spring.annotation.global;


//import javassist.bytecode.SignatureAttribute;
import com.lsy.test.spring.annotation.component.OperateLogAnnotation;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * Created by lisiyu on 2020/4/24.
 */
public class TestAspect {



    @Before("@annotation(operateLogAnnotation)")// 拦截被TestAnnotation注解的方法；如果你需要拦截指定package指定规则名称的方法，可以使用表达式execution(...)，具体百度一下资料一大堆
    public void before(JoinPoint joinPoint,OperateLogAnnotation operateLogAnnotation) {
        System.out.println("打印：" + operateLogAnnotation.value() + " 前置日志2");
    }

    @After("@annotation(operateLogAnnotation)")
    public void afterTTT(JoinPoint point,OperateLogAnnotation operateLogAnnotation) {
        System.out.println("打印自带参数：" + operateLogAnnotation.value() + " 后置日志2");
    }

}
