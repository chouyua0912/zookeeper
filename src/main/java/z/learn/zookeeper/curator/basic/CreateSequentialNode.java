package z.learn.zookeeper.curator.basic;

import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.CreateMode;
import z.learn.zookeeper.curator.common.ConnectionFactory;

import static z.learn.zookeeper.curator.common.Constant.*;

public class CreateSequentialNode {

    public static void main(String[] args) throws Exception {
        CuratorFramework client = ConnectionFactory.getClient();
        client.create().withMode(CreateMode.EPHEMERAL_SEQUENTIAL).forPath(ephemeralSequentialPath, helloWorld.getBytes());

        System.out.println(new String(client.getData().forPath(ephemeralSequentialPath)));

        System.out.println(client.getState());
    }
}
