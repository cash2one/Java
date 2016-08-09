package com.haizhi.Kafka;


import kafka.producer.Partitioner;
import kafka.utils.VerifiableProperties;

public class SimplePartitioner implements Partitioner {
    public SimplePartitioner (VerifiableProperties props) {

    }

    public int partition(Object key,int num_Partitions){
        int partition = 0;
        String str_key = (String) key;
        int offset = str_key.lastIndexOf('.');
        if ( offset > 0 ) {
            partition = Integer.parseInt( str_key.substring(offset+1) ) % num_Partitions;
        }

        return partition;
    }
}
