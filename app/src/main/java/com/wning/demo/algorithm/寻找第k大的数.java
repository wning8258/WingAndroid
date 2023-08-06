package com.wning.demo.algorithm;

import java.util.Arrays;

/**
 * 这题应该是用快排的思想：例如找49个元素里面第24大的元素，那么按如下步骤： 1.进行一次快排（将大的
 * 元素放在前半段，小的元素放在后半段）,假设得到的中轴为p 2.判断 p - low + 1 == k ，如果成立，直接输出
 * a[p]，（因为前半段有k - 1个大于a[p]的元素，故a[p]为第K大的元素） 3.如果 p - low + 1 > k， 则第k大的元
 * 素在前半段，此时更新high = p - 1，继续进行步骤1 4.如果p - low + 1 < k， 则第k大的元素在后半段， 此时
 * 更新low = p + 1, 且 k = k - (p - low + 1)，继续步骤1. 由于常规快排要得到整体有序的数组，而此方法每次可
 * 以去掉“一半”的元素，故实际的复杂度不是o(nlgn), 而是o(n)。
 * ————————————————
 * 版权声明：本文为CSDN博主「快乐键盘侠」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
 * 原文链接：https://blog.csdn.net/qq_42843894/article/details/113351260
 */
public class 寻找第k大的数 {
    public static void main(String[] args) {
        寻找第k大的数 instance =new 寻找第k大的数();
//        int a[]= {6,4,8,7,1,5,3,2};
        int a[]= {3,2,1,5,6,4};

//        int result = instance.findKth(a, 0,a.length-1,2);
//        System.out.println(result);

        int kth = instance.findKthLargest(a, 2);
        System.out.println(kth);
    }

    public int findKthLargest(int[] nums, int k) {
        if (nums.length < k) {
            return -1;
        }
        return quicksort(nums,0,nums.length-1,k);
    }

    private int quicksort(int[] nums,int left,int right,int k) {
        if (left >right) {
            return -1;
        }
        int pos = partition(nums,left,right);
        System.out.println("part :" + pos + "," + Arrays.toString(nums));

        if (k == pos -left+1) {
            System.out.println("k就是要找的");

            return nums[pos];
        } else if (k > pos - left +1) {
            System.out.println("k在右边");

            return quicksort(nums,pos+1,right,k-(pos - left +1));
        } else {
            System.out.println("k在左边");

            return quicksort(nums,left,pos-1,k);
        }
    }

    private int partition(int a[],int left,int right) {
        if (left>right) {
            return -1;
        }
        int temp = a[left];
        int temp2;
        int i = left;
        int j = right;
        while(i!=j) {
            while(j>i && a[j]<= temp) j--;
            while(j>i && a[i]>= temp) i++;
            if (i<j) {  //i和j交换
                temp2=a[i];
                a[i] = a[j];
                a[j] = temp2;
            }
        }
        //和左边的标志交换
        a[left] = a[i];
        a[i] = temp;
        return i;
    }

    /**
     *
     * @param a
     * @param low
     * @param high
     * @param k 第几个大的
     * @return
     */
    public int findKth(int[] a, int low, int high, int k) {
        int part = partation(a, low, high);
        System.out.println("part :" + part + "," + Arrays.toString(a));
        if(k == part - low + 1) {  //k正好是要找的，比如k =3 ,part = 2 (从0开始索引的，所以后边需要+1)low =0
            System.out.println("k就是要找的");
            return a[part];
        } else if(k > part - low + 1) {  //l在右边(k索引比较大，value比较小)
            System.out.println("k在右边");
            return findKth(a, part + 1, high, k - part + low -1);
        } else {  //k在左边(k索引比较小，value比较大)
            System.out.println("k在左边");
            return findKth(a, low, part -1, k);
        }
    }
    public int partation(int[] a, int left, int right) {
        int key = a[left];
        int temp;
        int low = left;
        int high = right;
        while(low != high) {
            while(high> low && a[high] <= key) high--;
            while(low < high && a[low] >= key) low++;

            if(low<high){  //a[i]+a[j]交换位置
                temp = a[low];
               a[low] = a[high];
               a[high] = temp;
            }
        }
        a[left] = a[low];
        a[low] = key;
        return low;
    }
}
