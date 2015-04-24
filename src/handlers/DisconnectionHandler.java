package handlers;

import tools.ServerLogger;


public class DisconnectionHandler extends TaskHandler {
    public void handleClientDisconnected() {
        ServerLogger.logMessage("Klient o nazwie " + tcpServer.getClientName() + " rozłączył się");
        tcpServer.close();
    }
}
