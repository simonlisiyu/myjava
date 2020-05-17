package com.lsy.test.spring.annotation.config;

import com.lsy.test.spring.annotation.component.MyComponent;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;

/**
 * Created by lisiyu on 2020/4/24.
 */
@Configuration
public class ComponentAnnotationTest {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext();
        annotationConfigApplicationContext.register(ComponentAnnotationTest.class);
        annotationConfigApplicationContext.refresh();
        InjectClass injectClass = annotationConfigApplicationContext.getBean(InjectClass.class);
        injectClass.print();
    }
    @MyComponent
    public static class InjectClass {
        public void print() {
            System.out.println("hello world, abc");
        }
    }
}
