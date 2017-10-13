package com.xinlingyijiu.yanchat.core.queue;

import com.xinlingyijiu.yanchat.core.exception.QueueException;

import java.util.concurrent.BlockingQueue;

/**
 * Created by laotou on 2017/10/13.
 */
public interface QueueManager<E> {
    BlockingQueue getQueue(String queueKey) throws QueueException;
    void putQueue(String queueKey,BlockingQueue queue) throws QueueException;

}
