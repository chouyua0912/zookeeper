package z.learn.zookeeper.curator.common;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

import static z.learn.zookeeper.curator.common.Constant.connection;

public class ConnectionFactory {

    public static CuratorFramework getClient() {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        CuratorFramework client = CuratorFrameworkFactory.newClient(connection, retryPolicy);
        client.start();
        return client;
    }
}
