package controlers;

import messages.Message;

import java.awt.*;
import java.awt.event.InputEvent;


public class CursorControler extends Controler {
    private int widthMultiplier;
    private int heightMultiplier;

    public void handleLeftMouseClick() {
        robot.mousePress(InputEvent.BUTTON1_MASK);
        robot.mouseRelease(InputEvent.BUTTON1_MASK);
    }

    public void handleRightMouseClick() {
        robot.mousePress(InputEvent.BUTTON2_MASK);
        robot.mouseRelease(InputEvent.BUTTON2_MASK);
    }

    public void setTouchAreaSize(Message message) {
        Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = new Double(screenSize.getWidth()).intValue();
        int screenHeight = new Double(screenSize.getHeight()).intValue();
        int touchAreaWidth = Integer.parseInt(message.getBodyArray().get(0));
        int touchAreaHeight = Integer.parseInt(message.getBodyArray().get(1));

        widthMultiplier = screenWidth / touchAreaWidth;
        heightMultiplier = screenHeight / touchAreaHeight;
    }

    public void handleAbsoluteMouseMovement(Message message) {
        int x = Integer.parseInt(message.getBodyArray().get(0));
        int y = Integer.parseInt(message.getBodyArray().get(1));

        robot.mouseMove(x * widthMultiplier , y * heightMultiplier);
    }
}
