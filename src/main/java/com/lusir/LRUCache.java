package com.lusir;

import java.util.HashMap;

public class LRUCache {
    private int cap;
    private int size;
    private HashMap<Integer, Node> cache;
    private Node cycleHead;

    public LRUCache(int capacity) {
        this.cache = new HashMap<>();
        this.cycleHead = new Node(0, 0);
        this.cap = capacity;
        this.size = 0;
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
        size--;
    }

    private void add(Node node) {
        if (size == cap) {
            this.cache.remove(this.cycleHead.pre);
        }

        this.cache.put(node.key, node);
        node.next = this.cycleHead.next;
        node.pre = this.cycleHead;
        this.cycleHead.next = node;
        size++;
    }

    class Node {
        public int key, value;
        public Node pre, next;

        public Node(int key, int value) {
            this.key = key;
            this.value = value;
            this.pre = this;
            this.next = this;
        }
    }
}
