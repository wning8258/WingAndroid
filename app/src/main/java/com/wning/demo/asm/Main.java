package com.wning.demo.asm;

/**
 * javac -cp . TestAnnotation.java Main.java
 */
public class Main {

    Main(){

    }

    @TestAnnotation
    public  void a(String[] args) {
        long start = System.currentTimeMillis();
        System.out.println("main");
        long end = System.currentTimeMillis();
        System.out.println("execute:" + (end - start));
    }
}
