package com.xinlingyijiu.yanchat.core.exception;

/**
 * Created by laotou on 2017/10/13.
 */
public class QueueException extends RuntimeException {

    private String queueKey;

    public QueueException() {
    }

    public QueueException(String message) {
        super(message);
    }
    public QueueException(String message,String queueKey) {
        super(message);
        this.queueKey = queueKey;
    }

    public QueueException(String message, Throwable cause) {
        super(message, cause);
    }
    public QueueException(String message,String queueKey, Throwable cause) {
        super(message, cause);
        this.queueKey = queueKey;
    }

    public QueueException(Throwable cause) {
        super(cause);
    }

    public QueueException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public String getQueueKey() {
        return queueKey;
    }

    public void setQueueKey(String queueKey) {
        this.queueKey = queueKey;
    }
}
