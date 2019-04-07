package z.learn.etcd.lock;

import z.learn.etcd.client.EtcdClient;
import z.learn.etcd.client.EtcdResponse;
import z.learn.etcd.client.SimpleEtcdClient;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

public class SimpleEtcdLock {
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
    private static final ExecutorService executor =
            Executors.newFixedThreadPool(4, r -> new Thread(r, "OperationLockThread-" + counter.incrementAndGet()));
    private static final EtcdClient client = new SimpleEtcdClient();
    private static final ConcurrentHashMap<String, InternalLock> holdingLocks = new ConcurrentHashMap<>();

    /**
     * 1.尝试CAS创建
     * 1.1 成功-》直接返回
     * 1.2 失败-》开始watch
     * 1.2.1 Watch失败、异常？
     * 2.Watch返回重新尝试CAS创建
     */

    public static void lock(String key) {
        Future f = executor.submit(() -> doLock(key));
        try {
            f.get();
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }

    public static void unlock(String key) {
        Future f = executor.submit(() -> doUnLock(key));
        try {
            f.get();
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }

    private static void doLock(String key) {
        InternalLock lock = holdingLocks.get(key);
        boolean tryAcquire = true;
        if (lock == null) {
            lock = new InternalLock(key, UUID.randomUUID().toString(), DEFAULT_TTL);
        } else {
            if (Thread.currentThread().getId() == lock.holderThreadId && MEMEBER_ID == lock.memeberId) {
                lock.count++;   // reentrant
            } else {
                tryAcquire = false;
            }
        }

        EtcdResponse response = null;
        while (true) {
            if (tryAcquire) {
                response = client.atomicCompareAndSwap(key, UUID.randomUUID().toString(), null);// 这里保证了只有一个对key的操作可以成功
            }
            if (response != null && response.isSuccess()) { //但是可能同时有多个key会在进行操作
                holdingLocks.put(lock.key, lock);
                return;
            }
            EtcdResponse watchResponse = client.watchKey(lock.key);
            if (watchResponse.isSuccess() && EtcdResponse.ActionEnum.delete.name().equals(watchResponse.getAction())) {


            } else {

                tryAcquire = false;
            }
        }
    }

    private static void doUnLock(String key) {
        InternalLock lock = new InternalLock(key, UUID.randomUUID().toString(), DEFAULT_TTL);
        while (true) {
            EtcdResponse response = client.atomicCompareAndSwap(key, UUID.randomUUID().toString(), null);
            if (response.isSuccess()) {

                return;
            }
            client.watchKey(lock.key);
        }
    }

    private static class InternalLock {
        private String memeberId;
        private long holderThreadId;
        private int count;
        private String key;
        private String value;
        private Integer ttl;

        InternalLock(String key, String value, Integer ttl) {
            this.key = key;
            this.value = value;
            this.ttl = ttl;
        }
    }
}
