package com.haizhi;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TBinaryProtocol.Factory;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TTransportException;



public class HelloServiceServer {
    public static void main(String[] args) {
        try {
            //InetSocketAddress address = new InetSocketAddress("20.20.1.122", 9090);
            //TServerSocket serverTransport = new TServerSocket(address);

            // 设置服务器端口
            TServerSocket serverTransport = new TServerSocket(9090);

            // 设置二进制协议工厂
            Factory protocolFactory = new TBinaryProtocol.Factory();

            //处理器关联业务实现
            HelloService.Processor<HelloService.Iface> processor = new HelloService.Processor<HelloService.Iface>(new HelloServiceImpl());

            // 1. 使用单线程标准阻塞I/O模型
            //TServer.Args simpleArgs = new TServer.Args(serverTransport);
            //simpleArgs.processor(processor);
            //simpleArgs.protocolFactory(protocolFactory);
            //TServer server = new TSimpleServer(simpleArgs);

            System.out.println("开启thrift服务器，使用线程池服务模型,监听端口：9090");
            // 2. 使用线程池服务模型
            TThreadPoolServer.Args poolArgs = new TThreadPoolServer.Args(serverTransport);
            poolArgs.processor(processor);
            poolArgs.protocolFactory(protocolFactory);
            TServer poolServer = new TThreadPoolServer(poolArgs);
            poolServer.serve();


            System.out.println("开启thrift服务器，单线程模型，监听端口：9090");
//            server.serve();
        } catch (TTransportException e) {
            e.printStackTrace();
        }
    }

}