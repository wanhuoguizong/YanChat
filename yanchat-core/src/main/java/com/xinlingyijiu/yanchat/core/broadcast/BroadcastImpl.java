package com.xinlingyijiu.yanchat.core.broadcast;

import com.xinlingyijiu.yanchat.core.broadcast.Broadcast;
import com.xinlingyijiu.yanchat.core.socket.MulticastSocketManager;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Objects;

public class BroadcastImpl implements Broadcast {
    public MulticastSocketManager socketManager;

    @Override
    public void listen(String broadcastIp, int port) throws SocketException {
        if (socketManager != null)  throw new SocketException("MulticastSocketManager already defined");
        Objects.requireNonNull(broadcastIp,"broadcastIp 不能是null");
        socketManager = new MulticastSocketManager(broadcastIp,port);
        while (true) {
            byte[] bytes = new byte[2048];
            DatagramPacket packet = new DatagramPacket(bytes, bytes.length);
            try {
                socketManager.getSocket().receive(packet);
                System.out.println(String.format("接收：%s", new String(packet.getData())));
                //todo 消息处理
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void send(int port, byte[] msg) {
        send(socketManager.getBroadcastIp(),port,msg);
    }

    @Override
    public void send(String ip,int port, byte[] msg) {
        Objects.requireNonNull(msg,"msg 不能是空");
        try {
            DatagramPacket packet = new DatagramPacket(msg,msg.length, InetAddress.getByName(ip),port );
            socketManager.getSocket().send(packet);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void send(byte[] msg) {
        send(socketManager.getBroadcastIp(),socketManager.getPort(),msg);
    }

    public MulticastSocketManager getSocketManager() {
        return socketManager;
    }
}
