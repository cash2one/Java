package com.haizhi.Kafka;


import javax.xml.soap.SAAJResult;

public interface KafkaProperties
{
    final static String zkConnect = "bdp-001:2181,bdp-002:2181,bdp-003:2181";
    final static String kafkaConnect = "bdp-001:9092";
    final static String groupId = "console-consumer-1";
//    final static String groupId = "test-consumer-group";

    final static String topic = "mytest";
    final static String topic1 = "mytest1";
    final static String topic2 = "mytest2";
    final static String topic3 = "mytest3";

    final static String[] topicList = {"mytest1", "mytest2", "mytest2"};

    final static String kafkaServerURL = "bdp-001:9092";
    final static int kafkaServerPort = 9092;
    final static int kafkaProducerBufferSize = 64 * 1024;
    final static int connectionTimeOut = 20000;
    final static int reconnectInterval = 10000;
    final static String clientId = "SimpleConsumerDemoClient";
}


