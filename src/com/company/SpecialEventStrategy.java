package com.company;

public class SpecialEventStrategy implements CalculateStrategy {
    private int parkingFee;
    @Override
    public void calculate() {
        parkingFee = 20;
    }
}
