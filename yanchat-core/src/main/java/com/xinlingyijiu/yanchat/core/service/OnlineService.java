package com.xinlingyijiu.yanchat.core.service;

import com.xinlingyijiu.yanchat.core.net.Connect;
import com.xinlingyijiu.yanchat.core.user.User;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public interface OnlineService {
    void setConnect(Connect connect);

    Connect getConnet();

    /**
     * 接收到用户信息后的逻辑
     * 并执行后续的 所有 after
     * @param user
     */
    void receive(User user);

    /**
     * 绑定用户信息改变(如上线，下线，其他信息修改等)后执行的操作，可以重复调用指定，并且都能执行
     * 执行时第一个参数是更新后的User,第二个参数是更新前的（如果该用户第一次上线则传null）
     * @param after
     */
    void after(BiConsumer<User,User> after);

    /**
     * 执行所有的after
     * @param afterUser 更新后的用户信息
     * @param  frontUser 更新前的用户信息
     */
    void executeAllAfter(User afterUser,User frontUser);

    /**
     * 轮询上线状态
     * @param cycleTime 周期
     */
    void onlinePolling( long cycleTime);

}
