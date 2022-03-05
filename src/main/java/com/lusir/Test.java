package com.lusir;

import java.util.ArrayList;
import java.util.concurrent.Executors;

public class Test {
    private static volatile Integer counter = 1;
    private static volatile boolean flag = true;

    public static void main(String[] args) {
        ArrayList<Integer> a = new ArrayList<>();
        Executors.newSingleThreadExecutor();
    }
}
