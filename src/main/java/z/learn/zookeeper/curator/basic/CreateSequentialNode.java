package z.learn.zookeeper.curator.basic;

import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.CreateMode;
import z.learn.zookeeper.curator.common.ConnectionFactory;

import java.util.List;

import static z.learn.zookeeper.curator.common.Constant.*;

public class CreateSequentialNode {

    public static void main(String[] args) throws Exception {
        CuratorFramework client = ConnectionFactory.getClient();
        String result = client.create().withMode(CreateMode.EPHEMERAL_SEQUENTIAL).forPath(ephemeralSequentialPath, helloWorld.getBytes());

        System.out.println(result);
        System.out.println("-----------EPHEMERAL_SEQUENTIAL 不一定什么时候就被删除了-----------------");

        /**
         * 获取指定路径下的所有数据，类似与ls pa
         * th
         */
        List<String> strs = client.getChildren().forPath("/");
        strs.forEach(System.out::println);
    }
}
