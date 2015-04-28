package main;

import servers.Settings;

import javax.swing.*;
import java.awt.event.ActionEvent;


public class SettingsFrame {
    private JPanel settingsPanel;
    private JTextField portTextField;
    private JButton saveButton;
    private JButton cancelButton;
    private JLabel portLabel;
    private JCheckBox allowAutoConfirm;

    private JFrame thisFrame;
    private Settings settings;

    public SettingsFrame(Settings settings) {
        this.settings = settings;

        portTextField.setText(settings.getPort().toString());
        allowAutoConfirm.setSelected(settings.getEnableAutoConfirm());

        JFrame frame = new JFrame("Serwer");
        frame.setContentPane(this.settingsPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        thisFrame = frame;
        initializeComponents();
    }

    public void initializeComponents() {
        saveButton.addActionListener((ActionEvent event) -> {
            Integer port = Integer.parseInt(portTextField.getText());
            boolean isAutoConfirmEnabled = allowAutoConfirm.isSelected();
            settings.setAllowAutoConfirm(isAutoConfirmEnabled);
            settings.setPort(port);
            thisFrame.dispose();
        });

        cancelButton.addActionListener((ActionEvent event) -> {
            thisFrame.dispose();
        });
    }
}
