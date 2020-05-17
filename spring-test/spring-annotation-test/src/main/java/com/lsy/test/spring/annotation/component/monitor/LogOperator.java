package com.lsy.test.spring.annotation.component.monitor;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;

/**
 * @author xiang.ji
 */
public class LogOperator {

    public static final String TRACE_ID = "traceid";

    public static final String SPAN_ID = "spanid";

    public static void init() {
        MDC.put(TRACE_ID, IdGenerator.getTraceId());
        MDC.put(SPAN_ID, IdGenerator.getSpanId());
    }

    public static void init(String traceId, String spanId) {
        MDC.put(TRACE_ID, StringUtils.defaultIfBlank(traceId, IdGenerator.getTraceId()));
        MDC.put(SPAN_ID, StringUtils.defaultIfBlank(spanId, IdGenerator.getSpanId()));
    }

    public static String getTraceId() {
        return StringUtils.defaultString(MDC.get(TRACE_ID));
    }

    public static String getSpanId() {
        return StringUtils.defaultString(MDC.get(SPAN_ID));
    }

    public static void remove() {
        MDC.remove(TRACE_ID);
        MDC.remove(SPAN_ID);
    }
}
