package com.lsy.test.spring.mybatis;

import com.lsy.test.spring.mybatis.service.MetaService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class HelloController {
    @Resource
    private MetaService metaService;

    @RequestMapping("/get")
    public Object get() {
        return metaService.getEdgeTypeList().get(0);
    }

}