package com.xinlingyijiu.yanchat.core.consumer;

import com.alibaba.fastjson.JSON;
import com.xinlingyijiu.yanchat.core.Constant;
import com.xinlingyijiu.yanchat.core.Context;
import com.xinlingyijiu.yanchat.core.bean.ChatMsg;
import com.xinlingyijiu.yanchat.core.bean.ChatSession;
import com.xinlingyijiu.yanchat.core.bean.ConnectMsg;
import com.xinlingyijiu.yanchat.core.queue.MsgConsumer;
import com.xinlingyijiu.yanchat.core.service.ChatMsgService;
import com.xinlingyijiu.yanchat.core.service.ChatSessionService;
import com.xinlingyijiu.yanchat.core.service.OnlineService;
import com.xinlingyijiu.yanchat.core.user.User;

import java.util.Objects;

/**
 * Created by laotou on 2017/10/13.
 */
public class ConnectMsgConsumer implements MsgConsumer<ConnectMsg> {
    private OnlineService onlineService;

    private ChatMsgService chatMsgService;

    private ChatSessionService chatSessionService;

    public ChatSessionService getChatSessionService() {
        return chatSessionService;
    }

    public void setChatSessionService(ChatSessionService chatSessionService) {
        this.chatSessionService = chatSessionService;
    }

    public ChatMsgService getChatMsgService() {
        return chatMsgService;
    }

    public void setChatMsgService(ChatMsgService chatMsgService) {
        this.chatMsgService = chatMsgService;
    }

    public OnlineService getOnlineService() {
        return onlineService;
    }

    public void setOnlineService(OnlineService onlineService) {
        this.onlineService = onlineService;
    }

    @Override
    public void onMessage(ConnectMsg msg) throws Exception {
//        System.out.println("ConnectMsgConsumer:接收："+msg);
        if (Objects.equals(Constant.MSG_TYPE.ONLINE, msg.getType())) {
            User user = ((JSON) msg.getData()).toJavaObject(User.class);
            user.setHost(msg.getHost());
//
            onlineService.receive(user);
        }else if (Objects.equals(Constant.MSG_TYPE.CHAT_TEXT, msg.getType())){
            ChatMsg chatMsg = ((JSON) msg.getData()).toJavaObject(ChatMsg.class);
//            chatMsg.setHost(connectMsg.getHost());
            chatMsgService.receive(chatMsg);
        }else if (Objects.equals(Constant.MSG_TYPE.CREATE_SESSION, msg.getType())){
            ChatSession session = ((JSON) msg.getData()).toJavaObject(ChatSession.class);
//            chatMsg.setHost(connectMsg.getHost());
            chatSessionService.createIfNotExist(session);
        }else if (Objects.equals(Constant.MSG_TYPE.SESSION_INFO, msg.getType())){
            String  sessionId = ((JSON) msg.getData()).toJavaObject(String.class);
//            chatMsg.setHost(connectMsg.getHost());
            if (sessionId != null) {
                Context context = Context.getInstance();
                ChatSession session = chatSessionService.getById(sessionId);
                if (session != null) {
                    ConnectMsg<ChatSession> connectMsg = new ConnectMsg<>(Constant.MSG_TYPE.CREATE_SESSION, session);
                    byte[] byteBroadcastMsg = context.getMsgHandleContext().getHandle(Constant.DATA_TYPE.TEXT).apply(connectMsg.toJSONString());
                    context.getUdpConnect().send(msg.getHost(),msg.getPort(),byteBroadcastMsg);
                }
            }
        }
    }
}
