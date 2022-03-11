package com.lusir;

public class Main {
    public static void main(String[] args) {
        Solution s = new Solution();
        int[][] b = new int[][]{{7,0},{4,4},{7,1},{5,0},{6,1},{5,2}};
        System.out.println(s.reconstructQueue(b));
        System.out.println(s.removeKdigits("10200",1));
        System.out.println(s.minWindow("abcdebdde","bde"));
    }
}
