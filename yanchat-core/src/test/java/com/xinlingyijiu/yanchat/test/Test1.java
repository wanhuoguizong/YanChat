package com.xinlingyijiu.yanchat.test;

import com.alibaba.fastjson.JSON;
import com.xinlingyijiu.yanchat.core.user.User;
import com.xinlingyijiu.yanchat.core.user.UserManager;
import com.xinlingyijiu.yanchat.core.user.UserManagerImpl;
import com.xinlingyijiu.yanchat.util.IpV4Util;
import org.junit.Test;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by laotou on 2017/10/12.
 */
public class Test1 {
    @Test
    public void testUserManager(){
        User user = new User();
        user.setHost("0.0.0.0");
        user.setNickName("Hello world!");
        user.setBroadcastPort(8080);
        user.setOnline(true);

        User user2 = new User();
        user2.setHost("0.0.0.1");
        user2.setNickName("Hello world!");
        user2.setBroadcastPort(8080);
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
    @Test
    public void getLocalIp() throws UnknownHostException {
        InetAddress[] ipAdd = InetAddress.getAllByName(InetAddress.getLocalHost().getHostAddress());
        System.out.println(Arrays.toString(ipAdd));

        System.out.println(IpV4Util.checkIpV4Type("169.254.108.1"));
        System.out.println("C类ip为："+IpV4Util.getLocalCTypeIpV4());
    }
    @Test
    public  void getIpAddress() {
        try {
            Enumeration<NetworkInterface> interfaces = null;
            interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                NetworkInterface ni = interfaces.nextElement();
                Enumeration<InetAddress> addresss = ni.getInetAddresses();
                while (addresss.hasMoreElements()) {
                    InetAddress nextElement = addresss.nextElement();
                    String hostAddress = nextElement.getHostAddress();
                    System.out.println("本机IP地址为：" + hostAddress);
                    NetworkInterface net = NetworkInterface.getByInetAddress(nextElement);
                    System.out.println(net.getName());
                    System.out.println(net.getDisplayName());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Test
    public void json(){
        TestUser testUser = new TestUser();
        testUser.setLoginTimeStemp(631232632123252453L);
        User user = testUser;
        System.out.println(JSON.toJSONString(testUser));

    }

    class TestUser extends User{
        private long loginTimeStemp;

        public long getLoginTimeStemp() {
            return loginTimeStemp;
        }

        public void setLoginTimeStemp(long loginTimeStemp) {
            this.loginTimeStemp = loginTimeStemp;
        }
    }
    @Test
    public void incretment(){
        AtomicInteger idAtomic = new AtomicInteger(0);
        for (int i = 0; i < 100; i++) {
            System.out.println(idAtomic.incrementAndGet());
        }
    }
}
