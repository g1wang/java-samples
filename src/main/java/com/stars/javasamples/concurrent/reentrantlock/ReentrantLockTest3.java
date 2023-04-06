package com.stars.javasamples.concurrent.reentrantlock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockTest3 {
    public static void main(String[] args) throws InterruptedException {
        final Lock lock1 = new ReentrantLock();
        final Lock lock2 = new ReentrantLock();
        new Thread(new ThreadTest3(lock1, lock2)).start();
        TimeUnit.MILLISECONDS.sleep(300);
        new Thread(new ThreadTest3(lock2, lock1)).start();

    }

    static class ThreadTest3 implements Runnable {

        Lock lock1;
        Lock lock2;

        public ThreadTest3(Lock lock1, Lock lock2) {
            this.lock1 = lock1;
            this.lock2 = lock2;
        }

        @Override
        public void run() {
            try {
                if (lock1.tryLock(4, TimeUnit.SECONDS)) {
                    TimeUnit.MILLISECONDS.sleep(500);
                    System.out.println("lock 1 lock");
                }
                if (lock2.tryLock(4, TimeUnit.SECONDS)) {
                    System.out.println("lock 2 lock");
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                lock1.unlock();
                lock2.unlock();
            }
        }
    }
}
