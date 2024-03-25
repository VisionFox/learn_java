package com.lusir;

// 读写锁
public class RWLock {
    // 定义一个读写锁共享变量 state
    private int state = 0;
    // ！！！位运算不熟，还是拆开成两个变量吧

    // state 高 16 位为读锁数量
    private int getReadCount() {
        return state >>> 16;
    }

    // state 低 16 位为写锁数量
    private int getWriteCount() {
        return state & ((1 << 16) - 1);
    }

    // 获取读锁时,先判断是否有写锁
    // 如果有写锁,就等待
    // 如果没有,进行加 1 操作
    public synchronized void lockRead() throws InterruptedException {
        while (getWriteCount() > 0) {
            wait();
        }
        System.out.println("lockRead --- " + Thread.currentThread().getName());
        state = state + (1 << 16);
    }

    // 释放读锁数量减 1 ,通知其他线程
    public synchronized void unLockRead() {
        state = state - (1 << 16);
        notifyAll();
    }

    // 获取写锁时需要判断读锁和写锁是否都存在,有则等待,没有则将写锁数量加 1
    public synchronized void lockWrite() throws InterruptedException {
        while (getReadCount() > 0 || getWriteCount() > 0) {
            wait();
        }
        System.out.println("lockWrite --- " + Thread.currentThread().getName());
        state++;
    }

    // 释放写锁数量减 1 ,通知所有等待线程
    public synchronized void unlockWriters() {
        state--;
        notifyAll();
    }
}
