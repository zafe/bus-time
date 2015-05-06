package com.zafemos.bustime.Controller;

import android.app.Activity;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


public class Home extends ListActivity {

    private ProgressDialog pDialog;

    // URL to get buses JSON
    private static String url = "http://api.androidhive.info/buses/";

    // JSON Node names
    private static final String TAG_BUSES = "buses";
    private static final String TAG_ORIGIN = "origin";
    private static final String TAG_DESTINY = "destiny";
    private static final String TAG_TIME = "time";

    // buses JSONArray
    JSONArray buses = null;

    // Hashmap for ListView
    ArrayList<HashMap<String, String>> busList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);



        busList = new ArrayList<HashMap<String, String>>();

        ListView lv = getListView();

        // Listview on item click listener
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // getting values from selected ListItem
                String name = ((TextView) view.findViewById(R.id.origin))
                        .getText().toString();
                String cost = ((TextView) view.findViewById(R.id.destiny))
                        .getText().toString();
                String description = ((TextView) view.findViewById(R.id.time))
                        .getText().toString();

                // Starting single contact activity
                Intent in = new Intent(getApplicationContext(),
                        SingleBusActivity.class);
                in.putExtra(TAG_ORIGIN, name);
                in.putExtra(TAG_DESTINY, cost);
                in.putExtra(TAG_TIME, description);
                startActivity(in);

            }
        });

        // Calling async task to get json
        new JSONParser().execute();


    }

    private class JSONParser extends AsyncTask<Void, Void, Void> {



        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(Home.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            // Creating service handler class instance
            ServiceHandler sh = new ServiceHandler();

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url, ServiceHandler.GET);

            Log.d("Response: ", "> " + jsonStr);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node
                    buses = jsonObj.getJSONArray(TAG_BUSES);

                    // looping through All Contacts
                    for (int i = 0; i < buses.length(); i++) {
                        JSONObject c = buses.getJSONObject(i);

                        String origin = c.getString(TAG_ORIGIN);
                        String destiny = c.getString(TAG_DESTINY);
                        String time = c.getString(TAG_TIME);

                        // tmp hashmap for single contact
                        HashMap<String, String> bus = new HashMap<String, String>();

                        // adding each child node to HashMap key => value
                        bus.put(TAG_ORIGIN, origin);
                        bus.put(TAG_DESTINY, destiny);
                        bus.put(TAG_TIME, time);

                        // adding contact to contact list
                        busList.add(bus);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Log.e("ServiceHandler", "Couldn't get any data from the url");
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();
            /**
             * Updating parsed JSON data into ListView
             * */
            ListAdapter adapter = new SimpleAdapter(
                    Home.this, busList,
                    R.layout.list_item, new String[] { TAG_ORIGIN,
                    TAG_DESTINY, TAG_TIME}, new int[] { R.id.origin,
                    R.id.destiny, R.id.time });

            setListAdapter(adapter);
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
