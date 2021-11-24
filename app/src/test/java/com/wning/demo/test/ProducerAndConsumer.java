package com.wning.demo.test;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ProducerAndConsumer {

    private static final int MAX_NUM = 10;
    private int num = 0;
    private ReentrantLock lock = new ReentrantLock();
    private Condition produceCondition = lock.newCondition();
    private Condition consumeCondition = lock.newCondition();

    public static void main(String[] args) {

        ProducerAndConsumer pac = new ProducerAndConsumer();

        new Thread(pac.new Producer()).start();
        new Thread(pac.new Consumer()).start();
    }


    class Consumer implements Runnable {

        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                lock.lock();
                try {
                    while (num == 0) {
                        System.out.println("consume is empty,wait");
                        consumeCondition.await();
                    }
                    num--;
                    System.out.println("consume num :" + num);
                    produceCondition.signalAll();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }
        }
    }


    class Producer implements Runnable {

        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                lock.lock();
                try {
                    while (num == MAX_NUM) {
                        System.out.println("produce full,wait");
                        produceCondition.await(); ////使当前线程加入 await() 等待队列中，并释放当锁，当其他线程调用signal()会重新请求锁。与Object.wait()类似。
                    }
                    num++;
                    System.out.println("produce num :" + num);
                    consumeCondition.signalAll();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }
        }
    }
}