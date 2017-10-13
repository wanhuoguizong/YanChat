package com.xinlingyijiu.yanchat.core.queue;

import com.xinlingyijiu.yanchat.core.exception.QueueException;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;

/**
 * Created by laotou on 2017/10/13.
 */
public class QueueListennerImpl implements QueueListenner {
    private Map<String,MsgConsumer> consumerMap;
    private static QueueListennerImpl queueListenner;
    private QueueManager queueManager;
    @Override
    public QueueManager getQueueManager() {
        return queueManager;
    }
    @Override
    public void setQueueManager(QueueManager queueManager) {
        this.queueManager = queueManager;
    }

    private QueueListennerImpl() {
        consumerMap = new HashMap<>();
    }

    public static QueueListennerImpl getInstance() {
        return queueListenner == null ? (queueListenner = new QueueListennerImpl()) : queueListenner;
    }
    @Override
    public void bindConsumer(String queueKey, MsgConsumer msgConsumer) throws QueueException{
        if (queueKey == null) throw new QueueException("queueKey must be not null!");
        if (msgConsumer == null) throw new QueueException(String.format("msgConsumer not defined:queueKey:'%s',msgConsumer:null",queueKey),queueKey);
        if (consumerMap.containsKey(queueKey)) {
            throw new QueueException(String.format("msgConsumer already defined,queueKey:'%s'", queueKey), queueKey);
        }
        consumerMap.put(queueKey, msgConsumer);
    }

    @Override
    public void listen() throws QueueException {
        if (queueManager == null) throw new QueueException("queueManager not defined");
        if (consumerMap.isEmpty()) throw new QueueException("No consumer was found");
        for(String key : consumerMap.keySet()){
            BlockingQueue queue = queueManager.getQueue(key);
            if (queue == null){
                throw new QueueException(String.format("queue not defined,queueKey:'%s'", key), key);
            }
            new Thread(() -> {
                while (true) {
                    try {

                        Object msg = queue.take();
                        consumerMap.get(key).onMessage(msg);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }
}
