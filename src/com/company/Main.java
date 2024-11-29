package com.company;

import com.company.components.*;

public class Main {
    public static void main(String[] args) {
        // Базовий інпут
        InputComponent input = new Input("Введіть текст:");

        // Декоратори для додавання функціональностей
        InputComponent dateInput = new DateInput(input);
        InputComponent timeInput = new TimeInput(dateInput);
        InputComponent dateTimeInput = new DateTimeInput(timeInput);

        // Виводимо тільки один раз в кінці
        dateTimeInput.render();
    }
}
