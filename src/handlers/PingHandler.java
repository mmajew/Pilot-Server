package handlers;


public class PingHandler extends TaskHandler {
    public void handle() {
        String pingResponse = "S:PONG";
        server.sendMessage(pingResponse);
    }
}
