package com.xinlingyijiu.yanchat.core.service;

import com.xinlingyijiu.yanchat.core.Constant;
import com.xinlingyijiu.yanchat.core.Context;
import com.xinlingyijiu.yanchat.core.bean.ChatMsg;
import com.xinlingyijiu.yanchat.core.bean.ChatSession;
import com.xinlingyijiu.yanchat.core.bean.ConnectMsg;
import com.xinlingyijiu.yanchat.core.exception.YanChatRuntimeException;
import com.xinlingyijiu.yanchat.core.net.Connect;
import com.xinlingyijiu.yanchat.core.user.User;

import java.io.IOException;
import java.util.*;
import java.util.function.Consumer;

/**
 *
 * Created by laotou on 2017/10/18.
 */
public class ChatMsgServiceImpl implements ChatMsgService {
    private Connect connect;
    private List<Consumer<ChatMsg>> afterList;

    private Map<String ,List<ChatMsg>> msgMap;

    private ChatSessionService chatSessionService;

    public ChatSessionService getChatSessionService() {
        return chatSessionService;
    }

    public void setChatSessionService(ChatSessionService chatSessionService) {
        this.chatSessionService = chatSessionService;
    }

    public ChatMsgServiceImpl() {
        afterList = new ArrayList<>();
        msgMap = new HashMap<>();
    }

    protected synchronized void putMsg(ChatMsg msg){
        List<ChatMsg> msgList = msgMap.get(msg.getChatSessionId());
        if (msgList == null) {
            msgList = new ArrayList<>();
            msgMap.put(msg.getChatSessionId(),msgList);
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
        getSessionInfoIfNotExist(msg);
        this.executeAllAfter(msg);
    }
    //如果会话不存在，则请求重发会话信息
    private void getSessionInfoIfNotExist(ChatMsg msg) {
        ChatSession session = chatSessionService.getById(msg.getChatSessionId());
        if (session == null){
            ConnectMsg<String> connectMsg = new ConnectMsg<>(Constant.MSG_TYPE.SESSION_INFO, msg.getChatSessionId());
            Context context = Context.getInstance();
            User targetUser = context.getUserContext().get(msg.getSendUserId());
            if (targetUser != null) {
                byte[] byteBroadcastMsg = context.getMsgHandleContext().getHandle(Constant.DATA_TYPE.TEXT).apply(connectMsg.toJSONString());
                try {
                    connect.send(targetUser.getHost(), targetUser.getUdpPort(), byteBroadcastMsg);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
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
        if (msg.getChatSessionId() == null) throw new YanChatRuntimeException("Unknown msg's chatSessionId;");
        ChatSession chatSession = getChatSession(msg);
        for (String targetUserId : chatSession.getUserIdList()) {
            if (Objects.equals(currentUser.getId(), targetUserId)) continue;
            User targetUser = context.getUserContext().get(targetUserId);
            if (targetUser == null) break;
            ConnectMsg<ChatMsg> connectMsg = new ConnectMsg<>(Constant.MSG_TYPE.CHAT_TEXT, msg);
            byte[] byteBroadcastMsg = context.getMsgHandleContext().getHandle(Constant.DATA_TYPE.TEXT).apply(connectMsg.toJSONString());
            connect.send(targetUser.getHost(),targetUser.getUdpPort(),byteBroadcastMsg);
        }


        this.receive(msg);
    }

    private ChatSession getChatSession(ChatMsg msg) {
        ChatSession chatSession = chatSessionService.getById(msg.getChatSessionId());
        if (chatSession == null) throw new RuntimeException("会话不存在");
        return chatSession;
    }

    @Override
    public void send(String chatSessionId, String content) throws IOException {

        ChatMsg msg = new ChatMsg();
        msg.setChatSessionId(chatSessionId);
        msg.setContent(content);
        send(msg);
    }
    @Override
    public void sendToUser(String targetUserId, String content) throws IOException {
        ChatSession chatSession = chatSessionService.getByTargetUserId(targetUserId);
        if (chatSession == null){
            ArrayList<String> userIds = new ArrayList<>();
            userIds.add(Context.getInstance().getUserContext().getCurrentUser().getId());
            userIds.add(targetUserId);
            chatSession = chatSessionService.createByUserIds(userIds);
        }
        send(chatSession.getId(),content);
    }
}
