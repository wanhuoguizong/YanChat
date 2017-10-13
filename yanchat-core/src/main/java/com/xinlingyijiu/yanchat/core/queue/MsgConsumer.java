package com.xinlingyijiu.yanchat.core.queue;

/**
 * Created by laotou on 2017/10/13.
 */
public interface MsgConsumer {
    public void onMessage(Object msg) throws Exception;
}
