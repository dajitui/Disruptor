package com.example.demo.disruptor;

import com.lmax.disruptor.EventHandler;

import java.util.concurrent.atomic.AtomicLong;

/**
 * 消费者(秒杀处理器)
 * 创建者 科帮网
 */
public class SeckillEventConsumer implements EventHandler<SeckillEvent> {

    private static AtomicLong atomicLong = new AtomicLong(0);

    @Override
    public void onEvent(SeckillEvent seckillEvent, long seq, boolean bool) throws Exception {

        if (Thing.number > 0) {
            Thing.number--;
            System.out.println(seckillEvent.getSeckillId() + "被" + seckillEvent.getUserId() + "抢购了");
            System.out.println(atomicLong.incrementAndGet());
        } else {
            System.out.println("数量为0");
        }

    }
}
