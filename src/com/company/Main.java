package com.company;

import com.company.components.*;

public class Main {
    public static void main(String[] args) {
        // ������� �����
        InputComponent input = new Input("������ �����:");

        // ���������� ��� ��������� �����������������
        InputComponent dateInput = new DateInput(input);
        InputComponent timeInput = new TimeInput(dateInput);
        InputComponent dateTimeInput = new DateTimeInput(timeInput);

        // �������� ����� ���� ��� � ����
        dateTimeInput.render();
    }
}
