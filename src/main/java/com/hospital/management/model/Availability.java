package com.hospital.management.model;

public class Availability {

    private String day; // MONDAY, TUESDAY, etc.
    private String startTime; // "09:00"
    private String endTime;   // "13:00"

    // getters & setters

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}