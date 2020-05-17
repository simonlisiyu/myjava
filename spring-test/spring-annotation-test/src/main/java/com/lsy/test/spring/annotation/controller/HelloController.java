package com.lsy.test.spring.annotation.controller;

import com.lsy.test.spring.annotation.component.log.WebLog;
import com.lsy.test.spring.annotation.component.monitor.Metric;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @WebLog(description = "user request")
    @RequestMapping("/hello")
    public String index() {
        return "Hello World";
    }

}