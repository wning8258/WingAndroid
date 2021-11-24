package com.wning.demo.test;

class A{
    public synchronized void methodA(){  //对A的同一个实例加锁，如果不是同一个A的同一个实例，不会等待
        System.out.println("methodA");
        try {
            System.out.println("sleep 5000");
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void methodB(){  //锁定当前对象，与A效果相同
        synchronized (this) {
            System.out.println("methodB");
        }
    }

    public static synchronized void methodC(){}//对类加锁，即对所有此类的对象加锁

    public void methodD(){
        synchronized(A.class){}//对类加锁，即对所有此类的对象加锁,与C相同

    }

}


public class Synchronized {

    public static void main(String[] args){
        final A a=new A();
        Thread thread1=new Thread(new Runnable() {
            @Override
            public void run() {
                a.methodA();
                a.methodB();
            }
        });
        thread1.start();

        Thread thread2=new Thread(new Runnable() {
            @Override
            public void run() {
                a.methodA();
                a.methodB();
            }
        });
        thread2.start();
    }
}
