package com.xinlingyijiu.yanchat.core.broadcast;

import java.io.Closeable;
import java.net.SocketException;

public interface Broadcast extends Closeable{

    /**
     * 监听广播,只能调用一次，并且保持监听状态
     * @param broadcastIp
     * @param port
     * @throws SocketException
     */
    void listen(String broadcastIp, int port) throws SocketException;

    /**
     * 对监听的ip，指定端口号发送信息
     * 必须在开始监听后才能调用
     * @param port
     * @param msg
     */
    void send(int port, byte[] msg) throws SocketException;

    /**
     * 对指定 ip、端口号发送信息
     * @param ip
     * @param port
     * @param msg
     */
    void send(String ip,int port, byte[] msg) ;

    /**
     * 向监听的广播ip、端口发送信息
     * 必须在开始监听后才能调用
     * @param msg
     */
    void send( byte[] msg) throws SocketException;

}