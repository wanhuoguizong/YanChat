package com.xinlingyijiu.yanchat.core.user;

import com.alibaba.fastjson.JSON;
import com.xinlingyijiu.yanchat.util.GUIDUtil;

import java.io.Serializable;

public class User implements Serializable{
    private String id;
    private String nickName;
    private String ip;
    private Integer port;
    private boolean online;

    public User() {

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

        if (online != user.online) return false;
        if (id != null ? !id.equals(user.id) : user.id != null) return false;
        if (nickName != null ? !nickName.equals(user.nickName) : user.nickName != null) return false;
        if (ip != null ? !ip.equals(user.ip) : user.ip != null) return false;
        return !(port != null ? !port.equals(user.port) : user.port != null);

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (nickName != null ? nickName.hashCode() : 0);
        result = 31 * result + (ip != null ? ip.hashCode() : 0);
        result = 31 * result + (port != null ? port.hashCode() : 0);
        result = 31 * result + (online ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", nickName='" + nickName + '\'' +
                ", ip='" + ip + '\'' +
                ", port=" + port +
                ", online=" + online +
                '}';
    }

    public String toJSONString(){
        return JSON.toJSONString(this);
     }
}
