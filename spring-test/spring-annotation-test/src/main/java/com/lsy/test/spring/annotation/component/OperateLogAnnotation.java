package com.lsy.test.spring.annotation.component;

import java.lang.annotation.*;

/**
 * Created by lisiyu on 2020/4/24.
 */
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface OperateLogAnnotation {
    String value();
}
