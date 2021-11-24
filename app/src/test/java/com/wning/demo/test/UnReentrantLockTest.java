package com.wning.demo.test;

import java.util.concurrent.atomic.AtomicReference;

public class UnReentrantLockTest {
    private static UnreentrantLock lock = new UnreentrantLock();

    public static void main(String[] args) {
        lock.lock();

        test();

        lock.unlock();
    }

    private static void test() {
        lock.lock();

        System.out.println("test invoked...");

        lock.unlock();
    }

    private static class UnreentrantLock {
        private AtomicReference<Thread> owner = new AtomicReference<>();

        public void lock() {
            Thread currentThread = Thread.currentThread();

            //重入，会陷入死锁
            for (; ; ) {
                System.out.println(owner);
                if (owner.compareAndSet(null, currentThread)) {
                    System.out.println(currentThread + " locked...");
                    return;
                }
            }
        }

        public void unlock() {
            Thread currentThread = Thread.currentThread();

            owner.compareAndSet(currentThread, null);

            System.out.println(currentThread + " unlock...");
        }
    }
}
