package com.wning.demo.test.algorithm;

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
        int a[]= {6,4,8,7,1,5,3,2};
        int kth = instance.findKth(a, 0, a.length - 1, 3);
        System.out.println(kth);
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
            return a[part];
        } else if(k > part - low + 1) {  //l在右边(k索引比较大，value比较小)
            return findKth(a, part + 1, high, k - part + low -1);
        } else {  //k在左边(k索引比较小，value比较大)
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
