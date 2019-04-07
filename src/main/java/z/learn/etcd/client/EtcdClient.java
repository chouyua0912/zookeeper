package z.learn.etcd.client;


public interface EtcdClient {

    EtcdResponse getKeyValue(String key);

    EtcdResponse setKeyValue(String key, String value);

    EtcdResponse deleteKey(String key);

    EtcdResponse setKeyValueWithTtl(String key, String value, Integer ttl);

    EtcdResponse refreshKeyTtl(String key, Integer ttl);

    EtcdResponse watchKey(String key);

    EtcdResponse atomicCreateSequenceKey(String key, String value);

    EtcdResponse atomicCompareAndSwap(String key, String newValue, String oldValue);

    EtcdResponse atomicCompareAndDelete(String key, String prevValue);

    EtcdResponse createDirectory();
}
