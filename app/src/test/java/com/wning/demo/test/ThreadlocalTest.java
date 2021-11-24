package com.wning.demo.test;

public class ThreadlocalTest {
    public static void main(String[] args){
        final ThreadLocal<Student> threadLocal = new ThreadLocal(){
            @Override
            protected Student initialValue() {
                return new Student("NULL");
            }
        };
        threadLocal.set(new Student("张三"));
        System.out.println(Thread.currentThread().getName()+"-->"+threadLocal.get().toString());
        //开启一个子线程获取Student
        new Thread(){
            @Override
            public void run() {
                super.run();
                System.out.println(Thread.currentThread().getName()+"-->"+threadLocal.get().toString());
            }
        }.start();
        //再开启一个子线程set一个Student
        new Thread(){
            @Override
            public void run() {
                super.run();
                threadLocal.set(new Student("丽丽"));
                System.out.println(Thread.currentThread().getName()+"-->"+threadLocal.get().toString());
            }
        }.start();
        System.out.println(Thread.currentThread().getName()+"-->"+threadLocal.get().toString());
    }
    public static class Student{
        public Student(String name) {
            this.name = name;
        }
        public String name ;
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
        @Override
        public String toString() {
            return "Student{" +
                    "name='" + name + '\'' +
                    '}';
        }
    }
}
