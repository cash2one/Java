package com.haizhi;

import org.apache.flume.EventDeliveryException;
import org.apache.flume.FlumeException;

import java.util.List;

/**
 * Created by Administrator on 2016/8/4.
 */
public interface RpcClient {
    public int getBatchSize();
    public void append(Event event) throws EventDeliveryException;
    public void appendBatch(List<Event> events) throws EventDeliveryException;

    public boolean isActive();
    public void close() throws FlumeException;
}
