package messages;

import controlers.KeyboardControler;
import servers.TcpServer;
import servers.UdpServer;
import controlers.AbsoluteCursorControler;
import handlers.DisconnectionHandler;
import handlers.PingHandler;
import handlers.TaskHandler;
import handlers.UdpConnectionHandler;
import handlers.ValidationHandler;


public class MessageReceiver {
    private AbsoluteCursorControler absoluteCursorControler;
    private KeyboardControler keyboardControler;

    private DisconnectionHandler disconnectionHandler;
    private PingHandler pingHandler;
    private ValidationHandler validationHandler;
    private UdpConnectionHandler udpConnectionHandler;

    private TcpServer tcpServer;
    private UdpServer udpServer;

    public MessageReceiver(TcpServer tcpServer) {
        this.udpServer = new UdpServer();
        this.tcpServer = tcpServer;

        keyboardControler = new KeyboardControler();
        absoluteCursorControler = new AbsoluteCursorControler();

        TaskHandler.initialize(tcpServer, udpServer);
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

                case ClientMessages.TCP_KEY_DOWN:
                    keyboardControler.handleKeyDown(message);
                    break;

                case ClientMessages.TCP_KEY_UP:
                    keyboardControler.handleKeyUp(message);

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
