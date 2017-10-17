package com.xinlingyijiu.yanchat.core;

/**
 * Created by laotou on 2017/10/11.
 */
public class Demo {
    public static void main(String[] args) {
        System.out.println("启动！");

        Context context = Context.getInstance();
        context.userDefaultContext();

        YanChatApplication.start(context);

        while (true);
    }
}
