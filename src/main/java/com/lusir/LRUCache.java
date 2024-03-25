package com.lusir;

import java.util.HashMap;

public class LRUCache {
    private int cap;
    private HashMap<Integer, Node> cache;
    private Node cycleHead;

    public LRUCache(int capacity) {
        this.cache = new HashMap<>();

        this.cycleHead = new Node(0, 0);
        this.cycleHead.next = this.cycleHead;
        this.cycleHead.pre = this.cycleHead;

        this.cap = capacity;
    }

    public int get(int key) {
        Node node = this.cache.get(key);
        if (node == null) {
            return -1;
        }

        remove(node);
        add(node);
        return node.value;
    }

    public void put(int key, int value) {
        Node node = new Node(key, value);
        if (this.cache.get(key) != null) {
            remove(this.cache.get(key));
        }
        add(node);
    }

    private void remove(Node node) {
        node.pre.next = node.next;
        node.next.pre = node.pre;

        cache.remove(node.key);
    }

    private void add(Node node) {
        if (this.cache.size()== cap) {
            // 只删 map 不删环有什么用
            // this.cache.remove(this.cycleHead.pre);、
            // ctm 忘记删环还有就是hashmap的 remove 和 自己写的 remove 太像了
            remove(node.pre);
        }

        this.cache.put(node.key, node);
        node.next = this.cycleHead.next;
        node.pre = this.cycleHead;
        // 少了新节点的next的 pre忘记处理
        this.cycleHead.next.pre = node;
        this.cycleHead.next = node;
    }

    class Node {
        public int key, value;
        public Node pre, next;

        public Node(int key, int value) {
            this.key = key;
            this.value = value;
        }
    }
}
