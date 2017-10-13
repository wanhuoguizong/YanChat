package com.xinlingyijiu.yanchat.core.queue;

import com.xinlingyijiu.yanchat.core.exception.QueueException;

/**
 * Created by laotou on 2017/10/13.
 */
public class MsgProducerImpl implements MsgProducer {
    private QueueManager manager;

    public QueueManager getManager() {
        return manager;
    }

    public void setManager(QueueManager manager) {
        this.manager = manager;
    }

    @Override
    public void sendMessage(String queueKey, Object msg) throws QueueException {
        System.out.println("MsgProducerImpl:发送：" + msg);
        try {
            manager.getQueue(queueKey).put(msg);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
