package com.xinlingyijiu.yanchat.core.msg;

import java.util.function.BiFunction;

/**
 * Created by laotou on 2017/10/12.
 */
public interface MsgConverseHandle <R> extends BiFunction<byte[],String,R> {
    //默认的返回byte长度1024
    int DEFAULT_LEN = 1024;

    String DEFAULT_CODING = "UTF-8";
    //继承次方法对要发送的消息进行处理
    R apply(byte[] bytes,String coding);

    default R apply(byte[] bytes){
        return apply(bytes,DEFAULT_CODING);
    }
}
