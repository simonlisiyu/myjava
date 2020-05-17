package com.lsy.test.spring.annotation.controller;

import com.lsy.test.spring.annotation.component.OperateLogAnnotation;
import com.lsy.test.spring.annotation.component.monitor.Metric;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by lisiyu on 2020/4/24.
 */
@RestController
@RequestMapping("/test")
public class TestAOPController {

    @Metric
    @RequestMapping("/show2")
    public String show2() {
        System.out.println("show2");
        return "OK2";
    }

    @Metric
    @RequestMapping("/show3")
    @ResponseBody
    @OperateLogAnnotation("测试")
    public String getById() {
        System.out.println(":show3");
        return "hello";
    }
}
