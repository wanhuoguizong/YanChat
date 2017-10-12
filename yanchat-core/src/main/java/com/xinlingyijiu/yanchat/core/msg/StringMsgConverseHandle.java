package com.xinlingyijiu.yanchat.core.msg;

import java.io.UnsupportedEncodingException;

public class StringMsgConverseHandle implements MsgConverseHandle <String>{



    @Override
    public  String apply(byte[] bytes,String coding) {
        try {
            return new String(bytes,coding);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            throw new RuntimeException("编码转换异常！");
        }
    }
}
