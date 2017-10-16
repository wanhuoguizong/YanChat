package com.xinlingyijiu.yanchat.core.net.socket;

import com.xinlingyijiu.yanchat.util.IOUtil;

import java.io.IOException;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class MulticastSocketManager implements UDPSocketManager {
    private MulticastSocket socket;

    private String broadcastHost;

    private int port;

    @Override
    public MulticastSocket getSocket() throws IOException {
        return this.socket;
    }

    /**
     * @param broadcastHost 广播Ip
     * @param port        监听端口
     */
    public MulticastSocketManager(String broadcastHost, int port) throws IOException {
        this.broadcastHost = broadcastHost;
        this.port = port;
        this.socket = new MulticastSocket(this.port);
        this.socket.joinGroup(InetAddress.getByName(this.broadcastHost));
    }

    public String getBroadcastHost() {
        return broadcastHost;
    }

//    public void setBroadcastIp(String broadcastHost) {
//        this.broadcastHost = broadcastHost;
//    }

    public int getPort() {
        return port;
    }

    /**
     * 关闭所有资源
     * @throws IOException
     */
    @Override
    public void close() throws IOException {
        IOUtil.close(this.socket);
        this.port = 0;
    }

//    public void setBroadcastPort(int port) {
//        this.port = port;
//    }
}
