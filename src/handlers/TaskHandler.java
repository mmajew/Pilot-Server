package handlers;

import connection.TcpServer;
import connection.UdpServer;


public abstract class TaskHandler {
    protected static TcpServer tcpServer;
    protected static UdpServer udpServer;

    public static void initialize(TcpServer tcpServer, UdpServer udpServer) {
        TaskHandler.tcpServer = tcpServer;
        TaskHandler.udpServer = udpServer;
    }
}
