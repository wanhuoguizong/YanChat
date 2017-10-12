package com.xinlingyijiu.yanchat.core.msg;

import java.util.function.BiFunction;


public interface MsgHandle <T>extends BiFunction<T,String,byte[]> {
    //默认的返回byte长度1024
    int DEFAULT_LEN = 1024;

    String DEFAULT_CODING = "UTF-8";
    //继承次方法对要发送的消息进行处理

    /**
     *
     * @param coding 编码
     * @param t 要发送的内容
     * @return 转换为byte后的内容
     */
    byte[] apply(T t,String coding);

    default  byte[] apply(T t){
        return apply(t,DEFAULT_CODING);
    }

}
