package demo;

/**
 * Created by Administrator on 2016/8/8.
 */

import cn.bdp.bean.Field;
import cn.bdp.bean.Schema;
import cn.bdp.bean.TableInfo;
import cn.bdp.sdk.BDPClient;
import cn.bdp.sdk.DS;
import cn.bdp.sdk.Table;

import java.util.Map;

import static cn.bdp.ClientConfig.ACCESS_TOKEN;


public class BdpMsg {
    public static void main(String[] args) {

        BDPClient client = new BDPClient(ACCESS_TOKEN);

        DS ds = client.getDs("dsName");

        Map<String, Table> tablesMap = ds.get_all_tables();

        Table table = ds.getTable("tableName");

        table.commit();
    }
}
