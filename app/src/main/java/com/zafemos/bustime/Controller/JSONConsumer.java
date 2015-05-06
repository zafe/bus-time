package com.zafemos.bustime.Controller;

import android.os.Message;
import android.util.JsonReader;

import com.zafemos.bustime.Model.Days;
import com.zafemos.bustime.Model.TimeTable;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by fernando on 30/04/15.
 */
public class JSONConsumer {

    //private final static String URL= "http://s3.amazonaws.com/codecademy-content/courses/ltp4/forecast-api/forecast.json";

    InputStream in = new InputStreamReader()

    public List readJsonStream(InputStream in) throws IOException {
        JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));
        try {
            return readMessagesArray(reader);
        }finally {
                reader.close();
            }
        }

        public List readMessagesArray(JsonReader reader) throws IOException {
            List messages = new ArrayList();

            reader.beginArray();
            while (reader.hasNext()) {
                messages.add(readMsg(reader));
            }
            reader.endArray();
            return messages;
        }

    public Message readMsg(JsonReader reader) throws IOException {

        String origin = null;
        String destiny = null;

        Days days = new Days();
        List<TimeTable> timeTables;

        reader.beginObject();

        while (reader.hasNext())
        {
            String name = reader.nextName();

                 if (name.equals("origin"))    origin = reader.nextString();
            else if (name.equals("destiny"))   destiny = reader.nextString();
            else if (name.equals("days"))      days = readDays(reader);
            else if (name.equals("timetable")) timeTables = readTimeTable(reader);

        }

        reader.endObject();

        return new Message();

    }

    public List<TimeTable> readTimeTable(JsonReader reader) throws IOException {
        List<TimeTable> timetables = new ArrayList<TimeTable>();
        TimeTable timeTable = new TimeTable();
        List<Integer> bustime = new ArrayList<Integer>();


        reader.beginArray();

        while(reader.hasNext())
        {
            reader.beginObject();
            String name = reader.nextName();
            if(name.equals("time"))
            {
                bustime = readTime(reader.nextString());
                timeTable.setHours(bustime.get(0));
                timeTable.setMinutes(bustime.get(1));
            }
            else if (name.equals("branch"))
            {
                timeTable.setBranch(reader.nextString());
            }
            reader.endObject();
            timetables.add(timeTable);

        }

        reader.endArray();

        return timetables;

    }

    public List<Integer> readTime(String time){

        List<Integer> bustime = new ArrayList<Integer>();
        StringTokenizer tokenizer = new StringTokenizer(time,":");

        bustime.add(Integer.parseInt(tokenizer.nextToken()));
        bustime.add(Integer.parseInt(tokenizer.nextToken()));

        return bustime;

    }

    public Days readDays(JsonReader reader) throws IOException{
        Days day = new Days();
        reader.beginArray();

        while (reader.hasNext())
        {
            String name = reader.nextString();

                 if (name.equals("L")) day.setLunes(true);
            else if (name.equals("M")) day.setMartes(true);
            else if (name.equals("X")) day.setMiercoles(true);
            else if (name.equals("J")) day.setJueves(true);
            else if (name.equals("V")) day.setViernes(true);
            else if (name.equals("S")) day.setSabado(true);
            else if (name.equals("D")) day.setDomingo(true);

        }

        reader.endArray();
        return day;
    }


}
