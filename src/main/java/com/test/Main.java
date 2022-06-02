package com.test;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        new Main().test();
    }

    public void test() throws InterruptedException {
        Object obj1 = new Object();
        Object obj2 = new Object();

        new Thread(() -> {
            synchronized (obj1) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                synchronized (obj2) {
                    System.out.println("1");
                }
            }
        }).start();

        new Thread(() -> {
            synchronized (obj2) {
                synchronized (obj1) {
                    System.out.println("2");
                }
            }
        }).start();

        Thread.sleep(100000);
    }
}
