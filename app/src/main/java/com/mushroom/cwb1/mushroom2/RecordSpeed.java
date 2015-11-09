package com.mushroom.cwb1.mushroom2;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class RecordSpeed extends AppCompatActivity {
    TextView snelheid;
    TextView succes;
    private LocationManager locationManager;
    private LocationListener locationListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_speed);

        succes = (TextView) findViewById(R.id.succes);
        snelheid = (TextView) findViewById(R.id.snelheid);
        succes.setVisibility(View.INVISIBLE);

    }


    public void clickStart(View view){
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {


            public void onLocationChanged(Location location) {
                float speed = location.getSpeed();
                double speedkmh = speed*3.6;
                snelheid.setText("Snelheid : " + speedkmh);
                if (speedkmh >= 20){
                    succes.setVisibility(View.VISIBLE);
                    locationManager.removeUpdates(locationListener);
                }
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {

                Toast.makeText(RecordSpeed.this, "onStatusChanged", Toast.LENGTH_SHORT).show();
            }

            public void onProviderEnabled(String provider) {

                Toast.makeText(RecordSpeed.this, "onProviderEnabled", Toast.LENGTH_SHORT).show();

            }

            public void onProviderDisabled(String provider) {

                Toast.makeText(RecordSpeed.this, "onProviderDisabled", Toast.LENGTH_SHORT).show();

            }
        };
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
    }


    public void clickStop(View view){
        locationManager.removeUpdates(locationListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_record_speed, menu);
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
