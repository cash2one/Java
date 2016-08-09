package BDPClient;

/**
 * Created by lixinxing on 16/7/27.
 */
import cn.bdp.sdk.BDPClient;
import org.junit.Test;

public class CreateBDPClient {
    private String accessToken;
    public BDPClient CreateBDPClient(){
        BDPClient client = new BDPClient(accessToken);
        return client;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    @Test
    public void testClient(){
        String token= "b0a9b37da708bc8bf4e774c682d2e4a1";
        BDPClient client = new BDPClient(token);
        System.out.println(client);
        System.out.println(client.getAllDs());
    }
}
