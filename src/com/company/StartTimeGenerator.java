package com.company;

public class StartTimeGenerator {
    private int startHour;
    private int startMinute;
    private String startTime;

    public StartTimeGenerator() {
        setStartHour();
        setStartMinute();
        setStartTime();
    }

    private void setStartHour() {
        startHour = randomize(7,12);
    }

    private void setStartMinute(){
        if (startHour == 12)
        {
            startMinute = 0;
        } else {
            startMinute = randomize(0, 59);
        }
    }

    private void setStartTime(){
        if (startHour == 12 && startMinute == 0) {
            startTime = "12:00pm";
        }
        else if (startMinute <= 9) {
            startTime = startHour + ":0" + startMinute + "am";
        }
        else {
            startTime = startHour + ":" + startMinute + "am";
        }
    }

    public String getStartTime(){
        return startTime;
    }

    public int randomize(int min, int max) {
        return min + (int)(Math.random()*((max - min) + 1));
    }
}
