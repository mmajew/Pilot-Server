package handlers;

import messages.Message;
import messages.ServerMessages;
import tools.ServerLogger;

import javax.swing.*;


public class ValidationHandler extends TaskHandler {
    private boolean result = false;

    public void handle(Message request) {
        String clientName = request.getBody() + " (" + tcpServer.getClientAddress() + ")";

        if(tcpServer.isAutoConfimEnabled()) {
            confirmConnection(clientName);
        }
        else {
            int response = JOptionPane.showConfirmDialog(null, "Klient o nazwie " + clientName + " próbuje się połączyć",
                    "Próba połączenia", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

            if (response == JOptionPane.YES_OPTION) {
                confirmConnection(clientName);
            } else {
                refuseConnection(clientName);
            }
        }
    }

    private void confirmConnection(String clientName) {
        ServerLogger.logMessage("Połaczono z " + clientName);
        tcpServer.confirmConnection();
        tcpServer.sendMessage(ServerMessages.CONNECTION_ACK);
        result = true;
    }

    private void refuseConnection(String clientName) {
        ServerLogger.logMessage("Odrzucono połączenie z " + clientName);
        tcpServer.sendMessage(ServerMessages.CONNECTION_NACK);
        tcpServer.close();
    }

    public boolean getResult() {
        return result;
    }
}
