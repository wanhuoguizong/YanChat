package com.xinlingyijiu.yanchat.core.bean;

import com.xinlingyijiu.yanchat.util.GUIDUtil;

import java.util.List;

public class ChatSession {
    private String id;
    private List<String> userIdList;

    private String sessionName;

    public String getSessionName() {
        return sessionName;
    }

    public void setSessionName(String sessionName) {
        this.sessionName = sessionName;
    }

    public ChatSession() {
        id = GUIDUtil.genRandomGUID();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getUserIdList() {
        return userIdList;
    }

    public void setUserIdList(List<String> userIdList) {
        this.userIdList = userIdList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ChatSession)) return false;

        ChatSession that = (ChatSession) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        return userIdList != null ? userIdList.equals(that.userIdList) : that.userIdList == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (userIdList != null ? userIdList.hashCode() : 0);
        return result;
    }
}
