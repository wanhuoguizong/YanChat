package com.xinlingyijiu.yanchat.core.service;

import com.xinlingyijiu.yanchat.core.bean.ChatSession;

import java.util.List;

/**
 * 聊天会话
 */
public interface ChatSessionService {
    ChatSession getById(String sessionId);

    //查看是否有对指定用户的会话
    ChatSession getByTargetUserId(String targetUserId);

    ChatSession createByUserIds(List<String> userIds);

    ChatSession createByUserIdsIfNotExist(List<String> userIds);

    void createIfNotExist(ChatSession session);

    List<ChatSession> getAll();

    void sendCreateSessionInfo(ChatSession session);
}
