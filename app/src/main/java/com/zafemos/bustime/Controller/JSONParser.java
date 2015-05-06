    package com.zafemos.bustime.Controller;

        import java.util.ArrayList;
        import java.util.HashMap;

        import org.json.JSONArray;
        import org.json.JSONException;
        import org.json.JSONObject;

        import android.app.ListActivity;
        import android.app.ProgressDialog;
        import android.content.Context;
        import android.os.AsyncTask;
        import android.util.Log;
        import android.widget.ListAdapter;
        import android.widget.SimpleAdapter;

    /**
     * Async task class to get json by making HTTP call
     * */
    public class JSONParser extends AsyncTask<Void, Void, Void> {



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

