package handlers;

/**
 * Created by marmajew on 4/13/2015.
 */
public class PingHandler extends TaskHandler {

    public void handle() {
        String pingResponse = "S:PONG";
        sendMessage(pingResponse);
    }
}
