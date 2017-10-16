package com.xinlingyijiu.yanchat.util;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * 定时任务
 * Created by laotou on 2017/10/13.
 */
public class ScheduledExecutorUtil {
    public static final int CORE_POOL_SIZE = 2;
    private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(CORE_POOL_SIZE);
    private ScheduledExecutorUtil(){}

    public static ScheduledExecutorService getScheduler(){
        return scheduler;
    }
}
