package com.stars.javasamples.concurrent.reentrantlock;

import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockTest {
    private static final Lock lock = new ReentrantLock();

    public static void main(String[] args) {
        new Thread(() -> test(), "T1").start();
        new Thread(() -> test(), "T2").start();
        new Thread(() -> test(), "T3").start();

    }

    public static void test() {
        lock.lock();
        System.out.println(new Date() + "," + Thread.currentThread().getName() + " lock");
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
            System.out.println(new Date() + "," + Thread.currentThread().getName() + " unlock");
        }
    }
}
