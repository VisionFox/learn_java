package com.lusir;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class XiaoPeng {
    /*小鹏三面翻车，也是求这种面积，只是输入是
        class building{
              start
              end
              height
        }
        类似leetcode 850
    */

    public int rectangleArea(int[][] rectangles) {
        if (rectangles == null || rectangles.length == 0) {
            return 0;
        }
        // 把所有x坐标排序，根据x轴的做宽度划分
        // ！！！！这里不是考虑把所有矩形重新挪动重排
        // ！！！只是单纯的找所有x坐标的分布排好  线段分宽
        List<Integer> xSortList = new ArrayList<>();
        for (int[] rect : rectangles) {
            xSortList.add(rect[0]);
            xSortList.add(rect[2]);
        }
        Collections.sort(xSortList);
        // 遍历所有划分好的矩形  （确定宽）
        // 再遍历所有在划分内，找出涉及到的所有原始矩形
        // 求划分内所有矩形涉及到的 Y轴 总长度 （确定高）
        int ans = 0;
        for (int i = 1; i < xSortList.size(); i++) {
            // 确定宽
            int xLeft = xSortList.get(i - 1);
            int xRight = xSortList.get(i);
            int wide = xRight - xLeft;
            if (wide == 0) {
                continue;
            }
            // 确定宽中涉及到那些原始矩形的高度
            List<int[]> innerHeightList = new ArrayList<>();
            for (int[] curRect : rectangles) {
                // 此宽 涉及到哪些原始矩形
                if (curRect[0] <= xLeft && xRight <= curRect[2]) {
                    innerHeightList.add(new int[]{curRect[1], curRect[3]});
                }
            }
            // ！！！！这些高度还再排一遍
            // 起点不一样的就先从小到大排 起点一样的就按照最终高度从小到大
            Collections.sort(innerHeightList, (l1, l2) -> l1[0] != l2[0] ? l1[0] - l2[0] : l1[1] - l2[1]);

            // 把这些高度横着放来理解
            // 这些区间的高就是一堆线段 有重合也有不重合的情况
            int lineTotal = 0;
            int leftFlag = -1;
            int rightFlag = -1;
            for (int[] heightLine : innerHeightList) {
                if (rightFlag < heightLine[0]) {
                    // 第一次遍历就当作初始化
                    // 后面的就当线段从新换起点重新累计
                    lineTotal += rightFlag - leftFlag;
                    leftFlag = heightLine[0];
                    rightFlag = heightLine[1];
                } else if (rightFlag < heightLine[1]) {
                    // 右标记位置继续右挪扩大
                    rightFlag = heightLine[1];
                }
            }
            // 处理最后一个没转换进 临时总长度的区间
            lineTotal += rightFlag - leftFlag;
            // 计算面积
            ans += lineTotal * wide;
        }

        return ans;
    }
}
