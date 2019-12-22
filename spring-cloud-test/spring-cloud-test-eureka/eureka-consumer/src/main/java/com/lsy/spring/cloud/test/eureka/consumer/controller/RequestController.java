package com.lsy.spring.cloud.test.eureka.consumer.controller;

import com.lsy.spring.cloud.test.eureka.api.domain.UserData;
import com.lsy.spring.cloud.test.eureka.api.facade.UserDataClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


@RestController
public class RequestController {

    @Resource
    private UserDataClient userDataClient;

    @RequestMapping(value = "/test/query")
    public String query(@RequestBody UserData userData) {
        System.out.println(userData);
        return userDataClient.query(userData);
    }
}
