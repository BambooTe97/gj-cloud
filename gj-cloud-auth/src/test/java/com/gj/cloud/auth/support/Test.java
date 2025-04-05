package com.gj.cloud.auth.support;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author houby@email.com
 * @date 2025/1/5
 */
public class Test {
    private static final AtomicInteger i = new AtomicInteger();
    private static Object obj = new Object();
    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(Test::print);
        thread.start();
//        thread.join(1000);

        Thread thread1 = new Thread(Test::print);
        thread1.start();
//        thread1.join(1000);

        Thread thread2 = new Thread(Test::print);
        thread2.start();
        thread2.join(1000);
    }

    private static void print() {
        while (i.get() < 100) {
            synchronized (obj) {
                obj.notifyAll();
                System.out.println(Thread.currentThread().getName() + "::" + i.getAndIncrement());
                try {
                    obj.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
