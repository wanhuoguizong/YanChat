package com.xinlingyijiu.yanchat.core.queue;

/**
 * Created by laotou on 2017/10/13.
 */
public interface MsgConsumer <T> {
    public void onMessage(T msg) throws Exception;
}
