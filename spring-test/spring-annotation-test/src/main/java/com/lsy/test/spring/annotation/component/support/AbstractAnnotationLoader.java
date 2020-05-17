package com.lsy.test.spring.annotation.component.support;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInvocation;
import org.javatuples.Pair;
import org.springframework.aop.support.AopUtils;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;

/**
 * @author xiang.ji
 */

@Slf4j
public abstract class AbstractAnnotationLoader<A extends Annotation, T> implements AnnotationLoader<T> {

    private Class<A> annotationClass;

    private AnnotationParser<A, T> parser;

    private CacheLoader<Pair<Class, Method>, T> cacheLoader = new CacheLoader<Pair<Class, Method>, T>() {
        @Override
        public T load(Pair<Class, Method> key) {
            return parseMetricInfo(key);
        }
    };

    private LoadingCache<Pair<Class, Method>, T> cache = CacheBuilder
            .newBuilder()
            .initialCapacity(10)
            .build(cacheLoader);

    @SuppressWarnings("unchecked")
    public AbstractAnnotationLoader(AnnotationParser<A, T> parser) {
        this.annotationClass = (Class<A>) (((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0]);
        this.parser = parser;
    }

    @Override
    public T load(MethodInvocation methodInvocation) {
        try {
            Class clazz = methodInvocation.getThis() == null ? null : methodInvocation.getThis().getClass();
            return cache.get(Pair.with(clazz, methodInvocation.getMethod()));
        } catch (Throwable e) {
            log.error("获取指标名称异常", e);
            return null;
        }
    }

    private T parseMetricInfo(Pair<Class, Method> methodPair) {
        try {
            //先查method上的注解,再查类定义. 支持接口和继承
            A annotation = findMethodAnnotation(methodPair);
            if (annotation == null && methodPair.getValue0() != null) {
                annotation = findClassAnnotation(methodPair.getValue0());
            }

            if (annotation == null) {
                log.warn("{}:{}未能正常解析指标配置", methodPair.getValue0(), methodPair.getValue1());
                return null;
            }

            return parser.parse(annotation, methodPair);
        } catch (Throwable e) {
            log.error("生成指标名称异常", e);
            return null;
        }
    }

    private A findClassAnnotation(Class clz) {
        A annotation = AnnotationUtils.findAnnotation(clz, annotationClass);
        if (annotation == null) {
            return AnnotationUtils.findAnnotation(AopUtils.getTargetClass(clz), annotationClass);
        }
        return annotation;
    }

    private A findMethodAnnotation(Pair<Class, Method> methodPair) {
        A annotation = AnnotationUtils.findAnnotation(methodPair.getValue1(), annotationClass);
        if (annotation == null && methodPair.getValue0() != null) {
            return AnnotationUtils.findAnnotation(AopUtils.getMostSpecificMethod(methodPair.getValue1(), methodPair.getValue0()), annotationClass);
        }
        return annotation;
    }
}
