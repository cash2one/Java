package Tables;

import cn.bdp.bean.TableInfo;
import cn.bdp.sdk.BDPClient;
import cn.bdp.sdk.DS;
import cn.bdp.sdk.Table;
import org.junit.Test;

/**
 * Created by lixinxing on 16/7/27.
 */
public class GetTableData {
    private Table table;
    private String tableId;
    private String fieldName;
    private TableConfig tableConfig = new TableConfig();

    public void initTable(){
        BDPClient client = new BDPClient(this.tableConfig.getToken());
        DS dataSource = client.getDs(this.tableConfig.getDataSourceName());
        table = dataSource.getTable(this.tableConfig.getTableName());
        tableId = table.getTbId();
    }
    public TableInfo getInfo(){
        initTable();
        TableInfo info = table.getInfo();
        return info;
    }
    @Test
    public void testGetInfo(){
        initTable();
        System.out.println(table.getInfo().toString());
    }
    public Table getTable(){
        return this.table;
    }

}
