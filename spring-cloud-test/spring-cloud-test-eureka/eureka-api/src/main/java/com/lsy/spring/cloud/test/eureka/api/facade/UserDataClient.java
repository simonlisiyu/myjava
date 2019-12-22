package com.lsy.spring.cloud.test.eureka.api.facade;

import com.lsy.spring.cloud.test.eureka.api.domain.UserData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by lisiyu on 2019/1/14.
 */
@FeignClient(name="eureka-server-provider")
@RequestMapping("/service")
public interface UserDataClient {

    @RequestMapping(value="/hello")
    String index(@RequestParam(value = "name") String name);

    @RequestMapping("/data")
    String query(@RequestBody UserData userData);
}
