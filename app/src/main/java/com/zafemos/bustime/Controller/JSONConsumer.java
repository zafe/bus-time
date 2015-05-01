package com.zafemos.bustime.Controller;

import android.os.Message;
import android.util.JsonReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
        ArrayList<Date> days = new ArrayList<Date>();
        return new Message();

    }

    /**
     public Message readMessage(JsonReader reader) throws IOException {
     long id = -1;
     String text = null;
     User user = null;
     List geo = null;

     reader.beginObject();
     while (reader.hasNext()) {
     String name = reader.nextName();
     if (name.equals("id")) {
     id = reader.nextLong();
     } else if (name.equals("text")) {
     text = reader.nextString();
     } else if (name.equals("geo") && reader.peek() != JsonToken.NULL) {
     geo = readDoublesArray(reader);
     } else if (name.equals("user")) {
     user = readUser(reader);
     } else {
     reader.skipValue();
     }
     }
     reader.endObject();
     return new Message(id, text, user, geo);
     }

     */

}
