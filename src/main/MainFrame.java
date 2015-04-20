package main;

import connection.TCPServer;
import handlers.TaskHandler;
import tools.ServerLogger;

import javax.swing.*;
import java.awt.event.ActionEvent;


public class MainFrame {
    private TCPServer server;
    private JPanel mainPanel;
    private JTextArea loggingArea;
    private JButton runButton;
    private JButton button2;

    public MainFrame() {
        ServerLogger.initialize(loggingArea);
        initializeComponents();
    }

    public void initializeComponents() {
        JFrame frame = new JFrame("Serwer");
        frame.setContentPane(this.mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        runButton.addActionListener((ActionEvent event) -> {
            server = new TCPServer(this);
            TaskHandler.initialize(server);
            server.start();
            runButton.setEnabled(false);
        });
    }

    public void setEnabledRunButton(boolean state) {
        runButton.setEnabled(state);
    }
}
