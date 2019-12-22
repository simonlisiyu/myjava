package com.lsy.java.test.advance.reflection;

/**
 * Created by lisiyu on 2019/7/20.
 *
 * 反射测试1：
 * 获取Class对象的三种方式:
 * 1 Object ——> getClass();
 * 2 任何数据类型（包括基本数据类型）都有一个“静态”的class属性
 * 3 通过Class类的静态方法：forName（String  className）(常用)
 *
 * 注意：在运行期间，一个类，只有一个Class对象产生。
 */
public class GetClassName {

    public static void main(String[] args) {
        //第一种方式获取Class对象
        Person stu1 = new Person();//这一new 产生一个Person对象，一个Class对象。
        Class stuClass = stu1.getClass();//获取Class对象
        System.out.println(stuClass.getName());

        //第二种方式获取Class对象
        Class stuClass2 = Person.class;
        System.out.println(stuClass == stuClass2);//判断第一种方式获取的Class对象和第二种方式获取的是否是同一个

        //第三种方式获取Class对象
        try {
            Class stuClass3 = Class.forName("com.lsy.java.test.advance.reflection.GetClassName$Person");//注意此字符串必须是真实路径，就是带包名的类路径，包名.类名
            System.out.println(stuClass3 == stuClass2);//判断三种方式是否获取的是同一个Class对象
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    static class Person {
        private String id;
        private String name;

        Person(){}
    }
}
