package com.lusir;

import java.util.Collections;
import java.util.PriorityQueue;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

// 数据流中位数 大小堆结合
public class MedianStreamHeap {
    private PriorityQueue<Integer> leftHeap = new PriorityQueue<>(5, Collections.reverseOrder());
    private PriorityQueue<Integer> rightHeap = new PriorityQueue<>(5);

    private boolean even = true;

    public double getMedian() {
        if (even) {
            return (leftHeap.peek() + rightHeap.peek()) / 2.0;
        } else {
            return leftHeap.peek();
        }
    }

    public void addNum(int num) {
        if (even) {
            rightHeap.offer(num);
            int rightMin = rightHeap.poll();
            leftHeap.offer(rightMin);
        } else {
            leftHeap.offer(num);
            int leftMax = leftHeap.poll();
            rightHeap.offer(leftMax);
        }
        System.out.println(leftHeap);
        System.out.println(rightHeap);
        // 奇偶变换.
        even = !even;
    }
}
