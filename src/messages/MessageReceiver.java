package messages;

import connection.TcpServer;
import connection.UdpServer;
import controlers.CursorControler;
import handlers.PingHandler;
import handlers.TaskHandler;
import handlers.UdpConnectionHandler;
import handlers.ValidationHandler;


public class MessageReceiver {
    private CursorControler cursorControler;
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
        udpConnectionHandler = new UdpConnectionHandler();
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
                    pingHandler.handle();
                    break;

                case ClientMessages.TOUCH_AREA_SIZE:
                    cursorControler.setTouchAreaSize(message);
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
                    tcpServer.close();
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
