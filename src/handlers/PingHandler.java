package handlers;


import messages.Message;
import messages.ServerMessages;
import tools.ServerLogger;

import java.util.Timer;
import java.util.TimerTask;


public class PingHandler extends TaskHandler {
    private boolean isPinged = false;
    private Timer timeoutTimer = new Timer();

    public void handlePing() {
        isPinged = true;
        server.sendMessage(ServerMessages.SERVER_PONG);
    }

    public void initializeTimeoutTimer() {
        int timeout =  12 * 1000;

        timeoutTimer.scheduleAtFixedRate(new TimerTask() {
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

    public void stopTimeoutTimer() {
        if(timeoutTimer != null)
            timeoutTimer.cancel();
    }
}
