package com.company;

public class Ticket {
    private int id;
    private String startTime;
    private String endTime;

    public Ticket(int id, String startTime, String endTime) {
        this.id = id;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Ticket(int id, String startTime) {
        this.id = id;
        this.startTime = startTime;
        endTime = "N/A";
    }

    public int getId(){
        return id;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }
}
