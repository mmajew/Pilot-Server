package controlers;

import messages.Message;

import java.awt.*;


public class RelativeCursorControler extends Controler {
    private Integer startingX;
    private Integer startingY;

    public RelativeCursorControler() {
        startingX = 0;
        startingY = 0;
    }

    public void handleMouseMotion(Message message) {
        robot.mouseMove(startingX + Integer.parseInt(message.getBodyArray().get(0)),
                startingY + Integer.parseInt(message.getBodyArray().get(1)));
    }

    public void handleCursorDown() {
        startingX = MouseInfo.getPointerInfo().getLocation().x;
        startingY = MouseInfo.getPointerInfo().getLocation().y;
    }
}
