package com.wning.demo.test.algorithm;

public class SteelCut {
    public static void main(String[] args){
        int[] p={1,5,8,9};
        System.out.println(cut(p,3));
        System.out.print(buttom_up_cut(p,3));
    }

    /**
     *
     * @param p 切成长度为index的子长度， 所能得到的收益，长度为1 收益1；长度为2 收益5...
     * @param n  钢管的总长度
     * @return 最大收益
     */
    public static int cut(int []p,int n)
    {
        if(n==0)
            return 0;
        int q=Integer.MIN_VALUE;
        for(int i=1;i<=n;i++)
        {
            q=Math.max(q, p[i-1]+cut(p, n-i));
        }
        return q;
    }

    public static int buttom_up_cut(int []p,int n)
    {
        int []r=new int[n+1];
        for(int i=1;i<=n;i++)
        {
            int q=-1;
            //①
            for(int j=1;j<=i;j++)
                q=Math.max(q, p[j-1]+r[i-j]);
            r[i]=q;
        }
        return r[n];
    }
}
