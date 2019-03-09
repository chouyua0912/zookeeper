package z.learn.etcd.basic;

import com.google.common.base.Charsets;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * In most cases, directories for a key are automatically created. But there are cases where you will want to create a directory or remove one.
 *
 * Creating a directory is just like a key except you cannot provide a value and must add the dir=true parameter.
 *
 * curl http://127.0.0.1:2379/v2/keys/dir -XPUT -d dir=true
 *
 * {
 *     "action": "set",
 *     "node": {
 *         "createdIndex": 30,
 *         "dir": true,
 *         "key": "/dir",
 *         "modifiedIndex": 30
 *     }
 * }
 */
public class EtcdCreateDirectoryAndList {

    public static void main(String[] args) throws IOException {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPut post = new HttpPut("http://127.0.0.1:2379/v2/keys/foo_dir?");
        List<NameValuePair> nvps = new ArrayList<>();
        nvps.add(new BasicNameValuePair("dir", "true"));

        post.setEntity(new UrlEncodedFormEntity(nvps));

        printResponse(httpclient, post);

        HttpGet get = new HttpGet("http://127.0.0.1:2379/v2/keys/");
        printResponse(httpclient, get);
    }

    private static void printResponse(CloseableHttpClient httpclient, HttpRequestBase requestBase) throws IOException {
        CloseableHttpResponse response2 = httpclient.execute(requestBase);
        try {
            System.out.println(response2.getStatusLine());
            HttpEntity entity2 = response2.getEntity();

            System.out.println(IOUtils.toString(entity2.getContent(), Charsets.UTF_8));
        } finally {
            response2.close();
        }
    }
}
