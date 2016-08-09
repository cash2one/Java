/**
 * Created by lixinxing on 16/7/26.
 */
import cn.bdp.sdk.BDPClient;
import cn.bdp.sdk.DS;
import cn.bdp.sdk.Table;
import cn.bdp.bean.Schema;

import java.util.ArrayList;
import java.util.List;

public class TestWrite {
    public static void main(String[] args){
        InitClinet();
    }

    public static void InitClinet(){
        //BDPClient bdpClient = new BDPClient("8518580c4560d73b5bed0a09f2aec2fb");
        BDPClient bdpClient = new BDPClient("b0a9b37da708bc8bf4e774c682d2e4a1");
       // DS TestSDK_DS =bdpClient.createDs("TestSDK");
        DS TestSDK_DS =bdpClient.getDs("TestSDK");
        System.out.println("--------1 DS-----------");
        System.out.println(System.currentTimeMillis());

        //Table terminal_tv_source_table = test_kj1_DS.getTable("terminal_usb_upgrade_event");
        List<Schema> schemas = new ArrayList<Schema>();
        Schema schema1 = new Schema("id", "string",null,null);
        Schema schema2 = new Schema("sn", "string",null,null);
        Schema schema3 = new Schema("ip", "string",null,null);
        Schema schema4 = new Schema("regioncode", "string",null,null);
        Schema schema5 = new Schema("create_date", "string",null,null);
        String[] uniqKey = new String[]{"id"};
        schemas.add(schema1);
        schemas.add(schema2);
        schemas.add(schema3);
        schemas.add(schema4);
        schemas.add(schema5);

        Table terminal_tv_source_table = TestSDK_DS.createTable("TestTable", schemas, uniqKey, null);


        String[] fields= {"id","sn","ip","regioncode","create_date"};
        String[][] date = new String[][]{
                {"0","kangjiasn0","127.0.0.0","kangjiacode0","2016-06-23 02:41:50"},
                {"1","kangjiasn0","127.0.0.0","kangjiacode0","2016-06-23 02:41:50"}
        };

        System.out.println("--------3 insert cache-----------");
        System.out.println(System.currentTimeMillis());
        terminal_tv_source_table.insertDataByName(fields,date);
        terminal_tv_source_table.commit();
        System.out.println("--------END-----------");
        System.out.println(System.currentTimeMillis());

//        List<Schema> schemas = new ArrayList<Schema>();
//        Schema schema1 = new Schema("id", "string",null,null);
//        Schema schema2 = new Schema("sn", "string",null,null);
//        Schema schema3 = new Schema("ip", "string",null,null);
//        Schema schema4 = new Schema("regioncode", "string",null,null);
//        Schema schema5 = new Schema("create_date", "string",null,null);
//        String[] uniqKey = new String[]{"id"};
//        schemas.add(schema1);
//        schemas.add(schema2);
//        schemas.add(schema3);
//        schemas.add(schema4);
//        schemas.add(schema5);
//
//        Table terminal_tv_source_table = TestSDK_DS.createTable("terminal_tv_source_table", schemas, uniqKey, null);
//
//
//        String[] fields= {"id","sn","ip","regioncode","create_date"};
//        String[][] date = new String[][]{
//                {"0","kangjiasn0","127.0.0.0","kangjiacode0","2016-06-23 02:41:50"},
//                {"0","kangjiasn0","127.0.0.0","kangjiacode0","2016-06-23 02:41:50"}
//        };
//
//        System.out.println("--------3 insert cache-----------");
//        System.out.println(System.currentTimeMillis());
//        terminal_tv_source_table.insertDataByName(fields,date);
//        terminal_tv_source_table.commit();
//        System.out.println("--------END-----------");
//        System.out.println(System.currentTimeMillis());

    }
}
