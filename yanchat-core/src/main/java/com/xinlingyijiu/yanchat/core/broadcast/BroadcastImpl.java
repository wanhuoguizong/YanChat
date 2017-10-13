package com.xinlingyijiu.yanchat.core.broadcast;

import com.alibaba.fastjson.JSON;
import com.xinlingyijiu.yanchat.core.Constant;
import com.xinlingyijiu.yanchat.core.bean.BroadcastMsg;
import com.xinlingyijiu.yanchat.core.msg.MsgHandleContext;
import com.xinlingyijiu.yanchat.core.msg.StringMsgConverseHandle;
import com.xinlingyijiu.yanchat.core.queue.MsgProducer;
import com.xinlingyijiu.yanchat.core.socket.MulticastSocketManager;
import com.xinlingyijiu.yanchat.util.IOUtil;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Objects;

public class BroadcastImpl implements Broadcast {
    public MulticastSocketManager socketManager;

    public MsgProducer msgProducer;



    public void setSocketManager(MulticastSocketManager socketManager) {
        this.socketManager = socketManager;
    }
    @Override
    public MsgProducer getMsgProducer() {
        return msgProducer;
    }
    @Override
    public void setMsgProducer(MsgProducer msgProducer) {
        this.msgProducer = msgProducer;
    }

    @Override
    public void listen(String broadcastHost, int port) throws SocketException {
        if (socketManager != null)  throw new SocketException("MulticastSocketManager already defined");
        Objects.requireNonNull(broadcastHost,"broadcastIp not defined");
        socketManager = new MulticastSocketManager(broadcastHost,port);
        new Thread(() -> {
            while (true) {
                byte[] bytes = new byte[Constant.BROADCAST_LISTEN_LEN];
                DatagramPacket packet = new DatagramPacket(bytes, bytes.length);
                try {
                    socketManager.getSocket().receive(packet);

                    String data = (String) MsgHandleContext.getInstance().getConverseHand(Constant.MSG_TYPE.TEXT).apply(packet.getData());
                    System.out.println(String.format("接收：%s", data));
                    System.out.println("ip:" + packet.getAddress().getHostName() + ";port:" + packet.getPort());
                    BroadcastMsg broadcastMsg = JSON.parseObject(data, BroadcastMsg.class);
                    broadcastMsg.setHost(packet.getAddress().getHostName());
                    broadcastMsg.setPort(packet.getPort());
//                System.out.println(broadcastMsg);
                    this.msgProducer.sendMessage(Constant.QUEUE_KEY.BROADCAST, broadcastMsg);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    public void send(int port, byte[] msg) throws SocketException {
        if (socketManager == null)  throw new SocketException("MulticastSocketManager is not defined");
        send(socketManager.getBroadcastHost(),port,msg);
    }

    @Override
    public void send(String host,int port, byte[] msg) {
        Objects.requireNonNull(msg,"msg 不能是空");
        try {
            DatagramPacket packet = new DatagramPacket(msg,msg.length, InetAddress.getByName(host),port );
            socketManager.getSocket().send(packet);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void send(byte[] msg) throws SocketException {
        if (socketManager == null)  throw new SocketException("MulticastSocketManager is not defined");
        send(socketManager.getBroadcastHost(),socketManager.getPort(),msg);
    }

    public MulticastSocketManager getSocketManager() {
        return socketManager;
    }


    /**
     * 关闭所有资源
     * @throws IOException
     */
    @Override
    public void close() throws IOException {
        IOUtil.close(this.socketManager);
    }
}
