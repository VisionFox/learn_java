package com.test;


import java.util.Stack;

// 存储n个元素的空间复杂度为 O(n) ，插入与删除的时间复杂度都是 O(1)
public class MyClass {
    // in
    Stack<Integer> stack1 = new Stack<>();
    // out
    Stack<Integer> stack2 = new Stack<>();

    public void push(int node) {
        stack1.push(node);
    }

    public int pop() {
        if (!stack2.isEmpty()) {
            return stack2.pop();
        }

        while (!stack1.isEmpty()) {
            stack2.push(stack1.pop());
        }

        return stack2.pop();
    }
}
