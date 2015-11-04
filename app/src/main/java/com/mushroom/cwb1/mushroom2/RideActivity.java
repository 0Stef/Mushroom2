package com.mushroom.cwb1.mushroom2;

import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
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

import android.support.v4.app.FragmentActivity;

import com.google.android.gms.fitness.data.DataPoint;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class RideActivity extends AppCompatActivity implements SensorEventListener {

    private GoogleMap mMap;


    TextView snelheid;
    TextView hoogte;
    TextView tijd;
    TextView breedtegraad;
    TextView lengtegraad;
    TextView magneticField;
    TextView accceleration;

    private SensorManager mSensorManager;

    private LocationManager locationManager;
    private LocationListener locationListener;


    //TODO uit db laatste ride_id opvragen en met 1ophogen
    private int current_ride_id;


    private long time;
    private float accx;
    private float accy;
    private float accz;
    private float magnfx;
    private float magnfy;
    private float magnfz;

    private LatLng lastPoint;
    private List<LatLng> gpsPoints;
    private Polyline route;

    private SimpleDateFormat sdf;

    TextView punten;

    DataBaseHandler2 handler;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ride);

        setUpMapIfNeeded();

        handler = new DataBaseHandler2(getApplicationContext());
        handler.onUpgrade(handler.getWritableDatabase(), 0, 0);


        punten = (TextView) findViewById(R.id.punten);


        Toast.makeText(RideActivity.this, "OnCreate", Toast.LENGTH_SHORT).show();

        snelheid = (TextView) findViewById(R.id.snelheid);
        hoogte = (TextView) findViewById(R.id.hoogte);
        tijd = (TextView) findViewById(R.id.tijd);
        breedtegraad = (TextView) findViewById(R.id.breedtegraad);
        lengtegraad = (TextView) findViewById(R.id.lengtegraad);
        magneticField = (TextView) findViewById(R.id.magneticfield);
        accceleration = (TextView) findViewById(R.id.acceleration);


        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);




        sdf = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy", Locale.ENGLISH);
        sdf.setTimeZone(TimeZone.getDefault());

    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    public void startrecording(View view){

        Toast.makeText(RideActivity.this, "start", Toast.LENGTH_SHORT).show();

        Sensor mAcceleration;
        Sensor mMagneticfield;

        //TODO sensor updatefrequentie verlagen

        if (mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null){
            mAcceleration = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            mSensorManager.registerListener(this,mAcceleration,1000000);
        }
        else{
            //TODO iets verzinnen als sensor niet aanwezig is
        }

        if (mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD) != null){
            mMagneticfield = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
            mSensorManager.registerListener(this,mMagneticfield,1000000);
        }
        else{
        }

        // Acquire a reference to the system Location Manager
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        // Define a listener that responds to location updates
        locationListener = new LocationListener() {

            public void onLocationChanged(Location location) {
                // Called when a new location is found by the network location provider.


                Toast.makeText(RideActivity.this, "onLocationChanged", Toast.LENGTH_SHORT).show();

                time = location.getTime();
                Date dateUnformatted = new Date(time);
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

                lastPoint = new LatLng(latitude,longitude);

                gpsPoints = route.getPoints();
                gpsPoints.add(lastPoint);
                route.setPoints(gpsPoints);

                //TODO zoom zodat alles in beeld is zie http://stackoverflow.com/questions/5114710/android-setting-zoom-level-in-google-maps-to-include-all-marker-points
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(lastPoint, 20.0f));



                dbRow punt = new dbRow(current_ride_id,time,accx,accy,accz,speed,longitude,latitude,0f,magnfx,magnfy,magnfz);
                handler.addPoint(punt);

                List<dbRow> list = handler.getAllDataPoints();
                for (dbRow point : list) {
                    punten.setText("Punt "+point.get_id()+" en ride id "+point.getRide_id()+" op "+sdf.format(point.getMillisec())+" coordinaten "+point.getLongitude()+","+point.getLatitude()+" snelheid "+point.getVelocity());
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


    public void stoprecording(View view){

        Toast.makeText(RideActivity.this, "stop", Toast.LENGTH_SHORT).show();

        mSensorManager.unregisterListener(this);
        locationManager.removeUpdates(locationListener);

    }


    @Override
    public final void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Do something here if sensor accuracy changes.
    }


    @Override
    public final void onSensorChanged(SensorEvent event){


        if(event.sensor.getType()==Sensor.TYPE_MAGNETIC_FIELD) {
            magnfx = event.values[0];
            magnfy = event.values[1];
            magnfz = event.values[2];
            magneticField.setText("Magnetisch veld is: " + magnfx + " x " + magnfy + " y " + magnfz + " z ");
        }

        if(event.sensor.getType()==Sensor.TYPE_ACCELEROMETER) {
            accx = event.values[0];
            accy = event.values[1];
            accz = event.values[2]-10;
            accceleration.setText("Versnelling: " + accx + " x " + accy + " y " + accz + " z ");
        }

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

        //mPolylineOptions.width(5).color(Color.BLUE);
        //route = mMap.addPolyline(mPolylineOptions);

        PolylineOptions mPolylineOptions = new PolylineOptions();
        mPolylineOptions.width(5).color(Color.BLUE);
        route = mMap.addPolyline(mPolylineOptions);


        //TODO HAAL LAATSTE PUNT UIT DB
        LatLng lastPointVisited = new LatLng(50.8671062,4.708445);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(lastPointVisited, 10.0f));

    }


}
