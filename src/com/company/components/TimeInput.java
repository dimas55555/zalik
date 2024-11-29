package com.company.components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TimeInput extends InputDecorator {
    public TimeInput(InputComponent component) {
        super(component);
    }

    @Override
    public void render() {
        super.render();

        final JFrame frame = new JFrame("TimeInput Component");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 120);
        frame.setLayout(new BorderLayout());

        JLabel label = new JLabel(" " + getLabelText());
        final JTextField textField = new JTextField();
        JButton timeButton = new JButton("\u23F0");

        timeButton.setPreferredSize(new Dimension(50, 30));

        timeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TimePickerDialog dialog = new TimePickerDialog(frame, textField);
                dialog.setVisible(true);
            }
        });

        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.add(textField, BorderLayout.CENTER);
        inputPanel.add(timeButton, BorderLayout.EAST);

        frame.add(label, BorderLayout.NORTH);
        frame.add(inputPanel, BorderLayout.CENTER);

        frame.setVisible(true);
    }

    private String getLabelText() {
        return "Час:";
    }

    class TimePickerDialog extends JDialog {
        private int selectedHour = 12;
        private int selectedMinute = 0;
        private boolean adjustingHour = true;

        public TimePickerDialog(JFrame parent, JTextField textField) {
            super(parent, "Виберіть час", true);
            setSize(350, 400);
            setLayout(new BorderLayout());
            setLocationRelativeTo(parent);

            ClockPanel clockPanel = new ClockPanel(textField);

            JPanel buttonPanel = new JPanel();
            JButton hourButton = new JButton("Години");
            JButton minuteButton = new JButton("Хвилини");

            hourButton.addActionListener(e -> {
                adjustingHour = true;
                clockPanel.repaint();
            });

            minuteButton.addActionListener(e -> {
                adjustingHour = false;
                clockPanel.repaint();
            });

            buttonPanel.add(hourButton);
            buttonPanel.add(minuteButton);

            JButton okButton = new JButton("OK");
            okButton.addActionListener(e -> {
                textField.setText(String.format("%02d:%02d", selectedHour, selectedMinute));
                dispose();
            });

            add(clockPanel, BorderLayout.CENTER);
            add(buttonPanel, BorderLayout.NORTH);
            add(okButton, BorderLayout.SOUTH);
        }

        class ClockPanel extends JPanel {
            private final int radius = 100;
            private JTextField textField;
            private JLabel timeLabel;

            public ClockPanel(JTextField textField) {
                this.textField = textField;
                setPreferredSize(new Dimension(300, 300));
                addMouseMotionListener(new MouseAdapter() {
                    @Override
                    public void mouseDragged(MouseEvent e) {
                        updateTimeFromMouse(e.getPoint());
                    }
                });
                addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        updateTimeFromMouse(e.getPoint());
                    }
                });

                timeLabel = new JLabel(String.format("Час: %02d:%02d", selectedHour, selectedMinute), SwingConstants.CENTER);
                timeLabel.setFont(new Font("Arial", Font.PLAIN, 18));
                timeLabel.setPreferredSize(new Dimension(200, 30));
                add(timeLabel, BorderLayout.SOUTH); // Додаємо label для часу
            }

            private void updateTimeFromMouse(Point clickPoint) {
                int centerX = getWidth() / 2;
                int centerY = getHeight() / 2;

                double dx = clickPoint.x - centerX;
                double dy = clickPoint.y - centerY;

                double angle = Math.toDegrees(Math.atan2(dy, dx));
                if (angle < 0) {
                    angle += 360;
                }

                if (adjustingHour) {
                    selectedHour = (int) (angle / 30);
                    if (selectedHour == 0) selectedHour = 12;
                } else {
                    selectedMinute = (int) (angle / 6);
                }

                textField.setText(String.format("%02d:%02d", selectedHour, selectedMinute));
                timeLabel.setText(String.format("Час: %02d:%02d", selectedHour, selectedMinute));
                repaint();
            }

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                int centerX = getWidth() / 2;
                int centerY = getHeight() / 2;

                g2d.setColor(Color.LIGHT_GRAY);
                g2d.fillOval(centerX - radius, centerY - radius, radius * 2, radius * 2);

                g2d.setColor(Color.BLACK);
                for (int i = 1; i <= 12; i++) {
                    double angle = Math.toRadians((i - 3) * 30);
                    int x = (int) (centerX + Math.cos(angle) * (radius - 20));
                    int y = (int) (centerY + Math.sin(angle) * (radius - 20));
                    g2d.drawString(Integer.toString(i), x - 5, y + 5);
                }

                double hourAngle = Math.toRadians((selectedHour - 3) * 30);
                g2d.setColor(adjustingHour ? Color.RED : Color.GRAY);
                g2d.drawLine(centerX, centerY, centerX + (int) (Math.cos(hourAngle) * (radius - 40)),
                        centerY + (int) (Math.sin(hourAngle) * (radius - 40)));

                double minuteAngle = Math.toRadians((selectedMinute - 15) * 6);
                g2d.setColor(adjustingHour ? Color.GRAY : Color.BLUE);
                g2d.drawLine(centerX, centerY, centerX + (int) (Math.cos(minuteAngle) * (radius - 60)),
                        centerY + (int) (Math.sin(minuteAngle) * (radius - 60)));
            }
        }
    }
}
