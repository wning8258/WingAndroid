package com.wning.demo.test.asm;

public class Main {

    Main(){

    }

    @TestAnnotation
    public static void main(String[] args) {
        System.out.println("main");
    }
}
