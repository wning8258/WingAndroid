package com.wning.demo.test.algorithm;

/**
 * 二分法求一个数的平方根
 */
public class 开平方根 {
    public static void main(String[] args) {
        开平方根 instance = new 开平方根();
        for (int i = 16; i<=16; i ++) {
            int result = instance.mySqrt(i);
            System.out.println(i + "-->" + result);
        }
    }
    public int mySqrt(int x) {
       int left = 0;
       //  # 为了照顾到 1 把右边界设置为 x // 2 + 1
       int right = x /2 + 1;
       while(left < right) {
           int mid = (left + right +1)/2;
           int result =mid * mid;
           System.out.println("left :"+ left + ", right :" + right + ",mid :" + mid + ",result :" + result);

           if (result > x) {
               right = mid -1;
           }else {
               left = mid;
           }
           System.out.println("after left :"+ left + ", right :" + right);

       }
       return left;
    }
}
