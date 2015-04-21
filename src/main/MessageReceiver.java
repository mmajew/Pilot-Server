package main;

import connection.TCPServer;
import handlers.PingHandler;
import handlers.ValidationHandler;
import tools.Message;


public class MessageReceiver {
    private PingHandler pingHandler;
    private ValidationHandler validationHandler;

    private TCPServer server;

    public MessageReceiver(TCPServer tcpServer) {
        pingHandler = new PingHandler();
        validationHandler = new ValidationHandler();

        server = tcpServer;
    }

    public void receiveMessage(Message message) {
        if(!server.isConnectionConfirmed()) {
            if(message.compareHeader("C:CONN")) {
                validationHandler.handle(message);
                if(validationHandler.getResult())
                    pingHandler.initializeTimeoutTimer();
            }
        }
        else {
            switch (message.getHeader()) {
                case "C:PING":
                    pingHandler.handle(message);
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
}
