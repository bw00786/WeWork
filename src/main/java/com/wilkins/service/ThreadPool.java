package com.wilkins.service;


//import java.util.concurrent.BlockingQueue;
import com.wilkins.pool.BlockingQueue;

import java.util.concurrent.LinkedBlockingQueue;


public class ThreadPool {

     BlockingQueue<Runnable> queue;
    public ThreadPool(int queueSize, int nThread) throws InterruptedException {
        queue = new BlockingQueue <>( queueSize );

        for (int count = 0; count < nThread; count++) {
            int finalCount = count;
            new Thread( new Runnable() {
                @Override
                public void run() {
                    try {
                        queue.dequeue().run();

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            } ).start();




        	//task = new TaskExecutor(queue);
            //Thread thread = new Thread(task, threadName);


        }
    }


    public void submitTask(Runnable task) throws InterruptedException {
        queue.enqueue( task );
    }
}
