package com.lsy.test.spring.jpa;

import com.lsy.test.spring.jpa.service.TUserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class HelloController {
    @Resource
    private TUserService tUserService;

    @RequestMapping("/hello")
    public String index() {
        return "Hello World";
    }

    @RequestMapping("/get")
    public String tuser() {
        return tUserService.getAllUser().get(0).toString();
    }

}