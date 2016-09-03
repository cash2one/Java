package com.haizhi;

import java.sql.*;
import java.util.Properties;

public class LogMysql {
    public static String url ;
    public static String name;
    public static String user;
    public static String password;

    public static Connection conn = null;
    public static PreparedStatement pst = null;



    //构造函数
    public  LogMysql(Properties conf) {

        url = "jdbc:mysql://"
                + conf.getProperty("ip")
                + ":" + conf.getProperty("port")
                + "/" + conf.getProperty("service");
        name = conf.getProperty("ClassName");
        user = conf.getProperty("user");
        password = conf.getProperty("password");

        try {
            Class.forName(name);                    //指定连接类型
            conn = DriverManager.getConnection(url, user, password);        //获取连接
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void close() {
        try {
            conn.close();
            pst.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static void mysqlInsert(String sql){
        if(sql==""){
            sql = "INSERT INTO `rsync_kangjia`.`rsync_info` (`status`, `type`, `ip`, `port`, `error_code`, `error_msg`) VALUES ('1', 'oracle', '11.2.33.4', '44', '1', 'sdsdfsdffff')";
        }

        try {
            pst = conn.prepareStatement(sql);       //准备执行语句
            int ret = pst.executeUpdate();
            System.out.println(ret);
            close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static String mysqlSelect(String sql) {
        String create_date="";
        String ufname;
        String ulname;
        String udate;
        ResultSet rs = null;

        if(sql=="") {
            sql = "show tables";
            System.out.println("eeeeeeeeeeeeeeeeee");
        }

        try {
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();

            while (rs.next()) {
                create_date = rs.getString(1);
//                ufname = rs.getString(2);
//                ulname = rs.getString(3);
//                udate = rs.getString(4);

            }

            rs.close();
            close();
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return create_date;
    }


}


