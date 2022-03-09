package com.lusir;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Combine {
    private List<List<Integer>> ans = new ArrayList<>();

    private List<List<Integer>> combine(int n, int k) {
        getCombine(n, k, 1, new ArrayList<>());
        return ans;
    }

    public void getCombine(int n, int k, int start, List<Integer> list) {
        if (k == 0) {
            ans.add(new ArrayList<>(list));
            return;
        }

        // 后面剩余的够不够k个
        for (int i = start; i <= n - k; i++) {
            list.add(i);
            getCombine(n, k - 1, i + 1, list);
            list.remove(list.size() - 1);
        }
    }
}
