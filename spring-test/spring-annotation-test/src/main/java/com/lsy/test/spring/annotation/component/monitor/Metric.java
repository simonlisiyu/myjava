package com.lsy.test.spring.annotation.component.monitor;


import java.lang.annotation.*;

/**
 * 对外接口各个方法的指标统计, 在接口/实现类上直接使用该注解
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Documented
public @interface Metric {

    /**
     * 是否是对外接口
     */
    boolean isController() default false;

    /**
     * 是否生成traceId spanId
     */
    boolean isGenerateTrace() default false;

    /**
     * 是否打印请求日志
     */
    boolean isShowReqLog() default true;

    /**
     * 是否打印返回日志
     */
    boolean isShowRespLog() default true;

    /**
     * 服务名称
     */
    String serverId() default "";

    /**
     * 接口名称
     */
    String interfaceId() default "";

}
