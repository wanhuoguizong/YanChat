package com.xinlingyijiu.yanchat.core;

import com.xinlingyijiu.yanchat.core.bean.BroadcastMsg;
import com.xinlingyijiu.yanchat.core.broadcast.Broadcast;
import com.xinlingyijiu.yanchat.core.broadcast.BroadcastImpl;
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
        user.setIp("0.0.0.0");
        user.setNickName("Hello world!");
        user.setPort(8080);
        user.setOnline(true);

        BroadcastMsg<User> userBroadcastMsg = new BroadcastMsg<>();
        Broadcast broadcast = new BroadcastImpl();
        new Thread(() -> {
            try {
                broadcast.listen(Constant.BROADCAST_DEFAULT_IP,Constant.BROADCAST_DEFAULT_PORT);
            } catch (SocketException e) {
                e.printStackTrace();
            }
        }).start();

        while (true){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(String.format("发送：%s",userBroadcastMsg.toJSONString()));
            try {
                broadcast.send(userBroadcastMsg.toJSONString().getBytes());
            } catch (SocketException e) {
                e.printStackTrace();
            }
        }
    }
}
