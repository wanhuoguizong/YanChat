package com.xinlingyijiu.yanchat.core.net.socket;

import java.io.Closeable;
import java.io.IOException;
import java.net.*;

public interface SocketManager extends Closeable{

    String getMulticastHost();

    int getMulticastPort();

    int getDatagramPort();

    int getServerSocketPort();

    DatagramSocket getDatagramSocket() throws SocketException;

    MulticastSocket getMulticastSocket() throws SocketException;

//    Socket getSocket();

    ServerSocket getServerSocket() throws SocketException;

    void initDatagramSocket(int port) throws SocketException;

    void initMulticastSocket(String host,int port) throws IOException;

    void initServerSocket(int port) throws IOException;
}
