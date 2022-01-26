package com.yg.edu.lock;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author ：杨过
 * @date ：Created in 2020/5/19 16:17
 * @version: V1.0
 * @slogan: 天下风云出我辈，一入代码岁月催
 * @description: 
 **/
public class Juc04_Thread_ReentrantLock {
    /**
     * 可重入锁，怎么实现类似于synchronized的功能
     */
    public static ReentrantLock lock = new ReentrantLock(true);

    static boolean flag = false;

    public static void main(String[] args) {
        List<Thread> list = new ArrayList<>();
        for (int i=0;i<10;i++){
            Thread t = new Thread(()->{
                lock.lock();
                System.out.println(Thread.currentThread().getName()+"get lock");
                    //拿到中断信号
                    while (!flag){
                        if(flag){
                            break;
                        }
                    }
                lock.unlock();
            },"t-"+i);
            list.add(t);
            t.start();
        }

        try {
            Thread.sleep(2000);
            list.get(3).interrupt();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }






    }

}
