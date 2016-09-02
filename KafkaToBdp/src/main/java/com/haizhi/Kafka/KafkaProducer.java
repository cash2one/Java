package com.haizhi.Kafka;

import java.util.Properties;

import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

public class KafkaProducer extends Thread
{
    private final String topic;
    //private final String message;

    private final kafka.javaapi.producer.Producer<Integer, String> producer;
    private final Properties props = new Properties();

    public KafkaProducer(String topic)
    {
        props.put("serializer.class", "kafka.serializer.StringEncoder");
        props.put("metadata.broker.list", KafkaProperties.kafkaConnect);
        producer = new kafka.javaapi.producer.Producer<Integer, String>(new ProducerConfig(props));
        this.topic = topic;
        //this.message = message;
    }

    @Override
    public void run() {

        while (true)
        {
            int messageNo = 1;
//            String messageStr = new String(
//                              "111111111111111111111111111111111111111111"
//                            + "111111111111111111111111111111111111111111"
//                            + "111111111111111111111111111111111111111111"
//                            + "111111111111111111111111111111111111111111"
//                            + "111111111111111111111111111111111111111111"
//                            + " Message_" + messageNo);


            String messageStr = "adv_log||1|6808484 | 182.242.227.147 | null | 云南省 | download | 27 | null | 0 | 2 | 2013-10-11 13:54:20";

            System.out.println("Send:" + messageStr);

            producer.send(new KeyedMessage<Integer, String>(topic, messageStr));
            //System.out.println("11111111111 !");

            messageNo++;

            try {
                System.out.println("start sleep 1 second!");
                sleep(1000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

           // if( messageNo > 3 ) { break; }
        }
    }

    public static void main(String[] args)
    {
        KafkaProducer producerThread = new KafkaProducer(KafkaProperties.topic);

        producerThread.start();
    }

}