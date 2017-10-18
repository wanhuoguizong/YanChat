package com.xinlingyijiu.yanchat.core.net.udp;

import com.alibaba.fastjson.JSON;
import com.xinlingyijiu.yanchat.core.Constant;
import com.xinlingyijiu.yanchat.core.bean.ConnectMsg;
import com.xinlingyijiu.yanchat.core.msg.MsgHandleContext;
import com.xinlingyijiu.yanchat.core.net.socket.SocketManager;
import com.xinlingyijiu.yanchat.core.queue.MsgProducer;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.SocketException;

/**
 * Created by laotou on 2017/10/17.
 */
public class UDPConnectImpl implements UDPConnect {
    private SocketManager socketManager;

    private boolean isListen;
    private MsgProducer msgProducer;

    public MsgProducer getMsgProducer() {
        return msgProducer;
    }

    public void setMsgProducer(MsgProducer msgProducer) {
        this.msgProducer = msgProducer;
    }
    @Override
    public boolean isListen() {
        return isListen;
    }

    public SocketManager getSocketManager() {
        return socketManager;
    }

    public void setSocketManager(SocketManager socketManager) {
        this.socketManager = socketManager;
    }

    @Override
    public void send(String host, byte[] bytes) throws IOException {
        this.send(host, socketManager.getDatagramPort(), bytes);
    }

    @Override
    public void send(String host, int port, byte[] bytes) throws IOException {
        DatagramPacket packet = new DatagramPacket(bytes, bytes.length, InetAddress.getByName(host), port);
        socketManager.getDatagramSocket().send(packet);
    }

    @Override
    public void send(String host, int port, String text) throws IOException {
        byte[] bytes = MsgHandleContext.getInstance().getHandle(Constant.DATA_TYPE.TEXT).apply(text);
        this.send(host, port, bytes);
    }

    @Override
    public void listen() throws IOException {
        if (isListen) throw new SocketException("UDPConnect already listen");
        if (socketManager.getDatagramSocket() == null)
            throw new SocketException("UDPConnect  is not defined");
        new Thread(() -> {
            while (true) {
                try {
                    byte[] bytes = new byte[Constant.BROADCAST_LISTEN_LEN];
                    DatagramPacket packet = new DatagramPacket(bytes, bytes.length);

                    socketManager.getDatagramSocket().receive(packet);

                    String data = (String) MsgHandleContext.getInstance().getConverseHand(Constant.DATA_TYPE.TEXT).apply(packet.getData());
                    System.out.println(String.format("接收：%s", data));
                    System.out.println("ip:" + packet.getAddress().getHostName() + ";port:" + packet.getPort());
                    ConnectMsg connectMsg = JSON.parseObject(data, ConnectMsg.class);
                    connectMsg.setHost(packet.getAddress().getHostName());
                    connectMsg.setPort(packet.getPort());
                    //                System.out.println(connectMsg);
                    this.msgProducer.sendMessage(Constant.QUEUE_KEY.BROADCAST, connectMsg);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
        isListen = true;
    }

    @Override
    public void close() throws IOException {
        socketManager.getDatagramSocket().close();
        isListen = false;

    }
}
