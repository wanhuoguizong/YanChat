package com.xinlingyijiu.yanchat.core.net.socket;

import java.io.Closeable;
import java.io.IOException;
import java.net.DatagramSocket;

public interface UDPSocketManager extends Closeable{
     DatagramSocket getSocket() throws IOException;
}
