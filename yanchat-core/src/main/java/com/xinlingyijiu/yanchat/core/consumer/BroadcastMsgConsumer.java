package com.xinlingyijiu.yanchat.core.consumer;

import com.xinlingyijiu.yanchat.core.queue.MsgConsumer;

/**
 * Created by laotou on 2017/10/13.
 */
public class BroadcastMsgConsumer implements MsgConsumer {
    @Override
    public void onMessage(Object msg) throws Exception {
        //todo
        System.out.println("BroadcastMsgConsumer:接收："+msg);
    }
}
