package com.xinlingyijiu.yanchat.cmd;

import com.xinlingyijiu.yanchat.cmd.service.CmdService;
import com.xinlingyijiu.yanchat.cmd.service.CmdServiceImpl;
import com.xinlingyijiu.yanchat.core.Constant;
import com.xinlingyijiu.yanchat.core.Context;
import com.xinlingyijiu.yanchat.core.YanChatApplication;
import com.xinlingyijiu.yanchat.core.bean.Address;
import com.xinlingyijiu.yanchat.core.bean.ChatSession;
import com.xinlingyijiu.yanchat.core.bean.Model;
import com.xinlingyijiu.yanchat.core.user.User;

import java.util.Objects;
import java.util.Scanner;
import java.util.function.Predicate;

import static com.xinlingyijiu.yanchat.cmd.ScannerUtil.scannerInput;

public class Main {
//    public static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        System.out.println("启动 YanChat-cmd！");

        CmdService cmdService = new CmdServiceImpl();

        Context context = Context.getInstance();
//        Scanner scanner = new Scanner(System.in);
        //1。启动后基础设置
        //设置用户信息
        User user = configureUser();
        context.setTcpPort(user.getTcpPort());
        context.setBroadcastPort(user.getBroadcastPort());
        context.setUdpPort(user.getUdpPort());

        //设置模式
        Model model = configureModel();
        context.init(model);


        context.getUserManager().setCurrentUser(user);

        context.getOnlineService().after((afterUser,frontUser) -> {
                    if (frontUser == null || !frontUser.isOnline()) {
                        System.out.println(String.format("%s(%s:%s)上线啦", afterUser.getNickName(), afterUser.getHost(), afterUser.getUdpPort()));
                    }
                }
        );
        context.getChatMsgService().after(chatMsg -> {
            ChatSession chatSession = context.getChatSessionService().getById(chatMsg.getChatSessionId());
            if (chatSession == null) return;
            User sendUser = Objects.equals(user.getId(),chatMsg.getSendUserId())?  user :  context.getUserContext().get(chatMsg.getSendUserId());
            if (sendUser == null) return;
            String format = String.format("【%s】%s(%s:%d)\n\t%s",
                    chatSession.getSessionName(),
                    sendUser.getNickName(),
                    sendUser.getHost(),
                    sendUser.getUdpPort(),
                    chatMsg.getContent());
            System.out.println(format);
        });
        //2.启动
        YanChatApplication.start(context);
        System.out.println("欢迎使用YanChat！");
        //3.接收命令
        cmdService.showHelp();
        while (true) {
            cmdService.waitForInput();
        }
        //3.1 获取列表
        //3.2 选择用户聊天
        //3.2.1 创建聊天室(会话)
        //3.2.2 聊天室（会话）列表
        //3.2.3 切换聊天室
    }

    private static Model configureModel() {
        String line = ScannerUtil.scannerInput("请选择模式：1 广播，2 指定地址", s -> "1".equals(s) || "2".equals(s));
        Model model = new Model();
        if (Objects.equals(line,"1")){
            Address address = new Address(Constant.BROADCAST_DEFAULT_HOST,Constant.DEFAULT_PORT.BROADCAST);
            model.setModel(Constant.MODEL.BROADCAST);
            model.addAddress(address);
        }else if (Objects.equals(line,"2")){
            model.setModel(Constant.MODEL.APPOINT);

            configureTargetAddress( model);
        }
        return model;

    }

    private static void configureTargetAddress( Model model) {
        String host  = ScannerUtil.scannerInput("请输入目标用户ip:", s -> s.length() > 0);
        int port =  Integer.parseInt(ScannerUtil.scannerInput( "请输入目标用户端口:", s -> s.matches("^\\d+$")));
        Address address = new Address(host,port);
        model.addAddress(address);

        String next  = ScannerUtil.scannerInput("请选择数字(不选表示结束添加)：1-继续添加目标用户，2-结束添加", s -> "".equals(s) || "1".equals(s) || "2".equals(s));
        if ("1".equals(next)) configureTargetAddress(model);
    }

    private static User configureUser() {
        String nickName = ScannerUtil.scannerInput("请输入您的昵称：", s -> s.length() > 0);
        User user = new User();

//        String tcpPort = configureInput(scanner,String.format("请输入您的tcp端口（默认：%d）：",Constant.DEFAULT_PORT.TCP), s -> s.matches("^\\d*$"));
//        user.setTcpPort(tcpPort.length() == 0 ? Constant.DEFAULT_PORT.TCP: Integer.valueOf(tcpPort));
        String udpPort = ScannerUtil.scannerInput(String.format("请输入您的udp端口（默认：%d）：",Constant.DEFAULT_PORT.UDP), s -> s.matches("^\\d*$"));
        user.setUdpPort(udpPort.length() == 0 ? Constant.DEFAULT_PORT.UDP: Integer.valueOf(udpPort));
        String broadcastPort = ScannerUtil.scannerInput(String.format("请输入您的广播端口（默认：%d）：",Constant.DEFAULT_PORT.BROADCAST), s -> s.matches("^\\d*$"));
        user.setBroadcastPort(broadcastPort.length() == 0 ? Constant.DEFAULT_PORT.BROADCAST : Integer.valueOf(broadcastPort));
        user.setNickName(nickName);
        user.setOnline(true);
        user.setHost("localhost");

        user.setTcpPort(Constant.DEFAULT_PORT.TCP);
//        user.setUdpPort(Constant.DEFAULT_PORT.UDP);
//        user.setBroadcastPort(Constant.DEFAULT_PORT.BROADCAST );


        return user;
    }


}
