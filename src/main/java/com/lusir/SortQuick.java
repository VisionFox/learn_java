package com.lusir;

import java.util.Arrays;

public class SortQuick {
    public static void main(String[] args) {
        SortQuick sortQuick = new SortQuick();
        System.out.println(Arrays.toString(sortQuick.sortArray(new int[]{5, 1, 4, 2, 3})));
    }

    public int[] sortArray(int[] nums) {
        qSort(nums, 0, nums.length - 1);
        return nums;
    }

    private void qSort(int[] arr, int left, int right) {
        if (left >= right) {
            return;
        }

        int p = partition(arr, left, right);
        qSort(arr, left, p - 1);
        qSort(arr, p + 1, right);
    }

    private int partition(int[] arr, int left, int right) {
        int pivotIdx = left;
        int splitIdx = pivotIdx + 1;
        for (int i = splitIdx; i <= right; i++) {
            // 注意：小于主元就排在左边 ！！！ 小于
            if (arr[i] < arr[pivotIdx]) {
                swap(arr, i, splitIdx);
                splitIdx++;
            }
        }
        // 注意：这里是 splitIdx-1
        swap(arr, pivotIdx, splitIdx - 1);
        // splitIdx-1
        return splitIdx - 1;
    }

    private void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}
