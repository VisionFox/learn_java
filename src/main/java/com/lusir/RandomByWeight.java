package com.lusir;

import java.util.Arrays;

// 528
public class RandomByWeight {

    public static void main(String[] args) {
        RandomByWeight t = new RandomByWeight(new int[]{1, 3});
        for (int i = 0; i < 7; i++) {
            System.out.println(t.pickIndex());
        }
    }

    private int[] prefixSums;

    public RandomByWeight(int[] weights) {
        int len = weights.length;
        prefixSums = new int[len];
        prefixSums[0] = weights[0];
        for (int i = 1; i < len; i++) {
            prefixSums[i] = prefixSums[i - 1] + weights[i];
        }
    }

    public int pickIndex() {
        // 为什么java版本的Arrays.binarySearch是错的？？ 下面的处理还是不对:
        int totalSum = prefixSums[prefixSums.length - 1];
        int len = prefixSums.length;
        int r = (int) (Math.random() * totalSum) + 1;
        int searchRes = Arrays.binarySearch(prefixSums, r);
//        [1] 搜索值是数组元素，从0开始计数，得搜索值的索引值；【找到了】
//        [2] 搜索值不是数组元素，且在数组范围内，从1开始计数，得“ - 插入点索引值”；【找不到】
//        [3] 搜索值不是数组元素，且大于数组内元素，索引值为 – (length + 1);【找不到】
//        [4] 搜索值不是数组元素，且小于数组内元素，索引值为 – 1。【找不到】
        if (searchRes >= 0) {
            return searchRes;
        } else if (-searchRes < len) {
            return -searchRes;
        } else if (searchRes == -1) {
            return 0;
        } else {
            System.out.println("---");
            System.out.println(r);
            System.out.println(searchRes);
            return -1;
        }
    }
}
