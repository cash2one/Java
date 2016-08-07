package com.haizhi;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;


public class HelloServiceClient {

    public static void main(String[] args) {

        try {

            // 设置调用的服务地址-端口
            TTransport transport = new TSocket("20.20.1.122", 9090);

            //  使用二进制协议
            TProtocol protocol = new TBinaryProtocol(transport);

            // 使用的接口
            HelloService.Client client = new HelloService.Client(protocol);

            //打开socket
            transport.open();

            client.sayBoolean(true);

            client.sayString("Hello worldfdfgdgdgdgdg");

            client.sayInt(111111111);

            client.sayVoid();

            transport.close();

        } catch (TTransportException e) {

            e.printStackTrace();

        } catch (TException te) {

            te.printStackTrace();

        }

    }

}