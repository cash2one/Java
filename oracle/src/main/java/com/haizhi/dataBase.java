package com.haizhi;

import java.io.FileInputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

/**
 * Created by Administrator on 2016/8/26.
 */
public class dataBase {
    public static Connection con = null;              // 创建一个数据库连接
    public static PreparedStatement pre = null;       // 创建预编译语句对象，一般都是用这个而不用Statement
    public static ResultSet result = null;            // 创建一个结果集对象
    public static Properties conf = null;
    public static dataBase test = new dataBase();

    public dataBase(){
        try {
            conf = new Properties();
//            conf.load(new FileInputStream("kunkang.properties"));
//            conf.load(new FileInputStream("guankang.properties"));
//            conf.load(new FileInputStream("ankang.properties"));
//            conf.load(new FileInputStream("bms.properties"));

            conf.load(new FileInputStream("common.properties"));
            System.out.println("111111111: " + conf.getProperty("ClassName"));
            Class.forName(conf.getProperty("ClassName"));   //加载Oracle驱动程序

            System.out.println("开始尝试连接数据库！");

            String url = null;

            if (conf.getProperty("type").equals("oracle")) {

                url = "jdbc:" + conf.getProperty("type")
                        + ":thin:@" + conf.getProperty("ip")
                        + ":" + conf.getProperty("port") + "/"
                        + conf.getProperty("service");
            } else if (conf.getProperty("type").equals("db2")) {
                System.out.println("444444444444444444");
                url = "jdbc:" + conf.getProperty("type")
                        + "://" + conf.getProperty("ip")
                        + ":" + conf.getProperty("port") + "/"
                        + conf.getProperty("service");
            }

            System.out.println("url: " + url);

            String user = conf.getProperty("user");
            String password = conf.getProperty("password");
            con = DriverManager.getConnection(url, user, password);     //获取连接
            System.out.println("连接成功！");

        }catch (Exception e) {
            e.printStackTrace();
        }

    }


    public static int excute_sql_count(String sql) {
        int count = 0;
        try {

            pre = con.prepareStatement(sql);    //实例化预编译语句
            result = pre.executeQuery();
            while (result.next()) {
                count = result.getInt(1);
            }

            System.out.println("<<<<<<<<<< pre.close()");

            //关闭oracle连接池，防止出现游标溢出： ORA-01000: maximum open cursors exceeded
            result.close();
            pre.close();

        } catch (Exception e){
            e.printStackTrace();
        }

        return count;
    }


    public static ArrayList<String> excute_sql_select(String sql) {
        System.out.println("enter excute_sql_select and sql:\n" + sql);
        ResultSetMetaData rsmd;
        int field_count;
        String tmp_arr = "";
        ArrayList<String> al = new ArrayList<String>();

        try {
            pre = con.prepareStatement(sql);        // 实例化预编译语句
            result = pre.executeQuery();            // 执行查询，注意括号中不需要再加参数
            rsmd = result.getMetaData();
            field_count = rsmd.getColumnCount();

            while (result.next()) {
                //遍历每一行sql查询结果
                //String tmp_arr = new String[field_count];
                tmp_arr="";
                for (int i = 1; i <= field_count; i++) {
                    //不能用getObject，很容易导致空指针
                    // result.getObject(i).toString();
                    if(i < field_count) {
                        tmp_arr += result.getString(i) + "|";
                    }else if(i == field_count) {
                        tmp_arr += result.getString(i);
                    }
                }

                al.add(tmp_arr);
            }

            System.out.println("<<<<<<<<<< pre.close()");

            //关闭oracle连接池，防止出现游标溢出： ORA-01000: maximum open cursors exceeded
            result.close();
            pre.close();

        }catch (Exception e){
            e.printStackTrace();
        }

        return al;
    }


    public static void dabase_close() {
        System.out.println("开始关闭数据库连接");
        try {
            result.close();
            //必须关闭pre，否则一个小时后会耗尽oracle连接开销， 不能关闭con，下次没法重启查询数据库
            if (pre != null) {
                System.out.println("<<<<<<<<<< pre.close()");
                pre.close();
            }
           // if (con != null) { con.close(); }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
