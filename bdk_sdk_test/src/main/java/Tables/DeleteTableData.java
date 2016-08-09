package Tables;

import cn.bdp.sdk.BDPClient;
import cn.bdp.sdk.DS;
import cn.bdp.sdk.Table;
import org.junit.Test;

/**
 * Created by lixinxing on 16/7/27.
 */
public class DeleteTableData {
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

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }
    //根据where条件批量删除数据
    public void bulkDelete(String where){
        initTable();
        table.bulkDelete(where);
        table.commit();
    }

    //根据fields列表中的字段名删除数据行
    public void deleteDataByName(String[] fields, String[][] data){
        initTable();
        table.deleteDataByName(fields,data);
        table.commit();
    }
    //清空该工作表
    public void cleanTable(){
        initTable();
        table.clean();
        table.commit();
    }

    @Test
    public void testDeleteDataByName(){
        initTable();
        String[] fields= {"id","sn","ip","regioncode","create_date"};
        String[][] data = new String[][]{
                {"3","kangjiasn0","127.0.0.1","kangjiacode0","2016-06-23 02:41:50"}
        };
        table.deleteDataByName(fields,data);
        table.commit();
    }

    @Test
    public void testBulkDelete(){
        initTable();
        String where = "`id`>1";
        table.bulkDelete(where);
        table.commit();
    }
}
