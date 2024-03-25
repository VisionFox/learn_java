package com.lusir;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class MyThreadPool {
    // 线程池最大允许的个数
    private final static int maxWorkerCount = 10;
    // 创建任务线程安全的队列, 保证多个线程对这个队列操作时是线程安全的
    private BlockingQueue<Runnable> queue = new LinkedBlockingQueue<>();
    // 线程管理列表 - 这个列表保存了所有线程对象的引用， 方便后续的管理
    private List<MyThreadWork> threadWorkers = new ArrayList<>();
    //execute方法
    public void execute(Runnable command) throws InterruptedException {
        if (threadWorkers.size() < maxWorkerCount) {
            //创建一个新的工作线程
            MyThreadWork threadWork = new MyThreadWork(queue);   //创建工作线程
            threadWork.start();                      //创建的工程线程启动
            threadWorkers.add(threadWork);                  //添加到管理列表中
        }
        queue.put(command);                      //添加任务到线程安全的队列中
    }

    //销毁所有线程 - 将每个线程中状态置为中断状态方法， 并且
    public void shutDown() throws InterruptedException {
        for (MyThreadWork threadWork : threadWorkers) {
            //将线程的状态置为中断， 调用isInterruptd()返回值为true
            threadWork.interrupt();
        }
        //并且让主线程join阻塞等待所有工作线程
        for (MyThreadWork threadWork : threadWorkers) {
            //join方法可以让调用的线程处于阻塞状态， 知道等待的线程结束完毕之后就会恢复
            threadWork.join();
        }
        //执行到这块， 代表所有的线程销毁完毕
        System.out.println("所有工作线程销毁完毕!");
    }
}

