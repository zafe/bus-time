package com.zafemos.bustime.Controller;

import android.os.Message;
import android.util.JsonReader;

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

    private final static String URL= "http://s3.amazonaws.com/codecademy-content/courses/ltp4/forecast-api/forecast.json";

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

        List days = new ArrayList();
        List<TimeTable> timeTables;

        reader.beginObject();

        while (reader.hasNext())
        {
            String name = reader.nextName();

                 if (name.equals("origin"))    origin = reader.nextString();
            else if (name.equals("destiny"))   destiny = reader.nextString();
            else if (name.equals("days")) { } //completar
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

            if(reader.equals("time"))
            {
                bustime = readTime(reader.nextString());
                timeTable.setHours(bustime.get(0));
                timeTable.setMinutes(bustime.get(1));
            }
            else if (reader.equals("branch"))
            {
                timeTable.setBranch(reader.nextString());
            }

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


}
