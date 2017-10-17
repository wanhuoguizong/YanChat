package com.xinlingyijiu.yanchat.core.net.socket;

import com.xinlingyijiu.yanchat.util.IOUtil;

import java.io.IOException;
import java.net.*;

/**
 * Created by laotou on 2017/10/17.
 */
public class SimpleSocketManager implements SocketManager {
    private MulticastSocket multicastSocket;
    private DatagramSocket datagramSocket;
    private ServerSocket serverSocket;

    private String multicastHost;
    private int multicastPort;
    private int datagramPort;
    private int serverSocketPort;
    @Override
    public String getMulticastHost() {
        return multicastHost;
    }
    @Override
    public int getMulticastPort() {
        return multicastPort;
    }
    @Override
    public int getDatagramPort() {
        return datagramPort;
    }
    @Override
    public int getServerSocketPort() {
        return serverSocketPort;
    }

    @Override
    public DatagramSocket getDatagramSocket() throws SocketException {
        if (datagramSocket == null) throw new SocketException("datagramSocket not initialized");
        return datagramSocket;
    }

    @Override
    public MulticastSocket getMulticastSocket() throws SocketException {
        if (multicastSocket == null) throw new SocketException("multicastSocket not initialized");
        return multicastSocket;
    }

    @Override
    public ServerSocket getServerSocket() throws SocketException {
        if (serverSocket == null) throw new SocketException("serverSocket not initialized");
        return serverSocket;
    }

    @Override
    public void initDatagramSocket(int port) throws SocketException {
        if (datagramSocket != null) throw new SocketException("datagramSocket has initialized!");
        this.datagramSocket = new DatagramSocket(port);
        this.datagramPort = port;
    }

    @Override
    public void initMulticastSocket(String host, int port) throws IOException {
        if (multicastSocket != null) throw new SocketException("multicastSocket has initialized!");
        this.multicastSocket = new MulticastSocket(port);
        InetAddress inetAddress = InetAddress.getByName(host);
        this.multicastSocket.joinGroup(inetAddress);
        this.multicastHost = host;
        this.multicastPort = port;
    }

    @Override
    public void initServerSocket(int port) throws IOException {
        if (serverSocket != null) throw new SocketException("serverSocket has initialized!");
        this.serverSocketPort = port;
        this.serverSocket = new ServerSocket(port);
    }

    @Override
    public void close() throws IOException {
        IOUtil.close(multicastSocket);
        IOUtil.close(serverSocket);
        IOUtil.close(datagramSocket);
    }
}
