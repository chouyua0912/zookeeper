package z.learn.etcd.client;

import java.util.List;

public class EtcdResponse {
    private boolean success;
    //http header
    private Integer xEtcdIndex;
    private Integer xRaftIndex;
    private Integer xRaftTerm;
    //http body
    //successful result
    private String action;
    private String node;
    private String prevNode;
    //error result
    private String cause;
    private Integer errorCode;
    private Integer index;
    private String message;

    public enum ActionEnum {
        create,
        get,
        set,
        update,
        delete,
        expire,
        compareAndSwap,
        compareAndDelete
    }

    public static class EtcdNode {
        private Integer createdIndex;
        private Integer modifiedIndex;
        private String key;
        private String value;
        private Integer ttl;
        private String expiration;
        private Boolean dir;
        private List<EtcdNode> nodes;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Integer getxEtcdIndex() {
        return xEtcdIndex;
    }

    void setxEtcdIndex(Integer xEtcdIndex) {
        this.xEtcdIndex = xEtcdIndex;
    }

    public Integer getxRaftIndex() {
        return xRaftIndex;
    }

    void setxRaftIndex(Integer xRaftIndex) {
        this.xRaftIndex = xRaftIndex;
    }

    public Integer getxRaftTerm() {
        return xRaftTerm;
    }

    void setxRaftTerm(Integer xRaftTerm) {
        this.xRaftTerm = xRaftTerm;
    }

    public String getAction() {
        return action;
    }

    void setAction(String action) {
        this.action = action;
    }

    public String getNode() {
        return node;
    }

    void setNode(String node) {
        this.node = node;
    }

    public String getPrevNode() {
        return prevNode;
    }

    void setPrevNode(String prevNode) {
        this.prevNode = prevNode;
    }

    public String getCause() {
        return cause;
    }

    void setCause(String cause) {
        this.cause = cause;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public Integer getIndex() {
        return index;
    }

    void setIndex(Integer index) {
        this.index = index;
    }

    public String getMessage() {
        return message;
    }

    void setMessage(String message) {
        this.message = message;
    }
}
