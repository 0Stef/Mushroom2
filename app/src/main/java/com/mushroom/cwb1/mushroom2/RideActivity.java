package com.mushroom.cwb1.mushroom2;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.lang.reflect.Array;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class RideActivity extends AppCompatActivity implements SensorEventListener {

    private GoogleMap mMap;

    TextView textCurrentSpeed;
    TextView textAverageSpeed;
    TextView textMaximumSpeed;
    TextView textCurrentAcceleration;
    TextView textAverageAcceleration;
    TextView textMaximumAcceleration;
    TextView textElapsedTime;
    TextView textDistance;
    TextView challengetime;
    TextView Succes;
    TextView currtemp;
    TextView starttemp;

    private Boolean firstLocationSet = false;
    private Location previousLocation;
    float distanceToPrev = 0f;
    long timeToPrev = 0l;
    private long elapsedTime = 0l;
    private float distance = 0f;
    private float maxSpeed = 0f;
    private float maxAcceleration = 0f;
    private float averageSpeed = 0f;
    private float averageAcceleration = 0f;
    private float speed = 0f;
    private float temperature = 0f;

    private boolean eerstekeer = true;
    private long startTime = 0L;

    private android.os.Handler customHandler = new android.os.Handler();

    long timeInMilliseconds = 0L;
    long timeSwapBuff = 0L;
    long updatedTime = 0L;


    private SensorManager mSensorManager;

    private LocationManager locationManager;
    private LocationListener locationListener;


    private int currentRideId;


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

    private SimpleDateFormat dateF;
    private SimpleDateFormat timeF;
    private DecimalFormat decimalF;

    TextView punten;

    DataBaseHandler2 handler;

    Button startrecordingbutton;
    Button resumerecordingbutton;
    Button pauserecordingbutton;
    Button stoprecordingbutton;
    Button challengebutton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ride);

        setUpMapIfNeeded();

        handler = new DataBaseHandler2(getApplicationContext());
        handler.onUpgrade(handler.getWritableDatabase(), 0, 0);


        textCurrentSpeed = (TextView) findViewById(R.id.currentSpeed);
        textAverageSpeed = (TextView) findViewById(R.id.averageSpeed);
        textMaximumSpeed = (TextView) findViewById(R.id.maximumSpeed);
        textCurrentAcceleration = (TextView) findViewById(R.id.currentAcceleration);
        textAverageAcceleration = (TextView) findViewById(R.id.averageAcceleration);
        textMaximumAcceleration = (TextView) findViewById(R.id.maximumAcceleration);
        textDistance = (TextView) findViewById(R.id.distance);
        textElapsedTime = (TextView) findViewById(R.id.elapsedTime);
        Succes = (TextView) findViewById(R.id.succes);
        challengetime = (TextView) findViewById(R.id.challengetime);
        starttemp = (TextView) findViewById(R.id.starttemp);
        currtemp = (TextView) findViewById(R.id.currtemp);
        Succes.setVisibility(View.INVISIBLE);
        challengetime.setVisibility(View.INVISIBLE);
        starttemp.setVisibility(View.INVISIBLE);
        currtemp.setVisibility(View.INVISIBLE);



        startrecordingbutton = (Button) findViewById(R.id.startrecordingbutton);
        resumerecordingbutton = (Button) findViewById(R.id.resumerecordingbutton);
        pauserecordingbutton = (Button) findViewById(R.id.pauserecordingbutton);
        stoprecordingbutton = (Button) findViewById(R.id.stoprecordingbutton);
        challengebutton = (Button) findViewById(R.id.challengebutton);
        startrecordingbutton.setVisibility(View.VISIBLE);
        resumerecordingbutton.setVisibility(View.INVISIBLE);
        pauserecordingbutton.setVisibility(View.INVISIBLE);
        stoprecordingbutton.setVisibility(View.INVISIBLE);
        challengebutton.setVisibility(View.INVISIBLE);


        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);


        elapsedTime = 0;
        distance = 0;

        dateF = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy", Locale.ENGLISH);
        dateF.setTimeZone(TimeZone.getDefault());

        timeF = new SimpleDateFormat("HH:mm:ss", Locale.ENGLISH);
        timeF.setTimeZone(TimeZone.getDefault());

        decimalF = new DecimalFormat("0.0");

    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    public void startrecording(View view) {

        //TODO zet een scherm met wacht op signaal vn gps


        startrecordingbutton.setVisibility(View.INVISIBLE);
        resumerecordingbutton.setVisibility(View.INVISIBLE);
        pauserecordingbutton.setVisibility(View.VISIBLE);
        stoprecordingbutton.setVisibility(View.VISIBLE);
        challengebutton.setVisibility(View.VISIBLE);


        textDistance.setText("wachten op gps signaal");

        final int previousRideId = handler.getGreatestRideID();
        currentRideId = previousRideId + 1;


        Sensor mAcceleration;
        Sensor mMagneticfield;
        Sensor mTemperature;

        if (mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null) {
            mAcceleration = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            mSensorManager.registerListener(this, mAcceleration, 1000000);
        } else {
            //TODO iets verzinnen als sensor niet aanwezig is
        }

        if (mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD) != null) {
            mMagneticfield = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
            mSensorManager.registerListener(this, mMagneticfield, 1000000);
        } else {
        }

        if (mSensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE) != null) {
            mTemperature = mSensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
            mSensorManager.registerListener(this, mTemperature, 1000000);
        } else if (mSensorManager.getDefaultSensor(Sensor.TYPE_TEMPERATURE) != null) {
            mTemperature = mSensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
            mSensorManager.registerListener(this, mTemperature, 1000000);
        }

        // Acquire a reference to the system Location Manager
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        // Define a listener that responds to location updates
        locationListener = new LocationListener() {

            public void onLocationChanged(Location location) {
                // Called when a new location is found by the network location provider.

                if (firstLocationSet) {

                    distanceToPrev = location.distanceTo(previousLocation);
                    timeToPrev = location.getTime() - previousLocation.getTime();

                }

                if (eerstekeer) {
                    startTime = SystemClock.uptimeMillis();
                    customHandler.postDelayed(updateTimerThread, 0);
                    eerstekeer = false;
                }

                time = location.getTime();
                Date dateUnformatted = new Date(time);
                String date = dateF.format(dateUnformatted);

                float msTokmu = 3.6f;
                speed = location.getSpeed() * msTokmu;

                if (maxSpeed < speed) {
                    maxSpeed = speed;
                    textMaximumSpeed.setText(decimalF.format(maxSpeed));
                }
                //double altitude = location.getAltitude();
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();


                if (elapsedTime != 0 || timeToPrev != 0) {
                    averageSpeed = (averageSpeed * elapsedTime + speed * timeToPrev) / (elapsedTime + timeToPrev);
                    averageAcceleration = (averageAcceleration * elapsedTime + accx * timeToPrev) / (elapsedTime + timeToPrev);
                }

                textCurrentSpeed.setText(decimalF.format(speed));
                textAverageSpeed.setText(decimalF.format(averageSpeed));


                if (maxAcceleration < accx) {
                    maxAcceleration = accx;
                    textMaximumAcceleration.setText(decimalF.format(maxAcceleration));
                }

                textCurrentAcceleration.setText(decimalF.format(accx));
                textAverageAcceleration.setText(decimalF.format(averageAcceleration));


                elapsedTime = elapsedTime + timeToPrev;
                distance = distance + distanceToPrev;

                textDistance.setText(decimalF.format(distance));
                textElapsedTime.setText(decimalF.format(elapsedTime / 1000));

                lastPoint = new LatLng(latitude, longitude);

                gpsPoints = route.getPoints();
                gpsPoints.add(lastPoint);
                route.setPoints(gpsPoints);

                //TODO zoom zodat alles in beeld is zie http://stackoverflow.com/questions/5114710/android-setting-zoom-level-in-google-maps-to-include-all-marker-points
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(lastPoint, 16.0f));


                dbRow punt = new dbRow(currentRideId, time, accx, accy, accz, speed, longitude, latitude, 0f, magnfx, magnfy, magnfz, distanceToPrev, timeToPrev);
                handler.addPoint(punt);


                //long voorlopigetijd = handler.getLastEntryRide(currentRideId).getMillisec() - handler.getFirstEntryRide(currentRideId).getMillisec();

                //textDistance.setText(handler.getFirstEntryRide(currentRideId).toString()+"\n "+handler.getLastEntryRide(currentRideId).toString()+"\n"+previousLocation+"\n"+location.toString());

                //textCurrentSpeed.setText(Long.toString(elapsedTime)+"\n"+Long.toString(voorlopigetijd));


                previousLocation = location;
                firstLocationSet = true;


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
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

        //TODO mag weg na testperiode
        //locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);


    }

    public void pauserecording(View view) {


        startrecordingbutton.setVisibility(View.INVISIBLE);
        resumerecordingbutton.setVisibility(View.VISIBLE);
        pauserecordingbutton.setVisibility(View.INVISIBLE);
        stoprecordingbutton.setVisibility(View.VISIBLE);

        mSensorManager.unregisterListener(this);
        locationManager.removeUpdates(locationListener);

        firstLocationSet = false;
  
        //TODO zoom veranderen zodat hele rit in beeld is


    }

    public void resumerecording(View view) {

        startrecordingbutton.setVisibility(View.INVISIBLE);
        resumerecordingbutton.setVisibility(View.INVISIBLE);
        pauserecordingbutton.setVisibility(View.VISIBLE);
        stoprecordingbutton.setVisibility(View.VISIBLE);
        //TODO de metingen worden nog niet opnieuw gestart
    }

    public void stoprecording(View view) {


        startrecordingbutton.setVisibility(View.VISIBLE);
        resumerecordingbutton.setVisibility(View.INVISIBLE);
        pauserecordingbutton.setVisibility(View.INVISIBLE);
        stoprecordingbutton.setVisibility(View.INVISIBLE);
        challengebutton.setVisibility(View.INVISIBLE);


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
    public final void onSensorChanged(SensorEvent event) {


        if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
            magnfx = event.values[0];
            magnfy = event.values[1];
            magnfz = event.values[2];
            //magneticField.setText("Magnetisch veld is: " + decimalF.format(magnfx) + " x " + magnfy + " y " + magnfz + " z ");
        }

        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            accx = event.values[0];
            accy = event.values[1];
            accz = event.values[2] - 10;
            //accceleration.setText("Versnelling: " + decimalF.format(accx) + " x " + accy + " y " + accz + " z ");
        }

        if (event.sensor.getType() == Sensor.TYPE_AMBIENT_TEMPERATURE || event.sensor.getType() == Sensor.TYPE_TEMPERATURE){
            temperature = event.values[0];
        }
    }


    private Runnable updateTimerThread = new Runnable() {

        public void run() {

//            timeInMilliseconds = SystemClock.uptimeMillis() - startTime;
//
//            updatedTime = timeSwapBuff + timeInMilliseconds;
//
//            int secs = (int) (updatedTime / 1000);
//            int mins = secs / 60;
//            secs = secs % 60;
//            int milliseconds = (int) (updatedTime % 1000);
//            textAverageSpeed.setText("" + mins + ":"
//                    + String.format("%02d", secs) + ":"
//                    + String.format("%03d", milliseconds));
//            customHandler.postDelayed(this, 0);
        }

    };

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
        dbRow LastPoint = new dbRow(10, 22222, 1f, 1f, 1f, 0.0f, 0.0d, 0.0d, 0.0f, 0f, 0f, 0.0f, 0f, 1000000);


        if (LastPoint.getLongitude() != 0.0f) {
            LatLng lastPointLatLng = new LatLng(LastPoint.getLatitude(), LastPoint.getLongitude());
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(lastPointLatLng, 10.0f));
            punten.setText("longitude != 0");
        } else {
            LatLng defaultPointLatLng = new LatLng(51.0015, 4.58550);
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(defaultPointLatLng, 10.0f));
            //punten.setText("longitude = 0");
        }

    }


    Long eltime = 0l;

    public void keepSpeed(View view) {
        new Thread(new Runnable() {
            public void run() {
                challengetime.post(new Runnable() {
                    public void run() {
                        challengetime = (TextView) findViewById(R.id.challengetime);
                        challengetime.setVisibility(View.VISIBLE);
                    }
                });
                try {
                    long gestart = SystemClock.uptimeMillis();
                    while (eltime < 30000) {
                        if (speed >= 20) {
                            eltime = (SystemClock.uptimeMillis() - gestart);
                            challengetime.post(new Runnable() {
                                public void run() {
                                    challengetime.setText("Tijd: " + eltime / 1000 + "s");
                                }
                            });
                        } else {
                            eltime = 0l;
                            gestart = SystemClock.uptimeMillis();
                        }
                        Thread.sleep(500);
                    }
                    Succes.post(new Runnable() {
                        public void run() {
                            Succes.setVisibility(View.VISIBLE);
                        }
                    });
                } catch (InterruptedException e) {

                }
            }
        }).start();
    }


    double acct;

    public void keepAcceleration(View view) {
        new Thread(new Runnable() {
            public void run() {
                challengetime.post(new Runnable() {
                    public void run() {
                        challengetime = (TextView) findViewById(R.id.challengetime);
                        challengetime.setVisibility(View.VISIBLE);
                    }
                });
                try {
                    long gestart = SystemClock.uptimeMillis();
                    while (eltime < 5000) {
                        acct = Math.sqrt(Math.pow(accx, 2) + Math.pow(accy, 2) + Math.pow(accz, 2));
                        if (acct >= 3) {
                            eltime = (SystemClock.uptimeMillis() - gestart);
                            challengetime.post(new Runnable() {
                                public void run() {
                                    challengetime.setText("Tijd: " + eltime / 1000 + "s");
                                }
                            });
                        } else {
                            eltime = 0l;
                            gestart = SystemClock.uptimeMillis();
                        }
                        Thread.sleep(500);
                    }
                    Succes.post(new Runnable() {
                        public void run() {
                            Succes.setVisibility(View.VISIBLE);
                        }
                    });
                } catch (InterruptedException e) {

                }
            }
        }).start();
    }

    public void temperatureDifference(View view) {
        new Thread(new Runnable() {
            public void run() {
                final float starttemperatuur = temperature;
                currtemp.post(new Runnable() {
                    public void run() {
                        currtemp.setText("Huidige temperatuur: " + temperature + "°C");
                        currtemp.setVisibility(View.VISIBLE);
                    }
                });
                starttemp.post(new Runnable() {
                    public void run() {
                        starttemp.setText("Starttemperatuur: " + starttemperatuur + "°C");
                        starttemp.setVisibility(View.VISIBLE);
                    }
                });
                try {
                    while (Math.abs(temperature - starttemperatuur) < 1) {
                        currtemp.post(new Runnable() {
                            public void run() {
                                currtemp.setText("Huidige temperatuur: " + temperature + "°C");
                            }
                        });
                        Thread.sleep(5000);
                    }
                }catch (InterruptedException e){

                }
                Succes.post(new Runnable() {
                    public void run() {
                        Succes.setVisibility(View.VISIBLE);
                    }
                });
            }
        }).start();
    }

    public void averageSpeed(View view) {
        new Thread(new Runnable() {
            public void run() {
                try {
                    while (elapsedTime < 5 * 60000 || averageSpeed < 15) {
                        Thread.sleep(1000);
                    }
                }catch (InterruptedException e){

                }
                Succes.post(new Runnable() {
                    public void run() {
                        Succes.setVisibility(View.VISIBLE);
                    }
                });
            }
        }).start();
    }

   // public void driveCircle(View view){
   //        Array
   // }
}

//Loction location = new Location("iets");

//Array[Float] array = new Array[]

//distance = array[0]
//location.distanceBetween();
