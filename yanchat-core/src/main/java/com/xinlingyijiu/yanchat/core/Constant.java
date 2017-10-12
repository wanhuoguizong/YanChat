package com.xinlingyijiu.yanchat.core;

/**
 * Created by laotou on 2017/10/12.
 */
public interface Constant {
    //缺省广播ip
    String BROADCAST_DEFAULT_IP = "230.250.250.250";
    //缺省广播端口
    int BROADCAST_DEFAULT_PORT = 9250;
    //默认广播接收byte[]长度
    int BROADCAST_LISTEN_LEN = 1024;

    interface MSG_TYPE{
        String TEST = "TEXT";
    }
}
