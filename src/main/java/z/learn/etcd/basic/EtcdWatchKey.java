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
 * We can watch for a change on a key and receive a notification by using long polling. This also works for child keys by passing recursive=true in curl.
 *
 * In one terminal, we send a GET with wait=true :
 *
 * curl http://127.0.0.1:2379/v2/keys/foo?wait=true
 *
 * Now we are waiting for any changes at path /foo.
 *
 * In another terminal, we set a key /foo with value bar:
 *
 * curl http://127.0.0.1:2379/v2/keys/foo -XPUT -d value=bar
 *
 * The first terminal should get the notification and return with the same response as the set request:
 *
 * {
 *     "action": "set",
 *     "node": {
 *         "createdIndex": 7,
 *         "key": "/foo",
 *         "modifiedIndex": 7,
 *         "value": "bar"
 *     },
 *     "prevNode": {
 *         "createdIndex": 6,
 *         "key": "/foo",
 *         "modifiedIndex": 6,
 *         "value": "bar"
 *     }
 * }
 *
 * However, the watch command can do more than this. Using the index, we can watch for commands that have happened in the past. This is useful for ensuring you don't miss events between watch commands. Typically, we watch again from the modifiedIndex + 1 of the node we got.
 *
 * Let's try to watch for the set command of index 7 again:
 *
 * curl 'http://127.0.0.1:2379/v2/keys/foo?wait=true&waitIndex=7'
 *
 * The watch command returns immediately with the same response as previously.
 *
 * If we were to restart the watch from index 8 with:
 *
 * curl 'http://127.0.0.1:2379/v2/keys/foo?wait=true&waitIndex=8'
 *
 * Then even if etcd is on index 9 or 800, the first event to occur to the /foo key between 8 and the current index will be returned.
 *
 * Note: etcd only keeps the responses of the most recent 1000 events across all etcd keys. It is recommended to send the response to another thread to process immediately instead of blocking the watch while processing the result.
 * Watch from cleared event index
 *
 * If we miss all the 1000 events, we need to recover the current state of the watching key space through a get and then start to watch from the X-Etcd-Index + 1.
 *
 * For example, we set /other="bar" for 2000 times and try to wait from index 8.
 *
 * curl 'http://127.0.0.1:2379/v2/keys/foo?wait=true&waitIndex=8'
 *
 * We get the index is outdated response, since we miss the 1000 events kept in etcd.
 *
 * {"errorCode":401,"message":"The event in requested index is outdated and cleared","cause":"the requested history has been cleared [1008/8]","index":2007}
 *
 * To start watch, first we need to fetch the current state of key /foo:
 *
 * curl 'http://127.0.0.1:2379/v2/keys/foo' -vv
 *
 * < HTTP/1.1 200 OK
 * < Content-Type: application/json
 * < X-Etcd-Cluster-Id: 7e27652122e8b2ae
 * < X-Etcd-Index: 2007
 * < X-Raft-Index: 2615
 * < X-Raft-Term: 2
 * < Date: Mon, 05 Jan 2015 18:54:43 GMT
 * < Transfer-Encoding: chunked
 * <
 * {"action":"get","node":{"key":"/foo","value":"bar","modifiedIndex":7,"createdIndex":7}}
 *
 * Unlike watches we use the X-Etcd-Index + 1 of the response as a waitIndex instead of the node's modifiedIndex + 1 for two reasons:
 *
 *     The X-Etcd-Index is always greater than or equal to the modifiedIndex when getting a key because X-Etcd-Index is the current etcd index, and the modifiedIndex is the index of an event already stored in etcd.
 *     None of the events represented by indexes between modifiedIndex and X-Etcd-Index will be related to the key being fetched.
 *
 * Using the modifiedIndex + 1 is functionally equivalent for subsequent watches, but since it is smaller than the X-Etcd-Index + 1, we may receive a 401 EventIndexCleared error immediately.
 *
 * So the first watch after the get should be:
 *
 * curl 'http://127.0.0.1:2379/v2/keys/foo?wait=true&waitIndex=2008'
 *
 * Connection being closed prematurely
 *
 * The server may close a long polling connection before emitting any events. This can happen due to a timeout or the server being shutdown. Since the HTTP header is sent immediately upon accepting the connection, the response will be seen as empty: 200 OK and empty body. The clients should be prepared to deal with this scenario and retry the watch.
 */
public class EtcdWatchKey {

    public static void main(String[] args) throws IOException {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet("http://127.0.0.1:2379/v2/keys/foo?wait=true");
        CloseableHttpResponse response1 = httpclient.execute(httpGet);
        try {
            System.out.println(response1.getStatusLine());
            HttpEntity entity1 = response1.getEntity();
            // do something useful with the response body
            // and ensure it is fully consumed
            // EntityUtils.consume(entity1);
            System.out.println("watch before read entity.");
            //读取http响应的body的内容时候才会Block，当发生变化时候被block的线程会返回，读取到变更的内容
            //{"action":"set","node":{"key":"/foo","value":"testwatch","modifiedIndex":17,"createdIndex":17},"prevNode":{"key":"/foo","value":"testwatch","modifiedIndex":16,"createdIndex":16}}
            System.out.println(IOUtils.toString(entity1.getContent(), Charsets.UTF_8));
            System.out.println("watch after read entity.");
        } finally {
            response1.close();
        }
    }
}
