package com.xinlingyijiu.yanchat.core.consumer;

import com.alibaba.fastjson.JSON;
import com.xinlingyijiu.yanchat.core.Constant;
import com.xinlingyijiu.yanchat.core.Context;
import com.xinlingyijiu.yanchat.core.bean.ConnectMsg;
import com.xinlingyijiu.yanchat.core.queue.MsgConsumer;
import com.xinlingyijiu.yanchat.core.service.OnlineService;
import com.xinlingyijiu.yanchat.core.user.User;

import java.util.Objects;

/**
 * Created by laotou on 2017/10/13.
 */
public class ConnectMsgConsumer implements MsgConsumer<ConnectMsg> {
    private OnlineService onlineService;

    public OnlineService getOnlineService() {
        return onlineService;
    }

    public void setOnlineService(OnlineService onlineService) {
        this.onlineService = onlineService;
    }

    @Override
    public void onMessage(ConnectMsg msg) throws Exception {
        //todo
        System.out.println("ConnectMsgConsumer:接收："+msg);
        ConnectMsg connectMsg =  msg;
        if (Objects.equals(Constant.BROADCAST_TYPE.ONLINE, connectMsg.getType())) {
            User user = ((JSON) connectMsg.getData()).toJavaObject(User.class);
            user.setHost(connectMsg.getHost());
//
            onlineService.receive(user);
        }
    }
}
