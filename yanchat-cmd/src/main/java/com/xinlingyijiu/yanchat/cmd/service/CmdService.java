package com.xinlingyijiu.yanchat.cmd.service;

public interface CmdService {
     void showHelp();


    public boolean isValiCmd(String cmd);
    public String inputCmd(String msg);

    void waitForInput();
}
