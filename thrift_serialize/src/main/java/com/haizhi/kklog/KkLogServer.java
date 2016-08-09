package com.haizhi.kklog;

import org.apache.thrift.TProcessorFactory;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.server.THsHaServer;
import org.apache.thrift.server.TServer;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.apache.thrift.transport.TTransportException;

/**
 * Created by tongange on 16/8/8.
 */

public class KkLogServer {
    public static final int PORT = 9090;
    public static void main(String[] args) {
        try {
            TNonblockingServerSocket socket = new TNonblockingServerSocket(PORT);
            final KkLog.Processor processor = new KkLog.Processor(new KkServiceImpl());
            THsHaServer.Args arg = new THsHaServer.Args(socket);

            // 高效率的、密集的二进制编码格式进行数据传输
            // 使用非阻塞方式
            arg.protocolFactory(new TCompactProtocol.Factory());   //压缩
            arg.transportFactory(new TFramedTransport.Factory());  //按帧传送
            //arg.protocolFactory(new TBinaryProtocol.Factory());

            arg.processorFactory(new TProcessorFactory(processor));
            TServer server = new THsHaServer(arg);

            System.out.println("#服务启动-使用:非阻塞&高效二进制编码");
            server.serve();

        } catch (TTransportException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}