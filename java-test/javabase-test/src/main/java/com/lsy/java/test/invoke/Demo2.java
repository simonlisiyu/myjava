package com.lsy.java.test.invoke;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lisiyu on 2020/5/12.
 */
public class Demo2 {



    public static void main(String[] args) throws Exception{
        List<String> list = new ArrayList<>();
        list.add("1");
        list.add("2");

        Method method = SomeBuilder.class.getMethod("build"+list.size(), String.class, List.class);

        SomeBuilder builder = new SomeBuilder();
        String result = builder.run(builder, method, "Hello World", list);
        System.out.println(result);
    }

    public void method1(String message) {
        System.out.println(message);
    }

    public void method2(Object object, Method method, String message) throws Exception {
        Object[] parameters = new Object[1];
        parameters[0] = message;
        method.invoke(object, parameters);
    }

    static class SomeBuilder {
        public String build1(String str1, List<String> list1){
            return list1.size()+str1+"11111";
        }

        public String build2(String str1, List<String> list1){
            return list1.size()+str1+"22222";
        }

        public String run(Object object, Method method, String str1, List<String> list1) throws Exception {
            Object[] parameters = new Object[2];
            parameters[0] = str1;
            parameters[1] = list1;
            String returnStr = (String) method.invoke(object, parameters);
            return returnStr;
        }
    }

}
