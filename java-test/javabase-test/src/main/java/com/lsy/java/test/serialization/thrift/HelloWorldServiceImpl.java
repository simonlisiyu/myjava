package com.lsy.java.test.serialization.thrift;

import org.apache.thrift.TException;

/**
 * Created by lisiyu on 2018/7/16.
 */
public class HelloWorldServiceImpl implements HelloWorldService.Iface{
    public String sayHello(String username) throws TException {
        return "Hello "+username;
    }
}
