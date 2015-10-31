package com.mushroom.cwb1.mushroom2;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.fitness.data.DataPoint;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class RideActivity extends AppCompatActivity {


    TextView snelheid;
    TextView hoogte;
    TextView tijd;
    TextView breedtegraad;
    TextView lengtegraad;

    TextView punten;

    DataBaseHandler2 handler;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ride);



        handler = new DataBaseHandler2(getApplicationContext());
        handler.onUpgrade(handler.getWritableDatabase(), 0, 0);


        punten = (TextView) findViewById(R.id.punten);


        Toast.makeText(RideActivity.this, "OnCreate", Toast.LENGTH_SHORT).show();

        snelheid = (TextView) findViewById(R.id.snelheid);
        hoogte = (TextView) findViewById(R.id.hoogte);
        tijd = (TextView) findViewById(R.id.tijd);
        breedtegraad = (TextView) findViewById(R.id.breedtegraad);
        lengtegraad = (TextView) findViewById(R.id.lengtegraad);




        // Acquire a reference to the system Location Manager
        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

// Define a listener that responds to location updates
        LocationListener locationListener = new LocationListener() {

            public void onLocationChanged(Location location) {
                // Called when a new location is found by the network location provider.

                Toast.makeText(RideActivity.this, "onLocationChanged", Toast.LENGTH_SHORT).show();

                Date dateUnformatted = new Date(location.getTime());
                SimpleDateFormat sdf;
                sdf = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy", Locale.ENGLISH);
                sdf.setTimeZone(TimeZone.getDefault());
                String date = sdf.format(dateUnformatted);

                float speed = location.getSpeed();
                double altitude = location.getAltitude();

                double latitude = location.getLatitude();
                double longitude = location.getLongitude();

                tijd.setText("Datum : " + date);
                snelheid.setText("Snelheid : " + speed * 3.6);
                hoogte.setText("Hoogte : " + altitude);
                breedtegraad.setText("Breedtegraad : " + latitude);
                lengtegraad.setText("Lengtegraad : " + longitude);


//TODO checken met db
                int current_ride_id = 1;

                float acc_x = 0;
                float acc_y = 0;
                float acc_z = 0;

                float magnf_x = 0;
                float magnf_y = 0;
                float magnf_z = 0;

                dbRow punt = new dbRow(current_ride_id,location.getTime(),acc_x,acc_y,acc_z,speed,longitude,altitude,0f,magnf_x,magnf_y,magnf_z);
                handler.addPoint(punt);

                List<DataPoint> list = handler.getAllDataPoints();
                for (DataPoint point : list) {
                    punten.setText("Punt"+point.getId()+": geeft "+sdf.format(point.getTime())+" en "+point.getLongitude());
                }


            }

            public void onStatusChanged(String provider, int status, Bundle extras) {

                Toast.makeText(RideActivity.this, "onStatusChanged", Toast.LENGTH_SHORT).show();


                /*
 OUT_OF_SERVICE if the provider is out of service,
 and this is not expected to change in the near future;
 TEMPORARILY_UNAVAILABLE if the provider is temporarily unavailable
 but is expected to be available shortly; and
 AVAILABLE if the provider is currently available.
                 */

            }

            public void onProviderEnabled(String provider) {

                Toast.makeText(RideActivity.this, "onProviderEnabled", Toast.LENGTH_SHORT).show();

            }

            public void onProviderDisabled(String provider) {

                Toast.makeText(RideActivity.this, "onProviderDisabled", Toast.LENGTH_SHORT).show();

            }
        };




// Register the listener with the Location Manager to receive location updates
        // Deze requestLocationUpdates() moet bij klik op start opgeroepen worden
        // de twee nullen zijn de frequentie, de eerste is het minimum frequentie en tweede is de min afst, 0 = zo snel mogelijk
        // kan op netwerk locatie zoeken en op GPS of op beiede, voor fiets is GPS het interessantst
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_ride, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
