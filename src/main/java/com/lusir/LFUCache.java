package com.lusir;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class LFUCache {
    private int minFreq;
    private int cap;
    private HashMap<Integer, Node> keyNodeMap;
    private HashMap<Integer, LinkedList<Node>> freqNodeListMap;

    public LFUCache(int capacity) {
        this.cap = 0;
        this.minFreq = 0;
        this.keyNodeMap = new HashMap<>();
        this.freqNodeListMap = new HashMap<>();
    }

    public int get(int key) {
        if (this.cap == 0) {
            return -1;
        }

        if (this.keyNodeMap.get(key) == null) {
            return -1;
        }

        Node node = keyNodeMap.get(key);
        int originFreq = node.freq;
        this.freqNodeListMap.get(originFreq).remove(node);
        // 如果当前链表为空，我们需要在哈希表中删除
        if (this.freqNodeListMap.get(originFreq).size() == 0) {
            this.freqNodeListMap.remove(originFreq);
            // 且 如果当前频率为最小的频率，更新minFreq
            if (this.minFreq == originFreq) {
                this.minFreq++;
            }
        }

        // 插入到 originFreq + 1 中
        LinkedList<Node> nodeList = freqNodeListMap.getOrDefault(originFreq + 1, new LinkedList<>());
        nodeList.offerFirst(new Node(node.key, node.val, originFreq + 1));
        this.freqNodeListMap.put(originFreq + 1, nodeList);
        this.keyNodeMap.put(key, this.freqNodeListMap.get(originFreq + 1).peekFirst());
        return node.val;
    }

    public void put(int key, int value) {
        if (this.cap == 0) {
            return;
        }

        if (!this.keyNodeMap.containsKey(key)) {
            // 缓存已满，需要进行删除操作
            if (this.keyNodeMap.size() == this.cap) {
                Node node = this.freqNodeListMap.get(this.minFreq).pollLast();
                this.keyNodeMap.remove(node.key);
                if (this.freqNodeListMap.get(this.minFreq).size() == 0) {
                    this.freqNodeListMap.remove(this.minFreq);
                }
            }
            LinkedList<Node> list = this.freqNodeListMap.getOrDefault(1, new LinkedList<Node>());
            list.offerFirst(new Node(key, value, 1));
            this.freqNodeListMap.put(1, list);
            this.keyNodeMap.put(key, this.freqNodeListMap.get(1).peekFirst());
            this.minFreq = 1;
        } else {
            // 与 get 操作基本一致，除了需要更新缓存的值
            Node node = this.keyNodeMap.get(key);
            int originFreq = node.freq;
            this.freqNodeListMap.get(originFreq).remove(node);
            if (this.freqNodeListMap.get(originFreq).size() == 0) {
                this.freqNodeListMap.remove(originFreq);
                if (this.minFreq == originFreq) {
                    this.minFreq += 1;
                }
            }
            LinkedList<Node> list = this.freqNodeListMap.getOrDefault(originFreq + 1, new LinkedList<>());
            list.offerFirst(new Node(key, value, originFreq + 1));
            this.freqNodeListMap.put(originFreq + 1, list);
            this.keyNodeMap.put(key, this.freqNodeListMap.get(originFreq + 1).peekFirst());
        }
    }


    private class Node {
        public int key, val;
        public int freq;

        Node(int key, int val, int freq) {
            this.key = key;
            this.val = val;
            this.freq = freq;
        }
    }
}
