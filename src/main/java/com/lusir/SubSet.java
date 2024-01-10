package com.lusir;

import java.util.ArrayList;
import java.util.List;

public class SubSet {
    private List<Integer> path = new ArrayList<>();
    private List<List<Integer>> ans = new ArrayList<>();

    // lc 78
    public List<List<Integer>> subsets(int[] nums) {
        dfs(nums, 0);
        return ans;
    }

    private void dfs(int[] nums, int cur) {
        if (cur == nums.length) {
            ans.add(new ArrayList<>(path));
            return;
        }
        // 要
        path.add(nums[cur]);
        dfs(nums, cur + 1);
        // 不要 挪出
        path.remove(path.size() - 1);
        dfs(nums, cur + 1);
    }
}
