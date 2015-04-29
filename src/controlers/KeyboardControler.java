package controlers;


import messages.Message;

import java.awt.event.KeyEvent;
import java.util.Map;

public class KeyboardControler extends Controler {
    private Map keyMap;

    public KeyboardControler() {
        keyMap = new KeyMap().initializeKeyMap();
    }

    public void handleKeyDown(Message message) {
        robot.keyPress(KeyEvent.VK_TAB);
    }

    public void handleKeyUp(Message message) {

    }
}
