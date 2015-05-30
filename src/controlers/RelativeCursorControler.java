package controlers;

import messages.Message;

import java.awt.*;


public class RelativeCursorControler extends Controler {
    private Integer startingX;
    private Integer startingY;

    private Integer currentX;
    private Integer currentY;

    public RelativeCursorControler() {
        PointerInfo a = MouseInfo.getPointerInfo();
        Point b = a.getLocation();

        startingX = currentX = (int) b.getX();
        startingY = currentY = (int) b.getY();
    }

    public void handleMouseMotion(Message message) {
        currentX = startingX - Integer.parseInt(message.getBodyArray().get(0));
        currentY = startingY - Integer.parseInt(message.getBodyArray().get(1));

        robot.mouseMove(currentX, currentY);
    }

    public void handleCursorDown() {
        startingX = currentX;
        startingY = currentY;
    }
}
