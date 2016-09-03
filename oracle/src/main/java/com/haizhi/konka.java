package com.haizhi;

import java.sql.*;


public class konka{
    /**设置参数**/
    private static Connection conn = null;
    private static Statement stmt = null;
    private static ResultSet rs = null;

    /**构造方法，链接数据库**/
    public konka() {
        try{
            System.out.println("正在连接数据库..........");
            Class.forName("com.ibm.db2.jcc.DB2Driver");                //加载mysql驱动程序类

            String url = "jdbc:db2://172.40.1.1:50000/KONKA";          //url为连接字符串
            // String url = "jdbc:db2:  //172.40.1.1:50000/sample";    //url为连接字符串
            String user = "hlw_db";     //数据库用户名
            String pwd = "hlw_db";      //数据库密码

//            String url = "jdbc:db2://20.20.1.160:50000/sample";
//            String user = "db2inst1";
//            String pwd = "haizhi";

            conn=(Connection) DriverManager.getConnection(url,user,pwd);
            System.out.println("数据库连接成功！！！");
        } catch(Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            System.out.println("111111111111");
            //e.printStackTrace();
        }
    }


    public static void main(String[] args) throws SQLException {
        konka a = new konka();//实例化对象，作用是调用构造方法
        a.getClass();//无意义
        /**查询语句**/
        String sql="select * from staff";

        String sql2 = " SELECT \n" +
                "distinct\n" +
                "T1.FIX_ID AS FIX_ID,\n" +
                "T1.FIX_NUM AS FIX_NUM,--维修单号\n" +
                "(SELECT D.DEPT_NAME FROM OPENEAP.FRAMEDEPARTMENT D WHERE D.DEPT_CODE = T1.SC_APPROVE_BY_DEPT) AS DEPT_NAME,--派单分公司\n" +
                "(SELECT D.DEPT_NAME FROM OPENEAP.FRAMEDEPARTMENT D WHERE D.DEPT_CODE = T1.CUSTOMER_ID) AS DEPT_NAME,--接单网点\n" +
                "(SELECT E.SERIES_NAME FROM ECC_MBOM.PRODUCT_SERIES_PROP E WHERE E.SERIES_ID = T2.PRODUCT_ID and E.LANGUAGE_LOOKUP_CODE = 'CHINESE') AS SERIES_NAME,  --产品中文 \n" +
                "T2.EMEI_NUM ,  --EMEI号 \n" +
                "date(T2.BUY_DATE) as BUY_DATE,  --购买日期 \n" +
                "(SELECT C.FAULT_TYPE_NAME FROM ECC_FND.ECC_FND_FAULT_TYPE_PROP C WHERE C.FAULT_TYPE_ID = T4.FAULT_TYPE_ID and C.LANGUAGE_LOOKUP_CODE = 'CHINESE') AS FAULT_TYPE_NAME,  --故障类型中文\n" +
                "(SELECT B.FAULT_REASON_NAME FROM ECC_FND.ECC_FND_FAULT_REASON_PROP B WHERE B.FAULT_REASON_ID = T4.FAULT_REASON_ID and B.LANGUAGE_LOOKUP_CODE = 'CHINESE') AS FAULT_REASON_NAME,  --故障原因中文\n" +
                "date(T1.ACCEPT_DISPOSE_DATE) as ACCEPT_DISPOSE_DATE,  --受理时间 \n" +
                "date(T5.FIX_COMPLETED_DATE ) as FIX_COMPLETED_DATE,  --维修完成时间 \n" +
                "date(T1.CREATED_DATE) as CREATED_DATE, --维修单创建日期\n" +
                "T1.PURCHASER_NAME AS PURCHASER_NAME,--用户姓名\n" +
                "T1.ADDRESS AS ADDRESS,--用户地址\n" +
                "T1.PURCHASER_MOBILE AS PURCHASER_MOBILE, --用户联系电话\n" +
                "T1.USER_FAULT_DESC AS USER_FAULT_DESC,--故障描述\n" +
                "(SELECT DD.LOOKUP_TYPE_NAME FROM ECC_FND.ECC_FND_LOOKUP_CODE_PROP AS DD WHERE DD.LOOKUP_TYPE = 'SERVICE_TYPE' AND DD.LOOKUP_CODE LIKE UPPER(T1.SERVICE_LOOKUP_CODE) AND DD.LANGUAGE_LOOKUP_CODE='CHINESE') AS LOOKUP_TYPE_NAME --服务类型\n" +
                "FROM ECC_SFM.FIX_AUTO_DOCUMENT_FLOWAXIS T1 \n" +
                "LEFT JOIN ECC_SFM.FIX_PRODUCT_AUTO_MATERIAL T2 ON T1.FIX_ID  = T2.FIX_ID \n" +
                "LEFT JOIN ECC_SFM.FIX_FAULT_INFO T4 ON T1.FIX_ID  = T4.FIX_ID \n" +
                "LEFT JOIN ECC_SFM.FIX_SERVICE_INFO T5  ON T1.FIX_ID  = T5.FIX_ID \n" +
                "LEFT JOIN ECC_SFM.FIX_MODULE_INFO T7 ON (T1.FIX_ID  = T7.FIX_ID AND T7.DELETE_FLAG='F')\n" +
                "WHERE \n" +
                "T1.FIX_ID = '22483510' --测试单据\n" +
                "AND T2.SERIES_ID = 46 --产品系列为康佳电视";

        stmt = (Statement) conn.createStatement();
        stmt.execute(sql2);//执行select语句用executeQuery()方法，执行insert、update、delete语句用executeUpdate()方法。
        rs=(ResultSet) stmt.getResultSet();

        ResultSetMetaData rsmd = rs.getMetaData();
        int count = rsmd.getColumnCount();

        String TEMP[] = null;

        while(rs.next()){                               //当前记录指针移动到下一条记录上
            String test = rs.getString(1);              //得到当前记录的第一个字段(id)的值
            String name =rs.getString(2);               //得到第二个字段(name)的值

            System.out.println("name: " + name );

            for(int i =1; i <=count; i++){
                System.out.println("field: " + rs.getString(i));
//                TEMP = (String[])rs.getArray("TEMP").getArray();
//                System.out.println(TEMP);
            }

            //String psw = rs.getString("FIX_ID");  //得到(password)的值
            //System.out.println(Integer.toString(i)+" "+name+" "+psw);
        }

        rs.close();     //后定义，先关闭
        stmt.close();
        conn.close();   //先定义，后关闭



    }

}
