package com.haizhi.Kafka;

import java.util.*;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;


public class TestProducer {
    public static void main(String[] args) {
        //long events = Long.parseLong(args[0]);
        long events = 2;
        Random rnd = new Random();

        Properties props = new Properties();
        props.put("metadata.broker.list", KafkaProperties.kafkaConnect);
        props.put("serializer.class", "kafka.serializer.StringEncoder"); //默认字符串编码消息
        //props.put("partitioner.class", "example.producer.SimplePartitioner");

        props.put("partitioner.class", "demo.SimplePartitioner");
        props.put("request.required.acks", "1");

        ProducerConfig config = new ProducerConfig(props);

        Producer<String, String> producer = new Producer<String, String>(config);

        for (long nEvents = 0; nEvents < events; nEvents++) {
            long runtime = new Date().getTime();
            String ip = "192.168.2." + rnd.nextInt(255);
            String msg = runtime + ",www.example.com," + ip;
            KeyedMessage<String, String> data = new KeyedMessage<String, String>("mytest1", "543", msg);
            producer.send(data);
        }

        producer.close();

    }
}

