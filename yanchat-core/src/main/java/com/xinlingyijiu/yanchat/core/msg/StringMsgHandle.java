package com.xinlingyijiu.yanchat.core.msg;

import java.io.UnsupportedEncodingException;

public class StringMsgHandle implements MsgHandle <String>{



    @Override
    public byte[] apply(String s,String coding) {
        try {
            return s == null ? new byte[0] : s.getBytes(coding);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            throw new RuntimeException("编码转换异常！");
        }
    }
}
