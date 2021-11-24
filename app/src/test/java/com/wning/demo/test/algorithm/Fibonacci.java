package com.wning.demo.test.algorithm;

/**
 * Fibonacci (n) = 1;   n = 0
 *
 * Fibonacci (n) = 1;   n = 1
 *
 * Fibonacci (n) = Fibonacci(n-1) + Fibonacci(n-2)
 *
 * 0 1 1 2 3 5 8 13 21 34
 */
public class Fibonacci {

    public static void main(String[] args){
        int index=10;
        int result=fib(index);
        System.out.println("fib :"+result);
        result=fib2(index);
        System.out.println("fib2 :"+result);
        result=Fib(index);
        System.out.println("fib3 :"+result);
    }

    //自底向上的动态规划
    public static int fib(int n){
        if(n<=1){
            return n;
        }
        int memo[]=new int[n+1];
        memo[0]=0;
        memo[1]=1;
        for(int i=2;i<=n;i++){
            memo[i]=memo[i-1]+memo[i-2];
        }
        return memo[n];
    }
    //自底向上的动态规划 优化
    public static int fib2(int n){
        if(n<=1){
            return n;
        }
        int memo_i_2=0;
        int memo_i_1=1;
        int memo = 0;
        for (int i=2;i<=n;i++){
            memo=memo_i_2+memo_i_1;
            memo_i_2=memo_i_1;
            memo_i_1=memo;
        }
        return memo;
    }

    //自顶向下的备忘录法
    public static int Fib(int n){
        if(n<=1){
            return n;
        }
        int[] memo=new int[n+1];
        for (int i = 0; i <=n; i++) {
            memo[i]=-1;
        }
        return fib3(memo,n);
    }
    public static int fib3(int[] memo,int n){
        if(memo[n]!=-1){
            return memo[n];
        }
        if(n<=2){
            memo[n]=1;
        }else {
            memo[n] = fib3(memo, n - 1) + fib3(memo, n - 2);
        }
        return memo[n];
    }
}
