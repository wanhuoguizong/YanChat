package com.xinlingyijiu.yanchat.core.exception;

/**
 * Created by laotou on 2017/10/13.
 */
public class YanChatRuntimeException extends RuntimeException {
    public YanChatRuntimeException() {
    }

    public YanChatRuntimeException(String message) {
        super(message);
    }

    public YanChatRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public YanChatRuntimeException(Throwable cause) {
        super(cause);
    }

    public YanChatRuntimeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
