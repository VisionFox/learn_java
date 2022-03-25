package com.lusir;

import java.util.HashMap;
import java.util.LinkedList;

public class FreqStack {
    private HashMap<Integer, Integer> KeyFreq;
    private HashMap<Integer, LinkedList<Integer>> FreqKey;

    private int maxFreq;

    public FreqStack() {
        KeyFreq = new HashMap<>();
        FreqKey = new HashMap<>();
        maxFreq = 0;
    }

    public void push(int val) {
        int newFreq = KeyFreq.getOrDefault(val, 0) + 1;
        KeyFreq.put(val, newFreq);
        if (maxFreq < newFreq) {
            maxFreq++;
        }
        FreqKey.putIfAbsent(newFreq, new LinkedList<>());
        FreqKey.get(newFreq).push(val);
    }

    public int pop() {
        int res = FreqKey.get(maxFreq).pop();
        if (FreqKey.get(maxFreq).isEmpty()) {
            FreqKey.remove(maxFreq);
            maxFreq--;
        }
        KeyFreq.put(res, KeyFreq.get(res) - 1);
        return res;
    }
}
