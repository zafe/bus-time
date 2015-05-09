package com.zafemos.bustime.Controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by fernando on 08/05/15.
 */
public class AppUtils {
    private static AppUtils ourInstance = new AppUtils();

    public static AppUtils getInstance() {
        return ourInstance;
    }

    private AppUtils() {
    }

    public String getDate(){
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE d");
        String today = sdf.format(c.getTime());
        today = Character.toUpperCase(today.charAt(0)) + today.substring(1);
        return today;
    }

}
