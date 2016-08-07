package com.haizhi; /**
 * Created by Administrator on 2016/8/2.
 */
import org.apache.thrift.TException;

public class HelloServiceImpl implements HelloService.Iface{

  //  @Override
    public int sayInt(int param) throws TException {

        System.out.println("say int :" + param);

        return param;

    }

   // @Override
    public String sayString(String param) throws TException {

        System.out.println("say string :" + param);

        return param;

    }

  //  @Override
    public boolean sayBoolean(boolean param) throws TException {

        System.out.println("say boolean :" + param);

        return param;

    }

  //  @Override
    public void sayVoid() throws TException {
        System.out.println("say void ...");

    }

}