package com.lsy.test.spring.annotation.component.support;

import org.aopalliance.intercept.MethodInvocation;

/**
 * @author xiang.ji
 */
public interface AnnotationLoader<T> {

    T load(MethodInvocation methodInvocation);
}
