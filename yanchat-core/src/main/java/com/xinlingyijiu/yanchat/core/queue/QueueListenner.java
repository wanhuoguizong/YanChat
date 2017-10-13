package com.xinlingyijiu.yanchat.core.queue;

import com.xinlingyijiu.yanchat.core.exception.QueueException;

/**
 * Created by laotou on 2017/10/13.
 */
public interface QueueListenner {
    QueueManager getQueueManager();

    void setQueueManager(QueueManager queueManager);

    void bindConsumer(String queueKey,MsgConsumer msgConsumer) throws QueueException;

    void listen() throws QueueException;
}
