package com.mushroom.cwb1.mushroom2;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

public class MapsActivity extends FragmentActivity {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.

    TextView textView_1;
    EditText edit_1;


    String dataToPut;



    /**
     * create a method bundled with the button of getting message
     * @param view
     */

    /*
    public void getMessage(View view){
        //set your own group number and session ID http://daddi.cs.kuleuven.be/peno3/data/{group number}/{session ID}
        new GetAsyncTask().execute("http://mushroom.16mb.com/select-db.php");
    }*/

    /**
     * create a method bundled with the button of putting message
     * @param view
     */
    public void putMessage(View view){
        textView_1.setText("...");
        dataToPut = edit_1.getText().toString();
        new PutAsyncTask().execute("http://mushroom.16mb.com/android/register_check_username.php");
    }

    /*****************************can be reused in your app*****************************
     * a method to get data from the server
     * @param URL
     * @return
     */

    public String getDataFromServer(String URL){
        String result="";
        try {
            java.net.URL url = new URL(URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));

            String output;
            while ((output = br.readLine()) != null) {
                result += output;
            }
            conn.disconnect();

        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }
        return result;
    };

    /*****************************can be reused in your app*******************************
     * a method to update or add data to the server
     * @param URL
     * @return
     */
/*
    public String putDataToServer(String URL){
        String status="Put the data to server successfully!";
        try {

            URL url = new URL(URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");

            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            //conn.setRequestProperty("Content-Type", "application/json");

            //String input = dataToPut;
            String input = URLEncoder.encode("userName", "UTF-8") + "=" + URLEncoder.encode(dataToPut, "UTF-8");



            OutputStream os = conn.getOutputStream();
            os.write(input.getBytes());
            os.flush();


            //Read the acknowledgement message after putting data to server
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));
            String output;
            System.out.println("Output from Server .... \n");
            while ((output = br.readLine()) != null) {
                System.out.println(output);
            }
            conn.disconnect();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return status;
    }*/




    public ArrayList<String> putDataToServer(String URL){
        ArrayList<String> status =  new ArrayList<>();
        status.add("test");
        try {

            URL url = new URL(URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");

            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            //conn.setRequestProperty("Content-Type", "application/json");

            //String input = dataToPut;
            String input = URLEncoder.encode("userName", "UTF-8") + "=" + URLEncoder.encode(dataToPut, "UTF-8");



            OutputStream os = conn.getOutputStream();
            os.write(input.getBytes());
            os.flush();


            //Read the acknowledgement message after putting data to server
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));
            String output;
            System.out.println("Output from Server .... \n");
            while ((output = br.readLine()) != null) {
                System.out.println(output);
                status.add(output);
            }
            conn.disconnect();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return status;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        setUpMapIfNeeded();

        edit_1 = (EditText) findViewById(R.id.edit_message_1);
        textView_1 = (TextView) findViewById(R.id.show_message_1);

    }


    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p/>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }


    private void setUpMap() {

        PolylineOptions lijn = new PolylineOptions()
                .add(new LatLng(50.8671753, 4.7084025), new LatLng(50.89, 4.72),new LatLng(50.85, 4.72))
                .width(5)
                .color(Color.RED);
        mMap.addPolyline(lijn);

        LatLng midden = new LatLng(50.89, 4.72);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(midden, 12.0f));

    }

    public void drawline(double prev_lat,double prev_long,double curr_lat,double curr_long) {

        PolylineOptions line = new PolylineOptions()
                .add(new LatLng(prev_lat, prev_long), new LatLng(curr_lat, curr_long))
                .width(5)
                .color(Color.BLUE);
        mMap.addPolyline(line);

    }


    /*****************************can be reused in your app*******************************
     * an inner class to call the method getDataFromServer() in an UI thread
     */

    class PutAsyncTask extends AsyncTask<String, Void, ArrayList<String>> {

        @Override
        protected ArrayList<String> doInBackground(String... urls) {
            return putDataToServer(urls[0]);
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(ArrayList<String> result) {
            textView_1.setText(result.get(0)+" - "+result.get(1));


        }
    }


    /*****************************can be reused in your app*******************************
     * an inner class to call the method putDataToServer() in an UI thread
     */
    /*
    class PutAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected ArrayList<String> doInBackground(String... urls) {
            return putDataToServer(urls[0]);
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            textView_1.setText(result);
        }
    }*/





}

