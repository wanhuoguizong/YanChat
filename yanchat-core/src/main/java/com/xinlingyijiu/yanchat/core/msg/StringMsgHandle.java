package com.xinlingyijiu.yanchat.core.msg;

public class StringMsgHandle implements MsgHandle <String>{



    @Override
    public byte[] apply(String s) {
        return s == null ? new byte[0] : s.getBytes();
    }
}
