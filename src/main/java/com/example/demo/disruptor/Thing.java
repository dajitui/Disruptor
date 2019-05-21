package com.example.demo.disruptor;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created on 2019/5/21.
 *
 * @author yangsen
 */
public class Thing {

    public static int number = 10010;

    private static int corePoolSize = Runtime.getRuntime().availableProcessors();

    private static ThreadPoolExecutor executor = new ThreadPoolExecutor(corePoolSize, corePoolSize + 1, 10l, TimeUnit.SECONDS,
            new LinkedBlockingQueue<Runnable>(1000));

    public static void main(String[] args) throws InterruptedException {
        final CountDownLatch latch = new CountDownLatch(10000);
        long time = System.nanoTime();
        for (int i = 0; i < 10009; i++) {
            final long userId = i;
            Runnable task = new Runnable() {
                @Override
                public void run() {
                    SeckillEvent kill = new SeckillEvent();
                    kill.setSeckillId(111);
                    kill.setUserId(userId);
                    DisruptorUtil.producer(kill);
                    latch.countDown();
                }
            };
            //executor.execute(task);
            new Thread(task).start();
        }
        latch.await();
        long time1 = System.nanoTime();
        System.out.println("消耗时间" + (time1 - time));


    }

}
