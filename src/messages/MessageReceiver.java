package messages;

import connection.TcpServer;
import connection.UdpServer;
import controlers.CursorControler;
import handlers.DisconnectionHandler;
import handlers.PingHandler;
import handlers.TaskHandler;
import handlers.UdpConnectionHandler;
import handlers.ValidationHandler;


public class MessageReceiver {
    private CursorControler cursorControler;

    private DisconnectionHandler disconnectionHandler;
    private PingHandler pingHandler;
    private ValidationHandler validationHandler;
    private UdpConnectionHandler udpConnectionHandler;

    private TcpServer tcpServer;
    private UdpServer udpServer;

    public MessageReceiver(TcpServer tcpServer) {
        this.udpServer = new UdpServer();
        this.tcpServer = tcpServer;
        TaskHandler.initialize(tcpServer, udpServer);

        cursorControler = new CursorControler();
        pingHandler = new PingHandler();
        validationHandler = new ValidationHandler();
<<<<<<< HEAD
        udpConnectionHandler = new UdpConnectionHandler();
=======
        disconnectionHandler = new DisconnectionHandler();

        server = tcpServer;
>>>>>>> a9358e5423342008671b14c5da2fd145298ec1f9
    }

    public void receiveMessage(Message message) {
        if(!tcpServer.isConnectionConfirmed()) {
            if(message.compareHeader(ClientMessages.CONNECTION_REQUEST)) {
                validationHandler.handle(message);
                if(validationHandler.getResult())
                    pingHandler.initializeTimeoutTimer();
            }
        }
        else {
            switch (message.getHeader()) {
                case ClientMessages.PING:
<<<<<<< HEAD
                    pingHandler.handle();
                    break;

                case ClientMessages.TOUCH_AREA_SIZE:
                    cursorControler.setTouchAreaSize(message);
=======
                    pingHandler.handlePing();
>>>>>>> a9358e5423342008671b14c5da2fd145298ec1f9
                    break;

                case ClientMessages.LEFT_CLICK:
                    cursorControler.handleLeftMouseClick();
                    break;

                case ClientMessages.RIGHT_CLICK:
                    cursorControler.handleRightMouseClick();
                    break;

                case ClientMessages.TCP_MOUSE_MOVE:
                    cursorControler.handleAbsoluteMouseMovement(message);
                    break;

                case ClientMessages.CLOSE:
<<<<<<< HEAD
                    tcpServer.close();
                    break;

                case ClientMessages.UDP_REQUEST:
                    udpConnectionHandler.handle(message);
=======
                    disconnectionHandler.handleClientDisconnected();
                    stopHandlers();
>>>>>>> a9358e5423342008671b14c5da2fd145298ec1f9
                    break;

                default:
                    break;
            }
        }
    }

<<<<<<< HEAD
    public void close() {
        pingHandler.stopTimeoutTimer();
        udpServer.close();
=======
    public void stopHandlers() {
        pingHandler.stopTimeoutTimer();
>>>>>>> a9358e5423342008671b14c5da2fd145298ec1f9
    }
}
