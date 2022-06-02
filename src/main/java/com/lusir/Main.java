package com.lusir;

import java.util.HashMap;

public class Main {
    public static void main(String[] args) {
        String[] s1 = new String[]{"aaa", "abc", "ababab", "abcab"};
        for (int i = 0; i < s1.length; i++) {
            System.out.println(s1[i]);
            System.out.println(test(s1[i]));
        }

    }

    public static boolean test(String str) {
        if (str == null || str.length() <= 1) {
            return false;
        }

        int len = str.length();

        for (int i = 1; i <= len / 2; i++) {
            String subStr = str.substring(0, i);
            String lastStr = str.substring(i);
            while (lastStr.length() > 0) {
                if (!subStr.equals(lastStr.substring(0, i))) {
                    break;
                }

                lastStr = lastStr.substring(i);
                if (lastStr.substring(i).length() == 0) {
                    return true;
                }
            }
        }

        return false;
    }
}
