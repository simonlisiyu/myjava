package com.lsy.java.test.object;

/**
 * Created by lisiyu on 2019/2/15.
 */
public class TheOverload {

    public int test(){
        System.out.println("test1");
        return 1;
    }

    public void test(int a){
        System.out.println("test2");
    }

    //以下两个参数类型顺序不同
    public String test(int a,String s){
        System.out.println("test3");
        return "returntest3";
    }

    public String test(String s,int a){
        System.out.println("test4");
        return "returntest4";
    }

    public static void main(String[] args){
        TheOverload o = new TheOverload();
        System.out.println(o.test());
        o.test(1);
        System.out.println(o.test(1,"test3"));
        System.out.println(o.test("test4",1));
    }

    /**
     * 区别点	重载方法	重写方法
     参数列表	必须修改	一定不能修改
     返回类型	可以修改	一定不能修改
     异常	可以修改	可以减少或删除，一定不能抛出新的或者更广的异常
     访问	可以修改	一定不能做更严格的限制（可以降低限制）
     */
}
