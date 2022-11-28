package com.lusir;

import java.util.HashMap;
import java.util.Map;

public class Bilibili {

    private int findPair(int[] nums) {
        // 前提：有序 可以用双指针 从两边往中间扫描 有重复项时 得指针回拉不好处理
        if (nums == null || nums.length < 2) {
            return -1;
        }

        int res = 0;

        Map<Integer, Integer> cntMap = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            int n = nums[i];
            if (cntMap.containsKey(-n)) {
                res += cntMap.get(-n);
            }

            cntMap.put(n, cntMap.getOrDefault(n, 0) + 1);
        }
        return res;
    }
}
