package z.learn.hazelcast;

import com.hazelcast.config.Config;
import com.hazelcast.config.MapConfig;
import com.hazelcast.core.DistributedObject;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.ILock;

import java.util.Collection;

public class ProgrammaticInstance {

    public static void main(String[] args) {
        Config config = new Config();
        config.getNetworkConfig().setPort(5900)
                .setPortAutoIncrement(false);

        MapConfig mapConfig = new MapConfig();
        mapConfig.setName("testMap")
                .setBackupCount(2)
                .setTimeToLiveSeconds(300);
        HazelcastInstance hazelcast = Hazelcast.newHazelcastInstance(config);
        ILock lock = hazelcast.getLock("test");

        if (lock.tryLock()) {
            try {
                System.out.println("Hello");
                Collection<DistributedObject> col = hazelcast.getDistributedObjects();
                col.forEach(System.out::println);
            } finally {
                lock.unlock();
            }
        }
    }
}
