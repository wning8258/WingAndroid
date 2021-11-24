package com.wning.demo.test;

public class Synchronized2 {
    public static void main(String[] args){
        Ticket ticket=new Ticket();
        for(int i=0;i<=5;i++){
            Thread thread=new Thread(ticket);
            thread.start();
        }

    }
}


class Ticket implements Runnable{
    int num=10;
    @Override
    public synchronized void run() {
        while(true){
            if(num>0){
                try {
                    Thread.sleep(10);
                    System.out.println(Thread.currentThread().getName() + ".....sale...." + num--);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    }
}
