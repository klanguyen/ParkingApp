package com.company;

public class ParkingFeeGenerator {
    private int differentHours;
    private int differentMinutes;
    private CalculateStrategy calculateStrategy;

    public ParkingFeeGenerator(int differentHours, int differentMinutes) {
        this.differentHours = differentHours;
        this.differentMinutes = differentMinutes;
    }

    public void calculateParkingFee(int differentHours, int differentMinutes){
        if((differentHours < 3) || (differentHours == 3 && differentMinutes == 0)){
            calculateStrategy = new MinimumStrategy();
        }
        else if (differentHours > 3 || (differentHours == 3 && differentMinutes != 0)) {
            calculateStrategy = new GeneralStrategy();
            if (differentHours >= 12 && differentMinutes > 0) {
                calculateStrategy = new MaximumStrategy();
            }
        }
    }
}
