package com.test;

import java.util.Random;

public class Draft {
    public static void main(String[] args) {
        int[] testArrA = new int[4];
        int[] testArrB = new int[4];
        Random random = new Random();
        for (int i = 0; i < testArrA.length; i++) {
            testArrA[i] = random.nextInt(10);
        }
    }

    private static void qSort(int[] nums, int left, int right) {
        if (left >= right) {
            return;
        }

        int partition = partition(nums, left, right);
        qSort(nums, left, partition - 1);
        qSort(nums, partition + 1, right);
    }

    private static int partition(int[] nums, int left, int right) {
        int baseIdx = left;
        int scanIdx = baseIdx + 1;
        for (int i = scanIdx; i <= right; i++) {
            if (nums[i] < nums[baseIdx]) {
                swap(nums, i, scanIdx);
                scanIdx++;
            }
        }
        swap(nums, baseIdx, scanIdx - 1);
        return scanIdx - 1;
    }

    private static void swap(int[] nums, int i, int j) {
        int t = nums[i];
        nums[i] = nums[j];
        nums[j] = t;
    }


    private static void mSort(int[] nums, int left, int right) {
        int mid = left + (right - left) / 2;
        mSort(nums, left, mid);
        mSort(nums, mid + 1, right);
        merge(nums, left, mid, right);
    }

    private static void merge(int[] nums, int left, int mid, int right) {
        if (left >= right) {
            return;
        }

        int[] tempArr = new int[right - left + 1];
        int scanIdx = 0;
        int i = left;
        int j = mid + 1;
        while (i <= mid && j <= right) {
            if (nums[i] < nums[j]) {
                tempArr[scanIdx] = nums[i];
                i++;
            } else {
                tempArr[scanIdx] = nums[j];
                j++;
            }
            scanIdx++;
        }

        for (int k = 0; k < tempArr.length; k++) {
            nums[left + k] = tempArr[k];
        }
    }
}
