package com.haizhi.DataSource;

import cn.bdp.bean.Schema;
import cn.bdp.sdk.BDPClient;
import cn.bdp.sdk.DS;
import cn.bdp.sdk.Table;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by lixinxing on 16/7/27.
 */
public class DataSourceFunctions {
    private DS dataSource;
    //private String token ="8518580c4560d73b5bed0a09f2aec2fb";
    private String token ="b0a9b37da708bc8bf4e774c682d2e4a1";
    private String dataSourceName ="TestSDK";
    private String dataSourceId;

    public void initDataSource(){
        BDPClient client = new BDPClient(token);
        dataSource = client.getDs(dataSourceName);
    }

    //获取数据源ID
    public String getDataSourceId() {
        return dataSourceId;
    }

    public void setDataSourceId() {
        initDataSource();
        this.dataSourceId = dataSource.getDsId();
    }

    public String getDataSourceName() {
        return dataSourceName;
    }

    public void setDataSourceName(String dataSourceName) {
        this.dataSourceName = dataSourceName;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    //获取该数据源的所有工作表对象
    public Map<String, Table> getAllTables() {
        initDataSource();
        return dataSource.getAllTables();
    }

    //创建工作表
    /**
     * 参数:
     *name 工作表名
     *schema 描述表结构schema列表
     *uniqKey 联合主键数组，可以为null
     *title 工作表别名，可以为null
     */
    public Table createTable(String name, List<Schema> schema, String[] uniqKey, String title){
        initDataSource();
        return dataSource.createTable(name,schema,uniqKey,title);
    }

    //根据名字删除工作表
    public void deleteTableByName(String tableName){
        initDataSource();
        dataSource.deleteTable(tableName);
    }

    //根据名字获取工作表
    public Table getTableByName(String tableName){
        initDataSource();
        return dataSource.getTable(tableName);
    }

    //根据工作表名列表更新相应工作表,
    /**
     * 在提交更改后,对数据源中的工作表进行一次更新操作.
     * @param tbNames
     */
    public void updateTableByName(String[] tbNames){
        initDataSource();
        dataSource.update(tbNames);
    }

    //级联更新该数据源的下的所有工作表
    public void updateAllTable(){
        initDataSource();
        dataSource.updateAll();
    }

    @Test
    public void testDeleteDS(){
        initDataSource();
        dataSource.deleteTable("terminal_tv_source");
    }

    @Test
    public void testDSId(){
        initDataSource();
        System.out.println(dataSource.getDsId());
    }

    @Test
    public void testGetTableByName(){
        initDataSource();
        System.out.println(dataSource.getTable("TestTable"));
    }

    @Test
    public void testCreateTable(){
        initDataSource();
        List<Schema> schemas = new ArrayList<Schema>();
        Schema schema1 = new Schema("id", "string","主键","编号");
        Schema schema2 = new Schema("sn", "string",null,null);
        Schema schema3 = new Schema("ip", "string",null,null);
        Schema schema4 = new Schema("regioncode", "string",null,null);
        Schema schema5 = new Schema("name", "string",null,null);
        Schema schema6 = new Schema("duration", "string",null,null);
        Schema schema7 = new Schema("create_date", "string",null,null);

        schemas.add(schema1);
        schemas.add(schema2);
        schemas.add(schema3);
        schemas.add(schema4);
        schemas.add(schema5);
        schemas.add(schema6);
        schemas.add(schema7);

        String[] uniqKey = new String[]{"id"}; //?

        String title = "电视资源表";

        String name="terminal_tv";

        dataSource.createTable(name,schemas,uniqKey,title);
        dataSource.updateAll();

    }

}
