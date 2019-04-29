package com.company;

public class MinimumStrategy implements CalculateStrategy {
    public int parkingFee;
    @Override
    public void calculate() {
        parkingFee = 5;
    }
}
