package com.xinlingyijiu.yanchat.core.service;

import com.xinlingyijiu.yanchat.core.Constant;
import com.xinlingyijiu.yanchat.core.Context;
import com.xinlingyijiu.yanchat.core.bean.ChatSession;
import com.xinlingyijiu.yanchat.core.bean.ConnectMsg;
import com.xinlingyijiu.yanchat.core.net.Connect;
import com.xinlingyijiu.yanchat.core.user.User;
import com.xinlingyijiu.yanchat.core.user.UserContext;

import java.io.IOException;
import java.util.*;

public class ChatSessionServiceImpl implements ChatSessionService {
    private List<ChatSession> chatSessionList = new ArrayList<>();
    private Map<String,ChatSession> chatSessionMap = new HashMap<>();
    private Connect connect;

    public Connect getConnect() {
        return connect;
    }

    public void setConnect(Connect connect) {
        this.connect = connect;
    }

    private synchronized int putChatSession(ChatSession chatSession){
        if (chatSession == null) return 0;
        if (chatSessionList.contains(chatSession)) return 0;
        if (chatSessionMap.containsKey(chatSession.getId())) return 0;//可以抛出id重复异常
        chatSessionMap.put(chatSession.getId(),chatSession);
        chatSessionList.add(chatSession);
        sendCreateSessionInfo(chatSession);
        return 1;
    }

    @Override
    public ChatSession getById(String sessionId) {
        return chatSessionMap.get(sessionId);
    }
    @Override
    public ChatSession getByTargetUserId(String targetUserId) {
        for (ChatSession chatSession : chatSessionList) {
            List<String> userIdList = chatSession.getUserIdList();
            if (userIdList.size()  == 2 && userIdList.contains(targetUserId)){
                return chatSession;
            }
        }
        return null;
    }

    @Override
    public ChatSession createByUserIds(List<String> userIds) {
        if (userIds != null && !userIds.isEmpty()){
            ChatSession chatSession = new ChatSession();
            chatSession.setUserIdList(userIds);
            putChatSession(chatSession);
            return chatSession;
        }
        return null;
    }
    @Override
    public ChatSession createByUserIdsIfNotExist(List<String> userIds) {
        if (userIds != null && !userIds.isEmpty()){
            for (ChatSession session : chatSessionList) {
                if (session.getUserIdList().equals(userIds)) return session;
            }
            ChatSession chatSession = new ChatSession();
            chatSession.setUserIdList(userIds);
            if (userIds.size() > 2) {
                chatSession.setSessionName(String.format("多人会话%s", chatSession.getId()));
            }else {
                UserContext userContext = Context.getInstance().getUserContext();
                User currentUser = userContext.getCurrentUser();
                User targetUser = userContext.get(Objects.equals(currentUser.getId(),userIds.get(0)) ? userIds.get(1) : userIds.get(0));
                chatSession.setSessionName(String.format("%s-%s",currentUser.getNickName(),targetUser.getNickName()));
            }
            putChatSession(chatSession);
            return chatSession;
        }
        return null;
    }
    @Override
    public void createIfNotExist(ChatSession session) {
        if (chatSessionMap.get(session.getId()) == null)
        putChatSession(session);
    }

    @Override
    public List<ChatSession> getAll() {
        return this.chatSessionList;
    }
    @Override
    public void sendCreateSessionInfo(ChatSession session){
        Context context = Context.getInstance();
//        ChatSession session = getById(sessionId);
        if (session != null) {
            ConnectMsg<ChatSession> connectMsg = new ConnectMsg<>(Constant.MSG_TYPE.CREATE_SESSION, session);
            byte[] byteBroadcastMsg = context.getMsgHandleContext().getHandle(Constant.DATA_TYPE.TEXT).apply(connectMsg.toJSONString());
            for (String uid : session.getUserIdList()) {
                User targetUser = context.getUserContext().get(uid);
                if (targetUser != null) {
                    try {
                        connect.send(targetUser.getHost(), targetUser.getUdpPort(), byteBroadcastMsg);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

        }

    }
}
