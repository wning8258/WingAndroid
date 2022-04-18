package com.wning.demo.test.test.asm;

/**
 * javac -cp . TestAnnotation.java Main.java
 */
public class Main {

    Main(){

    }

    @TestAnnotation
    public  void a(String[] args) {
        System.out.println("main");
    }
}
