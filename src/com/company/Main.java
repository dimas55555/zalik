package com.company;


import com.company.components.DateInput;
import com.company.components.DateTimeInput;
import com.company.components.Input;
import com.company.components.TimeInput;

public class Main {
    public static void main(String[] args) {
        // Приклад використання Input
        Input input = new Input("Введіть текст:");
        input.render();

        // Приклад використання DateInput
        DateInput dateInput = new DateInput("Введіть дату:");
        dateInput.render();

        // Приклад використання TimeInput
        TimeInput timeInput = new TimeInput("Введіть час:");
        timeInput.render();

        // Приклад використання DateTimeInput
        DateTimeInput dateTimeInput = new DateTimeInput("Введіть дату та час:");
        dateTimeInput.render();
    }
}


