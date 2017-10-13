package com.xinlingyijiu.yanchat.core;

import java.net.SocketException;

/**
 * Created by laotou on 2017/10/13.
 */
public class YanChatApplication {

    public static void start(Context context){
        context.userDefaultContext();
        try {
            context.getBroadcast().listen(context.getBroadcastHost(),context.getBroadcastPort());
        } catch (SocketException e) {
            e.printStackTrace();
        }
        context.getQueueListenner().listen();
    }
}
