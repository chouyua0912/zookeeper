package z.learn.zookeeper.curator.basic;

import org.apache.curator.framework.CuratorFramework;
import z.learn.zookeeper.curator.common.ConnectionFactory;

import static z.learn.zookeeper.curator.common.Constant.*;

public class CreatePersistentNode {

    public static void main(String[] args) throws Exception {
        CuratorFramework client = ConnectionFactory.getClient();
        client.create().forPath(path, helloWorld.getBytes());

        client.setData().forPath(path, "NewData".getBytes());
    }
}
