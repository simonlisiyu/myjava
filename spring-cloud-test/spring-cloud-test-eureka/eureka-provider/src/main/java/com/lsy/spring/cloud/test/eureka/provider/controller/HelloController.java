package com.lsy.spring.cloud.test.eureka.provider.controller;

import com.lsy.spring.cloud.test.eureka.api.domain.UserData;
import com.lsy.spring.cloud.test.eureka.api.facade.UserDataClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class HelloController implements UserDataClient {

    @Override
    public String index(@RequestParam String name) {
        return "hello " + name + "，this is first message";
    }

    @Override
    public String query(@RequestBody UserData userData) {
        System.out.println(userData);

        return "hello world";
    }
}
