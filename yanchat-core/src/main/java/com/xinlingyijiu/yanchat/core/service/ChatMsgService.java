package com.xinlingyijiu.yanchat.core.service;

import com.xinlingyijiu.yanchat.core.net.Connect;

import java.util.function.Consumer;

public interface ChatMsgService {
    void setConnect(Connect connect);

    Connect getConnet();

    /**
     * 接收到用户信息后的逻辑
     * 并执行后续的 所有 after
     * @param user
     */
//    void receive(User user);

    /**
     * 绑定用户信息改变(如上线，下线，其他信息修改等)后执行的操作，可以重复调用指定，并且都能执行
     * @param after
     */
//    void after(Consumer<User> after);

    /**
     * 执行所有的after
     * @param user
     */
//    void executeAllAfter(User user);

    /**
     * 轮询上线状态
     * @param cycleTime 周期
     */
    void onlinePolling( long cycleTime);
}
