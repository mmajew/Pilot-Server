package handlers;


import tools.Message;
import tools.ServerLogger;

import java.util.Timer;
import java.util.TimerTask;


public class PingHandler extends TaskHandler {
    private boolean isPinged = false;

    public void handle(Message message) {
        isPinged = true;
        server.sendMessage("S:PONG");
    }

    public void initializeTimeoutTimer() {
        int timeout =  10 * 1000;

        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (!isPinged) {
                    System.out.println("Ping timedout");
                    ServerLogger.logMessage("Utracono połączenie");
                    server.close();
                    this.cancel();
                }
                isPinged = false;
            }
        }, timeout, timeout);
    }
}
