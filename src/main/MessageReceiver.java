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


    public boolean validateConnectionRequest(Message requestMessage, String clientAddress) {
        boolean result = false;

        if(requestMessage.verifyHeader("C:CONN")) {
            String clientName = requestMessage.getBody() + " (" + clientAddress + ")";

            int response = JOptionPane.showConfirmDialog(null, "Klient o nazwie " + clientName + " próbuje się połączyć",
                    "Próba połączenia", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

            if (response == JOptionPane.YES_OPTION)
            {
                ServerLogger.logMessage("Połaczono z " + clientName);
                result = true;
            }
            else {
                ServerLogger.logMessage("Odrzucono połączenie z " + clientName);
            }
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
