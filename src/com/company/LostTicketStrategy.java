package com.company;

public class LostTicketStrategy implements CalculateStrategy{
    private int parkingFee;

    @Override
    public void calculate() {
        parkingFee = 25;
    }
}
