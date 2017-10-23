package com.xinlingyijiu.yanchat.cmd.service;

import com.xinlingyijiu.yanchat.cmd.Cmd;
import com.xinlingyijiu.yanchat.cmd.ScannerUtil;
import com.xinlingyijiu.yanchat.core.Context;
import com.xinlingyijiu.yanchat.core.bean.ChatSession;
import com.xinlingyijiu.yanchat.core.user.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CmdServiceImpl implements CmdService {
    private static final List<String> cmdList = new ArrayList<>();
    private static ChatSession currentSession;
    static {
        cmdList.add(Cmd.PREFIX_HELP);
        cmdList.add(Cmd.PREFIX_SESSION);
        cmdList.add(Cmd.PREFIX_USER);
        cmdList.add(Cmd.PREFIX_EXIT);
    }
    @Override
    public boolean isValiCmd(String cmd) {
        return cmdList.contains(cmd);
    }

    @Override
    public String inputCmd(String msg) {
        return ScannerUtil.scannerInput(msg,this::isValiCmd);
    }
    @Override
    public  void showHelp(){
        StringBuilder sb = new StringBuilder();
        sb.append("请输入以下命令：\n")
                .append(Cmd.PREFIX_HELP).append("  查看帮助；\n")
                .append(Cmd.PREFIX_USER).append("  查看当前用户列表；\n")
                .append(Cmd.PREFIX_SESSION).append("  查看会话列表。\n")
                .append(Cmd.PREFIX_EXIT).append("  退出会话。\n");
        String helpMsg = sb.toString();
        System.out.println(helpMsg);
//        waitForInput();
    }

    private void waitForCmd(String msg) {
        String cmd = inputCmd(msg);
        excutorCmd(cmd);
    }

    //查看用户列表
    public void showUsers()  {

        List<User> list = Context.getInstance().getUserContext().getAll();
        if (list.isEmpty()) {
            System.out.println("当前没有用户在线！");
//            waitForInput();
        }else {
//        System.out.println("请选择用户序号开始聊天！");
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < list.size(); i++) {
                User user = list.get(i);
                if (user == null) continue;
                sb.append(i).append("  ").append(user.getNickName())
                        .append("（").append(user.getHost())
                        .append(":").append(user.getUdpPort())
                        .append("）").append("\n");
            }
            System.out.println(sb.toString());

            selectUser(list);
        }
    }

    /**
     * 选择用户
     * @param list
     * @throws IOException
     */
    private void selectUser(List<User> list)  {
        String index = ScannerUtil.scannerInput(String.format("请选择用户序号[0-%d]开始聊天（多人聊天可用空格或逗号分隔）：", list.size() - 1),
                s1 -> s1.length() == 0 || s1.matches(String.format("^([0-%d]+[ ,，]?)*$",list.size() - 1)));
        if (index.length() == 0) {
            System.out.println("未选择用户");
            return;
        }
        List<String> userIdList = new ArrayList<>();
        Context context = Context.getInstance();
        userIdList.add(context.getUserContext().getCurrentUser().getId());
        for (String i : index.split("[ ,，]")) {
            if (i.length() == 0 || userIdList.contains(i)) continue;
            String userId = list.get(Integer.parseInt(i)).getId();

            userIdList.add(userId);

        }

        ChatSession chatSession = context.getChatSessionService().createByUserIdsIfNotExist(userIdList);
        intoSession(chatSession);

    }
    //进入聊天室
    private void intoSession(ChatSession chatSession){
        System.out.println(String.format("进入会话：%s", chatSession.getSessionName()));
        currentSession = chatSession;
        chat();
    }

    /**
     * 聊天
     */
    private void chat() {
        String inputString = ScannerUtil.scannerInput(null, s -> s.length() > 0);
        ChatSession session = currentSession;
        if (isValiCmd(inputString)) {

            excutorCmd(inputString);
        }else if (session != null){
            //发送信息
            try {
                Context.getInstance().getChatMsgService().send(session.getId(),inputString);
            } catch (IOException e) {
                System.out.println("发送失败！请检查网络设置。");
            }
        }
    }

    public void excutorCmd(String cmd){
        switch (cmd){
            case Cmd.PREFIX_HELP :
                showHelp();
                break;
            case Cmd.PREFIX_USER:
                showUsers();
                break;
            case Cmd.PREFIX_SESSION:
                showSession();
                break;
            case Cmd.PREFIX_EXIT:
                exitChatSession();
                break;
        }
//        waitForInput();
    }
    @Override
    public void waitForInput() {
        if (currentSession == null) {
            waitForCmd(null);
        }else {
            chat();
        }
    }

    private void showSession() {
        Context context = Context.getInstance();
        List<ChatSession> list = context.getChatSessionService().getAll();
        if (list.isEmpty()){
            System.out.println("当前还没有打开会话");
//            waitForInput();
            return;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            ChatSession session = list.get(i);

            sb.append(i).append("  ").append(session.getSessionName());
            for (String uid : session.getUserIdList()) {
                User user = context.getUserContext().get(uid);
                if (user == null) break;
                sb.append("\n\t").append(user.getNickName());
                sb.append("（").append(user.getHost())
                        .append(":").append(user.getUdpPort())
                        .append("）");
            }
            sb.append("\n");

        }

        System.out.println(sb.toString());

        selectChatSession(list);
    }

    private void selectChatSession(List<ChatSession> list) {
        String index = ScannerUtil.scannerInput(String.format("请选择会话序号[0-%d]开始聊天：", list.size() - 1),
                s1 -> s1.length() == 0 || s1.matches(String.format("^[0-%d]*$",list.size() - 1)));
        if (index.length() == 0) {
            System.out.println("未选择会话");
            return;
        }
        intoSession(list.get(Integer.parseInt(index)));
    }

    private void exitChatSession(){
        currentSession = null;
    }
}
