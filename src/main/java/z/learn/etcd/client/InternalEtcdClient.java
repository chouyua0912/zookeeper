package z.learn.etcd.client;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

class InternalEtcdClient {
    private static final String BASE_DIR = "CLUSTERS";
    private static final String MEMEBER_ID;

    static {
        String id;
        try {
            id = InetAddress.getLocalHost().toString();
        } catch (UnknownHostException e) {
            e.printStackTrace();
            id = "local";
        }
        MEMEBER_ID = id;
    }

    private static final int DEFAULT_TTL = 60;
    private static final AtomicInteger counter = new AtomicInteger(0);
    private static final ScheduledExecutorService executor =
            Executors.newScheduledThreadPool(4, r -> new Thread(r, "InternalOperationThread-" + counter.incrementAndGet()));

    private static volatile InternalEtcdClient client;

    private static void init() {
        // 注册自己，然后定期刷新ttl
        if (client == null) {
            synchronized (InternalEtcdClient.class) {
                if (client == null) {
                    client = new InternalEtcdClient();
                }
            }
        }
    }

    private void keepLived() {
    }

    static EtcdResponse send(EtcdRequest request) {
        if (client == null)
            init();
        HttpRequestBase httpReq = new HttpPost();


        throw new RuntimeException();
    }

    /**
     * 1.注册自己
     * 2.获取集群列表
     * 2.定时刷新
     */
    private InternalEtcdClient() {
        EtcdRequest registerReq = new EtcdRequest();

        executor.scheduleAtFixedRate(client::keepLived, DEFAULT_TTL / 2, DEFAULT_TTL / 2, TimeUnit.SECONDS);
    }
}
