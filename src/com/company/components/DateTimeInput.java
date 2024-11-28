package com.company.components;

import com.toedter.calendar.JCalendar;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeInput extends Input {
    public DateTimeInput(String labelText) {
        super(labelText);
    }

    @Override
    public void render() {
        final JFrame frame = new JFrame("DateTimeInput Component");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 250);
        frame.setLayout(new BorderLayout());

        JLabel label = new JLabel(" " + getLabelText());
        final JTextField textField = new JTextField();
        JButton dateTimeButton = new JButton("all");

        dateTimeButton.setPreferredSize(new Dimension(50, 30));

        dateTimeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DateTimePickerDialog dialog = new DateTimePickerDialog(frame, textField);
                dialog.setVisible(true);
            }
        });

        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.add(textField, BorderLayout.CENTER);
        inputPanel.add(dateTimeButton, BorderLayout.EAST);

        frame.add(label, BorderLayout.NORTH);
        frame.add(inputPanel, BorderLayout.CENTER);

        frame.setVisible(true);
    }

    private String getLabelText() {
        return "Дата і час:";
    }

    class DateTimePickerDialog extends JDialog {
        private Date selectedDate;
        private int selectedHour = 12;
        private int selectedMinute = 0;

        public DateTimePickerDialog(JFrame parent, JTextField textField) {
            super(parent, "Виберіть дату і час", true);
            setSize(500, 500);
            setLayout(new BorderLayout());
            setLocationRelativeTo(parent);

            JCalendar calendar = new JCalendar();
            calendar.setPreferredSize(new Dimension(200, 200));

            JPanel timePanel = new JPanel(new BorderLayout());
            JLabel timeLabel = new JLabel("Час: 12:00", SwingConstants.CENTER);

            JButton hourButton = new JButton("Години");
            JButton minuteButton = new JButton("Хвилини");

            timePanel.add(timeLabel, BorderLayout.CENTER);

            JPanel buttonPanel = new JPanel();
            buttonPanel.add(hourButton);
            buttonPanel.add(minuteButton);
            timePanel.add(buttonPanel, BorderLayout.SOUTH);

            ClockPanel clockPanel = new ClockPanel(timeLabel);
            timePanel.add(clockPanel, BorderLayout.NORTH);

            hourButton.addActionListener(e -> clockPanel.setAdjustingHour(true));
            minuteButton.addActionListener(e -> clockPanel.setAdjustingHour(false));

            JButton okButton = new JButton("OK");
            okButton.addActionListener(e -> {
                selectedDate = calendar.getDate();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String dateString = dateFormat.format(selectedDate);
                String timeString = String.format("%02d:%02d", selectedHour, selectedMinute);
                textField.setText(dateString + " " + timeString);
                dispose();
            });

            add(calendar, BorderLayout.WEST);
            add(timePanel, BorderLayout.CENTER);
            add(okButton, BorderLayout.SOUTH);
        }

        class ClockPanel extends JPanel {
            private final int radius = 100;
            private boolean adjustingHour = true;
            private JLabel timeLabel;

            public ClockPanel(JLabel timeLabel) {
                this.timeLabel = timeLabel;
                setPreferredSize(new Dimension(200, 200));
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
            }

            public void setAdjustingHour(boolean adjustingHour) {
                this.adjustingHour = adjustingHour;
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