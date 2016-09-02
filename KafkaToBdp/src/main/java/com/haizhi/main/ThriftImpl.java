package com.haizhi.main;

/**
 * Created by Administrator on 2016/8/9.
 */
import com.haizhi.Kafka.KafkaProperties;
import com.haizhi.thrift.KkConf;
import com.haizhi.thrift.KkLog;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

import java.util.Properties;


public class ThriftImpl implements KkLog.Iface {
    private final String topic;
    private static String message = null;

    private final kafka.javaapi.producer.Producer<Integer, String> producer;
    private final Properties props = new Properties();

    public ThriftImpl(String topic)
    {
        props.put("serializer.class", "kafka.serializer.StringEncoder");
        props.put("metadata.broker.list", KafkaProperties.kafkaConnect);
        producer = new kafka.javaapi.producer.Producer<Integer, String>(new ProducerConfig(props));
        this.topic = topic;
        //this.message = message;
    }

    public boolean sendLog(String type, String data){
        message = type + "||" + data;
        System.out.println("received log111: " + message);

        //todo 把数据传给kafka
        producer.send( new KeyedMessage<Integer, String>(topic, message) );

        //System.out.println(message);

        return true;
    }

    public KkConf getConf(){

        //todo 从sql中获取配置
        KkConf kc = new KkConf();
        kc.setRepeatTimes(3);
        kc.setRequestTime(1000);
        kc.setStatus(1);
        kc.setThreshhold(100);
        return kc;
    }


    public boolean verify(String token){
        //todo verify
        return true;
    }
}
