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
import android.widget.Button;
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
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class RideActivity extends AppCompatActivity implements SensorEventListener {

    private GoogleMap mMap;

    TextView currentSpeed;
    TextView averageSpeed;
    TextView maximumSpeed;
    TextView currentAcceleration;
    TextView averageAcceleration;
    TextView maximumAcceleration;
    TextView elapsedTime;
    TextView distance;


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
    private DecimalFormat df;

    TextView punten;

    DataBaseHandler2 handler;

    Button startrecordingbutton;
    Button pauserecordingbutton;
    Button stoprecordingbutton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ride);

        setUpMapIfNeeded();

        handler = new DataBaseHandler2(getApplicationContext());
        handler.onUpgrade(handler.getWritableDatabase(), 0, 0);


        punten = (TextView) findViewById(R.id.punten);

        currentSpeed = (TextView) findViewById(R.id.currentSpeed);


        snelheid = (TextView) findViewById(R.id.snelheid);
        hoogte = (TextView) findViewById(R.id.hoogte);
        tijd = (TextView) findViewById(R.id.tijd);
        breedtegraad = (TextView) findViewById(R.id.breedtegraad);
        lengtegraad = (TextView) findViewById(R.id.lengtegraad);
        magneticField = (TextView) findViewById(R.id.magneticfield);
        accceleration = (TextView) findViewById(R.id.acceleration);

        startrecordingbutton = (Button) findViewById(R.id.startrecordingbutton);
        pauserecordingbutton = (Button) findViewById(R.id.pauserecordingbutton);
        stoprecordingbutton = (Button) findViewById(R.id.stoprecordingbutton);
        startrecordingbutton.setVisibility(View.VISIBLE);
        pauserecordingbutton.setVisibility(View.INVISIBLE);
        stoprecordingbutton.setVisibility(View.INVISIBLE);


        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);




        sdf = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy", Locale.ENGLISH);
        sdf.setTimeZone(TimeZone.getDefault());

        df = new DecimalFormat("##.00");

    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    public void startrecording(View view){

        //startrecordingbutton = (Button) findViewById(R.id.startrecordingbutton);
        startrecordingbutton.setVisibility(View.INVISIBLE);
        //pauserecordingbutton = (Button) findViewById(R.id.pauserecordingbutton);
        pauserecordingbutton.setVisibility(View.VISIBLE);
        //stoprecordingbutton = (Button) findViewById(R.id.stoprecordingbutton);
        stoprecordingbutton.setVisibility(View.VISIBLE);



        int previous_ride_id = handler.getGreatestRideId();
        current_ride_id = previous_ride_id + 1;






        Sensor mAcceleration;
        Sensor mMagneticfield;

        //TODO sensor updatefrequentie verlagen -> niet mogelijk

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

                float distanceToPrev = 15f;
                long timeToPrev = 1000;


                dbRow punt = new dbRow(current_ride_id,time,accx,accy,accz,speed,longitude,latitude,0f,magnfx,magnfy,magnfz,distanceToPrev,timeToPrev);
                handler.addPoint(punt);

                List<dbRow> list = handler.getAllDataPoints();
                for (dbRow point : list) {
                    punten.setText("Punt "+point.get_id()+" en ride id "+point.getRide_id()+" op "+sdf.format(point.getMillisec())+" coordinaten "+point.getLongitude()+","+point.getLatitude()+" tijd nr vorig punt "+point.getTimetopreviouspoint());
                }


            }

            public void onStatusChanged(String provider, int status, Bundle extras) {

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

    public void pauserecording(View view){

        //startrecordingbutton = (Button) findViewById(R.id.startrecordingbutton);
        startrecordingbutton.setVisibility(View.VISIBLE);
        //pauserecordingbutton = (Button) findViewById(R.id.pauserecordingbutton);
        pauserecordingbutton.setVisibility(View.INVISIBLE);
        //stoprecordingbutton = (Button) findViewById(R.id.stoprecordingbutton);
        stoprecordingbutton.setVisibility(View.VISIBLE);

        mSensorManager.unregisterListener(this);
        locationManager.removeUpdates(locationListener);

        //TODO zoom veranderen zodat hele rit in beeld is



    }

    public void resumerecording(View view){


    }

    public void stoprecording(View view){

        //startrecordingbutton = (Button) findViewById(R.id.startrecordingbutton);
        startrecordingbutton.setVisibility(View.VISIBLE);
        //pauserecordingbutton = (Button) findViewById(R.id.pauserecordingbutton);
        pauserecordingbutton.setVisibility(View.INVISIBLE);
        //stoprecordingbutton = (Button) findViewById(R.id.stoprecordingbutton);
        stoprecordingbutton.setVisibility(View.INVISIBLE);


        mSensorManager.unregisterListener(this);
        locationManager.removeUpdates(locationListener);


        //TODO kiezen tss linked en arraylist
        gpsPoints = new LinkedList<>();

        //TODO route uploaden nr server indien verbinding

        //TODO punten van rit toevoegen

        //TODO zoom veranderen zodat hele rit in beeld is




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
            magneticField.setText("Magnetisch veld is: " + df.format(magnfx) + " x " + magnfy + " y " + magnfz + " z ");
        }

        if(event.sensor.getType()==Sensor.TYPE_ACCELEROMETER) {
            accx = event.values[0];
            accy = event.values[1];
            accz = event.values[2]-10;
            accceleration.setText("Versnelling: " + df.format(accx) + " x " + accy + " y " + accz + " z ");
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



        //TODO fixen laatste punt  uit db

        //dbRow LastPoint = handler.getGreatestValue(handler.COLUMN_TIME);
        //punten.setText("lastpoint voor setup" + LastPoint.toString());

        //dbRow LastPoint = new dbRow(10,22222,1f,1f,1f,0.0f,0.0d,0.0d,0.0f,51.0081564f,4.58057f,0.0f,0f,1000000);
        dbRow LastPoint = new dbRow(10,22222,1f,1f,1f,0.0f,0.0d,0.0d,0.0f,0f,0f,0.0f,0f,1000000);


        if (LastPoint.getLongitude()!= 0.0f) {
            LatLng lastPointLatLng = new LatLng(LastPoint.getLatitude(), LastPoint.getLongitude());
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(lastPointLatLng, 10.0f));
            punten.setText("longitude != 0");
        }
        else {
            LatLng defaultPointLatLng = new LatLng(50.7f,4.21f);
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(defaultPointLatLng, 10.0f));
            //punten.setText("longitude = 0");
        }

    }


}
