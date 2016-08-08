package com.zkClient;


import org.I0Itec.zkclient.serialize.SerializableSerializer;
import org.I0Itec.zkclient.ZkClient;
import org.apache.zookeeper.data.Stat;


public class zk_readData {
    public static void main(String[] args) {
        //zk集群的地址
        String ZKServers = "20.20.1.241:2181,20.20.1.241:2181,20.20.1.241:2181";
        ZkClient zkClient = new ZkClient(ZKServers,10000,10000,new SerializableSerializer());
        System.out.println("conneted ok!");

        Stat stat = new Stat();

        //获取 节点中的对象
        User  user = zkClient.readData("/testUserNode",stat);
        System.out.println("user.getName(): " + user.getName());
        System.out.println("user.getId(): " + user.getId());
        System.out.println(user.toString());
       // System.out.println(user.);
        System.out.println(stat);
    }
}
