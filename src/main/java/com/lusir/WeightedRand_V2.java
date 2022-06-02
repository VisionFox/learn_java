package com.lusir;

import java.util.ArrayList;
import java.util.List;

public class WeightedRand_V2 {
    // 桶编号 / 桶内编号 / 总数
    int bucketId, innerBucketId, total;
    List<int[]> list = new ArrayList<>();

    public WeightedRand_V2(int[] input) {
        // show me your code
        int n = input.length;
        double sum = 0, min = 1e9;
        for (int i : input) {
            sum += i;
            min = Math.min(min, i);
        }
        double minv = min / sum;
        int k = 1;
        while (minv * k < 1) k *= 10;
        for (int i = 0; i < n; i++) {
            int cnt = (int) (input[i] / sum * k);
            list.add(new int[]{i, cnt});
            total += cnt;
        }
    }

    public int next() {
        if (bucketId >= list.size()) {
            bucketId = 0;
            innerBucketId = 0;
        }
        int[] info = list.get(bucketId);
        int id = info[0], cnt = info[1];
        if (innerBucketId >= cnt) {
            bucketId++;
            innerBucketId = 0;
            return next();
        }
        innerBucketId++;
        return id;
    }
}
