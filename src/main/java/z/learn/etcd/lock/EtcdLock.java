package z.learn.etcd.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * 继承AQS实现时候如何实现将其他JVM内挂起的线程唤醒？
 * 需要Watch
 */
public class EtcdLock implements Lock {
    private final String lockName;
    private final Sync sync;

    public EtcdLock(String lockName) {
        this.lockName = lockName;
        this.sync = new Sync();
    }

    private class Sync extends AbstractQueuedSynchronizer {

        void lock() {

        }

        @Override
        protected boolean tryAcquire(int arg) {
            //CAS这个key实现
            throw new UnsupportedOperationException();
        }

        @Override
        protected boolean tryRelease(int arg) {
            throw new UnsupportedOperationException();
        }

        @Override
        protected boolean isHeldExclusively() {
            throw new UnsupportedOperationException();
        }
    }


    @Override
    public void lock() {//应该是用watch实现？

    }

    @Override
    public void lockInterruptibly() throws InterruptedException {

    }

    @Override
    public boolean tryLock() {
        return false;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }

    @Override
    public void unlock() {

    }

    @Override
    public Condition newCondition() {
        throw new UnsupportedOperationException();
    }
}
