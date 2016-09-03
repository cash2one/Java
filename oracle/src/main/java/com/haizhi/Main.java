package com.haizhi;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;


public class Main {
    public static void main(String[] args) {
        try {
            Properties conf = new Properties();
            //conf.load(new FileInputStream("kunkang.properties"));
            //conf.load(new FileInputStream("guankang.properties"));
            //conf.load(new FileInputStream("ankang.properties"));
            //conf.load(new FileInputStream("bms.properties"));
            //conf.load(new FileInputStream("KONKA.properties"));

            conf.load(new FileInputStream("common.properties"));
            String tableName = conf.getProperty("tableName");
            String[] fields = conf.getProperty("fields").split(",");

/*
            //如果表不存在就建表
            String[] fields = {
                    "mes_project_id", "mes_mo_number", "mes_serial_number", "mes_work_day", "mes_area_name",
                    "mes_model_code", "mes_model_name", "mes_out_line_time", "mes_in_station_time", "mes_produce_step",
                    "mes_factory", "mes_keyp_jx", "mes_keyp_mz", "mes_keyp_pcbi", "mes_keyp_fj",
                    "mes_keyp_zdz", "mes_keyp_ydz"
            };

            common.bdp_createtable(tableName, fields);
            //System.exit(1);
*/

            //从mysql取得最近一次次成功插入的时间，比如： 2016-8-11
            Calendar cale = get_successful_day(tableName);
            Calendar now  = new GregorianCalendar();

            //同步从最近一次成功同步的日期，到今天的康佳离线数据
            //rsync_data(cale, now, conf, tableName, fields);

            rsyncDataTodisk(cale, now, conf, tableName, fields);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static Calendar get_successful_date() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        String start_day_str = null;
        String str_tmp = null;

        //文件路径
        String path = "oracle_syncdata.log";
        try {
            //String start_day = null;

            FileReader fr = new FileReader(path);
            BufferedReader br = new BufferedReader(fr);

            while ((str_tmp = br.readLine()) != null) {
                //System.out.println("start_day_str: " + start_day_str.length() );
                if (str_tmp == null) {
                    break;
                } else if (str_tmp.length() == 0) {
                    continue;
                }
                start_day_str = str_tmp;
            }

            br.close();
            fr.close();
        }catch (Exception e){
            e.printStackTrace();
        }

        System.out.println("--------" + start_day_str);

        Calendar cale;
        if(start_day_str != null) {
            System.out.println("start_day_str " + start_day_str);
            String[] start_day = start_day_str.split("-");
            System.out.println(start_day[1]);
            cale = new GregorianCalendar(Integer.parseInt(start_day[0]), Integer.parseInt(start_day[1]) - 1, Integer.parseInt(start_day[2]), 23, 59);
        } else {
            cale = new GregorianCalendar();
            cale.add(Calendar.DAY_OF_MONTH, -1);
        }

//      System.out.println("1111111111111:" + format.format(cale.getTime()));
//      System.out.println("222222222222:" + format.format(now.getTime()));
        //不追加，直接写入一条记录，记载上次成功写入的日期，如果同步任务中断了，下次启动时，就从之前中断的那天开始同步数据

        return cale;
    }


    public static Calendar get_successful_day(String tableName) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        String start_day_str = null;
        String str_tmp = null;

        try {
            String sql = "SELECT day from "+ tableName +" where code=1 ORDER BY created_date desc limit 1";
            Properties conf = new Properties();

            try {
                conf.load(new FileInputStream("log.properties"));
                LogMysql conn  = new LogMysql(conf);
                start_day_str = conn.mysqlSelect(sql);
                System.out.println("get_successful_day : start_day_str || " + start_day_str);
            }catch (Exception e){
                e.printStackTrace();
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        Calendar cale = null;
        try {
            cale = new GregorianCalendar();
            //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            //mysql的rsync_info表的day字段对应的格式是： 2015-08-11
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            //如果数据库里没有查到最近一次成功更新的日期就使用当前时间的前一天的日期插入数据
             if(start_day_str == null || start_day_str == "" ) {
                cale.add(Calendar.DAY_OF_MONTH, -1);
            }else {
                //如果数据库有查到最近同步成功的日期，就取到对应的字符串，并转成Calendar
                Date date = sdf.parse(start_day_str);
                cale.setTime(date);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }

        return cale;
    }


    private static void rsync_data(Calendar cale, Calendar now, Properties conf,String tablename, String[] fields){
        String[][] result_array;
        ArrayList<String[]> result_arrlist;
        while (cale.compareTo(now) < 0) {
            result_arrlist = common.oracle(conf, cale);
            result_array = (String[][]) result_arrlist.toArray(new String[0][0]);
            System.out.println("===========sout the result=========================");
            System.out.println("result_array.length: " + Integer.toString(result_array.length));

            for(int i=0; i<30; i++) {
                if(result_array.length>0) {
                    System.out.println(Arrays.asList(result_arrlist.get(i)));
                }
            }

            //调用bdp接口插入数据
            common.bdp_update(result_array, tablename, fields);

//                System.out.println("start write to log file");
//                FileWriter writer = new FileWriter(path, false);
//                writer.write( format.format( cale.getTime()) + "\r\n");
//                writer.flush();
//                writer.close();

            try {
                //取得对应的数据库信息
                String type = conf.getProperty("type");
                String ip = conf.getProperty("ip");
                String port = conf.getProperty("port");

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String day = sdf.format(cale.getTime());

                //code为1表示插入成功
                int code = 1;
                String msg = "send message successful!";

                //插入日志到mysql，日志包含当前的康佳数据库信息，消息状态
                String sql = "INSERT INTO `rsync_info` (`type`, `ip`, `port`, `day`, `code`, `msg`) VALUES ('"
                        + type + "','"
                        + ip + "','"
                        + port + "','"
                        + day + "','"
                        + code + "','"
                        + msg
                        + "')";

                System.out.println("this sql will excute: \n" + sql);

                //开始插入日志
                Properties conf_log = new Properties();
                conf_log.load(new FileInputStream("log.properties"));
                LogMysql abc  = new LogMysql(conf_log);
                abc.mysqlInsert(sql);

            }catch (Exception e){
                e.printStackTrace();
            }

            //按天同步，每同步成功一次后，起始时间增加一天，
            cale.add(Calendar.DAY_OF_MONTH, 1);

        }
    }


    private static void rsyncDataTodisk(Calendar cale, Calendar now, Properties conf,String tablename, String[] fields) {
        String[][] result_array;
        ArrayList<String> result_arrlist;
        while (cale.compareTo(now) < 0) {
            //调用bdp接口插入数据
            //common.bdp_update(result_array, tablename, fields);

            try {
                /*
                通过oracle_arraylist查询数据库，查询结果保存到数组并返回
                通过file_write_arr写入到磁盘
                 */
                result_arrlist = common.oracle_arraylist(conf, cale);
                System.out.println("result_arrlist.size(): ++++++++++" + result_arrlist.size());
                EtlFileWriter.file_write_arr(result_arrlist);
                result_arrlist.clear();

                //取得对应的数据库信息
                String type = conf.getProperty("type");
                String ip = conf.getProperty("ip");
                String port = conf.getProperty("port");

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String day = sdf.format(cale.getTime());

                //code为1表示插入成功
                int code = 1;
                String msg = "send message successful!";

                //插入日志到mysql，日志包含当前的康佳数据库信息，消息状态
                String sql = "INSERT INTO "+ tablename + " (`type`, `ip`, `port`, `day`, `code`, `msg`) VALUES ('"
                        + type + "','"
                        + ip + "','"
                        + port + "','"
                        + day + "','"
                        + code + "','"
                        + msg
                        + "')";

                System.out.println("this sql will excute: \n" + sql);

                //开始插入日志到mysql
                Properties conf_log = new Properties();
                conf_log.load(new FileInputStream("log.properties"));
                LogMysql mysql_conn  = new LogMysql(conf_log);
                mysql_conn.mysqlInsert(sql);

            }catch (Exception e){
                e.printStackTrace();
            }

            //按天同步，每同步成功一次后，起始时间增加一天，
            cale.add(Calendar.DAY_OF_MONTH, 1);

        }
    }


}
