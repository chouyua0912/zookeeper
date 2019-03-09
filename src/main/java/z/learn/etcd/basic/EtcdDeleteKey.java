package z.learn.etcd.basic;

import com.google.common.base.Charsets;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;

/**
 * You can remove the /message key with a DELETE request:
 *
 * curl http://127.0.0.1:2379/v2/keys/message -XDELETE
 *
 * {
 *     "action": "delete",
 *     "node": {
 *         "createdIndex": 3,
 *         "key": "/message",
 *         "modifiedIndex": 4
 *     },
 *     "prevNode": {
 *     	"key": "/message",
 *     	"value": "Hello etcd",
 *     	"modifiedIndex": 3,
 *     	"createdIndex": 3
 *     }
 * }
 */
public class EtcdDeleteKey {

    public static void main(String[] args) throws IOException {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpDelete delete = new HttpDelete("http://127.0.0.1:2379/v2/keys/foo");
        CloseableHttpResponse response = httpclient.execute(delete);
        try {
            System.out.println(response.getStatusLine());
            HttpEntity entity1 = response.getEntity();

            System.out.println(IOUtils.toString(entity1.getContent(), Charsets.UTF_8));
        } finally {
            response.close();
        }
    }
}
