package com.xinlingyijiu.yanchat.core.queue;

import com.xinlingyijiu.yanchat.core.exception.QueueException;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;

/**
 * Created by laotou on 2017/10/13.
 */
public class QueueManagerImpl<E> implements QueueManager<E> {
    private Map<String,BlockingQueue<E>> queueMap;
    private static QueueManagerImpl queueManager;

    private QueueManagerImpl() {
        queueMap = new HashMap<>();
    }

    public static QueueManagerImpl getInstance() {
        return queueManager == null ? (queueManager = new QueueManagerImpl()) : queueManager;
    }
    @Override
    public BlockingQueue getQueue(String queueKey) throws QueueException {
        BlockingQueue queue = queueMap.get(queueKey);
        if (queue == null) throw new QueueException(String.format("queue not found.queueKey:'%s'",queueKey),queueKey);
        return queue;
    }

    @Override
    public void putQueue(String queueKey, BlockingQueue queue) throws QueueException {
        if (queueKey == null) throw new QueueException("queueKey must be not null!",queueKey);
        if (queue == null) throw new QueueException(String.format("queue not defined:queueKey:'%s',queue:null",queueKey),queueKey);
        if (queueMap.containsKey(queueKey)) {
            throw new QueueException(String.format("queue already defined,queueKey:'%s'", queueKey), queueKey);
        }
        queueMap.put(queueKey, queue);
    }
}
