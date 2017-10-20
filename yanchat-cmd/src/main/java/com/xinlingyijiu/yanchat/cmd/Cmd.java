package com.xinlingyijiu.yanchat.cmd;

public interface Cmd {
    String PREFIX = "/";
    String SESSION = "s";
    String USER = "u";
    String HELP = "h";
    String EXIT = "e";//退出当前会话，返回主菜单

    String PREFIX_SESSION = PREFIX + SESSION;
    String PREFIX_USER = PREFIX + USER;
    String PREFIX_HELP = PREFIX + HELP;
    String PREFIX_EXIT = PREFIX + EXIT;

}
