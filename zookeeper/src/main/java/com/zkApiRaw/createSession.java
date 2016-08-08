package com.zkApiRaw;

/**
 * Created by Administrator on 2016/8/6.
 */

import java.util.concurrent.CountDownLatch;

import org.I0Itec.zkclient.ZkClient;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooKeeper;

public class createSession implements  Watcher {
    private static CountDownLatch connectSemaphore = new CountDownLatch(1);

    public static void main(String[] args) {
        try {
            ZooKeeper zooKeeper = new ZooKeeper("20.20.1.241:2181", 5000, new createSession());
            System.out.println(zooKeeper.getState());
        } catch (Exception e) {}


        try {
            connectSemaphore.await();
        } catch (InterruptedException e) {
        }
        System.out.println("ZooKeeper session established. ");
    }

    public void process(WatchedEvent event) {
        System.out.println("Receive watch event: " + event);
        if(KeeperState.SyncConnected == event.getState()) {
            connectSemaphore.countDown();
        }
    }

}
