package com.haizhi;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;


/**
 * Created by Administrator on 2016/8/8.
 */

public class MyThread extends Thread {

    private HelloService.Client client;
    public MyThread(HelloService.Client client)
    {
        this.client = client;
    }

    public void run() {
        int seq = 0;
        while(true) {
            System.out.println("MyThread.run(" + Integer.toString(seq++) + ")");

            try {
                client.sayString("Hello , the stream to lvs !!! ");
            } catch (TTransportException e) {
                e.printStackTrace();
            } catch (TException te) {
                te.printStackTrace();
            }
        }
    }
}