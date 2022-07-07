package com.lusir;

import java.util.ArrayList;

public class AiChiQiChe {

    private int no1(int[] nums) {
        if (nums == null || nums.length == 0) {
            return -1;
        }

        if (nums.length == 1) {
            return nums[0];
        }

        int flagNum = nums[0];
        int flag = 1;

        for (int i = 1; i < nums.length; i++) {
            if (nums[i] != flagNum) {
                flag--;
            } else if (nums[i] == flagNum) {
                flag++;
            }

            if (flag <= 0) {
                flag = 1;
                flagNum = nums[i];
            }
        }

        int cnt = 0;
        for (int num : nums) {
            if (num == flagNum) {
                cnt++;
            }
        }

        if (cnt >= nums.length / 2) {
            return flagNum;
        }

        return -1;
    }

    //2、你正在使用一堆木板建造跳水板。有两种类型的木板，其中长度较短的木板长度为shorter，长度较长的木板长度为longer。你必须正好使用k块木板。编写一个方法，生成跳水板所有可能的长度。
    // 返回的长度需要从小到大排列。


    //示例 1

    //输入：
    //shorter = 1
    //longer = 2
    //k = 3
    //输出： [3,4,5,6]
    //解释：
    //可以使用 3 次 shorter，得到结果 3；使用 2 次 shorter 和 1 次 longer，得到结果 4 。以此类推，得到最终结果。

    private ArrayList<Integer> ansNo2 = new ArrayList<>();

    private void no2(int shorter, int longer, int k, ArrayList<Integer> path) {
        if (k == 3) {
            int ans = 0;
            for (int i = 0; i < path.size(); i++) {
                ans += path.indexOf(i);
            }
            ansNo2.add(ans);
        }

        path.add(shorter);
        no2(shorter, longer, k + 1, path);
        path.remove(path.size() - 1);

        path.add(longer);
        no2(shorter, longer, k + 1, path);
        path.remove(path.size() - 1);
    }
}
