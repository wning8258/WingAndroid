package com.wning.demo.test.algorithm;

/**
 * 对于一组不同重量、不可分割的物品，我们需要选择一些装入背包，在满足背包最大重量限制的前提下，背包中物品总重量的最大值是多少呢？
 */
public class 动态规划 {
    public static void main(String[] args) {
        int maxW = Integer.MIN_VALUE; // 结果放到maxW中
        int[] weight = {2,2,4,6,3}; // 物品重量
        int n = 5; // 物品个数
        int w = 9; // 背包承受的最大重量
        boolean[][] states = new boolean[n][w+1]; // 默认值false

        maxW = knapsack(states,weight, n,w);
        System.out.println("maxW :" + maxW);
        for (int i = w; i >= 0; --i) { // 输出结果
            if (states[n-1][i] == true) {
                System.out.println("maxW :" + maxW);
            }
        }
    }



    //weight:物品重量，n:物品个数，w:背包可承载重量
    public static int knapsack(boolean[][]  states, int[] weight, int n, int w) {
        states[0][0] = true;  // 第一行的数据要特殊处理，可以利用哨兵优化
        if (weight[0] <= w) {
            states[0][weight[0]] = true;
        }
        for (int i = 1; i < n; ++i) { // 动态规划状态转移
            for (int j = 0; j <= w; ++j) {// 不把第i个物品放入背包
                if (states[i-1][j] == true) states[i][j] = states[i-1][j];
            }
            for (int j = 0; j <= w-weight[i]; ++j) {//把第i个物品放入背包
                if (states[i-1][j]==true) states[i][j+weight[i]] = true;
            }
        }
        for (int i = w; i >= 0; --i) { // 输出结果
            if (states[n-1][i] == true) {
                return i;
            }
        }
        return 0;
    }
}
