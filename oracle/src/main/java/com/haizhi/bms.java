package com.haizhi;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class bms {
    public static void main(String[] args) {
        System.out.println("192.168.1.68:1521/bms:\n");

        List<Map> list = oracle_ankang();
        Map<String, String> map = list.get(0);
        for (String key : map.keySet()) {
            System.out.println("Key = " + key);
        }

        //遍历map中的值
        for (Object value : map.values()) {
            System.out.println("Value = " + value.toString());
        }
    }


    public static List<Map> oracle_ankang(){
        Connection con = null;// 创建一个数据库连接
        PreparedStatement pre = null;// 创建预编译语句对象，一般都是用这个而不用Statement
        ResultSet result = null;// 创建一个结果集对象
        List<Map> list = null;

        try
        {
            Class.forName("oracle.jdbc.driver.OracleDriver");// 加载Oracle驱动程序
            System.out.println("开始尝试连接数据库！");
            String url = "jdbc:oracle:thin:@192.168.1.68:1521/bms";// 127.0.0.1是本机地址，XE是精简版Oracle的默认数据库名
            String user = "HLW_DB";// 用户名,系统默认的账户名
            String password = "hlwdb";// 你安装时选设置的密码
            con = DriverManager.getConnection(url, user, password);// 获取连接
            System.out.println("连接成功！");

            // 预编译语句，“？”代表参数
            //String sql1 = "select * from KKADMIN.V_HLW_SN_COMPONENTS v where v.MES_OUT_LINE_TIME>to_date('2015-05-05','yyyy-mm-dd') and rownum<=5 ";
          //  String sql2 = "select * from user_tab_privs;" ;
            String sql3 = "select * from bms_prod.bo_ht_bms_snlife_kkhlw_v where sn='MJL1601YC002692RVYJ1';";

            pre = con.prepareStatement(sql3);        // 实例化预编译语句
            //pre.setString(1, "刘显安");            // 设置参数，前面的1表示参数的索引，而不是表中列名的索引
            result = pre.executeQuery();            // 执行查询，注意括号中不需要再加参数

            int field_rowCount = result.getRow();

            ResultSetMetaData rsmd = result.getMetaData();
            int field_count = rsmd.getColumnCount();
            Map map = null;

            list = new ArrayList<Map>();

            while (result.next()) {
                map = new HashMap();

                for(int i = 1; i <= field_count; i++) {
                    map.put(rsmd.getColumnName(i), result.getObject(i));
                }
            }
            list.add(map);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                // 逐一将上面的几个对象关闭，因为不关闭的话会影响性能、并且占用资源
                // 注意关闭的顺序，最后使用的最先关闭
                if (result != null)
                    result.close();
                if (pre != null)
                    pre.close();
                if (con != null)
                    con.close();
                System.out.println("数据库连接已关闭！");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return list;
    }
}


