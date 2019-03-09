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
 *
 Keys in etcd can be set to expire after a specified number of seconds. You can do this by setting a TTL (time to live) on the key when sending a PUT request:

 curl http://127.0.0.1:2379/v2/keys/foo -XPUT -d value=bar -d ttl=5

 {
 "action": "set",
 "node": {
 "createdIndex": 5,
 "expiration": "2013-12-04T12:01:21.874888581-08:00",
 "key": "/foo",
 "modifiedIndex": 5,
 "ttl": 5,
 "value": "bar"
 }
 }

 Note the two new fields in response:

 The expiration is the time at which this key will expire and be deleted.

 The ttl is the specified time to live for the key, in seconds.

 NOTE: Keys can only be expired by a cluster leader, so if a member gets disconnected from the cluster, its keys will not expire until it rejoins.

 Now you can try to get the key by sending a GET request:

 curl http://127.0.0.1:2379/v2/keys/foo

 If the TTL has expired, the key will have been deleted, and you will be returned a 100.

 {
 "cause": "/foo",
 "errorCode": 100,
 "index": 6,
 "message": "Key not found"
 }

 The TTL can be unset to avoid expiration through update operation:

 curl http://127.0.0.1:2379/v2/keys/foo -XPUT -d value=bar -d ttl= -d prevExist=true

 {
 "action": "update",
 "node": {
 "createdIndex": 5,
 "key": "/foo",
 "modifiedIndex": 6,
 "value": "bar"
 },
 "prevNode": {
 "createdIndex": 5,
 "expiration": "2013-12-04T12:01:21.874888581-08:00",
 "key": "/foo",
 "modifiedIndex": 5,
 "ttl": 3,
 "value": "bar"
 }
 }
 */
public class EtcdSetKeyTtl {

    public static void main(String[] args) throws IOException {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPut post = new HttpPut("http://127.0.0.1:2379/v2/keys/foo");
        List<NameValuePair> nvps = new ArrayList<>();
        nvps.add(new BasicNameValuePair("value", "test"));
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
