package com.haizhi;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Administrator on 2016/8/26.
 */


public class EtlFileWriter {
    String path;
    private static FileWriter writer;
    public static EtlFileWriter efw = new EtlFileWriter();

    public EtlFileWriter() {
        path = "datafile";
        File file = new File(path);

        try {
            if (!file.exists()) { file.createNewFile(); }
            writer = new FileWriter(file.getAbsoluteFile(), true);
            System.out.println("new FileWriter 构造函数");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public static void file_write(String msg) throws IOException {
        writer.write(msg);
        System.out.println("file_write finished: " + msg.trim());
    }


    public static void file_write_arr(ArrayList<String> list){
        /* 一次写入write_step条记录到磁盘 */
        System.out.println("enter file_write_arr list.size(): " + list.size());
        String ret = "";
        int write_step = 10000;
        int start = 0;
        int end = 0;
        try {
            int loop = list.size()/write_step;
            for(int j=0; j < loop; j++){
                ret = "";
                start = write_step * j;
                end = write_step * (j+1);
                for(int i = start ; i < end; i++) {
                    ret += list.get(i) + "\n";
                    //System.out.println(">>>>>>>>>>>>>>>>> " + ret);
                }
                System.out.println("write and flush to disk and loop index: " + j);
                writer.write(ret);
                writer.flush();
            }

            //扫尾
            ret = "";
            start = write_step * loop;
            end = list.size();
            for(int i = start ; i < end; i++) {
                ret += list.get(i) + "\n";
                //System.out.println(">>>>>>>>>>>>>>>>> " + ret);
            }
            writer.write(ret);
            writer.flush();

            //清理数组
            list.clear();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public static void file_flush() throws IOException{
        //必须刷新，才会保存到磁盘上
        writer.flush();
    }


    public static void file_close() throws IOException{
        //必须刷新，才会保存到磁盘上
        writer.close();
    }

}
