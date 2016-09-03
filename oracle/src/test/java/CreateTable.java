import java.io.FileInputStream;
import java.util.Properties;

/**
 * Created by Administrator on 2016/8/31.
 */
public class CreateTable {
    public static void main(String[] args) throws Exception {
        Properties conf = new Properties();
        //conf.load(new FileInputStream("kunkang.properties"));
        //conf.load(new FileInputStream("guankang.properties"));
        //conf.load(new FileInputStream("ankang.properties"));
        //conf.load(new FileInputStream("bms.properties"));
        //conf.load(new FileInputStream("KONKA.properties"));

        conf.load(new FileInputStream("common.properties"));
        String tableName = conf.getProperty("tableName");
        String[] fields = conf.getProperty("fields").split(",");


/*
            //如果表不存在就建表
            String[] fields = {
                    "mes_project_id", "mes_mo_number", "mes_serial_number", "mes_work_day", "mes_area_name",
                    "mes_model_code", "mes_model_name", "mes_out_line_time", "mes_in_station_time", "mes_produce_step",
                    "mes_factory", "mes_keyp_jx", "mes_keyp_mz", "mes_keyp_pcbi", "mes_keyp_fj",
                    "mes_keyp_zdz", "mes_keyp_ydz"
            };

            common.bdp_createtable(tableName, fields);
            //System.exit(1);
*/

    }

}