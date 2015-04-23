package handlers;

import messages.Message;
import messages.ServerMessages;

import java.net.SocketException;


public class UdpConnectionHandler extends TaskHandler {
    public void handle(Message message) {
        try {
            Integer requestedPort = Integer.parseInt(message.getBody());
            udpServer.initialize(requestedPort);
            udpServer.start();
            tcpServer.sendMessage(ServerMessages.UDP_ACK);
        } catch (SocketException exception) {
            tcpServer.sendMessage(ServerMessages.UDP_NACK);
        }
    }
}
