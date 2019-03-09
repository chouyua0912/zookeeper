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
 * Refreshing key TTL
 *
 * Keys in etcd can be refreshed without notifying current watchers.
 *
 * This can be achieved by setting the refresh to true when updating a TTL.
 *
 * You cannot update the value of a key when refreshing it.
 *
 * curl http://127.0.0.1:2379/v2/keys/foo -XPUT -d value=bar -d ttl=5   创建key并且设置ttl
 * curl http://127.0.0.1:2379/v2/keys/foo -XPUT -d ttl=5 -d refresh=true -d prevExist=true  更新之前的key的ttl
 *
 * {
 *     "action": "set",
 *     "node": {
 *         "createdIndex": 5,
 *         "expiration": "2013-12-04T12:01:21.874888581-08:00",
 *         "key": "/foo",
 *         "modifiedIndex": 5,
 *         "ttl": 5,
 *         "value": "bar"
 *     }
 * }
 * {
 *    "action":"update",
 *    "node":{
 *        "key":"/foo",
 *        "value":"bar",
 *        "expiration": "2013-12-04T12:01:26.874888581-08:00",
 *        "ttl":5,
 *        "modifiedIndex":6,
 *        "createdIndex":5
 *     },
 *    "prevNode":{
 *        "key":"/foo",
 *        "value":"bar",
 *        "expiration":"2013-12-04T12:01:21.874888581-08:00",
 *        "ttl":3,
 *        "modifiedIndex":5,
 *        "createdIndex":5
 *      }
 * }
 */
public class EtcdRefreshKeyTtl {
    public static void main(String[] args) throws IOException {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPut post = new HttpPut("http://127.0.0.1:2379/v2/keys/foo");
        List<NameValuePair> nvps = new ArrayList<>();
        nvps.add(new BasicNameValuePair("refresh", "true"));//刷新key TTL，key必须已经存在
        nvps.add(new BasicNameValuePair("prevExist", "true"));//可以把没有设置ttl的key也更新成有ttl的
        nvps.add(new BasicNameValuePair("ttl", "60"));

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
