package com.xinlingyijiu.yanchat.core.msg;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Created by laotou on 2017/10/12.
 */
public class MsgHandleContext {
    private Map<String,MsgHandle> handleMap;
    private Map<String,MsgConverseHandle> converseHandleMap;


    private static MsgHandleContext context;

    private MsgHandleContext() {
        this.converseHandleMap = new HashMap<>();
        this.handleMap = new HashMap<>();
    }

    public static MsgHandleContext getInstance() {
        return context == null ? (context = new MsgHandleContext()) : context;
    }

    public Map<String, MsgConverseHandle> getConverseHandleMap() {
        return converseHandleMap;
    }

    public void setConverseHandleMap(Map<String, MsgConverseHandle> converseHandleMap) {
        this.converseHandleMap = converseHandleMap;
    }

    public Map<String, MsgHandle> getHandleMap() {
        return handleMap;
    }

    public void setHandleMap(Map<String, MsgHandle> handleMap) {
        this.handleMap = handleMap;
    }
     public MsgHandle putMsgHandle(String msgType,MsgHandle handle){
         Objects.requireNonNull(msgType);
         Objects.requireNonNull(handle);
         return this.handleMap.put(msgType,handle);
     }
     public MsgConverseHandle putConverseHandle(String msgType,MsgConverseHandle handle){
         Objects.requireNonNull(msgType);
         Objects.requireNonNull(handle);
         return this.converseHandleMap.put(msgType,handle);
     }

    public MsgHandle getHandle(String msgType){
        return this.handleMap.get(msgType);
    }
    public MsgConverseHandle getConverseHand(String msgType){
        return this.converseHandleMap.get(msgType);
    }
}
