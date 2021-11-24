package com.wning.demo.test;

import java.util.concurrent.atomic.AtomicReference;

public class ReentrantLockTest {
    private static ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) {
        lock.lock();

        test();

        lock.unlock();
    }

    private static void test() {
        lock.lock();

        System.out.println("test invoked...");

        test1();

        lock.unlock();
    }

    private static void test1() {
        lock.lock();

        System.out.println("test2 invoked...");

        lock.unlock();
    }

    private static class ReentrantLock {
        private AtomicReference<Thread> owner = new AtomicReference<>();
        private int state = 0;

        public void lock() {
            Thread currentThread = Thread.currentThread();

            if (currentThread == owner.get()) {
                state++;
                return;
            }

            for (; ; ) {
                System.out.println(owner);
                if (!owner.compareAndSet(null, currentThread)) {
                    return;
                }
            }
        }

        public void unlock() {
            Thread currentThread = Thread.currentThread();

            if (currentThread == owner.get()) {
                if (state != 0) {
                    state--;
                } else {
                    owner.compareAndSet(currentThread, null);
                }
            }
        }
    }
}
