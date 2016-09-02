/**
 * Created by lixinxing on 16/7/26.
 */
import cn.bdp.sdk.BDPClient;
import cn.bdp.sdk.DS;
import cn.bdp.sdk.Table;
public class write {
    public static void main(String[] args){
        InitClinet();
    }

    public static void InitClinet(){
        BDPClient bdpClient = new BDPClient("b0a9b37da708bc8bf4e774c682d2e4a1");
        DS test_kj1_DS =bdpClient.getDs("test_kj1");

        System.out.println("--------1 DS-----------");
        System.out.println(System.currentTimeMillis());
        Table terminal_tv_source_table = test_kj1_DS.getTable("terminal_tv_source");

        String[] fields= {"id","sn","ip","regioncode","name","duration","create_date"};
        String[][] date = new String[][]{
                {"0","0","0","kangjiacode0","kangjiasn0","0","2016-06-23 02:41:50"},
                {"0","kangjiasn0","127.0.0.0","kangjiacode0","kangjiasn0","0","2016-06-23 02:41:50"}
        };

        System.out.println("--------3 insert cache-----------");
        System.out.println(System.currentTimeMillis());

        terminal_tv_source_table.insertDataByName(fields,date);
        terminal_tv_source_table.commit();

        System.out.println("--------END-----------");
        System.out.println(System.currentTimeMillis());

    }
}