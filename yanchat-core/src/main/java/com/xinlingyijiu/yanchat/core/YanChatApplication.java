package com.xinlingyijiu.yanchat.core;

import com.alibaba.fastjson.JSON;
import com.xinlingyijiu.yanchat.core.bean.BroadcastMsg;
import com.xinlingyijiu.yanchat.core.user.User;

import java.net.SocketException;

/**
 * Created by laotou on 2017/10/13.
 */
public class YanChatApplication {




    public static void start(Context context){

        try {
            context.getBroadcast().listen(context.getBroadcastHost(),context.getBroadcastPort());
        } catch (SocketException e) {
            e.printStackTrace();
        }
        context.getQueueListenner().listen();

        BroadcastMsg<User> broadcastMsg = new BroadcastMsg<>(Constant.BROADCAST_TYPE.ONLINE,context.getUserContext().getCurrentUser());
        byte[] byteBroadcastMsg = context.getMsgHandleContext().getHandle(Constant.MSG_TYPE.TEXT).apply(broadcastMsg.toJSONString());
        context.getBroadcast().cycle(byteBroadcastMsg,Constant.ONLINE_BROADCAST_CYCLE_TIME);


    }

}
