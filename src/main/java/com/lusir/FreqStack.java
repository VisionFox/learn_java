package com.lusir;

import java.util.HashMap;
import java.util.LinkedList;

public class FreqStack {
    private HashMap<Integer, Integer> val2Freq;
    private HashMap<Integer, LinkedList<Integer>> freq2Vals;

    private int maxFreq;

    public FreqStack() {
        val2Freq = new HashMap<>();
        freq2Vals = new HashMap<>();
        maxFreq = 0;
    }

    public void push(int val) {
        int newFreq = val2Freq.getOrDefault(val, 0) + 1;
        val2Freq.put(val, newFreq);
        if (maxFreq < newFreq) {
            maxFreq++;
        }
        freq2Vals.putIfAbsent(newFreq, new LinkedList<>());
        freq2Vals.get(newFreq).push(val);
    }

    public int pop() {
        int res = freq2Vals.get(maxFreq).pop();
        if (freq2Vals.get(maxFreq).isEmpty()) {
            freq2Vals.remove(maxFreq);
            maxFreq--;
        }
        val2Freq.put(res, val2Freq.get(res) - 1);
        return res;
    }
}
