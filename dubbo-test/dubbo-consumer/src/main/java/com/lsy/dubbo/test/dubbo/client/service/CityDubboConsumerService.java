package com.lsy.dubbo.test.dubbo.client.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lsy.dubbo.test.dubbo.api.bean.City;
import com.lsy.dubbo.test.dubbo.api.service.CityDubboService;
import org.springframework.stereotype.Component;

/**
 * 城市 Dubbo 服务消费者
 *
 * Created by bysocket on 28/02/2017.
 */
@Component
public class CityDubboConsumerService {

    @Reference(version = "1.0.0")
    CityDubboService cityDubboService;

    public void printCity() {
        String cityName="BJ";
        City city = cityDubboService.findCityByName(cityName);
        System.out.println(city.toString());
    }
}
