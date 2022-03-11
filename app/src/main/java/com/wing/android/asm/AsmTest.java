package com.wing.android.asm;

public class AsmTest {
    @AsmMethod
    public static void main(String[] args) throws InterruptedException {
//        long startTime = System.currentTimeMillis();
        Thread.sleep(2000);
//        long endTime = System.currentTimeMillis();
//        System.out.println("execute time = " + (endTime - startTime) + " ms");
    }
}
