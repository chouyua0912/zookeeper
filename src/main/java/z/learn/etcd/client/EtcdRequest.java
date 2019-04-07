package z.learn.etcd.client;

import org.jboss.netty.handler.codec.http.HttpMethod;

public class EtcdRequest {
    private HttpMethod method;
    //url request
    private String prevValue;
    private Integer prevIndex;
    private Boolean prevExist;
    private Boolean recursive;
    //body
    private String key;
    private String value;
    private Integer ttl;
    private Boolean dir;

    public HttpMethod getMethod() {
        return method;
    }

    public void setMethod(HttpMethod method) {
        this.method = method;
    }

    public String getPrevValue() {
        return prevValue;
    }

    public void setPrevValue(String prevValue) {
        this.prevValue = prevValue;
    }

    public Integer getPrevIndex() {
        return prevIndex;
    }

    public void setPrevIndex(Integer prevIndex) {
        this.prevIndex = prevIndex;
    }

    public Boolean getPrevExist() {
        return prevExist;
    }

    public void setPrevExist(Boolean prevExist) {
        this.prevExist = prevExist;
    }

    public Boolean getRecursive() {
        return recursive;
    }

    public void setRecursive(Boolean recursive) {
        this.recursive = recursive;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Integer getTtl() {
        return ttl;
    }

    public void setTtl(Integer ttl) {
        this.ttl = ttl;
    }

    public Boolean getDir() {
        return dir;
    }

    public void setDir(Boolean dir) {
        this.dir = dir;
    }
}
