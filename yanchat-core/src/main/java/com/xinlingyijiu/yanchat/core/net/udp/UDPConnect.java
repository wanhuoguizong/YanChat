package com.xinlingyijiu.yanchat.core.net.udp;

import com.xinlingyijiu.yanchat.core.net.Connect;

import java.io.IOException;

/**
 * Created by laotou on 2017/10/17.
 */
public interface UDPConnect extends Connect{
    void send(String host,byte[] bytes) throws IOException;
//    void listen();
}
