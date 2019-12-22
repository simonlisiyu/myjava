package com.lsy.java.test.serialization.thrift;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerSocket;

/**
 * Created by lisiyu on 2018/7/16.
 */
public class SimpleServer {

    public static void main(String[] args) throws Exception {

        TServerSocket serverSocket = new TServerSocket(Integer.valueOf(8811));

        HelloWorldService.Processor processor =
                new HelloWorldService.Processor<HelloWorldService.Iface>(new HelloWorldServiceImpl());
        TThreadPoolServer.Args serverParams = new TThreadPoolServer.Args(serverSocket);
        serverParams.processor(processor);
        serverParams.protocolFactory(new TBinaryProtocol.Factory());
        serverParams.maxWorkerThreads(1000);
        TServer server = new TThreadPoolServer(serverParams);

        server.serve();


    }
}
