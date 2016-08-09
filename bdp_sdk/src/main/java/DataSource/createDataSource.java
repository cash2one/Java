package DataSource;

/**
 * Created by lixinxing on 16/7/27.
 */
import cn.bdp.sdk.BDPClient;
import cn.bdp.sdk.DS;
import org.junit.Test;

public class createDataSource {
    private DS ds;

    public DS getDs() {
        return ds;
    }

    public void setDs(String dataSourceName,String token ) {
        BDPClient client = new BDPClient(token);
        this.ds = client.createDs(dataSourceName);
    }

    @Test
    public void testDs(){
        BDPClient client = new BDPClient("b0a9b37da708bc8bf4e774c682d2e4a1");
        this.ds = client.createDs("TestSDK");
    }
}
