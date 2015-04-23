package handlers;


import messages.ServerMessages;
import tools.ServerLogger;

import java.util.Timer;
import java.util.TimerTask;


public class PingHandler extends TaskHandler {
    private Timer timer = new Timer();
    private boolean isPinged = false;
    private Timer timeoutTimer = new Timer();

<<<<<<< HEAD
    public void handle() {
=======
    public void handlePing() {
>>>>>>> a9358e5423342008671b14c5da2fd145298ec1f9
        isPinged = true;
        tcpServer.sendMessage(ServerMessages.SERVER_PONG);
    }

    public void initializeTimeoutTimer() {
        int timeout =  12 * 1000;

<<<<<<< HEAD
        timer.scheduleAtFixedRate(new TimerTask() {
=======
        timeoutTimer.scheduleAtFixedRate(new TimerTask() {
>>>>>>> a9358e5423342008671b14c5da2fd145298ec1f9
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
<<<<<<< HEAD
        timer.cancel();
=======
        if(timeoutTimer != null)
            timeoutTimer.cancel();
>>>>>>> a9358e5423342008671b14c5da2fd145298ec1f9
    }
}
