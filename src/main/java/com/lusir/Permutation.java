package com.lusir;

import java.util.Arrays;

public class Permutation {
    /**
     * 记录数字是否使用过
     */
    private boolean[] used;

    /**
     * 阶乘数组
     */
    private int[] factorial;
    private int n;
    private int k;

    // 方法1：使用回溯法的思路思考，则每当选择一个数加入排列时，可以计算出剩下的数还有多少种排列的可能，
    // 即可以计算出当前被选择的数的排列总数，设用 remain_fac 表示， remain_fac = 剩下的待选择的数的阶乘。
    // 然后将 remain_fac 与 k 进行大小比较，若小于 k ，则说明所要求的第 k 个排列不在以 当前选定的数 为开头的所有排列中，直接跳过
    // 一次递归到底就能找到 第 k 个排列
    public String getPermutation(int n, int k) {
        this.n = n;
        this.k = k;
        calculateFactorial(n);

        // 查找全排列需要的布尔数组
        used = new boolean[n + 1];
        Arrays.fill(used, false);

        StringBuilder path = new StringBuilder();
        dfs(0, path);
        return path.toString();
    }


    /**
     * @param index 在这一步之前已经选择了几个数字，其值恰好等于这一步需要确定的下标位置
     * @param path
     */
    private void dfs(int index, StringBuilder path) {
        if (index == n) {
            return;
        }

        // 计算还未确定的数字的全排列的个数，第 1 次进入的时候是 n - 1
        // 剩下的数的全排列的个数
        int remainFac = factorial[n - 1 - index];
        // 遍历 [1, n]
        for (int i = 1; i <= n; i++) {
            // 跳过已使用的数
            if (used[i]) {
                continue;
            }
            // 剩下的数的全排列个数小于当前 k ，说明第 k 个排列肯定不在当前的递归子树中，直接跳过该递归
            if (remainFac < k) {
                // 剪枝
                k -= remainFac;
                continue;
            }

            path.append(i);
            used[i] = true;
            dfs(index + 1, path);
            // 因为是一次递归直接到叶子，所以不需要还原状态
            // 注意 1：不可以回溯（重置变量），算法设计是「一下子来到叶子结点」，没有回头的过程
            // 注意 2：这里要加 return，后面的数没有必要遍历去尝试了
            return;
        }
    }

    /**
     * 计算阶乘数组
     *
     * @param n
     */
    private void calculateFactorial(int n) {
        factorial = new int[n + 1];
        factorial[0] = 1;
        for (int i = 1; i <= n; i++) {
            factorial[i] = factorial[i - 1] * i;
        }
    }
}
