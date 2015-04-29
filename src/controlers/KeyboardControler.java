package controlers;


import messages.Message;

public class KeyboardControler extends Controler {
    private KeyMap keyMap;

    public KeyboardControler() {
        keyMap = new KeyMap();
    }

    public void handleKeyDown(Message message) {
        robot.keyPress((int) keyMap.get(message.getBody()));
    }

    public void handleKeyUp(Message message) {
        robot.keyRelease((int)keyMap.get(message.getBody()));
    }
}
