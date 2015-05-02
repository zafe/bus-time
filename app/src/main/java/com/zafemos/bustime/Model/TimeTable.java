package com.zafemos.bustime.Model;

/**
 * Created by fernando on 01/05/15.
 */
public class TimeTable {

    private int hours;
    private int minutes;
    private String branch = null;

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }
}
