package com.xinlingyijiu.yanchat.core.user;

import com.alibaba.fastjson.JSON;
import com.xinlingyijiu.yanchat.util.GUIDUtil;

import java.io.Serializable;

public class User implements Serializable{
    private String id;
    private String nickName;
    private String host;
    private Integer broadcastPort;
    private Integer tcpPort;
    private Integer udpPort;
    private boolean online;


    public User() {

    }

    public Integer getTcpPort() {
        return tcpPort;
    }

    public void setTcpPort(Integer tcpPort) {
        this.tcpPort = tcpPort;
    }

    public Integer getUdpPort() {
        return udpPort;
    }

    public void setUdpPort(Integer udpPort) {
        this.udpPort = udpPort;
    }

    public String getId() {
        return id == null ? ( this.id = GUIDUtil.genRandomGUID()) : id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getBroadcastPort() {
        return broadcastPort;
    }

    public void setBroadcastPort(Integer broadcastPort) {
        this.broadcastPort = broadcastPort;
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

        if (online != user.online) return false;
        if (id != null ? !id.equals(user.id) : user.id != null) return false;
        if (nickName != null ? !nickName.equals(user.nickName) : user.nickName != null) return false;
        if (host != null ? !host.equals(user.host) : user.host != null) return false;
        return !(broadcastPort != null ? !broadcastPort.equals(user.broadcastPort) : user.broadcastPort != null);

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (nickName != null ? nickName.hashCode() : 0);
        result = 31 * result + (host != null ? host.hashCode() : 0);
        result = 31 * result + (broadcastPort != null ? broadcastPort.hashCode() : 0);
        result = 31 * result + (online ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", nickName='" + nickName + '\'' +
                ", host='" + host + '\'' +
                ", broadcastPort=" + broadcastPort +
                ", online=" + online +
                '}';
    }

    public String toJSONString(){
        return JSON.toJSONString(this);
     }
}
