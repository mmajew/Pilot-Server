package controlers;

import java.awt.*;

/**
 * Created by marmajew on 4/22/2015.
 */
public abstract class Controler {
    protected static Robot robot;

    public static void initialize() {
        try {
            robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }
}
