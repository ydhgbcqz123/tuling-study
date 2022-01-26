package com.yg.edu.lock;

import lombok.extern.slf4j.Slf4j;

import java.sql.SQLOutput;
import java.util.concurrent.locks.LockSupport;

/**
 * @author ：杨过
 * @date ：Created in 2020/4/28
 * @version: V1.0
 * @slogan: 天下风云出我辈，一入代码岁月催
 * @description:
 **/
@Slf4j
public class Juc01_Thread_LockSupport {

    public static void main(String[] args) {
        Object lock = new Object();

        Thread t0 = new Thread(new Runnable() {
            @Override
            public void run() {
                Thread current = Thread.currentThread();
                int i = 0;
                log.info("{},开始执行!",current.getName());
                for(;;){//spin 自旋
                    log.info("准备park住当前线程：{}....",current.getName());
//                    LockSupport.park();

                    synchronized (lock){
                        try {
                            System.out.println("测试1:"+i);
                            i++;
                            lock.wait();
                            System.out.println("测试2:"+i);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    log.info("当前线程{}已经被唤醒....",current.getName());
                }
            }
        },"t0");
        t0.start();


        try {

            //Thread.sleep(2000);
            log.info("准备唤醒{}线程!",t0.getName());
//            LockSupport.unpark(t0);
            Thread.sleep(1000);
            synchronized (lock){
                lock.notifyAll();
                Thread.sleep(5000);
                System.out.println("qqqqq");
            }


            t0.interrupt();

            Thread.sleep(1000);
            synchronized (lock){
                lock.notifyAll();
            }
//            Thread.sleep(2000);
            LockSupport.unpark(t0);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
