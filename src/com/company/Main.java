package com.company;


import com.company.components.DateInput;
import com.company.components.DateTimeInput;
import com.company.components.Input;
import com.company.components.TimeInput;

public class Main {
    public static void main(String[] args) {
        // ������� ������������ Input
        Input input = new Input("������ �����:");
        input.render();

        // ������� ������������ DateInput
        DateInput dateInput = new DateInput("������ ����:");
        dateInput.render();

        // ������� ������������ TimeInput
        TimeInput timeInput = new TimeInput("������ ���:");
        timeInput.render();

        // ������� ������������ DateTimeInput
        DateTimeInput dateTimeInput = new DateTimeInput("������ ���� �� ���:");
        dateTimeInput.render();
    }
}


