package messages;

import controlers.RelativeCursorControler;


public class UdpMessageReceiver {
    private RelativeCursorControler cursorControler;

    public UdpMessageReceiver() {
        cursorControler = new RelativeCursorControler();
    }

    public void receiveMessage(Message message) {
        switch(message.getHeader()) {
        case ClientMessages.UDP_CURSOR_DOWN:
            cursorControler.handleCursorDown();
            break;

        case ClientMessages.UDP_MOUSE_MOTION:
            cursorControler.handleMouseMotion(message);
            break;

        default:
            break;
        }
    }
}
