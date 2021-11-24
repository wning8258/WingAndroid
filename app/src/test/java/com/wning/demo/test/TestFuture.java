package com.wning.demo.test;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by wning on 2018/3/23.
 */

public class TestFuture {
    public static void main(String[] args) throws Exception{
        ExecutorService executorService = Executors.newCachedThreadPool();
        Future<String> future = executorService.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
                return "结果是：" + new Random().nextInt(100);
            }
        });
        executorService.shutdown();
        String result = future.get();
        System.out.println(result);
    }
}
