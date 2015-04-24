package main;

import connection.TcpServer;
import controlers.Controler;
import handlers.TaskHandler;
import messages.ServerMessages;
import tools.ServerLogger;

import javax.swing.*;
import java.awt.event.ActionEvent;


public class MainFrame {
    private TcpServer server;
    private JPanel mainPanel;
    private JTextArea loggingArea;
    private JButton runButton;
    private JButton settingsButton;

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

        Controler.initialize();
        runButton.addActionListener((ActionEvent event) -> {
            if (server != null && server.isRunning()) {
                server.sendMessage(ServerMessages.CONNECTION_NACK);
                server.close();
            } else {
                server = new TcpServer(this);
                server.start();
                runButton.setEnabled(false);
            }
        });
    }

    public void enableStartButton() {
        runButton.setText("Start");
        runButton.setEnabled(true);
    }

    public void enableStopButton() {
        runButton.setText("Stop");
        runButton.setEnabled(true);
    }
}
