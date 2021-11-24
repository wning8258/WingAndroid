package com.wning.demo.test.algorithm;

import java.util.List;

/**
 * 给定一个非空字符串 s 和一个包含非空单词列表的字典 wordDict，判定 s 是否可以被空格拆分为一个或多个在字典中出现的单词。
 *
 * 说明：
 *
 * 拆分时可以重复使用字典中的单词。
 * 你可以假设字典中没有重复的单词。
 * 示例 1：
 *
 * 输入: s = "leetcode", wordDict = ["leet", "code"]
 * 输出: true
 * 解释: 返回 true 因为 "leetcode" 可以被拆分成 "leet code"。
 */
class WordBreak {
    public static boolean wordBreak(String s, List<String> wordDict) {
        int length=s.length();
        boolean[] d=new boolean[length+1];
        d[0]=true;
        for(int i=1;i<=length;i++){
            for(int j=0;j<i;j++){
                if(d[j]&&wordDict.contains(s.substring(j,i))){
                    d[i]=true;
                    break;
                }
            }
        }
        return d[length];
    }
}
