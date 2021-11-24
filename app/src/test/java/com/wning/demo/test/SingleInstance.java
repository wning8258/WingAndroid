package com.wning.demo.test;

public class SingleInstance {
//    private static volatile SingleInstance instance;  //可见性
//
//    private SingleInstance(){}
//
//    public static SingleInstance getInstance(){
//        if(instance==null){
//            synchronized (SingleInstance.class){
//                if(instance==null){
//                    instance=new SingleInstance();
//                }
//            }
//        }
//        return instance;
//    }

    private SingleInstance(){}

    private static class SingleInstanceHolder{
        private static final SingleInstance INSTANCE=new SingleInstance();
    }

    public static SingleInstance getInstance(){
        return SingleInstanceHolder.INSTANCE;
    }
}
