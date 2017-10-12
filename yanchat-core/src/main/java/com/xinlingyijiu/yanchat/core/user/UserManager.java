package com.xinlingyijiu.yanchat.core.user;

/**
 * Created by laotou on 2017/10/12.
 */
public interface UserManager extends UserContext {


    User setCurrentUser(User user);
    /**
     * 移除用户
     * @param user
     * @return
     */
    User remove(User user);

    /**
     * 根据用户id移除并返回该用户，如果该id对应的用户不存在，则返回null
     * @param userId
     * @return
     */
    User remove(String userId);



    /**
     * 根据userId下线用户
     * @param userId
     * @return 如果用户存在且有改变用户在线状态，返回true,否则返回false
     */
    boolean offline(String userId);

//    /**
//     * 下线用户
//     * @param user
//     * @return 如果用户存在且有改变用户在线状态，返回true,否则返回false
//     */
//    boolean offline(User user);

    /**
     * 上线
     * @param userId
     * @return 如果用户存在且有改变用户在线状态，返回true,否则返回false
     */
    boolean online(String userId);
    /**
     * 上线
     * @param user
     * @return 如果有修改用户信息以及在线状态，返回true,否则返回false
     */
    boolean online(User user);
}
