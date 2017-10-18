package com.xinlingyijiu.yanchat.core;

import com.xinlingyijiu.yanchat.core.bean.Address;
import com.xinlingyijiu.yanchat.core.bean.Model;
import com.xinlingyijiu.yanchat.core.user.User;

import java.util.*;

/**
 * Created by laotou on 2017/10/11.
 */
public class Demo {
    public static void main(String[] args) {
        System.out.println("启动！");
        Context context = Context.getInstance();
        Scanner scanner = new Scanner(System.in);
        String line = getScannerNextLine(scanner,"请选择模式：1 广播，2 指定地址","1","2");

        if (Objects.equals(line,"1")){
            Address address = new Address(Constant.BROADCAST_DEFAULT_HOST,Constant.DEFAULT_PORT.BROADCAST);
            Model model = new Model();
            model.setModel(Constant.MODEL.BROADCAST);
            model.addAddress(address);
            context.init(model);
        }else if (Objects.equals(line,"2")){
            String host  = getScannerNextLine(scanner,"请输入目标用户ip:");
            int port =  Integer.parseInt(getScannerNextLine(scanner, "请输入目标用户端口:"));
            Address address = new Address(host,port);
            Model model = new Model();
            model.setModel(Constant.MODEL.APPOINT);
            model.addAddress(address);
            context.init(model);
        }

//        System.out.println("请输入昵称");
        String nickName = getScannerNextLine(scanner,"您的昵称：");
        User user = new User();
        user.setTcpPort(context.getTcpPort());
        user.setUdpPort(context.getUdpPort());
        user.setBroadcastPort(context.getBroadcastPort());
        user.setNickName(nickName);
        user.setOnline(true);
        user.setHost("localhost");
        context.getUserManager().setCurrentUser(user);

        context.getOnlineService().after(u ->
                        System.out.println(String.format("%s(%s:%s)上线啦", u.getNickName(), u.getHost(), u.getUdpPort()))
        );

        YanChatApplication.start(context);

        while (true);
    }

    public static String getScannerNextLine(Scanner scanner,String msg ,String... contan){
        String line = null;

        if (contan != null && contan.length > 0) {
            List<String> list = Arrays.asList(contan);
            while (true) {
                System.out.println(msg);
                line = scanner.nextLine();
                if (list.contains(line)){
                    return line;
                }
            }
        }
        while (true){
            System.out.println(msg);
            if ((line = scanner.nextLine()) != null && line.length() != 0)
                return line;
        }
    }
}
