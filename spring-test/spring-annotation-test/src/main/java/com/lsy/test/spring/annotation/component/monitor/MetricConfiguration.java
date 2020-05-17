package com.lsy.test.spring.annotation.component.monitor;

import org.aopalliance.intercept.MethodInterceptor;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.DefaultBeanFactoryPointcutAdvisor;
import org.springframework.aop.support.Pointcuts;
import org.springframework.aop.support.annotation.AnnotationMatchingPointcut;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Role;

/**
 * 通过原生api注入spring
 * 代码中在方法上添加注解即可
 *
 * @author xiang.ji
 */

@Configuration
public class MetricConfiguration {

    @Bean
    public DefaultBeanFactoryPointcutAdvisor metricAdvisor() {
        DefaultBeanFactoryPointcutAdvisor advisor = new DefaultBeanFactoryPointcutAdvisor();
        advisor.setPointcut(metricPointcut());
        advisor.setAdvice(metricInterceptor());
        advisor.setOrder(1);
        return advisor;
    }

    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public Pointcut metricPointcut() {
        return Pointcuts.union(
                new AnnotationMatchingPointcut(Metric.class, true),
                new AnnotationMatchingPointcut(null, Metric.class)
        );
    }

    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public MethodInterceptor metricInterceptor() {
        return new MetricInterceptor(new MetricLoader());
    }
}
