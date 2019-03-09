package z.learn.etcd.basic;

import com.google.common.base.Charsets;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;

/**
 * cd ~/workspace/etcd-v3.3.12-linux-amd64
 * etcd --version
 * etcd
 * <p>
 * ETCDCTL_API=3 etcdctl --endpoints=localhost:2379 put foo bar
 * ETCDCTL_API=3 etcdctl --endpoints=localhost:2379 get foo
 * <p>
 * $ etcdctl set /message Hello
 * Hello
 * $ curl -X PUT http://127.0.0.1:2379/v2/keys/message -d value="Hello"
 * {"action":"set","node":{"key":"/message","value":"Hello","modifiedIndex":4,"createdIndex":4}}
 * <p>
 * $ etcdctl get /message
 * Hello
 * $ curl http://127.0.0.1:2379/v2/keys/message
 * {"action":"get","node":{"key":"/message","value":"Hello","modifiedIndex":4,"createdIndex":4}}
 *
 * We can get the value that we just set in /message by issuing a GET request:
 *
 * curl http://127.0.0.1:2379/v2/keys/message
 *
 * {
 *     "action": "get",
 *     "node": {
 *         "createdIndex": 2,
 *         "key": "/message",
 *         "modifiedIndex": 2,
 *         "value": "Hello world"
 *     }
 * }
 */
public class EtcdGetKey {

    public static void main(String[] args) throws IOException {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet("http://127.0.0.1:2379/v2/keys/foo");
        CloseableHttpResponse response1 = httpclient.execute(httpGet);
        try {
            System.out.println(response1.getStatusLine());
            HttpEntity entity1 = response1.getEntity();
            // do something useful with the response body
            // and ensure it is fully consumed
            // EntityUtils.consume(entity1);

            System.out.println(IOUtils.toString(entity1.getContent(), Charsets.UTF_8));
        } finally {
            response1.close();
        }
    }
}
