package com.lusir;

public class WeightedRand_V1 {
    private int[] prefixSums;

    public WeightedRand_V1(int[] input) {
        // show me your code
        int n = input.length;
        prefixSums = new int[n + 1];
        for (int i = 1; i <= n; i++)
            prefixSums[i] = prefixSums[i - 1] + input[i - 1];
    }

    public int next() {
        // show me your code, please delete the next line
        int n = prefixSums.length;
        int random = (int) (Math.random() * prefixSums[n - 1]) + 1;
        int left = 1, right = n - 1;
        while (left < right) {
            int mid = left + right >> 1;
            if (prefixSums[mid] >= random) right = mid;
            else left = mid + 1;
        }
        return right - 1;
    }
}
