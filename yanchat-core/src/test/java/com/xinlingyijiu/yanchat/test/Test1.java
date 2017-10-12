package com.xinlingyijiu.yanchat.test;

import com.xinlingyijiu.yanchat.core.user.User;
import com.xinlingyijiu.yanchat.core.user.UserManager;
import com.xinlingyijiu.yanchat.core.user.UserManagerImpl;
import org.junit.Test;

import java.util.List;

/**
 * Created by laotou on 2017/10/12.
 */
public class Test1 {
    @Test
    public void testUserManager(){
        User user = new User();
        user.setIp("0.0.0.0");
        user.setNickName("Hello world!");
        user.setPort(8080);
        user.setOnline(true);

        User user2 = new User();
        user2.setIp("0.0.0.1");
        user2.setNickName("Hello world!");
        user2.setPort(8080);
        user2.setOnline(true);

        UserManager um =  new UserManagerImpl();
        um.online(user);
        um.online(user2);

        User u = um.get(user.getId());
        System.out.println(user.toJSONString());
        System.out.println(u.toJSONString());
        System.out.println(u == user);
        System.out.println(user.equals(u));

        List<User> list = um.getAll();
        System.out.println(list);
        System.out.println(list.get(0).toJSONString());
        System.out.println(list.get(0).equals(user));
        System.out.println(list.get(0) == user);

    }
}
