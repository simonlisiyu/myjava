package com.lsy.test.spring.annotation.component.monitor;

import com.google.common.base.Stopwatch;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import java.util.concurrent.TimeUnit;

/**
 * Metric接口监控的切片实现
 *
 * @author xiang.ji
 */
@Slf4j
public class MetricInterceptor implements MethodInterceptor {

    private MetricLoader metricLoader;


    public MetricInterceptor(MetricLoader metricLoader) {
        this.metricLoader = metricLoader;
    }

    public Object invoke(MethodInvocation invocation) {
        MetricInfo metricInfo = metricLoader.load(invocation);

        preHandle(metricInfo, invocation);

        Object result = null;
        Stopwatch stopwatch = Stopwatch.createStarted();
        try {
            if (metricInfo.isShowReqLog()) {
                log.info("method={}.{},request={}", invocation.getThis().getClass().getSimpleName(), invocation.getMethod().getName(), invocation.getArguments());
            }

            result = invocation.proceed();

            if (metricInfo.isShowRespLog()) {
                log.info("method={}.{},response={}", invocation.getThis().getClass().getSimpleName(), invocation.getMethod().getName(), result);
            }
            return result;
        }  catch (Throwable e) {
            log.error("{}未知异常", metricInfo.getInterfaceId(), e);
            return result;
        } finally {
            Long time = stopwatch.stop().elapsed(TimeUnit.MILLISECONDS);
            postHandle(metricInfo, invocation, time, result);
        }
    }

    private void preHandle(MetricInfo metricInfo, MethodInvocation invocation) {
        try {
            //设置traceId, spanId
            if (metricInfo.isGenerateTrace()) {
                LogOperator.init();
            }

        } catch (Exception e) {
            log.error("preProcess发送指标异常", e);
        }
    }

    private void postHandle(MetricInfo metricInfo, MethodInvocation invocation, Long time, Object result) {
        clearAll(metricInfo, time);
    }

    /**
     * 清空
     */
    private void clearAll(MetricInfo metricInfo, Long processTime) {
        log.info("请求完成:processTime={}", processTime);
        if (metricInfo.isGenerateTrace()) {
            LogOperator.remove();
        }
    }
}
