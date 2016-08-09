package com.haizhi.Tables;

/**
 * Created by lixinxing on 16/7/27.
 */
public class TableConfig {
    //private String token ="8518580c4560d73b5bed0a09f2aec2fb";
    private String token ="b0a9b37da708bc8bf4e774c682d2e4a1";
    private String dataSourceName ="TestSDK";
    private String tableName = "terminal_tv";

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getDataSourceName() {
        return dataSourceName;
    }

    public void setDataSourceName(String dataSourceName) {
        this.dataSourceName = dataSourceName;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
}
