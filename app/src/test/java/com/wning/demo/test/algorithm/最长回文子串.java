package com.wning.demo.test.algorithm;

public class 最长回文子串 {
    public static void main(String[] args) {
        最长回文子串 instance =new 最长回文子串();
        String s = "babad";
        String result = instance.longestPalindrome(s);
        System.out.println(s + "-->" +result);
    }

    public String longestPalindrome(String s) {
        int len = s.length();
        if (len <=1) {
            return s;
        }
        int begin = 0;
        int max = 1;
        boolean dp[][] = new boolean[len][len];
        //dp[i][j] =  value[i]== value[j]  && (dp[i+1][j-1] or j-1 -(i+1) +1 < 2 = j-i < 3
        for (int j = 0;j<len;j++) {
            for (int i = 0; i<=j; i++) {
                if (s.charAt(i) != s.charAt(j)) {
                    dp[i][j] = false;
                } else {
                    if (j-i < 3) {
                        dp[i][j] = true;
                    } else {
                        dp[i][j] = dp[i+1][j-1];
                    }
                }

                // 只要 dp[i][L] == true 成立，就表示子串 s[i..L] 是回文，此时记录回文长度和起始位置
                if (dp[i][j] && j - i + 1 > max) {
                    max = j - i + 1;
                    begin = i;
                }
            }
        }
        return s.substring(begin,begin+max);
    }
}
