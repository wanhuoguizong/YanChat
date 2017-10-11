package com.xinlingyijiu.yanchat.core.socket;

import java.io.IOException;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class MulticastSocketManager implements SocketManager{
    private MulticastSocket socket;

    private String broadcastIp;

    private int port;

    @Override
    public MulticastSocket getSocket() throws IOException {
        if (this.socket == null) {
            this.socket = new MulticastSocket(this.port);
            this.socket.joinGroup(InetAddress.getByName(this.broadcastIp));
        }
        return this.socket;
    }

    /**
     * @param broadcastIp 广播Ip
     * @param port        监听端口
     */
    public MulticastSocketManager(String broadcastIp, int port) {
        this.broadcastIp = broadcastIp;
        this.port = port;
    }

    public String getBroadcastIp() {
        return broadcastIp;
    }

//    public void setBroadcastIp(String broadcastIp) {
//        this.broadcastIp = broadcastIp;
//    }

    public int getPort() {
        return port;
    }

//    public void setPort(int port) {
//        this.port = port;
//    }
}
