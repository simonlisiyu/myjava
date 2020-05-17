package com.lsy.test.spring.annotation;

import com.lsy.test.spring.annotation.component.MyAnnotation;
import com.lsy.test.spring.annotation.component.MyAnnotation1;

/**
 * Created by lisiyu on 2020/4/24.
 */
@MyAnnotation(name="myTest")
public class MyTest {

    @MyAnnotation1
    String myTest;

    @MyAnnotation1(name="test",getFieldValue="1",setFieldValue="2",realValue= MyAnnotation1.FieldValue.MYVALUE)
    String testValue;

    public String getMyTest() {
        return myTest;
    }

    public void setMyTest(String myTest) {
        this.myTest = myTest;
    }

    public String getTestValue() {
        return testValue;
    }

    public void setTestValue(String testValue) {
        this.testValue = testValue;
    }

}
