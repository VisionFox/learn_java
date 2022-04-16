package com.lusir;

import io.netty.util.concurrent.FastThreadLocal;

import java.util.PriorityQueue;

public class PriorityQueueExample {
    public static void main(String[] args) {
        PriorityQueue<Integer> queue = new PriorityQueue<>((a, b) -> b.compareTo(a));

        queue.offer(21);
        queue.offer(2);
        queue.offer(30);

        System.out.println(queue.toString());

        System.out.println(queue.peek());
    }
}
