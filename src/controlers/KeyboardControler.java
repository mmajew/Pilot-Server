package controlers;


import messages.Message;


public class KeyboardControler extends Controler {
    private KeyMap keyMap;

    public KeyboardControler() {
        keyMap = new KeyMap();
    }

    public void handleKeyDown(Message message) {
        int keyCode = (int) keyMap.get(message.getBody());
        robot.keyPress(keyCode);
    }

    public void handleKeyUp(Message message) {
        int keyCode = (int) keyMap.get(message.getBody());
        robot.keyRelease(keyCode);
    }
}