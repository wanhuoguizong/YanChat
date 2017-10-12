package com.xinlingyijiu.yanchat.core.user;

import java.util.List;

/**
 * Created by laotou on 2017/10/12.
 */
public interface UserContext {
    /**
     * 获取所有用户
     * @return
     */
    List<User> getAll();

//    User get(int index);

    //根据id获取用户
    User get(String userId);

    User getCurrentUser();
}
