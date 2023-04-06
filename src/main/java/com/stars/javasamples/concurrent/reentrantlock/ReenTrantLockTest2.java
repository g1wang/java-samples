package com.stars.javasamples.concurrent.reentrantlock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReenTrantLockTest2 {

    public static void main(String[] args) throws InterruptedException {
        final Lock lockA = new ReentrantLock();
        final Lock lockB = new ReentrantLock();
        Thread t1 = new Thread(new ThreadTest(lockA, lockB));
        Thread t2 = new Thread(new ThreadTest(lockB, lockA));
        t1.start();
        t2.start();
        TimeUnit.MILLISECONDS.sleep(2000);
        //给予一个中断回应
        t1.interrupt();
    }

    static class ThreadTest implements Runnable {
        Lock lockA;
        Lock lockB;

        public ThreadTest(Lock lockA, Lock lockB) {
            this.lockA = lockA;
            this.lockB = lockB;
        }

        @Override
        public void run() {
            try {
                lockA.lockInterruptibly();
                System.out.println("lock A lock");
                TimeUnit.MILLISECONDS.sleep(1000);
                lockB.lockInterruptibly();
                System.out.println("lock B lock");
            } catch (InterruptedException e) {
            } finally {
                lockA.unlock();
                System.out.println("lock A unlock");
                lockB.unlock();
                System.out.println("lock B unlock");
            }
        }
    }
}
