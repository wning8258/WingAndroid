package com.wning.demo.test;

public class DeadLock {

     Object o1=new Object();
     Object o2=new Object();

    private void a(){

        synchronized (o1){
            System.out.println("a o1");
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (o2){
                System.out.println("a o2");
            }
        }
    }

    private  void b(){
        synchronized (o2){
            System.out.println("b o2");
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (o1){
                System.out.println("b o1");
            }
        }
    }
    public static void main(String[] args){  //死锁必须是多个线程访问同一个实例的加锁对象

        final DeadLock deadLock=new DeadLock();

        Thread thread1=new Thread(){
            @Override
            public void run() {
                deadLock.a();
            }
        };

        Thread thread2=new Thread(){
            @Override
            public void run() {
                deadLock.b();
            }
        };

        thread1.start();
        thread2.start();
    }
}
