package com.lsy.test.spring.annotation.component.monitor;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author xiang.ji
 */

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
@Inherited
@Import(MetricConfiguration.class)
public @interface EnableMetric {
}
