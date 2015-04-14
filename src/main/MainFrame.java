package main;

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
        TaskHandler.initialize(server);

        server = new TCPServer();

        initializeComponents();
    }


    public void initializeComponents() {
        JFrame frame = new JFrame("Serwer");
        frame.setContentPane(this.mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

        runButton.addActionListener((ActionEvent event) -> {
            if(!server.isRunning())
                server.start();
            else
                server.close();
        });
    }
}
