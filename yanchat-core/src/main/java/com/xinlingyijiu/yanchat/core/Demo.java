package com.xinlingyijiu.yanchat.core;

import com.xinlingyijiu.yanchat.core.bean.BroadcastMsg;
import com.xinlingyijiu.yanchat.core.broadcast.Broadcast;
import com.xinlingyijiu.yanchat.core.broadcast.BroadcastImpl;
import com.xinlingyijiu.yanchat.core.msg.MsgHandleContext;
import com.xinlingyijiu.yanchat.core.msg.StringMsgConverseHandle;
import com.xinlingyijiu.yanchat.core.user.User;

import java.net.SocketException;

/**
 * Created by laotou on 2017/10/11.
 */
public class Demo {
    public static void main(String[] args) {
        System.out.println("启动！");
//        Demo demo = new Demo();
//        MulticastSocket
//        DatagramSocket
        User user = new User();
        user.setHost("0.0.0.0");
        user.setNickName("Hello world!");
        user.setBroadcastPort(Constant.DEFAULT_PORT.BROADCAST);
        user.setTcpPort(Constant.DEFAULT_PORT.TCP);
        user.setUdpPort(Constant.DEFAULT_PORT.UDP);
        user.setOnline(true);
        BroadcastMsg<User> userBroadcastMsg = new BroadcastMsg<>(Constant.MSG_TYPE.TEXT,user);

        Context context = Context.getInstance();
//        context.userDefaultContext();

//        Broadcast broadcast = context.getBroadcast();


//        new Thread(() -> {
//            try {
//                broadcast.listen(Constant.BROADCAST_DEFAULT_HOST,Constant.DEFAULT_PORT.BROADCAST);
//            } catch (SocketException e) {
//                e.printStackTrace();
//            }
//        }).start();

        YanChatApplication.start(context);

        while (true){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(String.format("发送：%s",userBroadcastMsg.toJSONString()));
            try {
                context.getBroadcast().send(userBroadcastMsg.toJSONString().getBytes());
            } catch (SocketException e) {
                e.printStackTrace();
            }
        }
    }
}
