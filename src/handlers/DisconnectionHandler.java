package handlers;

import tools.ServerLogger;

/**
 * Created by Martin on 22/04/2015.
 */
public class DisconnectionHandler extends TaskHandler {
    public void handleClientDisconnected() {
        ServerLogger.logMessage("Klient o nazwie " + server.getClientName() + " rozłączył się");
        server.close();
    }
}
