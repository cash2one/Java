package com.haizhi;

import cn.bdp.bean.Schema;
import cn.bdp.sdk.BDPClient;
import cn.bdp.sdk.DS;
import cn.bdp.sdk.Table;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

/**
 * Created by Administrator on 2016/8/17.
 */

public class common {

    public static void list_map(List<Map> list){
        Map<String, String> map = null;

        String field = null;
        String value = null;

        for(int i=0; i<list.size(); i++) {
            map = list.get(i);

            for (String key : map.keySet()) {
                field += key;
                field += "\t";
            }
            System.out.println("field = " + field);

            //遍历map中的值
            for (Object obj : map.values()) {
                value += obj.toString();
                value += "\t";
            }
            System.out.println("Value = " + value);
        }
    }


    public static ArrayList<String []> oracle(Properties conf, Calendar cale ){
        Connection con = null;              // 创建一个数据库连接
        PreparedStatement pre = null;       // 创建预编译语句对象，一般都是用这个而不用Statement
        ResultSet result = null;            // 创建一个结果集对象

        ArrayList<String []> al = new ArrayList<String []>();
        System.out.println("======================================================" + Integer.toString(al.size()) );
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String day_current = format.format( cale.getTime() );

        //从当天到第二天的数据量
        cale.add(Calendar.DAY_OF_MONTH, 1);
        String day_second = format.format( cale.getTime() );

        try
        {
            Class.forName(conf.getProperty("ClassName"));   //加载Oracle驱动程序

            System.out.println("开始尝试连接数据库！");

            String url = null;

            if(conf.getProperty("type").equals("oracle") ) {

                url = "jdbc:" + conf.getProperty("type")
                        + ":thin:@" + conf.getProperty("ip")
                        + ":" + conf.getProperty("port") + "/"
                        + conf.getProperty("service");
            } else if(conf.getProperty("type").equals("db2") ){
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

            // 预编译语句，“？”代表参数
            // 这个地方很坑，step设置为200以上，就抛出空指针异常，莫名其妙，可能跟java数组容器有关系。
            // 终于查出来，原因是 result.getObject(i).toString() 很容易导致空指针，这个方法不适合在这里使用
            int step = 2000;
            ResultSetMetaData rsmd;
            int field_count;
            String[] tmp_arr;

            int count = 0;
            String sql_count = "select count(*) from KKADMIN.V_HLW_SN_COMPONENTS v where v.MES_OUT_LINE_TIME>to_date('"
                                + day_current + "','yyyy-mm-dd') and v.MES_OUT_LINE_TIME< to_date('"
                                + day_second + "','yyyy-mm-dd')";

            pre = con.prepareStatement(sql_count);        // 实例化预编译语句
            result = pre.executeQuery();
            while (result.next()) {
                count = result.getInt(1);
            }

            System.out.println("查询结果总条数count(*)： " + Integer.toString(count));

            for(int index=0; index < count; index+=step) {
//                sql_page = "SELECT * FROM ( SELECT A.*, ROWNUM RN FROM ("
//                        + sql
//                        + ") A WHERE ROWNUM <="
//                        + Integer.toString(index+step)
//                        + ") WHERE RN >= "
//                        + Integer.toString(index);

                System.out.println("index: " + Integer.toString(index));

                String sql = "SELECT * FROM ( SELECT A.*, ROWNUM RN FROM (select * from KKADMIN.V_HLW_SN_COMPONENTS v where v.MES_OUT_LINE_TIME>to_date('"+
                        day_current + "','yyyy-mm-dd') and v.MES_OUT_LINE_TIME< to_date('"
                        + day_second + "','yyyy-mm-dd')) A WHERE ROWNUM <=" + Integer.toString(index+step) + ") WHERE RN >" + Integer.toString(index) ;

                //String sqlpage = "select * from KKADMIN.V_HLW_SN_COMPONENTS v where v.MES_OUT_LINE_TIME>=to_date('2016-08-17','yyyy-mm-dd') and v.MES_OUT_LINE_TIME< to_date('2016-08-18','yyyy-mm-dd')";
                //System.out.println("the sql_page is: " + sqlpage);

                System.out.println("sql1: \n" + sql);

                pre = con.prepareStatement(sql);        // 实例化预编译语句
                result = pre.executeQuery();            // 执行查询，注意括号中不需要再加参数
                rsmd = result.getMetaData();
                field_count = rsmd.getColumnCount();

                while (result.next()) {
                    //遍历每一行sql查询结果
                    tmp_arr = new String[field_count];

                    for (int i = 1; i < field_count; i++) {
                        try {
                            //不能用getObject，很容易导致空指针
                            // result.getObject(i).toString();
                            tmp_arr[i-1] = result.getString(i);
                        } catch (Exception e) {
                            e.printStackTrace();
                            System.out.println("i: " + Integer.toString(i) + " and field count： " + Integer.toString(field_count) );
                        }
                    }

                    //System.out.println(Arrays.asList(tmp_arr));
                    al.add(tmp_arr);
                }
            }


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

        return al;
    }


    public static ArrayList<String> oracle_arraylist(Properties conf, Calendar cale ){
        //跟函数oracle相似，当时返回值是ArrayList<String>，不是ArrayList<String[]>,主要是为了方便写入本地文件
        Connection con = null;              // 创建一个数据库连接
        PreparedStatement pre = null;       // 创建预编译语句对象，一般都是用这个而不用Statement
        ResultSet result = null;            // 创建一个结果集对象

        ArrayList<String> al = new ArrayList<String>();
        System.out.println("======================================================" + Integer.toString(al.size()) );
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String day_current = format.format( cale.getTime() );

        System.out.println("!!!!!!!!!!!!!!!! " + format.format( cale.getTime()));

        //从当天到第二天的数据量,注意参数cale其实是指针，不是变量，不能当成函数内部变量修改其值，否则导致日期只取到奇数。
        cale.add(Calendar.DAY_OF_MONTH, 1);

        String day_second = format.format( cale.getTime() );

        //为什么要减去一天呢，因为cale是指针，一旦修改，全局都改了，不能用简单变量赋值方法来解决，
        // 比如cale_second = cale,修改cale_second会有问题。最好的办法是再减掉一次，这样抓取日期就全了
        cale.add(Calendar.DAY_OF_MONTH, -1);


        String database_type =  conf.getProperty("type");
        String database_service = conf.getProperty("service");
        String sql_count = "";

        System.out.println("database_service: " + database_service + "\ndatabase_type : " + database_type);

        try{
            // 查出来，原因是 result.getObject(i).toString() 很容易导致空指针，这个方法不适合在这里使用
            int step = 10000;

            //根据数据库类型赋值不同的sql语句，如果是oracle，判断是判断是BMS还是MES系统，分别设置对应的sql
            //如果是db2类型，设置成另外一种sql语句
            if(database_type.compareTo("oracle") == 0){
                if(database_service.compareTo("orcl") ==0){
                    //sql: bms
//                  String sql_count = "select count(*) from bms_prod.bo_ht_bms_snlife_kkhlw_v v where v.ADDWHEN >to_date('"
//                    + day_current + "','yyyy-mm-dd') and v.ADDWHEN < to_date('"
//                    + day_second + "','yyyy-mm-dd')";
                    System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
                    //sql: bms test
                    sql_count = "select count(*) from bmstest.bo_ht_bms_snlife_kkhlw_v v where v.ADDWHEN >to_date('"
                            + day_current + "','yyyy-mm-dd') and v.ADDWHEN < to_date('"
                            + day_second + "','yyyy-mm-dd')";

                }else {
                    //sql:  ankang, kunkang,guankang
                    sql_count = "select count(*) from KKADMIN.V_HLW_SN_COMPONENTS v where v.MES_OUT_LINE_TIME>to_date('"
                    + day_current + "','yyyy-mm-dd') and v.MES_OUT_LINE_TIME< to_date('"
                    + day_second + "','yyyy-mm-dd')";
                }

            }else if(database_type == "db2"){
                sql_count = "select count(*) from staff";
            }


            System.out.println("sql_count: " + sql_count);

            int count = dataBase.excute_sql_count(sql_count);

            System.out.println("查询结果总条数count(*)： " + Integer.toString(count));
            String sql = "";
            for(int index=0; index < count; index += step) {
                System.out.println("分配查询 index: " + Integer.toString(index));
                if(database_type.compareTo("oracle") == 0){
                    if(database_service.compareTo("orcl") ==0){
                        //sql: bms
//                      String sql = "SELECT * FROM ( SELECT A.*, ROWNUM RN FROM (select * from bms_prod.bo_ht_bms_snlife_kkhlw_v v where v.MES_OUT_LINE_TIME>to_date('" +
//                        day_current + "','yyyy-mm-dd') and v.MES_OUT_LINE_TIME< to_date('"
//                        + day_second + "','yyyy-mm-dd')) A WHERE ROWNUM <=" + Integer.toString(index + step) + ") WHERE RN >" + Integer.toString(index);

                        //sql: bms test
                        sql = "SELECT * FROM ( SELECT A.*, ROWNUM RN FROM (select * from bmstest.bo_ht_bms_snlife_kkhlw_v v where v.ADDWHEN>to_date('" +
                                day_current + "','yyyy-mm-dd') and v.ADDWHEN< to_date('"
                                + day_second + "','yyyy-mm-dd')) A WHERE ROWNUM <=" + Integer.toString(index + step) + ") WHERE RN >" + Integer.toString(index);

                    }else {

                        //sql:  ankang,kunkang,guankang
                        sql = "SELECT * FROM ( SELECT A.*, ROWNUM RN FROM (select * from KKADMIN.V_HLW_SN_COMPONENTS v where v.MES_OUT_LINE_TIME>to_date('" +
                        day_current + "','yyyy-mm-dd') and v.MES_OUT_LINE_TIME< to_date('"
                        + day_second + "','yyyy-mm-dd')) A WHERE ROWNUM <=" + Integer.toString(index + step) + ") WHERE RN >" + Integer.toString(index);
                    }

                }else if(database_type == "db2"){
                    sql = "select * from staff";
                }


                System.out.println(new Date());
                al.addAll(dataBase.excute_sql_select(sql));
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        //关闭数据连接，已经放到dataBase内部
        //dataBase.dabase_close();

        return al;
    }


    public static void bdp_createtable(String tablename, String[] fields) {
        //BDPClient bdpClient = new BDPClient("8518580c4560d73b5bed0a09f2aec2fb");
        BDPClient bdpClient = new BDPClient("b0a9b37da708bc8bf4e774c682d2e4a1");
        // DS SaleData =bdpClient.createDs("SaleData");
        DS bdpDatabase = bdpClient.getDs("SaleData");

        List<Schema> schemas = new ArrayList<Schema>();

        //使用field的第一和第三个字段作为联合主键
        String[] uniqKey = new String[]{fields[0],fields[2]};
        for(int i=0; i< fields.length; i++) {
            schemas.add(new Schema(fields[i], "string", null, null));
        }

        try {
            Table table = bdpDatabase.getTable(tablename);
        }catch (Exception e){
            //e.printStackTrace();

            System.out.println(tablename + " is not exist,start create table !");
            Table terminal_tv_source_table = bdpDatabase.createTable(tablename, schemas, uniqKey, null);
        }
    }


    public static void bdp_update(String[][] result_array, String tablename,String[] fields) {
        //公网bdp的token
        //BDPClient bdpClient = new BDPClient("8518580c4560d73b5bed0a09f2aec2fb");

        //本地bdp的token
        BDPClient bdpClient = new BDPClient("b0a9b37da708bc8bf4e774c682d2e4a1");

        // DS SaleData =bdpClient.createDs("SaleData");
        DS bdpDatabase = bdpClient.getDs("SaleData");
        System.out.println("--------1 DS-----------");
        System.out.println(System.currentTimeMillis());

        Table terminal_tv_source_table = bdpDatabase.getTable(tablename);

        System.out.println("===== start insertDataByName =====");
        System.out.println(System.currentTimeMillis());

        terminal_tv_source_table.insertDataByName(fields, result_array);
        terminal_tv_source_table.commit();

        System.out.println("--------END-----------");
        System.out.println(System.currentTimeMillis());

    }





}
