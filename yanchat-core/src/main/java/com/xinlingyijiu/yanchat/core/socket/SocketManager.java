package com.xinlingyijiu.yanchat.core.socket;

import java.io.Closeable;
import java.io.IOException;
import java.net.DatagramSocket;

public interface SocketManager extends Closeable{
     DatagramSocket getSocket() throws IOException;
}
