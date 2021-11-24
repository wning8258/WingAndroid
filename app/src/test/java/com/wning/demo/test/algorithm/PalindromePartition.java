package com.wning.demo.test.algorithm;

import java.util.ArrayList;
import java.util.List;

/**
 * 给定一个字符串 s，将 s 分割成一些子串，使每个子串都是回文串。
 *
 * 返回 s 所有可能的分割方案。
 * 输入: "aab"
 * 输出:
 * [
 *   ["aa","b"],
 *   ["a","a","b"]
 * ]
 */
public class PalindromePartition {

    public static void main(String[] args){
        List<List<String>> list = partition("aab");
        System.out.println(list);
    }

    static List<List<String>> listList = new ArrayList<>();
    public static List<List<String>> partition(String s) {
        nextWords(s, 0, new ArrayList<String>());
        return listList;
    }
    private static void nextWords(String s, int index, List<String> list){
        if(index == s.length()){
            listList.add(new ArrayList<>(list));
            return;
        }
        for(int i = index; i < s.length(); i++){
            String subStr = s.substring(index, i + 1);
            if(isPalindrome(subStr)){
                list.add(subStr);
                nextWords(s, i + 1, list);
                list.remove(list.size() - 1);
            }
        }
    }
    private static boolean isPalindrome(String s){
        for(int i = 0; i <= s.length() / 2; i++){
            if(s.charAt(i) != s.charAt(s.length() - 1 - i)){
                return false;
            }
        }
        return true;
    }
}
