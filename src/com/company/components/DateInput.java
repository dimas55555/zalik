package com.company.components;

import com.toedter.calendar.JCalendar;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateInput extends Input {
    public DateInput(String labelText) {
        super(labelText);
    }

    @Override
    public void render() {
        final JFrame frame = new JFrame("DateInput Component");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 250);
        frame.setLayout(new BorderLayout());

        JLabel label = new JLabel(" " + getLabelText());
        final JTextField textField = new JTextField();
        JButton calendarButton = new JButton("\uD83D\uDCC5");

        calendarButton.setPreferredSize(new Dimension(50, 30));

        // Додавання слухача для кнопки
        calendarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Створення компонента календаря
                JCalendar calendar = new JCalendar();

                // Показати діалог з календарем
                int result = JOptionPane.showConfirmDialog(frame, calendar, "Виберіть дату", JOptionPane.OK_CANCEL_OPTION);
                if (result == JOptionPane.OK_OPTION) {
                    // Отримуємо вибрану дату з календаря
                    Date selectedDate = calendar.getDate();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    textField.setText(dateFormat.format(selectedDate));
                }
            }
        });

        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.add(textField, BorderLayout.CENTER);
        inputPanel.add(calendarButton, BorderLayout.EAST);

        frame.add(label, BorderLayout.NORTH);
        frame.add(inputPanel, BorderLayout.CENTER);

        frame.setVisible(true);
    }

    private String getLabelText() {
        return "Дата:";
    }
}
