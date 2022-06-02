package com.lusir;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MyThreadPrintNums {
    public static void main(String[] args) throws InterruptedException, BrokenBarrierException {
        new MyThreadPrintNums().twoThreadPrint(100);
    }

    private int cnt = 0;

    public void twoThreadPrint(int n) throws InterruptedException, BrokenBarrierException {
        Object obj = new Object();

        new Thread(() -> {
            synchronized (obj) {
                while (cnt < n) {
                    try {
                        obj.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.println("t1: " + cnt);
                    cnt++;
                    obj.notifyAll();
                }
            }
        }).start();

        new Thread(() -> {
            synchronized (obj) {
                while (cnt < n) {
                    System.out.println("t2: " + cnt);
                    cnt++;
                    obj.notifyAll();
                    try {
                        obj.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }).start();
    }

    Lock lock = new ReentrantLock();
    Condition condition = lock.newCondition();

    // https://blog.csdn.net/wenzhouxiaomayi77/article/details/104921312
    class printWorker implements Runnable {
        @Override
        public void run() {

        }
    }
}
