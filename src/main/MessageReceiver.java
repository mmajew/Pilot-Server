package main;

import handlers.PingHandler;

/**
 * Created by marmajew on 4/13/2015.
 */
public class MessageReceiver {
    private PingHandler pingHandler;

    public MessageReceiver() {
        pingHandler = new PingHandler();
    }

    public void receiveMessage(String message) {
        String [] splitMessage = message.split(";");
        String header = splitMessage[0];

        switch(header) {
            case "C:PING":
                pingHandler.handle();
                break;
            case "C:RCLICK":
                //mRobot.mousePress(InputEvent.BUTTON3_MASK);
                //mRobot.mouseRelease(InputEvent.BUTTON3_MASK);

                break;
            case "C:LCLICK":
                //mRobot.mousePress(InputEvent.BUTTON1_MASK);
                //mRobot.mouseRelease(InputEvent.BUTTON1_MASK);

                break;
            case "C:MOVE":
                //if (cursorCheck.isSelected()) {
                //    mMoveHandler.handleMoveMessage(here, message, updatedMousePosition);
                //    mRobot.mouseMove(updatedMousePosition.x, updatedMousePosition.y);
                break;

            default:
                break;
        }
    }
}
