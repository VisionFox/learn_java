package com.lusir;

import java.util.Random;

public class Shuffle {
    private int[] nums;
    private Random random = new Random();

    public Shuffle(int[] nums) {
        this.nums = nums;
    }

    public int[] reset() {
        return nums;
    }

    public int[] shuffle() {
        int[] ans = nums.clone();
        for (int i = 0; i < ans.length; i++) {
            int idx1 = i;
            int idx2 = i + random.nextInt(ans.length - idx1);
            swap(ans, idx1, idx2);
        }
        return ans;
    }

    private void swap(int[] nums, int i, int j) {
        int t = nums[i];
        nums[i] = nums[j];
        nums[j] = t;
    }
}
