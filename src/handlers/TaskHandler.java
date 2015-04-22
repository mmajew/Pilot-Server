package handlers;

import connection.TCPServer;
import messages.Message;


public abstract class TaskHandler {
    protected static TCPServer server;

    public static void initialize(TCPServer tcpServer) {
        server = tcpServer;
    }

    public abstract void handle(Message message);
}
