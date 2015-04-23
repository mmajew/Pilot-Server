package handlers;

import messages.Message;
import messages.ServerMessages;
import tools.ServerLogger;

import javax.swing.*;


public class ValidationHandler extends TaskHandler {
    private boolean result = false;

    public void handle(Message request) {
        String clientName = request.getBody() + " (" + tcpServer.getClientAddress() + ")";

        int response = JOptionPane.showConfirmDialog(null, "Klient o nazwie " + clientName + " próbuje się połączyć",
                "Próba połączenia", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

        if (response == JOptionPane.YES_OPTION)
        {
            ServerLogger.logMessage("Połaczono z " + clientName);
<<<<<<< HEAD
            tcpServer.confirmConnection();
            tcpServer.sendMessage(ServerMessages.CONNECTION_ACK);
=======
            server.confirmConnection();
            server.saveClientName(clientName);
            server.sendMessage(ServerMessages.CONNECTION_ACK);
>>>>>>> a9358e5423342008671b14c5da2fd145298ec1f9
            result = true;
        }
        else {
            ServerLogger.logMessage("Odrzucono połączenie z " + clientName);
            tcpServer.sendMessage(ServerMessages.CONNECTION_NACK);
            tcpServer.close();
        }
    }

    public boolean getResult() {
        return result;
    }
}
