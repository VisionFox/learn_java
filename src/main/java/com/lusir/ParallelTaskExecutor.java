package com.lusir;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * Q3: 实现一个简单的多任务执行器, 其最多可以同时执行 capacity 个任务
 * <p>
 * 注意: 不可使用 JDK 提供的线程池相关接口.
 */
public class ParallelTaskExecutor {
    private int cap;
    private BlockingQueue<FutureTask> queue = new LinkedBlockingQueue<>();
    private List<MyWork> threadWorkers = new ArrayList<>();


    /**
     * @param capacity 最多可同时执行的任务个数
     */
    public ParallelTaskExecutor(int capacity) {
        // show me your code here
        this.cap = capacity;
    }

    /**
     * 异步执行任务, 返回 Future 对象
     *
     * @param callable 要执行的任务
     * @param <T>      任务的返回值类型
     * @return 返回一个 Future, 任务执行完成时其状态变更为 Done.
     */
    public <T> Future<T> submit(Callable<T> callable) throws Exception {
        // show me your code here, please delete the following line.
        if (threadWorkers.size() < cap) {
            MyWork myWork = new MyWork(queue);
            myWork.start();
            threadWorkers.add(myWork);
        }
        FutureTask futureTask = new FutureTask(callable);
        queue.add(futureTask);
        return futureTask;
    }


    class MyWork extends Thread {
        private BlockingQueue<FutureTask> queue;

        public MyWork(BlockingQueue<FutureTask> queue) {
            this.queue = queue;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    FutureTask futureTask = queue.take();
                    futureTask.run();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
