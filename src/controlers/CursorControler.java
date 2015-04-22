package controlers;

import java.awt.event.InputEvent;

/**
 * Created by marmajew on 4/22/2015.
 */
public class CursorControler extends Controler {
    public void handleLeftMouseClick() {
        robot.mousePress(InputEvent.BUTTON1_MASK);
        robot.mouseRelease(InputEvent.BUTTON1_MASK);
    }

    public void handleRightMouseClick() {
        robot.mousePress(InputEvent.BUTTON2_MASK);
        robot.mouseRelease(InputEvent.BUTTON2_MASK);
    }
}
