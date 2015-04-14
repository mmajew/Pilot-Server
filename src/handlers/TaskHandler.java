package handlers;

import main.TCPServer;

/**
 * Created by marmajew on 4/13/2015.
 */
public abstract class TaskHandler {
    private static TCPServer server;


    public static void initialize(TCPServer tcpServer) {
        server = tcpServer;
    }


    protected void sendMessage(String message) {
        server.sendMessage(message);
    }


    public abstract void handle();
}
