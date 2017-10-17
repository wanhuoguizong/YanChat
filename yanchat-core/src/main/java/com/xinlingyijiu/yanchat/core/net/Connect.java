package com.xinlingyijiu.yanchat.core.net;

import java.io.Closeable;
import java.io.IOException;

/**
 * Created by laotou on 2017/10/17.
 */
public interface Connect extends Closeable{
    /**
     * 链接管理
     * @param host 目标地址
     * @param port 目标端口
     * @param bytes 发送内容
     */
    void send(String host,int port,byte[] bytes) throws IOException;
    /**
     * 链接管理
     * @param host 目标地址
     * @param port 目标端口
     * @param text 发送文本内容
     */
    void send(String host,int port,String text) throws IOException;

    void listen() throws IOException;
}
