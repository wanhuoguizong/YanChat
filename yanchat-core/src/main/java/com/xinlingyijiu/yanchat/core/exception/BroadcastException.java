package com.xinlingyijiu.yanchat.core.exception;

import java.net.SocketException;

/**
 * Created by laotou on 2017/10/13.
 */
public class BroadcastException extends SocketException {
    public BroadcastException(String msg) {
        super(msg);
    }

    public BroadcastException() {
    }
}
