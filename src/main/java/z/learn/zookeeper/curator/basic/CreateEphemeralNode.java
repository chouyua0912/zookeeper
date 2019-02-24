package z.learn.zookeeper.curator.basic;

import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import z.learn.zookeeper.curator.common.ConnectionFactory;

import static z.learn.zookeeper.curator.common.Constant.*;

public class CreateEphemeralNode {

    public static void main(String[] args) throws Exception {
        CuratorFramework client = ConnectionFactory.getClient();
        client.create().withMode(CreateMode.EPHEMERAL).forPath(ephemeralPath, ephemeralData.getBytes());

        byte[] bytes = client.getData().forPath(ephemeralPath);
        String got = new String(bytes);
        System.out.println(got);
        System.out.println("Compare: " + ephemeralData.equals(got));

        client.close();

        // 断开连接之后，ephemeral数据会被删掉。
        client = ConnectionFactory.getClient();
        try {
            client.getData().forPath(ephemeralPath);
        } catch (KeeperException.NoNodeException ex) {
            System.out.println("Node not found for path: " + ephemeralPath);
        }
    }
}
