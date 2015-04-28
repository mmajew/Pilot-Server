package main;

import servers.Settings;
import servers.TcpServer;
import controlers.Controler;
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

    private Settings settings;

    public MainFrame() {
        ServerLogger.initialize(loggingArea);
        settings = new Settings();

        JFrame frame = new JFrame("Serwer");
        frame.setContentPane(this.mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        initializeComponents();
    }

    public void initializeComponents() {
        Controler.initialize();
        runButton.addActionListener((ActionEvent event) -> {
            if (server != null && server.isRunning()) {
                server.sendMessage(ServerMessages.CONNECTION_NACK);
                server.close();
            } else {
                server = new TcpServer(this, settings);
                runButton.setEnabled(false);
                settingsButton.setEnabled(false);
                server.start();
            }
        });

        settingsButton.addActionListener((ActionEvent event) -> {
            new SettingsFrame(settings);
        });
    }

    public void enableStartButton() {
        runButton.setText("Start");
        runButton.setEnabled(true);
        settingsButton.setEnabled(true);
    }

    public void enableStopButton() {
        runButton.setText("Stop");
        runButton.setEnabled(true);
    }
}
