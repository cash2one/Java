package com.demo;

import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.serialize.SerializableSerializer;
import org.apache.zookeeper.CreateMode;

public class createNode {

    public static void main(String[] args) {
        //zk集群的地址
        String ZKServers = "20.20.1.241:2181,20.20.1.241:2181,20.20.1.241:2181";
        ZkClient zkClient = new ZkClient(ZKServers,10000,10000,new SerializableSerializer());

        System.out.println("conneted ok!");

        User user = new User();
        user.setId(1);
        user.setName("testUser");

        /**
         * "/testUserNode" :节点的地址
         * user：数据的对象
         * CreateMode.PERSISTENT：创建的节点类型
         */

        try {
            String path = zkClient.create("/testUserNode3/pppp", user, CreateMode.PERSISTENT);
            System.out.println("created path:"+path);

            //输出创建节点的路径
        } catch (Exception e) {
            System.out.println(e.toString());
            System.out.println("1111111111111111111111");
            System.out.println(e.getMessage());
        }

    }
}