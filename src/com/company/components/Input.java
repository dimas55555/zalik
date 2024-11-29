package com.company.components;

import javax.swing.*;
import java.awt.*;

public class Input implements InputComponent {
    private String labelText;

    public Input(String labelText) {
        this.labelText = labelText;
    }

    @Override
    public void render() {
        JFrame frame = new JFrame("Input Component");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 100);
        frame.setLayout(new BorderLayout());

        JLabel label = new JLabel(labelText);
        JTextField textField = new JTextField();

        frame.add(label, BorderLayout.WEST);
        frame.add(textField, BorderLayout.CENTER);

        frame.setVisible(true);
    }
}
