package com.xinlingyijiu.yanchat.core.net.socket;

import java.net.DatagramSocket;
import java.net.MulticastSocket;
import java.net.ServerSocket;
import java.net.Socket;

public interface SocketManager {

    DatagramSocket getDatagramSocket();

    MulticastSocket getMulticastSocket();

    Socket getSocket();

    ServerSocket getServerSocket();
}
