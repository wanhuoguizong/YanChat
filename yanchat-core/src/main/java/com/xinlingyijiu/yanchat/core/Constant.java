package com.xinlingyijiu.yanchat.core;

/**
 * Created by laotou on 2017/10/12.
 */
public interface Constant {
    //缺省广播ip
    String BROADCAST_DEFAULT_HOST = "230.250.250.251";


    //默认端口
    interface DEFAULT_PORT{
        int BROADCAST = 9250;//广播
        int TCP = 9350;//tcp
        int UDP = 9450;//udp
    }
    //队列key
    interface QUEUE_KEY{
        String BROADCAST = "BROADCAST";
    }

    //默认广播接收byte[]长度
    int BROADCAST_LISTEN_LEN = 1024;

    /**
     * 消息数据类型
     */
    interface DATA_TYPE {
        String TEXT = "TEXT";//文本
    }
    //队列默认容量
    int DEFAULT_QUEUE_CAPACITY = 20;
    //消息类型
    interface MSG_TYPE {
        String ONLINE = "online";
        String CHAT_TEXT = "文字聊天";//文字聊天
    }
    //上线广播周期，单位秒
    int ONLINE_POLLING_CYCLE_TIME = 30;
}
