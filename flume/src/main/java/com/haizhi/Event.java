package com.haizhi;

import java.util.Map;

/**
 * Created by Administrator on 2016/8/4.
 */
public interface Event {
    public Map<String, String> getHeaders();
    public void setHeaders(Map<String, String> headers);
    public byte[] getBody();
    public void setBody(byte[] body);
}
