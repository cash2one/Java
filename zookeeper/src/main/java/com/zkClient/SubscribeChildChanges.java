package com.demo;

import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.serialize.SerializableSerializer;

import java.util.List;

public class SubscribeChildChanges {
    private static class ZKChildListener implements IZkChildListener {
        /**
         * handleChildChange： 用来处理服务器端发送过来的通知
         * parentPath：对应的父节点的路径
         * currentChilds：子节点的相对路径
         */
        public void handleChildChange(String parentPath, List<String> currentChilds) throws Exception {

            System.out.println(parentPath);
            System.out.println(currentChilds.toString());

        }

    }

    public static void main(String[] args) throws InterruptedException {
        //zk集群的地址
        String ZKServers = "20.20.1.241:2181,20.20.1.241:2181,20.20.1.241:2181";
        ZkClient zkClient = new ZkClient(ZKServers,10000,10000,new SerializableSerializer());
        System.out.println("conneted ok!");
        /**
         * "/testUserNode" 监听的节点，可以是现在存在的也可以是不存在的
         */
        zkClient.subscribeChildChanges("/testUserNode3", new ZKChildListener());
        Thread.sleep(Integer.MAX_VALUE);
    }
}