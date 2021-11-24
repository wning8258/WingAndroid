package com.wning.demo.test.algorithm;

/**
 * https://www.cnblogs.com/zhanghongfeng/p/11771758.html
 * 给定一个排序数组，需要在原地删除重复出现的元素，使得每个元素只出现一次，返回移除后数组的新长度
 *
 * 数组nums=[0,0,1,1,1,2,2,3,3,4]
 *
 * 函数应该返回新的长度为5，并且原数组nums的前五个元素被修改为0，1，2,3,4。不要使用额外的数组空间，必须在原地修改输入数据并在O(1)额外空间的条件下完成
 *
 *
 * 分析：
 *
 * 对于问题。如果不要求空间和时间要求的话，还是很easy的。
 * 但是要求O(1)的时间。因此必须得另外想办法解决。这个的前提是有序数组，因此一样的数字都是排列在一起的。
 * 这里可以用到两个指针位，一个是慢指针，一个是快指针。快指针在慢指针之前，当遇到重复的数字的时候，
 * 快指针一直往前移动，当遇到和慢指针不相同的数字的时候，慢指针移动，并用快指针的值来替代此时慢指针的值。最终slow指针之前的值都是不重复的，之后的都是重复的值。
 */
public class 有序数组去重 {
    public static void main(String[] args) {
        int[] nums =new int[]{0,0,1,1,1,2,2,3,3,4};

        有序数组去重 instance = new 有序数组去重();
        int newLen= instance.removeDuplicates(nums);

        System.out.println("newLen :" + newLen);
    }


    int removeDuplicates(int num[]){

        int slow, fast,len;
        slow = 0;
        fast =1;
        len = num.length;
        while(fast<len) {
            if (num[slow] != num[fast]) {
                slow++;
                num[slow] = num[fast];
            }
            fast ++;
        }
        for (int i = 0; i < num.length; i++) {
            System.out.println("new nums :" + num[i]);

        }
        return slow+1;//新数组的长度，所以要+1
    }
}

