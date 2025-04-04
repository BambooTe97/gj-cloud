package com.gj.cloud.gjcloudai.custom;

import java.io.Serial;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * 自定义锁，实现 synchronized 关键字的功能
 * @author houby@email.com
 * @date 2025/1/15
 */
public class SelfLock implements Lock {
    /**
     * AbstractQueuedSynchronizer 被称为队列同步器。简称 AQS
     */
    private static class Sync extends AbstractQueuedSynchronizer {
        @Serial
        private static final long serialVersionUID = -2692711503114507054L;

        /**
         * 加锁
         * @param arg the acquire argument. This value is always the one
         *        passed to an acquire method, or is the value saved on entry
         *        to a condition wait.  The value is otherwise uninterpreted
         *        and can represent anything you like.
         * @return
         */
        public boolean tryAcquire(int arg) {
            if (compareAndSetState(0, arg)) {
                setExclusiveOwnerThread(Thread.currentThread());
                return true;
            }
            return false;
        }

        /**
         * 释放锁
         * @param arg the release argument. This value is always the one
         *        passed to a release method, or the current state value upon
         *        entry to a condition wait.  The value is otherwise
         *        uninterpreted and can represent anything you like.
         * @return
         */
        public boolean tryRelease(int arg) {
            if (getState() != arg) {
                throw new IllegalMonitorStateException();
            }
            setState(0);
            return true;
        }

        Condition newCondition() {
            return new ConditionObject();
        }
    }

    public Sync sync = new Sync();
    public static final int lockFlag = 1;
    @Override
    public void lock() {
        sync.acquire(lockFlag);
    }

    @Override
    public boolean tryLock() {
        return sync.tryAcquire(lockFlag);
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return sync.tryAcquireNanos(lockFlag, unit.toNanos(time));
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {
        sync.acquireInterruptibly(lockFlag);
    }

    @Override
    public void unlock() {
        sync.release(lockFlag);
    }

    @Override
    public Condition newCondition() {
        return sync.newCondition();
    }
}
