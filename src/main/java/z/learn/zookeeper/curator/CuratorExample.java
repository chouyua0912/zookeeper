package z.learn.zookeeper.curator;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.imps.CuratorFrameworkState;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 * 启动 zookeeper
 * cd /home/z/workspace/kafka_2.11-1.1.0
 * bin/zookeeper-server-start.sh config/zookeeper.properties
 * <p>
 * 2.x的curator才能跟zookeeper3.4兼容
 * cd /home/z/workspace/zookeeper-3.4.13
 * bin/zkServer.sh start
 */
public class CuratorExample {

    public static void main(String[] args) throws Exception {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        CuratorFramework client = CuratorFrameworkFactory.newClient("localhost:2181", retryPolicy);
        client.start();
        CuratorFrameworkState state = client.getState();
        System.out.println(state.name());

        String path = "/my";
        String helloWorld = "HelloWorld";
        client.create().forPath(path, helloWorld.getBytes());

        byte[] bytes = client.getData().forPath(path);
        System.out.println("Compare: " + helloWorld.equals(new String(bytes)));
    }
}
