package com.test;

import com.lusir.Solution;

import javax.imageio.spi.ImageReaderWriterSpi;
import java.lang.ref.SoftReference;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;

public class Main {

    public static void main(String[] args) {
        PriorityQueue<Object> objects = new PriorityQueue<>();
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
