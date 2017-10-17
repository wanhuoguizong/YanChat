package com.xinlingyijiu.yanchat.core.net.broadcast;

import com.xinlingyijiu.yanchat.core.net.Connect;

import java.io.Closeable;
import java.io.IOException;
import java.net.SocketException;

public interface Broadcast extends Connect{


    /**
     * 监听广播,只能调用一次，并且保持监听状态
//     * @param broadcastHost
//     * @param port
     * @throws SocketException
     */
//    void listen() throws  IOException;

    /**
     * 对监听的ip，指定端口号发送信息
     * 必须在开始监听后才能调用
     * @param port
     * @param msg
     */
    void send(int port, byte[] msg) throws SocketException, IOException;

//    /**
//     * 对指定 ip、端口号发送信息
//     * @param ip
//     * @param port
//     * @param msg
//     */
//    void send(String ip,int port, byte[] msg) ;

    /**
     * 向监听的广播ip、端口发送信息
     * 必须在开始监听后才能调用
     * @param msg
     */
    void send( byte[] msg) throws IOException;

    /**
     * 上线播报，每隔一段时间广播一次
     * @param msg
     * @param cycleTime，周期,单位秒
     */
    void cycle(byte[] msg,long cycleTime);

}
