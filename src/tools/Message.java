package tools;

import java.util.ArrayList;
import java.util.Arrays;


public class Message {
    private final String header;
    private final ArrayList<String> body;

    public Message(String receivedString) {
        String [] splitMessage = receivedString.split(";");

        header = splitMessage[0];
        body = new ArrayList<>(Arrays.asList(Arrays.copyOfRange(splitMessage, 1, splitMessage.length)));
    }

    public boolean compareHeader(String desiredHeader) {
        return header.equals(desiredHeader);
    }

    public String getHeader() {
        return header;
    }

    public String getBody() {
        return body.get(0);
    }

    public ArrayList<String> getBodyArray() {
        return body;
    }
}
