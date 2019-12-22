package com.lsy.java.test.serialization.thrift;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

/**
 * Created by lisiyu on 2018/7/16.
 */
public class SimpleClient {
    public static void main(String[] args) {
        try {
            TTransport transport = new TSocket("localhost",
                    Integer.valueOf(8811));
            transport.open();

            TProtocol protocol = new TBinaryProtocol(transport);
            HelloWorldService.Client client = new HelloWorldService.Client(protocol);
            String result = client.sayHello("leo");

            System.out.println(result);
            transport.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
