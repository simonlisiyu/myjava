package com.lsy.test.spring.annotation.component;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * Created by lisiyu on 2020/4/24.
 */
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface MyComponent {
    String value() default "";
}
