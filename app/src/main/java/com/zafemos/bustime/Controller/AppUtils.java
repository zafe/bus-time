package com.zafemos.bustime.Controller;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by fernando on 08/05/15.
 */
public class AppUtils {

    private static AppUtils ourInstance = new AppUtils();
    private String url = "https://raw.githubusercontent.com/zafemos/bus-time/master/timetable.json";

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

    public String getJSON(Context context) {
        String response = null;
        ServiceHandler sh = new ServiceHandler();
        response = sh.makeServiceCall(url, ServiceHandler.GET);

        return response;
    }


    public void saveFile(Context context, String JSONData) throws IOException{

    String filename = "timetable.json";
    try
    {
        FileOutputStream fos = new FileOutputStream("/mnt/sdcard/TimeTable/bus.json", false);
        fos.write(JSONData.getBytes());
        fos.close();
    }
    catch (FileNotFoundException e)
    {
        e.printStackTrace();
    }



    System.out.println(context.getFilesDir().toString() + "HOLA PERINOLAS");
    File file = new File(context.getFilesDir(), filename);



    }
}
