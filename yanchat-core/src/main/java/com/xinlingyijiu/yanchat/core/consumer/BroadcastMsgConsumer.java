package com.xinlingyijiu.yanchat.core.consumer;

import com.alibaba.fastjson.JSON;
import com.xinlingyijiu.yanchat.core.Constant;
import com.xinlingyijiu.yanchat.core.Context;
import com.xinlingyijiu.yanchat.core.bean.BroadcastMsg;
import com.xinlingyijiu.yanchat.core.queue.MsgConsumer;
import com.xinlingyijiu.yanchat.core.user.User;

import java.util.Objects;

/**
 * Created by laotou on 2017/10/13.
 */
public class BroadcastMsgConsumer implements MsgConsumer<BroadcastMsg> {
    @Override
    public void onMessage(BroadcastMsg msg) throws Exception {
        //todo
        System.out.println("BroadcastMsgConsumer:接收："+msg);
        BroadcastMsg broadcastMsg =  msg;
        if (Objects.equals(Constant.BROADCAST_TYPE.ONLINE,broadcastMsg.getType())) {
            User user = ((JSON)broadcastMsg.getData()).toJavaObject(User.class);
            user.setHost(broadcastMsg.getHost());
            Context.getInstance().getUserManager().online(user);
        }
    }
}
