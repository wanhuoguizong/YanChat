package com.xinlingyijiu.yanchat.core.net.socket;

import com.xinlingyijiu.yanchat.util.IOUtil;

import java.io.IOException;
import java.net.DatagramSocket;

public class DatagramSocketManager implements UDPSocketManager {
    private DatagramSocket socket;



    private int port;

    @Override
    public DatagramSocket getSocket() throws IOException {
        return this.socket;
    }

    /**
     * @param port        监听端口
     */
    public DatagramSocketManager( int port) throws IOException {
        this.socket = new DatagramSocket(this.port);
        this.port = port;
    }



//    public void setBroadcastIp(String broadcastIp) {
//        this.broadcastIp = broadcastIp;
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
