package com.lsy.java.test.javassist;

/**
 * Created by lisiyu on 2019/1/21.
 */
public class JavassistClass {

    private String name="default";

    public JavassistClass(){

        name="me";

    }

    public String getName() {

        return name;

    }

    public void setName(String name) {

        this.name = name;

    }
    public void execute(){
        System.out.println(name);
        System.out.println("execute ok");
    }
}
