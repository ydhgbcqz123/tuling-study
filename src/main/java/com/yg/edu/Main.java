package com.yg.edu;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.locks.LockSupport;

/**
 * ,;,,;
 * ,;;'(    社
 * __      ,;;' ' \   会
 * /'  '\'~~'~' \ /'\.)  主
 * ,;(      )    /  |.     义
 * ,;' \    /-.,,(   ) \    码
 * ) /       ) / )|    农
 * ||        ||  \)
 * (_\       (_\
 *
 * @author ：杨过
 * @date ：Created in 2020/8/16
 * @version: V1.0
 * @slogan: 天下风云出我辈，一入代码岁月催
 * @description:
 **/
public class Main {

    public void method(){
        try {
            boolean flag = true;
            if(flag){
                throw new IllegalMonitorStateException();
            }
        } finally {
            System.out.println("finally");
        }
    }

    static Queue queue = new LinkedBlockingQueue();

    static List list = new LinkedList();

    static Object lock = new Object();

    public static void main(String[] args) {
        //Main main = new Main();
        //main.method();

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (;;){
                    if(queue.size()<1000){
                        LockSupport.park();
                    } else {
                        Queue temp = queue;
                        queue = new LinkedBlockingQueue();
                        System.out.println(temp.size());

                    }
                }
            }
        });
        t1.start();



        for (int i = 0;i < 100000;i++ ){
            final int j = i;
            Thread t0 = new Thread(new Runnable() {
                @Override
                public void run() {
                    //synchronized(lock){
                        queue.add(j);
                    //}
                    if(queue.size() == 10000){
                        LockSupport.unpark(t1);
                    }
//                    System.out.println(queue.size());
                }
            });
            t0.start();
        }

        try {
            Thread.sleep(10000);
            LockSupport.unpark(t1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
