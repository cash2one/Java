package com.haizhi.kklog;

import org.apache.thrift.TApplicationException;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

import java.util.Scanner;

/**
 * Created by tongange on 16/8/8.
 */
public class KkLogClient {
    public static final String IP = "localhost";
    public static final int PORT = 9090;
    public static final int clientTimeout = 30000;

    public static void main(String[] args) {
        TTransport transport = new TFramedTransport(new TSocket(IP, PORT, clientTimeout));
        TProtocol protocol = new TCompactProtocol(transport);
        KkLog.Client client = new KkLog.Client(protocol);

        try {
            transport.open();
            KkConf kc = client.getConf();
            System.out.println("获取配置: status:" + kc.getStatus() + ",Threshhold:" + kc.getThreshhold()
                    + ",RequestTime:" + kc.getRequestTime() + ",RepeatTimes:" + kc.getRepeatTimes());

            if (!client.verify("EKJCNALNJLKB8U4HJDKJNDKJO2O323IE")) {
                System.out.println("INVALID TOKEN !");
                //todo 无效token的后续处理
            }

            String firstLog="1|jiangsu|1000\n2|minhang|2100\n1|jilin|5000";
            client.sendLog("tv_source",firstLog);

            System.out.println("请输入日志\"exit\"结束:");
            Scanner scan = new Scanner(System.in);
            while (scan.hasNext()) {
                String log = scan.nextLine();
                System.out.println(log);
                if (log.equals("exit")) break;
                client.sendLog("tv_update",log); //如果服务器中断会抛出异常
            }
        } catch (TApplicationException e) {      //异常的文档 http://people.apache.org/~thejas/thrift-0.9/javadoc/org/apache/thrift/TException.html
            System.out.println(e.getMessage() + " " + e.getType());
        } catch (TTransportException e) {
            e.printStackTrace();
        } catch (TException e) {
            e.printStackTrace();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        transport.close();
    }

}