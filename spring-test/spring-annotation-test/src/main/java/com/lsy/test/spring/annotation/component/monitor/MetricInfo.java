package com.lsy.test.spring.annotation.component.monitor;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author xiang.ji
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MetricInfo {
    /**
     * 是否是对外服务
     */
    private boolean isController;

    /**
     * 是否生成trace
     */
    private boolean isGenerateTrace;

    /**
     * 是否打印请求日志
     */
    private boolean isShowReqLog;

    /**
     * 是否打印返回日志
     */
    private boolean isShowRespLog;

    /**
     * 服务名称
     */
    private String serverId;

    /**
     * 接口名称
     */
    private String interfaceId;

    /**
     * 是否是生产环境
     */
    private boolean isProd;

}
