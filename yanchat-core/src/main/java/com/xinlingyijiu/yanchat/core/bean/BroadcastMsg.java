package com.xinlingyijiu.yanchat.core.bean;

import com.alibaba.fastjson.JSON;
import com.xinlingyijiu.yanchat.util.GUIDUtil;

import java.io.Serializable;

public class BroadcastMsg<T> implements Serializable{
    private String id;
    private long timestemp;
    private String type;
    private T data;
    private String host;
    private Integer port;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public BroadcastMsg() {
        this(null,null);
    }

    public BroadcastMsg(String type, T data) {
        this(type,data,null,null);
    }
    public BroadcastMsg(String type, T data,String host,Integer port) {
        this.id = GUIDUtil.genRandomGUID();
        this.timestemp = System.currentTimeMillis();
        this.type = type;
        this.data = data;
        this.host = host;
        this.port = port;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTimestemp(long timestemp) {
        this.timestemp = timestemp;
    }

    public long getTimestemp() {
        return timestemp;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return toJSONString();
    }

    public String toJSONString(){
        return JSON.toJSONString(this);
    }
}
