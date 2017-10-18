package com.xinlingyijiu.yanchat.core.service;

import com.xinlingyijiu.yanchat.core.bean.ChatMsg;
import com.xinlingyijiu.yanchat.core.net.Connect;

import java.io.IOException;
import java.util.function.Consumer;

public interface ChatMsgService {
    void setConnect(Connect connect);

    Connect getConnet();

    /**
     * 接收到用户信息后的逻辑
     * 并执行后续的 所有 after
     * @param msg
     */
    void receive(ChatMsg msg);

    /**
     * 绑定用户信息改变(如上线，下线，其他信息修改等)后执行的操作，可以重复调用指定，并且都能执行
     * @param after
     */
    void after(Consumer<ChatMsg> after);

    /**
     * 执行所有的after
     * @param msg
     */
    void executeAllAfter(ChatMsg msg);

    /**
     * 发送消息
     * @param msg
     */
    void send(ChatMsg msg) throws IOException;

    /**
     * 发送
     * @param targetUserId 目标用户id
     * @param content 消息内容
     * @throws IOException
     */
    void send(String  targetUserId,String content) throws IOException;


}
