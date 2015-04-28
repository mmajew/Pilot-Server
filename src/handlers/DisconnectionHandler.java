package handlers;

import tools.ServerLogger;


public class DisconnectionHandler extends TaskHandler {
    public void handleClientDisconnected() {
        ServerLogger.logMessage("Klient rozłączył się");
        tcpServer.close();
    }
}
