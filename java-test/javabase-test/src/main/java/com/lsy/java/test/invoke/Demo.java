package com.lsy.java.test.invoke;

import java.lang.reflect.Method;

/**
 * Created by lisiyu on 2020/5/12.
 */
public class Demo {

    public static void main(String[] args) throws Exception{
        Class[] parameterTypes = new Class[1];
        parameterTypes[0] = String.class;
        Method method1 = Demo.class.getMethod("method1", parameterTypes);

        Demo demo = new Demo();
        demo.method2(demo, method1, "Hello World");
    }

    public void method1(String message) {
        System.out.println(message);
    }

    public void method2(Object object, Method method, String message) throws Exception {
        Object[] parameters = new Object[1];
        parameters[0] = message;
        method.invoke(object, parameters);
    }

}
