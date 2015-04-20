package tools;

import javax.swing.*;


public class ServerLogger {
    private static JTextArea loggingArea;

    public static void initialize(JTextArea textArea) {
        loggingArea = textArea;

        logMessage(AddressAssistant.getAddresses());
    }

    public static void logMessage(String message) {
        loggingArea.append(message + "\n");
    }
}
