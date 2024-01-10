package com.lusir;

import java.util.Map;
import java.util.TreeMap;

// 数据流中位数 堆会爆内存 换分桶法
public class MedianStreamSlot {
    private static int BUCKET_SIZE = 1000;

    private int left = 0;
    private int right = Integer.MAX_VALUE;

    // 流这里用int[] 代替
    public double findMedian(int[] nums) {
        // 第一遍读取stream 将问题复杂度转化为内存可接受的量级.
        int[] bucket = new int[BUCKET_SIZE];
        int step = (right - left) / BUCKET_SIZE;
        boolean even = true;
        int sumCount = 0;
        for (int i = 0; i < nums.length; i++) {
            int index = nums[i] / step;
            bucket[index] = bucket[index] + 1;
            sumCount++;
            even = !even;
        }
        // 如果是偶数,那么就需要计算第topK 个数
        // 如果是奇数, 那么需要计算第 topK和topK+1的个数.
        int topK = even ? sumCount / 2 : sumCount / 2 + 1;

        int index = 0;
        int indexBucketCount = 0;
        for (index = 0; index < bucket.length; index++) {
            indexBucketCount = bucket[index];
            if (indexBucketCount >= topK) {
                // 当前bucket 就是中位数的bucket.
                break;
            }
            topK -= indexBucketCount;
        }

        // 划分到这里其实转化为一个topK的问题, 再读一遍流.
        if (even && indexBucketCount == topK) {
            left = index * step;
            right = (index + 2) * step;
            return helperEven(nums, topK);
            // 偶数的时候, 恰好划分到在左右两个子段中.
            // 左右两段中 [topIndex-K + (topIndex-K + 1)] / 2.
        } else if (even) {
            left = index * step;
            right = (index + 1) * step;
            return helperEven(nums, topK);
            // 左边 [topIndex-K + (topIndex-K + 1)] / 2
        } else {
            left = index * step;
            right = (index + 1) * step;
            return helperOdd(nums, topK);
            // 奇数, 左边topIndex-K
        }
    }

    private double helperEven(int[] nums, int topK) {
        TreeMap<Integer, Integer> map = new TreeMap<>();
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] >= left && nums[i] <= right) {
                if (!map.containsKey(nums[i])) {
                    map.put(nums[i], 1);
                } else {
                    map.put(nums[i], map.get(nums[i]) + 1);
                }
            }
        }

        int count = 0;
        int kNum = Integer.MIN_VALUE;
        int kNextNum = 0;
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            int currentCountIndex = entry.getValue();
            if (kNum != Integer.MIN_VALUE) {
                kNextNum = entry.getKey();
                break;
            }
            if (count + currentCountIndex == topK) {
                kNum = entry.getKey();
            } else if (count + currentCountIndex > topK) {
                kNum = entry.getKey();
                kNextNum = entry.getKey();
                break;
            } else {
                count += currentCountIndex;
            }
        }

        return (kNum + kNextNum) / 2.0;
    }

    private double helperOdd(int[] nums, int topK) {
        TreeMap<Integer, Integer> map = new TreeMap<>();
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] >= left && nums[i] <= right) {
                if (!map.containsKey(nums[i])) {
                    map.put(nums[i], 1);
                } else {
                    map.put(nums[i], map.get(nums[i]) + 1);
                }
            }
        }
        int count = 0;
        int kNum = Integer.MIN_VALUE;
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            int currentCountIndex = entry.getValue();
            if (currentCountIndex + count >= topK) {
                kNum = entry.getKey();
                break;
            } else {
                count += currentCountIndex;
            }
        }

        return kNum;
    }
}
