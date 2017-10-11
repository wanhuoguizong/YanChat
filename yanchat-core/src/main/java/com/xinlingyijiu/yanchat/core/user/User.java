package com.xinlingyijiu.yanchat.core.user;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;

public class User implements Serializable{
    private String nickName;
    private String ip;
    private Integer port;
    private boolean isOnline;

    public boolean isOnline() {
        return isOnline;
    }

    public void setOnline(boolean online) {
        isOnline = online;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;

        User user = (User) o;

        if (getNickName() != null ? !getNickName().equals(user.getNickName()) : user.getNickName() != null)
            return false;
        if (getIp() != null ? !getIp().equals(user.getIp()) : user.getIp() != null) return false;
        return getPort() != null ? getPort().equals(user.getPort()) : user.getPort() == null;
    }

    @Override
    public int hashCode() {
        int result = getNickName() != null ? getNickName().hashCode() : 0;
        result = 31 * result + (getIp() != null ? getIp().hashCode() : 0);
        result = 31 * result + (getPort() != null ? getPort().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "User{" +
                "nickName='" + nickName + '\'' +
                ", ip='" + ip + '\'' +
                ", port=" + port +
                ", isOnline=" + isOnline +
                '}';
    }
     public String toJSONString(){
        return JSON.toJSONString(this);
     }
}
