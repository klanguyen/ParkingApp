package com.company;

public class GeneralStrategy implements CalculateStrategy {
    private int parkingFee;
    public int differentHours;
    @Override
    public void calculate() {
        parkingFee = 5 + differentHours * 1;
    }
}
