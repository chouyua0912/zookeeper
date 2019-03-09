package z.learn.etcd.basic;

import com.google.common.base.Charsets;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * https://coreos.com/etcd/docs/latest/v2/api.html#setting-the-value-of-a-key
 * Let's set the first key-value pair in the datastore. In this case the key is /message and the value is Hello world.
 *
 * curl http://127.0.0.1:2379/v2/keys/message -XPUT -d value="Hello world"
 * {
 *     "action": "set",
 *     "node": {
 *         "createdIndex": 2,
 *         "key": "/message",
 *         "modifiedIndex": 2,
 *         "value": "Hello world"
 *     }
 * }
 * The response object contains several attributes:
 *
 *     action: the action of the request that was just made. The request attempted to modify node.value via a PUT HTTP request, thus the value of action is set.
 *
 *     node.key: the HTTP path to which the request was made. We set /message to Hello world, so the key field is /message. etcd uses a file-system-like structure to represent the key-value pairs, therefore all keys start with /.
 *
 *     node.value: the value of the key after resolving the request. In this case, a successful request was made that attempted to change the node's value to Hello world.
 *
 *     node.createdIndex: an index is a unique, monotonically-incrementing integer created for each change to etcd. This specific index reflects the point in the etcd state member at which a given key was created. You may notice that in this example the index is 2 even though it is the first request you sent to the server. This is because there are internal commands that also change the state behind the scenes, like adding and syncing servers.
 *
 *     node.modifiedIndex: like node.createdIndex, this attribute is also an etcd index. Actions that cause the value to change include set, delete, update, create, compareAndSwap and compareAndDelete. Since the get and watch commands do not change state in the store, they do not change the value of node.modifiedIndex.
 *
 * Response Headers
 *
 * etcd includes a few HTTP headers in responses that provide global information about the etcd cluster that serviced a request:
 *
 * X-Etcd-Index: 35
 * X-Raft-Index: 5398
 * X-Raft-Term: 1
 *
 *     X-Etcd-Index is the current etcd index as explained above. When request is a watch on key space, X-Etcd-Index is the current etcd index when the watch starts, which means that the watched event may happen after X-Etcd-Index.
 *     X-Raft-Index is similar to the etcd index but is for the underlying raft protocol.
 *     X-Raft-Term is an integer that will increase whenever an etcd master election happens in the cluster. If this number is increasing rapidly, you may need to tune the election timeout. See the tuning section for details.
 */
public class EtcdSetKey {

    public static void main(String[] args) throws IOException {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPut post = new HttpPut("http://127.0.0.1:2379/v2/keys/foo");
        List<NameValuePair> nvps = new ArrayList<>();
        nvps.add(new BasicNameValuePair("value", "test"));

        post.setEntity(new UrlEncodedFormEntity(nvps));
        CloseableHttpResponse response = httpclient.execute(post);
        try {
            System.out.println(response.getStatusLine());
            HttpEntity entity1 = response.getEntity();

            System.out.println(IOUtils.toString(entity1.getContent(), Charsets.UTF_8));
        } finally {
            response.close();
        }
    }
}
/**
 * 修改和创建（设置）的请求是一样的
 * Changing the value of a key
 *
 * You can change the value of /message from Hello world to Hello etcd with another PUT request to the key:
 *
 * curl http://127.0.0.1:2379/v2/keys/message -XPUT -d value="Hello etcd"
 *
 * {
 *     "action": "set",
 *     "node": {
 *         "createdIndex": 3,
 *         "key": "/message",
 *         "modifiedIndex": 3,
 *         "value": "Hello etcd"
 *     },
 *     "prevNode": {
 *     	"createdIndex": 2,
 *     	"key": "/message",
 *     	"value": "Hello world",
 *     	"modifiedIndex": 2
 *     }
 * }
 *
 * Here we introduce a new field: prevNode. The prevNode field represents what the state of a given node was before resolving the request at hand. The prevNode field follows the same format as the node, and is omitted in the event that there was no previous state for a given node.
 */
