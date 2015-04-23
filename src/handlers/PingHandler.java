package handlers;


import messages.ServerMessages;
import tools.ServerLogger;

import java.util.Timer;
import java.util.TimerTask;


public class PingHandler extends TaskHandler {
    private Timer timer = new Timer();
    private boolean isPinged = false;

    public void handle() {
        isPinged = true;
        tcpServer.sendMessage(ServerMessages.SERVER_PONG);
    }

    public void initializeTimeoutTimer() {
        int timeout =  12 * 1000;

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (!isPinged) {
                    System.out.println("Ping timedout");
                    ServerLogger.logMessage("Utracono połączenie");
                    tcpServer.close();
                    udpServer.close();
                    this.cancel();
                }
                isPinged = false;
            }
        }, timeout, timeout);
    }

    public void stopTimeoutTimer() {
        timer.cancel();
    }
}
