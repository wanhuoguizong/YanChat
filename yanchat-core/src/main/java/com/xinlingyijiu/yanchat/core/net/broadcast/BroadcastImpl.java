package com.xinlingyijiu.yanchat.core.net.broadcast;

import com.alibaba.fastjson.JSON;
import com.xinlingyijiu.yanchat.core.Constant;
import com.xinlingyijiu.yanchat.core.bean.ConnectMsg;
import com.xinlingyijiu.yanchat.core.msg.MsgHandleContext;
import com.xinlingyijiu.yanchat.core.net.socket.SocketManager;
import com.xinlingyijiu.yanchat.core.queue.MsgProducer;
import com.xinlingyijiu.yanchat.util.ScheduledExecutorUtil;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class BroadcastImpl implements Broadcast {

    public MsgProducer msgProducer;

    public SocketManager socketManager;

    public boolean isListen = false;
    @Override
    public boolean isListen() {
        return isListen;
    }

    public void setSocketManager(SocketManager socketManager) {
        this.socketManager = socketManager;
    }
//    @Override
    public MsgProducer getMsgProducer() {
        return msgProducer;
    }
//    @Override
    public void setMsgProducer(MsgProducer msgProducer) {
        this.msgProducer = msgProducer;
    }

    @Override
    public void listen() throws IOException {
        if (isListen )  throw new SocketException("Broadcast already listen");
        if (socketManager.getMulticastSocket() == null )  throw new SocketException("MulticastSocketManager  is not defined");

        new Thread(() -> {
            try {
                while (true) {
                    byte[] bytes = new byte[Constant.BROADCAST_LISTEN_LEN];
                    DatagramPacket packet = new DatagramPacket(bytes, bytes.length);
                    try {
                        socketManager.getMulticastSocket().receive(packet);

                        String data = (String) MsgHandleContext.getInstance().getConverseHand(Constant.DATA_TYPE.TEXT).apply(packet.getData());
//                        System.out.println(String.format("接收：%s", data));
//                        System.out.println("ip:" + packet.getAddress().getHostName() + ";port:" + packet.getPort());
                        ConnectMsg connectMsg = JSON.parseObject(data, ConnectMsg.class);
                        connectMsg.setHost(packet.getAddress().getHostName());
                        connectMsg.setPort(packet.getPort());
    //                System.out.println(connectMsg);
                        this.msgProducer.sendMessage(Constant.QUEUE_KEY.BROADCAST, connectMsg);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    @Override
    public void send(int port, byte[] msg) throws IOException {
        if (socketManager == null)  throw new SocketException("MulticastSocketManager is not defined");
        send(socketManager.getMulticastSocket().getInetAddress().getHostName(),port,msg);
    }

    @Override
    public void send(String host,int port, byte[] msg) throws IOException {
        Objects.requireNonNull(msg,"msg 不能是空");
        DatagramPacket packet = new DatagramPacket(msg,msg.length, InetAddress.getByName(host),port );
        socketManager.getMulticastSocket().send(packet);
    }



    @Override
    public void send(byte[] msg) throws IOException {
        if (socketManager == null)  throw new SocketException("MulticastSocketManager is not defined");
        String host = socketManager.getMulticastHost();
        int port = socketManager.getMulticastPort();
        send(host, port,msg);
    }

    @Override
    public void cycle(byte[] msg, long cycleTime) {
        Runnable runnable = () -> {
            try {
                this.send(msg);
            } catch (Exception e) {
                e.printStackTrace();
            }
        };
        ScheduledExecutorUtil.getScheduler().scheduleAtFixedRate(runnable,0,cycleTime, TimeUnit.SECONDS);
    }

    public SocketManager getSocketManager() {
        return socketManager;
    }

    @Override
    public void send(String host, int port, String text) throws IOException {
        //todo
        byte[] bytes =  MsgHandleContext.getInstance().getHandle(Constant.DATA_TYPE.TEXT).apply(text);
        send(host,port,text);
    }
    /**
     * 关闭所有资源
     * @throws IOException
     */
    @Override
    public void close() throws IOException {
        this.socketManager.getMulticastSocket().close();
        isListen = false;
        ScheduledExecutorUtil.getScheduler().shutdown();
    }
}
