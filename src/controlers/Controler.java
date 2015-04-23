package controlers;

import java.awt.*;


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
