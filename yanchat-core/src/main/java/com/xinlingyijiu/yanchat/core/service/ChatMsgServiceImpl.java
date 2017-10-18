package com.xinlingyijiu.yanchat.core.service;

import com.xinlingyijiu.yanchat.core.Constant;
import com.xinlingyijiu.yanchat.core.Context;
import com.xinlingyijiu.yanchat.core.bean.ChatMsg;
import com.xinlingyijiu.yanchat.core.bean.ConnectMsg;
import com.xinlingyijiu.yanchat.core.exception.YanChatRuntimeException;
import com.xinlingyijiu.yanchat.core.net.Connect;
import com.xinlingyijiu.yanchat.core.user.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 *
 * Created by laotou on 2017/10/18.
 */
public class ChatMsgServiceImpl implements ChatMsgService {
    private Connect connect;
    private List<Consumer<ChatMsg>> afterList;

    private Map<String ,List<ChatMsg>> msgMap;

    public ChatMsgServiceImpl() {
        afterList = new ArrayList<>();
        msgMap = new HashMap<>();
    }

    protected synchronized void putMsg(ChatMsg msg){
        List<ChatMsg> msgList = msgMap.get(msg.getSendUserId());
        if (msgList == null) {
            msgList = new ArrayList<>();
            msgMap.put(msg.getSendUserId(),msgList);
        }
        msgList.add(msg);
    }

    @Override
    public void setConnect(Connect connect) {
        this.connect = connect;
    }

    @Override
    public Connect getConnet() {
        return this.connect;
    }

    @Override
    public void receive(ChatMsg msg) {
        putMsg(msg);
        this.executeAllAfter(msg);
    }

    @Override
    public void after(Consumer<ChatMsg> after) {
        this.afterList.add(after);
    }

    @Override
    public void executeAllAfter(ChatMsg msg) {
        if (this.afterList.isEmpty()) return;
        for (Consumer<ChatMsg> after : this.afterList) {
            try {
                after.accept(msg);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void send(ChatMsg msg) throws IOException {
        Context context = Context.getInstance();
        User currentUser = context.getUserContext().getCurrentUser();
        if (msg.getSendUserId() == null) msg.setSendUserId(currentUser.getId());
        if (msg.getTargetUserId() == null) throw new YanChatRuntimeException("Unknown msg's targetUserId;");
        User targetUser = context.getUserContext().get(msg.getTargetUserId());
        if (targetUser == null) return;
        ConnectMsg<ChatMsg> connectMsg = new ConnectMsg<>(Constant.MSG_TYPE.CHAT_TEXT, msg);
        byte[] byteBroadcastMsg = context.getMsgHandleContext().getHandle(Constant.DATA_TYPE.TEXT).apply(connectMsg.toJSONString());
        connect.send(targetUser.getHost(),targetUser.getUdpPort(),byteBroadcastMsg);

        this.receive(msg);
    }

    @Override
    public void send(String targetUserId, String content) throws IOException {
        ChatMsg msg = new ChatMsg();
        msg.setTargetUserId(targetUserId);
        msg.setContent(content);
        send(msg);
    }
}
