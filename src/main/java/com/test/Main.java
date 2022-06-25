package com.test;

import java.util.ArrayList;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

public class Main {

    private static int  x=0;
    public static void main(String[] args) throws InterruptedException {
        x=10;
        x+=x-=x-x;
        System.out.println(x);
    }

    private volatile BlockingDeque<Integer> blockingDeque = new LinkedBlockingDeque<>();

    //消费者
    public synchronized int consumer(){
        if (blockingDeque.isEmpty()){
            return -1;
        }

        return this.blockingDeque.pollFirst();
    }

    // 生产者
    public synchronized void provider(int num){
        this.blockingDeque.offer(num);
    }

}
