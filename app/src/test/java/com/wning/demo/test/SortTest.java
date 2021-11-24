package com.wning.demo.test;

import java.util.Arrays;

public class SortTest {
    public static void main(String[] args){

        int a[]={6,4,8,7,1,5,3,2};
        SortTest sort=new SortTest();
       // sort.quickSort(a,0,a.length-1);
        sort.mergeSort(a,0,a.length-1);
        System.out.println("result :"+ Arrays.toString(a));
    }

    /**
     * 快速排序
     * @param a
     * @param left
     * @param right
     */
    private void quickSort(int[] a, int left, int right) {
//        System.out.println("left :"+left+",right :"+right);
        if(left>=right){  //这行必须有，而且是大于等于
            return;
        }
        int temp,i,j,t;
        temp=a[left];
        i=left;
        j=right;

        while(i!=j){
            while(j>i&&a[j]>=temp){
                j--;
            }
            while(i<j&&a[i]<=temp){
                i++;
            }
            if(i<j){
                t=a[i];
                a[i]=a[j];
                a[j]=t;
            }
        }
        a[left]=a[i];
        a[i]=temp;
        System.out.println(i+" :"+j+" ,"+ Arrays.toString(a));

        quickSort(a,left,i-1);
        quickSort(a,i+1,right);
    }

    /**
     * 归并排序
     * @param a
     * @param low
     * @param high
     * @return
     */
    public static int[] mergeSort(int[] a,int low,int high){
        int mid = (low+high)/2;
        if(low<high){
            mergeSort(a,low,mid);
            mergeSort(a,mid+1,high);
            //左右归并
            merge(a,low,mid,high);
        }
        return a;
    }

    public static void merge(int[] a, int low, int mid, int high) {
        int[] temp = new int[high-low+1];
        int i= low;
        int j = mid+1;
        int k=0;
        // 把较小的数先移到新数组中
        while(i<=mid && j<=high){
            if(a[i]<a[j]){
                temp[k++] = a[i++];
            }else{
                temp[k++] = a[j++];
            }
        }
        // 把左边剩余的数移入数组
        while(i<=mid){
            temp[k++] = a[i++];
        }
        // 把右边边剩余的数移入数组
        while(j<=high){
            temp[k++] = a[j++];
        }
        // 把新数组中的数覆盖nums数组
        for(int x=0;x<temp.length;x++){
            a[x+low] = temp[x];
        }
    }
}
