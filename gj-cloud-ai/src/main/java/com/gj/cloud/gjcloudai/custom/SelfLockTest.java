package com.gj.cloud.gjcloudai.custom;

import java.util.concurrent.locks.Lock;

/**
 * @author houby@email.com
 * @date 2025/1/15
 */
public class SelfLockTest {
    public static Lock lock = new SelfLock();
    public static void main(String[] args) throws InterruptedException {
        Thread a = new Thread(() -> {
            testLock();
        });
        Thread b = new Thread(() -> {
            testLock();
        });
        a.setName("I am A");
        b.setName("I am B");

        a.start();
        Thread.sleep(100);
        b.start();
    }

    public static void testLock() {
        System.out.println("我想要抢到锁！！！Thread = " + Thread.currentThread().getName());
        lock.lock();
        try {
            System.out.println("我拿到了锁，可执行操作了，Thread =" + Thread.currentThread().getName());
//            while (true) {
//
//            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}
