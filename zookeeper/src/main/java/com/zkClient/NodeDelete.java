package com.demo;

import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.serialize.SerializableSerializer;


public class NodeDelete {
    public static void main(String[] args) {
        //zk集群的地址
        String ZKServers = "20.20.1.241:2181,20.20.1.241:2181,20.20.1.241:2181";
        ZkClient zkClient = new ZkClient(ZKServers,10000,10000,new SerializableSerializer());
        System.out.println("conneted ok!");

        //删除单独一个节点，返回true表示成功
        boolean e1 = zkClient.delete("/testUserNode");

        //删除含有子节点的节点
        boolean e2 = zkClient.deleteRecursive("/testUserNode3");

        //返回 true表示节点成功 ，false表示删除失败
        System.out.println(e2);
    }
}