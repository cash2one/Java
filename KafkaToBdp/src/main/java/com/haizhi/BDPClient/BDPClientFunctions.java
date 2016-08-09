package com.haizhi.BDPClient;

/**
 * Created by lixinxing on 16/7/27.
 */
import cn.bdp.sdk.BDPClient;
import cn.bdp.sdk.DS;
import org.junit.Test;

import java.util.Map;

public class BDPClientFunctions {
    private BDPClient client;
    //private String token ="8518580c4560d73b5bed0a09f2aec2fb";
    // 本地b0a9b37da708bc8bf4e774c682d2e4a1
    private String token ="b0a9b37da708bc8bf4e774c682d2e4a1";

    public void initBDPClient(){
        client = new BDPClient(token);
    }

    //根据数据源名获取数据源对象
    public DS getDataSource(String DSName){
        initBDPClient();
        return client.getDs(DSName);
    }

    //根据名字创建数据源
    public DS createDataSource(String DSName){
        initBDPClient();
        return client.createDs(DSName);
    }

    //根据名字删除数据源
    public void deleteDataSource(String DSName){
        initBDPClient();
        client.deleteDs(DSName);
    }

    //获取所有数据源对象
    public Map<String, DS> getAllDataSource(){
        initBDPClient();
        return client.getAllDs();
    }

    public static void main(String[] args) {

        BDPClientFunctions bdpClientFunctions =new BDPClientFunctions();

 //       System.out.println(bdpClientFunctions.createDataSource("createDataSource").toString());

//        System.out.println(bdpClientFunctions.getAllDataSource().toString());
//
//        System.out.println(bdpClientFunctions.getDataSource("TestSDK"));
//
        bdpClientFunctions.deleteDataSource("createDataSource");
//
//        System.out.println(bdpClientFunctions.getAllDataSource().toString());

    }

}
