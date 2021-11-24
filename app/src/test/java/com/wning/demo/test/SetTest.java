package com.wning.demo.test;


import java.util.HashSet;
import java.util.Iterator;

public class SetTest {
    public static void main(String[] args){

        HashSet set=new HashSet();
        for(int i=0;i<10;i++){
            set.add(i);
        }
        Iterator iterator = set.iterator();
        while(iterator.hasNext()){
            System.out.println(iterator.next());
        }

        int n1 = 1;
        long start_1 = System.nanoTime();
        for (int n = 0;n<1000;n++){
            n1 *=2;
        }
        System.out.println("normal:"+(System.nanoTime()- start_1));

        int n2 = 1;
        long start_2 = System.nanoTime();
        for(int n =0;n<1000;n++){
            n2 = n2<<1;
        }
        System.out.println("bit operation:"+(System.nanoTime()-start_2));


    }

}
