package com.xinlingyijiu.yanchat.core.socket;

import java.io.IOException;
import java.net.DatagramSocket;

public interface SocketManager {
     DatagramSocket getSocket() throws IOException;
}
