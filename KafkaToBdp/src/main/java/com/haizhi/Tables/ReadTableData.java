package com.haizhi.Tables;

import cn.bdp.bean.PreviewInfo;
import cn.bdp.sdk.BDPClient;
import cn.bdp.sdk.DS;
import cn.bdp.sdk.Table;
import org.junit.Test;

/**
 * Created by lixinxing on 16/7/27.
 */

public class ReadTableData {
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
    //预览数据

    public PreviewInfo preview() {
        initTable();
        PreviewInfo info = table.preview();
        System.out.println(info);
        return info;
    }

    @Test
    public void testPreview(){
        initTable();
        ReadTableData readTableData = new ReadTableData();
        readTableData.preview();
    }
}
