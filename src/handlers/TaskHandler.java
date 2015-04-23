package handlers;

<<<<<<< HEAD
import connection.TcpServer;
import connection.UdpServer;
=======
import connection.TCPServer;
>>>>>>> a9358e5423342008671b14c5da2fd145298ec1f9


public abstract class TaskHandler {
    protected static TcpServer tcpServer;
    protected static UdpServer udpServer;

    public static void initialize(TcpServer tcpServer, UdpServer udpServer) {
        TaskHandler.tcpServer = tcpServer;
        TaskHandler.udpServer = udpServer;
    }
}
