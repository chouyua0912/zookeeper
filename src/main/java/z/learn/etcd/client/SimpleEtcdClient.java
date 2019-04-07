package z.learn.etcd.client;

public class SimpleEtcdClient implements EtcdClient {

    @Override
    public EtcdResponse getKeyValue(String key) {
        return null;
    }

    @Override
    public EtcdResponse setKeyValue(String key, String value) {
        return null;
    }

    @Override
    public EtcdResponse deleteKey(String key) {
        return null;
    }

    @Override
    public EtcdResponse setKeyValueWithTtl(String key, String value, Integer ttl) {
        return null;
    }

    @Override
    public EtcdResponse refreshKeyTtl(String key, Integer ttl) {
        return null;
    }

    @Override
    public EtcdResponse watchKey(String key) {
        return null;
    }

    @Override
    public EtcdResponse atomicCreateSequenceKey(String key, String value) {
        return null;
    }

    @Override
    public EtcdResponse atomicCompareAndSwap(String key, String newValue, String oldValue) {
        return null;
    }

    @Override
    public EtcdResponse atomicCompareAndDelete(String key, String prevValue) {
        return null;
    }

    @Override
    public EtcdResponse createDirectory() {
        return null;
    }
}
