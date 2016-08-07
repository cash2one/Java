package com.haizhi;

import java.nio.charset.Charset;
import java.util.Map;

/**
 * Created by Administrator on 2016/8/4.
 */

public interface EventBuilder {
    public static Event withBody(byte[] body, Map<String, String> headers);
    public static Event withBody(byte[] body);
    190
    public static Event withBody(String body, Charset charset,
                                 Map<String, String> headers);
    public static Event withBody(String body, Charset charset);
}