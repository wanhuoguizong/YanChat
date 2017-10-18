package com.xinlingyijiu.yanchat.core;

import java.io.IOException;
import java.util.Objects;

/**
 * Created by laotou on 2017/10/13.
 */
public class YanChatApplication {


    public static void start(Context context) {

        try {
            if (Objects.equals(Constant.MODEL.BROADCAST,context.getModel().getModel())) {
                context.getBroadcast().listen();
            }
            context.getUdpConnect().listen();

            context.getQueueListenner().listen();

//            ConnectMsg<User> connectMsg = new ConnectMsg<>(Constant.MSG_TYPE.ONLINE, context.getUserContext().getCurrentUser());
//            byte[] byteBroadcastMsg = context.getMsgHandleContext().getHandle(Constant.DATA_TYPE.TEXT).apply(connectMsg.toJSONString());
////            context.getBroadcast().cycle(byteBroadcastMsg, Constant.ONLINE_POLLING_CYCLE_TIME);
//
//            Runnable runnable = () -> {
//                try {
//                    context.getUdpConnect().send("localhost", byteBroadcastMsg);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            };
//            ScheduledExecutorUtil.getScheduler().scheduleAtFixedRate(runnable,0,10, TimeUnit.SECONDS);
            context.getOnlineService().onlinePolling(Constant.ONLINE_POLLING_CYCLE_TIME);


        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
