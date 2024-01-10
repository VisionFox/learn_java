package com.test;

import com.lusir.Solution;

import java.lang.ref.SoftReference;
import java.util.*;
import java.util.concurrent.*;

public class Main {

    public static void main(String[] args) {

    }

    public int lengthOfLastWord(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }

        int ans = 0;
        for (int i = s.length() - 1; i >= 0; i--) {
            if (s.charAt(i) == ' ' && ans == 0) {
                continue;
            }
            if (s.charAt(i) == ' ' && ans > 0) {
                return ans;
            }

            if (s.charAt(i) != ' ') {
                ans++;
            }
        }

        return ans;
    }
}
