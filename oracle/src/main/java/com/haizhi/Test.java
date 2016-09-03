package com.haizhi;

import javafx.scene.input.DataFormat;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;



public class Test {
    public static void main(String[] args) {
        //String sql = "SELECT * FROM ( SELECT A.*, ROWNUM RN FROM (select * from KKADMIN.V_HLW_SN_COMPONENTS v where v.MES_OUT_LINE_TIME>to_date('2016-08-25','yyyy-mm-dd') and v.MES_OUT_LINE_TIME< to_date('2016-08-26','yyyy-mm-dd')) A WHERE ROWNUM <=2000) WHERE RN >0";
        String sql = "SELECT * FROM OPENEAP.V_HLW A WHERE A.FIX_ID = '16370479'";
        System.out.println(new Date());

        ArrayList<String> al = dataBase.excute_sql_select(sql);
        System.out.println("11111111111111111");

        System.out.println(new Date());
        ArrayList<String> bl = dataBase.excute_sql_select(sql);

        System.out.println(new Date());
        ArrayList<String> cl = dataBase.excute_sql_select(sql);

        System.out.println(new Date());
        System.out.println(al.size());
        System.out.println(al.get(0));


/*        try {
*//*            EtlFileWriter.efw.file_write("333\n");
            EtlFileWriter.efw.file_write("44444444\n");
            EtlFileWriter.efw.file_close();
*//*

            ArrayList List = new ArrayList<String>();
            //test = new ArrayList<String>()
            List.add("111111111");
            List.add("2222222");
            List.add("3333333333333333");
            List.add("444444444444");
            List.add("55555555555");
            List.add("66666666666666");

            EtlFileWriter.efw.file_write_arr(List);
            EtlFileWriter.efw.file_flush();

            EtlFileWriter.efw.file_write_arr(List);
            EtlFileWriter.efw.file_flush();

        } catch (Exception e) {
            e.printStackTrace();
        }*/


//        String[] fields = {
//                "mes_project_id", "mes_mo_number", "mes_serial_number", "mes_work_day", "mes_area_name",
//                "mes_model_code", "mes_model_name", "mes_out_line_time", "mes_in_station_time", "mes_produce_step",
//                "mes_factory", "mes_keyp_jx", "mes_keyp_mz", "mes_keyp_pcbi", "mes_keyp_fj",
//                "mes_keyp_zdz", "mes_keyp_ydz"};

/*        try {
            Properties conf = new Properties();
            conf.load(new FileInputStream("kunkang.properties"));

            System.out.println(conf.getProperty("field"));
            String[] fields = conf.getProperty("field").split(",");

            System.out.println("===" + fields[1]);

        }catch (Exception e){
            e.printStackTrace();
        }*/



/*        String sql = "INSERT INTO `rsync_info` (`type`, `ip`, `port`, `code`, `msg`) VALUES ('db2', '172.20.4.131', '44', '3', 'sdsdfsdffff')";
        Properties conf = new Properties();

        try {
            conf.load(new FileInputStream("log.properties"));
            LogMysql abc  = new LogMysql(conf);
            abc.mysqlInsert(sql);

        }catch (Exception e) {
            e.printStackTrace();
        }*/



/*        //字符串转Calendar
        String datestr = "2002-01-29 04:37:21.453";
        //String datestr = "";
        Timestamp ts = Timestamp.valueOf(datestr);
        System.out.println(ts);

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = sdf.parse(datestr);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
        }catch (Exception e){
            e.printStackTrace();
        }*/


        /*
        kafka字节流消息的拼接
        byte[] test = new byte[100];
        String ip = "172.20.4.131";
        String name = "adv_log";
        String tmp=new String(test);
        tmp = ip + "|" + name + "|" + tmp;

        byte[] ret = tmp.getBytes();

        byte[] data1 = ip.getBytes();
        byte[] data2 = name.getBytes();

        byte[] dst = new byte[4+20+100];

        byte[] data3 = new byte[data1.length+data2.length];

        System.arraycopy(data1,0,dst,0, data1.length);
        System.arraycopy(data2,0,dst,20,data2.length);

        System.out.println(Arrays.toString(ret));

        String ss=new String(dst,0,20);
        System.out.println(ss);
        System.out.println(new String(dst,20,40));
        */

    }


    public static void testCalendar(){
        Calendar now = Calendar.getInstance();
        //now.setTime(new Date("2015-01-11"));

        //Calendar cale = new GregorianCalendar(2016,7,18);
        Calendar cale = new GregorianCalendar();
        DataFormat format1 = new DataFormat("2015-01-11");

        //Date date = new Date("2008-04-14");

        // System.out.println( Integer.toString( cale.compareTo(now) ) );

        //cale.add(Calendar.DAY_OF_MONTH, 1);

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        String today = format.format( cale.getTime() );
        System.out.println( "today: " + today );

        //System.out.println(format.format( cale.getTime()) + " compareTo " + format.format(now.getTime()) );


        String string2= null;
        try {
            FileReader fr = new FileReader("oracle_syncdata.log");
            BufferedReader br = new BufferedReader(fr);
            String str = null;
            while ((str = br.readLine()) != null) {
                string2= str;
            }
            br.close();
            fr.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(string2);

    }




    public static void testArraylist(){
        //        ArrayList al = new ArrayList();
//        int i = 0;
//        while (true) {
//            al.add(new ArrayList());
//            ((ArrayList)al.get(i)).add(new String("str"+i));
//            ((ArrayList)al.get(i)).add(new String("abc"+i));
//            i++;
//
//            //System.out.println("111111111111");
//
//            if (i>5) {
//                break;
//            }
//        }
//        System.out.println("al: " + al);


        ArrayList<ArrayList<String>> arrayList = new ArrayList();
        ArrayList<String> a = new ArrayList<String>();
        ArrayList<String> b = new ArrayList<String>();
        ArrayList<String> c = new ArrayList<String>();

        a.add("11111");
        a.add("11111");

        b.add("222222");
        b.add("222222");

        c.add("333333");
        c.add("333333");

        arrayList.add(a);
        arrayList.add(b);
        arrayList.add(c);


        //String[][] test = (String [][])arrayList.toArray(new String[arrayList.size()][0]);
//        String[][] test = (String [][])arrayList.toArray(new String[3][2]);
//
//        for(int i = 0; i < test.length; i++)
//        {
//            for(int j = 0; j < test[i].length; j++)
//            {
//                System.out.println(test[i][j]);
//            }
//        }

//        ArrayList<String []> arr = new ArrayList<String []>();
//        arr.add(new String[]{"a","b"});
//        arr.add(new String[]{"c","d"});
//        String [][] str_a  = (String[][]) arr.toArray(new String[0][0]);

//        String names[] = { "Georgianna", "Tenn", "Simon", "Tom" };
//        System.out.println(Arrays.asList(names));

    }



}
