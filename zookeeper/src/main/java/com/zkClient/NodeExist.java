package com.zkClient;

import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.serialize.SerializableSerializer;

public class NodeExist {
    public static void main(String[] args) {
        //zk集群的地址
        String ZKServers = "20.20.1.241:2181,20.20.1.241:2181,20.20.1.241:2181";
        ZkClient zkClient = new ZkClient(ZKServers,10000,10000,new SerializableSerializer());
        System.out.println("conneted ok!");

        boolean e = zkClient.exists("/testUserNode");
        //返回 true表示节点存在 ，false表示不存在
        System.out.println(e);
    }
}
