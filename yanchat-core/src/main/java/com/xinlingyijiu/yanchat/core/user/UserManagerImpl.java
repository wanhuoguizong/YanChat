package com.xinlingyijiu.yanchat.core.user;

import org.apache.commons.beanutils.BeanUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by laotou on 2017/10/12.
 */
public class UserManagerImpl implements UserManager {
    private final List<User> userList = new ArrayList<>();
    private final Map<String, User> userMap = new HashMap<>();
    private User currentUser;

    /**
     * id相同视为同一用户
     * ip、端口、昵称 都分别相同视为同一用户
     *
     * @param user1
     * @param user2
     * @return
     */
    protected boolean equalsUser(User user1, User user2) {
        if (user1 == user2) return true;
        if (user1 != null && user2 != null) {
            if (Objects.equals(user1.getId(), user2.getId())) {
                return true;
            }
            if (Objects.equals(user1.getHost(), user2.getHost())
                    && Objects.equals(user1.getNickName(), user2.getNickName())
                    && Objects.equals(user1.getBroadcastPort(), user2.getBroadcastPort())
                    ) {
                return true;
            }
        }
        return false;
    }

    protected final synchronized User getEqualsUser(User user) {
        User oldUser = userMap.get(user.getId());
        if (oldUser == null) {
            for (User u : userList) {
                if (this.equalsUser(u, user)) return u;
            }
        }
        return oldUser;
    }

    //    @Override
    protected synchronized boolean addUser(User user) {
        userMap.put(user.getId(), user);
        userList.add(user);
        return true;
    }

    @Override
    public synchronized User setCurrentUser(User user) {
        User oldUser = currentUser;
        online(user);
        currentUser = getEqualsUser(user);
        return oldUser;
    }

    @Override
    public synchronized User remove(User user) {
        User oldUser = getEqualsUser(user);
        if (oldUser == null) return null;
        remove(oldUser.getId());
        return oldUser;
    }

    @Override
    public synchronized User remove(String userId) {
        User oldUser = userMap.remove(userId);
        userList.remove(oldUser);
        return oldUser;
    }

    @Override
    public synchronized List<User> getAll() {
        if (this.userList.isEmpty()) return new ArrayList<>(0);
        return this.userList.stream().map(this::cloneUser).collect(Collectors.toList());
    }

    @Override
    public synchronized User get(String userId) {
        User user = this.userMap.get(userId);
        if (user == null) return null;
        return cloneUser(user);
    }

    @Override
    public User getCurrentUser() {
        return currentUser;
    }

    //出于安全原因。不直接返回原始对象，避免在其他地方无意中修改属性
    private User cloneUser(User user) {
        try {
            User nUser = new User();
            BeanUtils.copyProperties(nUser, user);
            return nUser;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("clone user Exception");
        }
    }

    @Override
    public synchronized boolean offline(String userId) {
        User user = this.userMap.get(userId);
        if (user == null || !user.isOnline()) return false;
        user.setOnline(false);
        return true;
    }

    @Override
    public synchronized boolean online(String userId) {
        User user = this.userMap.get(userId);
        if (user == null || user.isOnline()) return false;
        user.setOnline(true);
        return true;
    }

    @Override
    public synchronized boolean online(User user) {
        user.setOnline(true);
        User oldUser = getEqualsUser(user);
        if (Objects.equals(user, oldUser)) {
            return false;
        }
        if (oldUser != null) {
            remove(oldUser.getId());
        }
        User newUser = cloneUser(user);
        addUser(newUser);//保存克隆信息
        if (currentUser != null && currentUser == oldUser) currentUser = newUser;
        return true;
    }
}
