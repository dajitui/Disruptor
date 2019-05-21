package com.example.demo.disruptor;

import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;

import java.util.concurrent.ThreadFactory;

/**
 * Created on 2019/5/21.
 *
 * @author yangsen
 */
public class DisruptorUtil {

    static Disruptor<SeckillEvent> disruptor = null;

    static{
        int ringBufferSize = 1024;
        ThreadFactory threadFactory = new ThreadFactory() {
            @Override
            public Thread newThread(Runnable runnable) {
                return new Thread(runnable);
            }
        };
        disruptor = new Disruptor<SeckillEvent>(new SeckillEventFactory(), ringBufferSize, threadFactory);
        disruptor.handleEventsWith(new SeckillEventConsumer());
        disruptor.start();
    }

    public static void producer(SeckillEvent kill){
        RingBuffer<SeckillEvent> ringBuffer = disruptor.getRingBuffer();
        SeckillEventProducer producer = new SeckillEventProducer(ringBuffer);
        producer.seckill(kill.getSeckillId(),kill.getUserId());
    }

}
