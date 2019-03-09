package z.learn.etcd.basic;

import com.google.common.base.Charsets;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.CountDownLatch;

/**
 * 异步watch key
 * 可以同时对很多key进行watch
 */
public class EtcdWatchKeyAsync {

    public static void main(String[] args) throws InterruptedException {
        CloseableHttpAsyncClient httpclient = HttpAsyncClients.createDefault();
        /**
         * 异步客户端需要首先启动
         * 主线程结束之后程序还是会继续运行，async client启动的pool-1-thread还存在
         */
        httpclient.start();

        Thread.sleep(60 * 1000);
        System.out.println("Http async client started.");

        HttpGet httpGet = new HttpGet("http://127.0.0.1:2379/v2/keys/foo?wait=true");
        final CountDownLatch latch1 = new CountDownLatch(1);
        httpclient.execute(httpGet, new FutureCallback<HttpResponse>() {
            @Override
            public void completed(HttpResponse result) {
                System.out.println("On completed triggered. " + new Date());
                latch1.countDown();
                System.out.println(result.getStatusLine());
                HttpEntity entity1 = result.getEntity();
                try {
                    System.out.println(IOUtils.toString(entity1.getContent(), Charsets.UTF_8));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void failed(Exception ex) {
                latch1.countDown();
                System.out.println("exception:" + ex);
            }

            @Override
            public void cancelled() {
                latch1.countDown();
                System.out.println("cancelled");
            }
        });
        System.out.println("wait async start, " + new Date() + ".");
        latch1.await();
        System.out.println("wait async end, " + new Date() + ".");
    }
}
