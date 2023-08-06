package com.wning.demo;

import android.annotation.SuppressLint;

import java.time.LocalDateTime;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Synchronized3 {
    /**
     * 静态方法执行时间：2023-06-27T15:24:05.659779
     * 同一个对象 普通方法执行时间：2023-06-27T15:24:05.659776
     * 不同对象 普通方法执行时间：2023-06-27T15:24:05.659776
     * 不同对象 普通方法执行时间：2023-06-27T15:24:05.659781
     * 同一个对象 普通方法执行时间：2023-06-27T15:24:08.663511
     * 静态方法执行时间：2023-06-27T15:24:08.665684
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        // 创建线程池同时执行任务
        ExecutorService threadPool = Executors.newFixedThreadPool(10);

        // 执行两次静态方法（不能并发）
        threadPool.execute(() -> {
            staticMethod();
        });
        threadPool.execute(() -> {
            staticMethod();
        });

        // 不同对象执行两次普通方法（可以并发）
        threadPool.execute(() -> {
            Synchronized3 usage = new Synchronized3();
            usage.method("不同对象");
        });
        threadPool.execute(() -> {
            Synchronized3 usage2 = new Synchronized3();
            usage2.method("不同对象");

        });


        //同一对象执行两次普通方法 （不能并发）
        Synchronized3 usage3 = new Synchronized3();
        threadPool.execute(() -> {
            usage3.method("同一个对象");
        });
        threadPool.execute(() -> {
            usage3.method("同一个对象");

        });

    }

    /**
     * synchronized 修饰普通方法
     * 本方法的执行需要 3s（因为有 3s 的休眠时间）
     */
    @SuppressLint("NewApi")
    public synchronized void method(String s) {
        System.out.println(s +" 普通方法执行时间：" + LocalDateTime.now());
        try {
            // 休眠 3s
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * synchronized 修饰静态方法
     * 本方法的执行需要 3s（因为有 3s 的休眠时间）
     */
    @SuppressLint("NewApi")
    public static synchronized void staticMethod() {
        System.out.println("静态方法执行时间：" + LocalDateTime.now());
        try {
            // 休眠 3s
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
