package com.xinlingyijiu.yanchat.core.broadcast;

import java.net.SocketException;

public interface Broadcast {
    void listen(String broadcastIp, int port) throws SocketException;


    void send(int port, byte[] msg);

    void send(String ip,int port, byte[] msg) ;

    void send( byte[] msg);
}
