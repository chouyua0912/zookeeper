package z.learn.zookeeper.curator.basic;

import org.apache.curator.framework.CuratorFramework;
import z.learn.zookeeper.curator.common.ConnectionFactory;

import static z.learn.zookeeper.curator.common.Constant.*;

public class GetNode {

    public static void main(String[] args) throws Exception {
        CuratorFramework client = ConnectionFactory.getClient();

        byte[] bytes = client.getData().forPath(path);
        String got = new String(bytes);
        System.out.println(got);
        System.out.println("Compare: " + helloWorld.equals(got));
    }
}
