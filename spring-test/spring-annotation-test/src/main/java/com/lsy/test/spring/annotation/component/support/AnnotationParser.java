package com.lsy.test.spring.annotation.component.support;

import org.javatuples.Pair;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * @author xiang.ji
 */
public interface AnnotationParser<A extends Annotation, T> {

    T parse(A annotation, Pair<Class, Method> methodPair);
}
