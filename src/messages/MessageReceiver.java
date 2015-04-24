package messages;

import connection.TcpServer;
import connection.UdpServer;
import controlers.AbsoluteCursorControler;
import handlers.DisconnectionHandler;
import handlers.PingHandler;
import handlers.TaskHandler;
import handlers.UdpConnectionHandler;
import handlers.ValidationHandler;


public class MessageReceiver {
    private AbsoluteCursorControler absoluteCursorControler;

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

        absoluteCursorControler = new AbsoluteCursorControler();
        pingHandler = new PingHandler();
        validationHandler = new ValidationHandler();
        udpConnectionHandler = new UdpConnectionHandler();
        disconnectionHandler = new DisconnectionHandler();
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
                    pingHandler.handlePing();
                    break;

                case ClientMessages.TOUCH_AREA_SIZE:
                    absoluteCursorControler.setTouchAreaSize(message);
                    break;

                case ClientMessages.LEFT_CLICK:
                    absoluteCursorControler.handleLeftMouseClick();
                    break;

                case ClientMessages.RIGHT_CLICK:
                    absoluteCursorControler.handleRightMouseClick();
                    break;

                case ClientMessages.TCP_MOUSE_MOVE:
                    absoluteCursorControler.handleAbsoluteMouseMovement(message);
                    break;

                case ClientMessages.CLOSE:
                    disconnectionHandler.handleClientDisconnected();
                    break;

                case ClientMessages.UDP_REQUEST:
                    udpConnectionHandler.handle(message);
                    break;

                default:
                    break;
            }
        }
    }

    public void close() {
        pingHandler.stopTimeoutTimer();
        udpServer.close();
    }
}
