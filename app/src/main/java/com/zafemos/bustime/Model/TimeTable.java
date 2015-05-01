package com.zafemos.bustime.Model;

import java.util.Calendar;

/**
 * Created by fernando on 01/05/15.
 */
public class TimeTable {

    private Calendar time;
    private String branch = null;

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public Calendar getTime() {
        return time;
    }

    public void setTime(Calendar time) {
        this.time = time;
    }
}
