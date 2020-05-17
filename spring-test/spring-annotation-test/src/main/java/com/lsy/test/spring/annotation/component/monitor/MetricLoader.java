package com.lsy.test.spring.annotation.component.monitor;

import com.google.common.base.CaseFormat;
import com.google.common.base.Strings;
import com.lsy.test.spring.annotation.component.support.AbstractAnnotationLoader;
import com.lsy.test.spring.annotation.component.support.AnnotationLoader;
import com.lsy.test.spring.annotation.component.support.AnnotationParser;
import org.aopalliance.intercept.MethodInvocation;
import org.javatuples.Pair;

import java.lang.reflect.Method;

/**
 * @author xiang.ji
 */
public class MetricLoader implements AnnotationLoader<MetricInfo> {

    private static final MetricInfo DEFAULT_METRIC_INFO = new MetricInfo();

    private AnnotationLoader<MetricInfo> delegate = new AbstractAnnotationLoader<Metric, MetricInfo>(new MetricParser()) {};

    @Override
    public MetricInfo load(MethodInvocation methodInvocation) {
        MetricInfo metricInfo = delegate.load(methodInvocation);
        return metricInfo != null ? metricInfo : DEFAULT_METRIC_INFO;
    }

    private class MetricParser implements AnnotationParser<Metric, MetricInfo> {
        @Override
        public MetricInfo parse(Metric annotation, Pair<Class, Method> methodPair) {
            String interfaceId = Strings.isNullOrEmpty(annotation.interfaceId())
                    ? CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, methodPair.getValue1().getName())
                    : annotation.interfaceId();

            return new MetricInfo(annotation.isController(), annotation.isGenerateTrace(), annotation.isShowReqLog(),
                    annotation.isShowRespLog(), annotation.serverId(), interfaceId, true);
        }
    }
}
