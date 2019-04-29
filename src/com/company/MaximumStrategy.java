package com.company;

public class MaximumStrategy implements CalculateStrategy {
    private int parkingFee;
    @Override
    public void calculate() {
        parkingFee = 15;
    }
}
