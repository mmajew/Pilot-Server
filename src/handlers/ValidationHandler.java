package handlers;

import tools.Message;
import tools.ServerLogger;

import javax.swing.*;

/**
 * Created by marmajew on 4/20/2015.
 */
public class ValidationHandler extends TaskHandler {

    public void handle(Message request) {
        String clientName = request.getBody() + " (" + server.getClientAddress() + ")";

        int response = JOptionPane.showConfirmDialog(null, "Klient o nazwie " + clientName + " próbuje się połączyć",
                "Próba połączenia", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

        if (response == JOptionPane.YES_OPTION)
        {
            ServerLogger.logMessage("Połaczono z " + clientName);
            server.setConnectionConfirmed(true);
            server.sendMessage("S:ACK");
        }
        else {
            ServerLogger.logMessage("Odrzucono połączenie z " + clientName);
            server.sendMessage("S:NACK");
            server.close();
        }
    }
}
