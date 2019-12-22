package com.lsy.spring.cloud.test.eureka.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * Hello world!
 *
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"com.lsy.spring.cloud.test.eureka.api.facade"})
public class ServerConsumerApplication {
    public static void main( String[] args ) {
        SpringApplication.run(ServerConsumerApplication.class, args);
    }
}
