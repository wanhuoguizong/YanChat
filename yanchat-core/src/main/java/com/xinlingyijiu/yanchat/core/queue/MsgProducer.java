package com.xinlingyijiu.yanchat.core.queue;

import com.xinlingyijiu.yanchat.core.exception.QueueException;

/**
 * Created by laotou on 2017/10/13.
 */
public interface MsgProducer {
    void sendMessage(String queueKey,Object msg) throws QueueException;
}
