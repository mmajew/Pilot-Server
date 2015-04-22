package messages;

import connection.TCPServer;
import controlers.CursorControler;
import handlers.PingHandler;
import handlers.ValidationHandler;


public class MessageReceiver {
    private CursorControler cursorControler;
    private PingHandler pingHandler;
    private ValidationHandler validationHandler;

    private TCPServer server;

    public MessageReceiver(TCPServer tcpServer) {
        cursorControler = new CursorControler();
        pingHandler = new PingHandler();
        validationHandler = new ValidationHandler();

        server = tcpServer;
    }

    public void receiveMessage(Message message) {
        if(!server.isConnectionConfirmed()) {
            if(message.compareHeader(ClientMessages.CONNECTION_REQUEST)) {
                validationHandler.handle(message);
                if(validationHandler.getResult())
                    pingHandler.initializeTimeoutTimer();
            }
        }
        else {
            switch (message.getHeader()) {
                case ClientMessages.PING:
                    pingHandler.handle(message);
                    break;
                case ClientMessages.LEFT_CLICK:
                    cursorControler.handleLeftMouseClick();
                    break;
                case ClientMessages.RIGHT_CLICK:
                    cursorControler.handleRightMouseClick();
                    break;
                case ClientMessages.TCP_MOUSE_MOVE:
                    //if (cursorCheck.isSelected()) {
                    //    mMoveHandler.handleMoveMessage(here, message, updatedMousePosition);
                    //    mRobot.mouseMove(updatedMousePosition.x, updatedMousePosition.y);
                    break;
                case ClientMessages.CLOSE:
                    server.close();
                    break;
                default:
                    break;
            }
        }
    }
}