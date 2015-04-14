package main;

import handlers.PingHandler;
import tools.ServerLogger;

import javax.swing.*;


public class MessageReceiver {
    private PingHandler pingHandler;

    public MessageReceiver() {
        pingHandler = new PingHandler();
        pingHandler.initializeTimeoutTimer();
    }


    public boolean validateConnectionRequest(String requestMessage, String clientAddress) {
        boolean result = false;

        String [] splitMessage = requestMessage.split(";");
        String header = splitMessage[0];

        if(header.equals("C:CONN")) {
            String clientName = requestMessage;
            int response = JOptionPane.showConfirmDialog(null, "Klient o nazwie " + clientName + " (" + clientAddress + ") próbuje się połączyć",
                    "Próba połączenia", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

            if (response == JOptionPane.YES_OPTION)
                result = true;
        }
        else {
            ServerLogger.logMessage("Otrzymano nieprawidłowe żądanie połączenia z " + clientAddress);
        }

        return result;
    }


    public void receiveMessage(String message) {
        String [] splitMessage = message.split(";");
        String header = splitMessage[0];

        switch(header) {
            case "C:PING":
                pingHandler.handle();
                break;
            case "C:RCLICK":
                //mRobot.mousePress(InputEvent.BUTTON3_MASK);
                //mRobot.mouseRelease(InputEvent.BUTTON3_MASK);

                break;
            case "C:LCLICK":
                //mRobot.mousePress(InputEvent.BUTTON1_MASK);
                //mRobot.mouseRelease(InputEvent.BUTTON1_MASK);

                break;
            case "C:MOVE":
                //if (cursorCheck.isSelected()) {
                //    mMoveHandler.handleMoveMessage(here, message, updatedMousePosition);
                //    mRobot.mouseMove(updatedMousePosition.x, updatedMousePosition.y);
                break;

            default:
                break;
        }
    }
}
