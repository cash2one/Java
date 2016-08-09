package com.haizhi.kklog;

/**
 * Created by tongange on 16/8/8.
 */
public class KkServiceImpl implements KkLog.Iface {

    public boolean sendLog(String type, String data){
        System.out.println("received log: type:\""+type+"\", data:\""+data+"\"");

        //todo 把数据传给kafka

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
