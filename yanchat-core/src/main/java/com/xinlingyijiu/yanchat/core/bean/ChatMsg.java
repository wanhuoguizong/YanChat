package com.xinlingyijiu.yanchat.core.bean;

import com.xinlingyijiu.yanchat.util.GUIDUtil;

/**
 * Created by laotou on 2017/10/18.
 */
public class ChatMsg {
    private String id;
    private String content;
    private String sendUserId;
//    private String targetUserId;
    private String chatSessionId;
    private long timestemp;

    public String getChatSessionId() {
        return chatSessionId;
    }

    public void setChatSessionId(String chatSessionId) {
        this.chatSessionId = chatSessionId;
    }

//    public String getTargetUserId() {
//        return targetUserId;
//    }
//
//    public void setTargetUserId(String targetUserId) {
//        this.targetUserId = targetUserId;
//    }

    public ChatMsg() {
        id = GUIDUtil.genRandomGUID();
        timestemp = System.currentTimeMillis();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSendUserId() {
        return sendUserId;
    }

    public void setSendUserId(String sendUserId) {
        this.sendUserId = sendUserId;
    }

    public long getTimestemp() {
        return timestemp;
    }

    public void setTimestemp(long timestemp) {
        this.timestemp = timestemp;
    }
}
