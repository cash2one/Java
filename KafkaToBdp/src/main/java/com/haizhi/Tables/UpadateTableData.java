package com.haizhi.Tables;

/**
 * Created by lixinxing on 16/7/27.
 */

import cn.bdp.sdk.BDPClient;
import cn.bdp.sdk.DS;
import cn.bdp.sdk.Table;
import cn.bdp.bean.Field;
import org.junit.Test;

import com.haizhi.thrift.*;
import com.haizhi.Tables.*;

public class UpadateTableData {
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

    //修改表的别名（在web端显示的名称)
    public void modifyTable(String alias) {
        initTable();
        table.modifyTable(alias);
        table.commit();
    }

    //修改字段属性
    /**
     * 参数:
     *field 字段名
     *type 字段类型，只能是string/number/date
     *uniqIndex 是否为联合主键，1是，0不是，可以为null
     *title 字段别名，可以为null
     */
    public void modifyField(String field,String type,String uniqIndex,String title)	{
        initTable();
        table.modifyField(field, type, uniqIndex, title);
        table.commit();
    }

    //向指定的字段插入数据，根据字段名
    public void insertDataByName(String[] fields, String[][] data){
        initTable();
        table.insertDataByName(fields,data);
        table.commit();
    }

    //根据fields列表中的字段名更新数据行,主键要写对
    public void updateDataByName(String[] fields, String[][] data){
        initTable();
        table.updateDataByName(fields,data);
        table.commit();
    }

    @Test
    public void testmodifyTable(){
        initTable();
        String alias = "konka table";
        table.modifyTable(alias);
        table.commit();
    }

    @Test
    public void testUpdateDataByName(){
        initTable();
        String[] fields= {"id","sn","ip","regioncode","create_date"};
        String[][] data = new String[][]{
                {"4","kangjiasn0","127.0.0.1","kangjiacode0","2016-06-23 02:41:50"},
                {"5","kangjiasn0","127.0.0.1","kangjiacode0","2016-06-23 02:41:50"}
        };

        UpadateTableData upadateTableData = new UpadateTableData();
        upadateTableData.updateDataByName(fields,data);
    }

    @Test
    public void testInsertDataByName(){
        initTable();

        String[] fields= {"id","sn","ip","regioncode","create_date"};
        String[][] data = new String[][]{
                {"2","kangjiasn0","127.0.0.0","kangjiacode0","2016-06-23 02:41:50"},
                {"3","kangjiasn0","127.0.0.0","kangjiacode0","2016-06-23 02:41:50"}
        };

        UpadateTableData upadateTableData = new UpadateTableData();
        upadateTableData.insertDataByName(fields,data);
    }

    @Test
    public void testModifyField(){
        initTable();
        table.modifyField("id", "number", "1", "id编号");
        table.commit();
    }

}
