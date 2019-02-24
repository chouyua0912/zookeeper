package z.learn.zookeeper.curator.basic;

import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.data.Stat;
import z.learn.zookeeper.curator.common.ConnectionFactory;

import static z.learn.zookeeper.curator.common.Constant.path;

public class DeletePersistentNode {

    public static void main(String[] args) throws Exception {
        CuratorFramework client = ConnectionFactory.getClient();

        Stat stat = client.checkExists().forPath(path);
        if (stat != null)
            client.delete().forPath(path);
    }
}
