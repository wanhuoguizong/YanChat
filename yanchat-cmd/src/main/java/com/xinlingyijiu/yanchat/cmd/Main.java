package com.xinlingyijiu.yanchat.cmd;

import com.xinlingyijiu.yanchat.core.Constant;
import com.xinlingyijiu.yanchat.core.Context;
import com.xinlingyijiu.yanchat.core.YanChatApplication;
import com.xinlingyijiu.yanchat.core.bean.Address;
import com.xinlingyijiu.yanchat.core.bean.Model;
import com.xinlingyijiu.yanchat.core.user.User;
import com.xinlingyijiu.yanchat.util.ScheduledExecutorUtil;

import java.util.Objects;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;

public class Main {
    public static void main(String[] args) {
        System.out.println("启动 YanChat-cmd！");
        Context context = Context.getInstance();
        Scanner scanner = new Scanner(System.in);
        //1。启动后基础设置
        //设置用户信息
        User user = getUser(context, scanner);
        //设置模式
        Model model = getModel(context, scanner);
        //todo 端口
        context.init(model);
        context.getUserManager().setCurrentUser(user);
        //2.启动
        context.getOnlineService().after(u ->
                System.out.println(String.format("%s(%s:%s)上线啦", u.getNickName(), u.getHost(), u.getUdpPort()))
        );

        YanChatApplication.start(context);
        //3.接收命令
        //3.1 获取列表
        //3.2 选择用户聊天
        //3.2.1 创建聊天室(会话)
        //3.2.2 聊天室（会话）列表
        //3.2.3 切换聊天室
    }

    private static Model getModel(Context context, Scanner scanner) {
        String line = getInput(scanner,"请选择模式：1 广播，2 指定地址",s -> "1".equals(s) || "2".equals(s));
        Model model = new Model();
        if (Objects.equals(line,"1")){
            Address address = new Address(Constant.BROADCAST_DEFAULT_HOST,Constant.DEFAULT_PORT.BROADCAST);
            model.setModel(Constant.MODEL.BROADCAST);
            model.addAddress(address);
        }else if (Objects.equals(line,"2")){
            model.setModel(Constant.MODEL.APPOINT);

            setTargetAddress(scanner, model);
        }
        return model;

    }

    private static void setTargetAddress(Scanner scanner, Model model) {
        String host  = getInput(scanner,"请输入目标用户ip:",s -> s.length() > 0);
        int port =  Integer.parseInt(getInput(scanner, "请输入目标用户端口:",s -> s.matches("^\\d+$")));
        Address address = new Address(host,port);
        model.addAddress(address);

        String next  = getInput(scanner,"请选择数字(不选表示结束添加)：1-继续添加目标用户，2-结束添加",s -> "".equals(s) || "1".equals(s) || "2".equals(s));
        if ("1".equals(next)) setTargetAddress(scanner,model);
    }

    private static User getUser(Context context, Scanner scanner) {
        String nickName = getInput(scanner,"请输入您的昵称：",s -> s.length() > 0);
        User user = new User();
        String tcpPort = getInput(scanner,String.format("请输入您的tcp端口（默认：%d）：",Constant.DEFAULT_PORT.TCP),s -> s.matches("^\\d*$"));
        user.setTcpPort(tcpPort.length() == 0 ? Constant.DEFAULT_PORT.TCP: Integer.valueOf(tcpPort));
        String udpPort = getInput(scanner,String.format("请输入您的udp端口（默认：%d）：",Constant.DEFAULT_PORT.UDP),s -> s.matches("^\\d*$"));
        user.setUdpPort(udpPort.length() == 0 ? Constant.DEFAULT_PORT.UDP: Integer.valueOf(udpPort));
        String broadcastPort = getInput(scanner,String.format("请输入您的广播端口（默认：%d）：",Constant.DEFAULT_PORT.BROADCAST),s -> s.matches("^\\d*$"));
        user.setBroadcastPort(broadcastPort.length() == 0 ? Constant.DEFAULT_PORT.BROADCAST : Integer.valueOf(broadcastPort));
//        user.setBroadcastPort(context.getBroadcastPort());
        user.setNickName(nickName);
        user.setOnline(true);
        user.setHost("localhost");

        context.setTcpPort(user.getTcpPort());
        context.setBroadcastPort(user.getBroadcastPort());
        context.setUdpPort(user.getUdpPort());
        return user;
    }

    /**
     * 获取命令行输入
     * @param scanner
     * @param msg 提示信息
     * @param predicate 判断符合条件的
     * @return
     */
    public static String getInput(Scanner scanner , String msg, Predicate<String > predicate){
        String line = null;
        while (true){
            System.out.println(msg);
            line = scanner.nextLine();
            if (line != null && predicate.test(line)){
                return line;
            }
        }
    }
}
