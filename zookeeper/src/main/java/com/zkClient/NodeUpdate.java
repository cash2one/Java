package com.demo;

import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.serialize.SerializableSerializer;


public class NodeUpdate {
    public static void main(String[] args) {
        //zk集群的地址
        String ZKServers = "20.20.1.241:2181,20.20.1.241:2181,20.20.1.241:2181";
        ZkClient zkClient = new ZkClient(ZKServers, 10000, 10000, new SerializableSerializer());
        System.out.println("conneted ok!");

        User user = new User();
        user.setId(123);
        user.setName("testUser2");

        /**
         * testUserNode 节点的路径
         *
         * user 传入的数据对象
         */
        zkClient.writeData("/testUserNode", user);
    }
}