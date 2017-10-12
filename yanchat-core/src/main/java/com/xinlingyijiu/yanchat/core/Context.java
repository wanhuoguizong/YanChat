package com.xinlingyijiu.yanchat.core;

import com.xinlingyijiu.yanchat.core.broadcast.Broadcast;
import com.xinlingyijiu.yanchat.core.user.UserContext;
import com.xinlingyijiu.yanchat.core.user.UserManager;

/**
 * Created by laotou on 2017/10/12.
 */
public class Context {
    private Broadcast broadcast;
    private UserContext userContext;
    private UserManager userManager;

    public Broadcast getBroadcast() {
        return broadcast;
    }

    public void setBroadcast(Broadcast broadcast) {
        this.broadcast = broadcast;
    }

    public UserContext getUserContext() {
        return userContext;
    }

//    public void setUserContext(UserContext userContext) {
//        this.userContext = userContext;
//    }

    public UserManager getUserManager() {
        return userManager;
    }

    public void setUserManager(UserManager userManager) {
        this.userContext = userManager;
        this.userManager = userManager;
    }
}
