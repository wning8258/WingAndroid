package com.wning.demo.test;

public class StaticTest {

    static int a=1;

    static A classA;

    static class A{

    }


    public static void main(String[] args){
        StaticTest test1=new StaticTest();
        StaticTest test2=new StaticTest();

        test1.a=2222;

        test1.classA=new A();

        System.out.println("test2 a:"+test2.a);
        System.out.println("test2 classA:"+test2.classA);
    }

}
