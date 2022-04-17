package com.lusir;

import java.util.concurrent.BlockingQueue;

public class MyThreadWork extends Thread {
    // 阻塞任务队列 - 可以保证多个线程对队列操作， 线程安全
    private BlockingQueue<Runnable> queue = null;

    // 每个工作线程都会有一个阻塞队列，这个队列中保存了所有的任务
    public MyThreadWork(BlockingQueue<Runnable> queue) {
        this.queue = queue;
    }

    // 工作线程执行内容
    @Override
    public void run() {
        try {
            // 每个线程通过isInterrupted()判断线程异常状态。
            while (!Thread.currentThread().isInterrupted()) {
                // isInterrupted: 如果线程正常， 返回false, 出现异常， 返回true， 该状态默认为false
                //如果队列为空， take会让线程阻塞
                Runnable runnable = queue.take();
                System.out.println(Thread.currentThread().getName() +
                        "正在处理任务:" +
                        runnable.hashCode());
                runnable.run();
            }
        } catch (InterruptedException e) {
            System.out.println(Thread.currentThread().getName() + "被中止了");
        }
    }
}
