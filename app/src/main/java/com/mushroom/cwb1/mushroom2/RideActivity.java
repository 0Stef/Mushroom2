package com.mushroom.cwb1.mushroom2;

import android.content.Context;
import android.content.res.Configuration;
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
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Random;
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
    TextView Succes;
    TextView challenge2;
    TextView challenge1;
    TextView uitleg;

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
    private double latitude;
    private double longitude;
    private double altitude;
    private float afstand = 0f;
    private float afwijking = 0f;
    private float[] results;
    private float richting;
    private String windrichting;
    private String zoekrichting;
    private Random r = new Random();

    private boolean temps = false;
    private boolean accs = false;
    private boolean magns = false;
    private boolean orients = false;



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
    UserHandler userhandler;

    Button startrecordingbutton;
    Button resumerecordingbutton;
    Button pauserecordingbutton;
    Button stoprecordingbutton;
    Button challengebutton;

    public String currentUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ride);

        currentUser = getIntent().getStringExtra("username");

        setUpMapIfNeeded();

        handler = new DataBaseHandler2(getApplicationContext());
        //handler.onUpgrade(handler.getWritableDatabase(), 0, 0);







        textCurrentSpeed = (TextView) findViewById(R.id.currentSpeed);
        textAverageSpeed = (TextView) findViewById(R.id.averageSpeed);
        textMaximumSpeed = (TextView) findViewById(R.id.maximumSpeed);
        textCurrentAcceleration = (TextView) findViewById(R.id.currentAcceleration);
        textAverageAcceleration = (TextView) findViewById(R.id.averageAcceleration);
        textMaximumAcceleration = (TextView) findViewById(R.id.maximumAcceleration);
        textDistance = (TextView) findViewById(R.id.distance);
        textElapsedTime = (TextView) findViewById(R.id.elapsedTime);
        Succes = (TextView) findViewById(R.id.succes);
        challenge1 = (TextView) findViewById(R.id.challenge1);
        challenge2 = (TextView) findViewById(R.id.challenge2);
        uitleg = (TextView) findViewById(R.id.uitleg);
        Succes.setVisibility(View.INVISIBLE);
        challenge1.setVisibility(View.INVISIBLE);
        challenge2.setVisibility(View.INVISIBLE);
        uitleg.setVisibility(View.INVISIBLE);



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

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(null);
    }

    public void startrecording(View view) {

        //TODO zet een scherm met wacht op signaal vn gps


        startrecordingbutton.setVisibility(View.INVISIBLE);
        resumerecordingbutton.setVisibility(View.INVISIBLE);
        pauserecordingbutton.setVisibility(View.VISIBLE);
        stoprecordingbutton.setVisibility(View.VISIBLE);


        textDistance.setText("wachten op gps signaal");

        final int previousRideId = handler.getGreatestRideID();
        currentRideId = previousRideId + 1;


        Sensor mAcceleration;
        Sensor mMagneticfield;
        Sensor mTemperature;
        Sensor mOrientation;

        if (mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null) {
            mAcceleration = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            mSensorManager.registerListener(this, mAcceleration, 1000000);
            accs = true;
        } else {
            //TODO iets verzinnen als sensor niet aanwezig is
        }

        if (mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD) != null) {
            mMagneticfield = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
            mSensorManager.registerListener(this, mMagneticfield, 1000000);
            magns = true;
        } else {
        }

        if (mSensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE) != null) {
            mTemperature = mSensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
            mSensorManager.registerListener(this, mTemperature, 1000000);
            temps = true;
        } else if (mSensorManager.getDefaultSensor(Sensor.TYPE_TEMPERATURE) != null) {
            mTemperature = mSensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
            mSensorManager.registerListener(this, mTemperature, 1000000);
            temps = true;
        }

        if (mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION) != null) {
            mOrientation = mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
            mSensorManager.registerListener(this, mOrientation, 1000000);
            orients = true;
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
                    challengebutton.setVisibility(View.VISIBLE);
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
                latitude = location.getLatitude();
                longitude = location.getLongitude();


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




        userhandler = new UserHandler(getApplicationContext());
        User user =userhandler.getUserInformation(currentUser);

        float total_prev_dist = user.getTotal_distance();
        float current_ride_dist = distance;

        user.setTotal_distance(total_prev_dist + current_ride_dist);
        user.setHighest_speed(maxSpeed);

        userhandler.overWrite(user);





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
            acct = Math.sqrt(Math.pow(accx, 2) + Math.pow(accy, 2) + Math.pow(accz, 2));
            //accceleration.setText("Versnelling: " + decimalF.format(accx) + " x " + accy + " y " + accz + " z ");
        }

        if (event.sensor.getType() == Sensor.TYPE_AMBIENT_TEMPERATURE || event.sensor.getType() == Sensor.TYPE_TEMPERATURE){
            temperature = event.values[0];
        }

        if (event.sensor.getType() == Sensor.TYPE_ORIENTATION) {
            richting = event.values[0];
            if (richting <= 45 || richting > 315) {
                windrichting = "Noorden ";
            } else if (richting > 45 && richting <= 135) {
                windrichting = "Oosten ";
            } else if (richting > 135 && richting <= 225) {
                windrichting = "Zuiden ";
            } else if (richting > 225 && richting <= 315) {
                windrichting = "Westen ";
            }
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

    public void keepSpeed(int moeilijkheidsgraad) {
        final int snelheid;
        if (moeilijkheidsgraad == 1){
            snelheid = 20;
        } else if (moeilijkheidsgraad == 2){
            snelheid = 25;
        }else {
            snelheid = 35;
        }
        uitleg.setText("Hou 30 seconden een snelheid van " + snelheid + " km/u aan");
        uitleg.setVisibility(View.VISIBLE);

        new Thread(new Runnable() {
            public void run() {
                challenge1.post(new Runnable() {
                    public void run() {
                        challenge1.setText("Tijd: 0s");
                        challenge1.setVisibility(View.VISIBLE);
                    }
                });
                try {
                    long gestart = SystemClock.uptimeMillis();
                    while (eltime < 30000) {
                        if (speed >= snelheid) {
                            eltime = (SystemClock.uptimeMillis() - gestart);
                            challenge1.post(new Runnable() {
                                public void run() {
                                    challenge1.setText("Tijd: " + eltime / 1000 + "s");
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

    public void keepAcceleration(int moeilijkheidsgraad) {
        final int versnelling;
        if (moeilijkheidsgraad == 1){
            versnelling = 2;
        } else if (moeilijkheidsgraad == 2) {
            versnelling = 3;
        }else {
            versnelling = 5;
        }
        uitleg.setText("Hou 5 seconden een versnelling van " + versnelling + " m/s² aan");
        uitleg.setVisibility(View.VISIBLE);
        new Thread(new Runnable() {
            public void run() {
                challenge1.post(new Runnable() {
                    public void run() {
                        challenge1.setText("Tijd: 0s");
                        challenge1.setVisibility(View.VISIBLE);
                    }
                });
                try {
                    long gestart = SystemClock.uptimeMillis();
                    while (eltime < 5000) {
                        if (acct >= versnelling) {
                            eltime = (SystemClock.uptimeMillis() - gestart);
                            challenge1.post(new Runnable() {
                                public void run() {
                                    challenge1.setText("Tijd: " + eltime / 1000 + "s");
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

    public void temperatureDifference(int moeilijkheidsgraad) {
        final int temperatuurverschil;
        if (moeilijkheidsgraad == 1){
            temperatuurverschil = 1;
        } else if (moeilijkheidsgraad == 2){
            temperatuurverschil = 2;
        }else {
            temperatuurverschil = 4;
        }
        uitleg.setText("Vind een plaats met een temperatuurverschil van " + temperatuurverschil + " °C");
        uitleg.setVisibility(View.VISIBLE);
        new Thread(new Runnable() {
            public void run() {
                final float starttemperatuur = temperature;
                challenge2.post(new Runnable() {
                    public void run() {
                        challenge2.setText("Huidige temperatuur: " + temperature + "°C");
                        challenge2.setVisibility(View.VISIBLE);
                    }
                });
                challenge1.post(new Runnable() {
                    public void run() {
                        challenge1.setText("Starttemperatuur: " + starttemperatuur + "°C");
                        challenge1.setVisibility(View.VISIBLE);
                    }
                });
                try {
                    while (Math.abs(temperature - starttemperatuur) < 1) {
                        challenge2.post(new Runnable() {
                            public void run() {
                                challenge2.setText("Huidige temperatuur: " + temperature + "°C");
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

    public void averageSpeed() {
        uitleg.setText("Haal deze rit (minstens 5 minuten) een gemiddelde snelheid van 15 km/u");
        uitleg.setVisibility(View.VISIBLE);
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

    public void averageAcceleration() {
        uitleg.setText("Haal deze rit (minstens 5 minuten) een gemiddelde versnelling van 1 m/s²");
        uitleg.setVisibility(View.VISIBLE);
        new Thread(new Runnable() {
            public void run() {
                try {
                    while (elapsedTime < 5 * 60000 || averageAcceleration < 15) {
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

    public void driveCircle(){
        uitleg.setText("Rij een rondje van minstens 500 m");
        uitleg.setVisibility(View.VISIBLE);
        final double startbreedte = latitude;
        final double startlengte = longitude;
        results = new float[1];

        Location.distanceBetween(startbreedte, startlengte, latitude, longitude, results);
        afwijking = results[0];
        final float startafstand = distance;
        afstand = 0f;

        challenge1.setText("Afgelegde weg: 0m");
        challenge1.setVisibility(View.VISIBLE);
        challenge2.setText("Afstand tot vertrekpunt: 0m");
        challenge2.setVisibility(View.VISIBLE);

        new Thread(new Runnable() {
            public void run() {
                try {
                    while(afwijking > 10 || afstand < 500){
                        Thread.sleep(1000);
                        afstand = distance - startafstand;
                        challenge1.post(new Runnable() {
                            public void run() {
                                challenge1.setText("Afgelegde weg: " + afstand + "m");
                            }
                        });
                        Location.distanceBetween(startbreedte, startlengte, latitude, longitude, results);
                        afwijking = results[0];
                        challenge2.post(new Runnable() {
                            public void run() {
                                challenge2.setText("Afstand tot vertrekpunt: " + afwijking + "m");
                            }
                        });
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

    public void getAcceleration(){
        uitleg.setText("Haal een versnelling van 5 m/s²");
        uitleg.setVisibility(View.VISIBLE);
        new Thread(new Runnable() {
            public void run() {
                try {
                    while (acct < 5) {
                        Thread.sleep(500);
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


    public void altitudeDifferenceEasy() {
        uitleg.setText("Maak een klim van 10 m hoog");
        uitleg.setVisibility(View.VISIBLE);
        new Thread(new Runnable() {
            public void run() {
                final double starthoogte = altitude;
                challenge2.post(new Runnable() {
                    public void run() {
                        challenge2.setText("Huidige hoogte" + starthoogte + "m");
                        challenge2.setVisibility(View.VISIBLE);
                    }
                });
                challenge1.post(new Runnable() {
                    public void run() {
                        challenge1.setText("Starthoogte" + starthoogte + "m");
                        challenge1.setVisibility(View.VISIBLE);
                    }
                });
                try {
                    while ((altitude - starthoogte) <= 10) {
                        challenge2.post(new Runnable() {
                            public void run() {
                                challenge2.setText("Huidige hoogte" + altitude + "m");
                            }
                        });
                        Thread.sleep(2500);
                    }
                } catch (InterruptedException e) {

                }
                Succes.post(new Runnable() {
                    public void run() {
                        Succes.setVisibility(View.VISIBLE);
                    }
                });
            }
        }).start();
    }


    public void getSpeed(){
        uitleg.setText("Haal een snelheid van 20 km/u");
        uitleg.setVisibility(View.VISIBLE);
        new Thread(new Runnable() {
            public void run() {
                try {
                    while (speed < 20) {
                        Thread.sleep(500);
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

    public void driveDirection(){
        String[] windrichtingen = {"Noorden ", "Oosten ", "Zuiden ", "Westen "};
        int idx = r.nextInt(windrichtingen.length);
        zoekrichting = (windrichtingen[idx]);

        uitleg.setText("Rij 1 km naar het " + zoekrichting);
        uitleg.setVisibility(View.VISIBLE);
        new Thread(new Runnable() {
            public void run() {
                afstand = 0f;
                challenge1.post(new Runnable() {
                    public void run() {
                        challenge1.setText("Windrichting: " + windrichting + "Afwijking: " + richting);
                        challenge1.setVisibility(View.VISIBLE);
                    }
                });
                challenge2.post(new Runnable() {
                    public void run() {
                        challenge2.setText("Afstand: " + afstand + "m");
                        challenge2.setVisibility(View.VISIBLE);
                    }
                });

                try {
                    float startafstand = distance;
                    while (afstand < 1000) {
                        if (windrichting.equals(zoekrichting)){
                            afstand = distance - startafstand;
                        }
                        else {
                            startafstand = distance;
                        }
                        challenge1.post(new Runnable() {
                            public void run() {
                                challenge1.setText("Windrichting: " + windrichting + " Afwijking: " + richting);
                            }
                        });
                        challenge2.post(new Runnable() {
                            public void run() {
                                challenge2.setText("Afstand: " + afstand + "m");
                            }
                        });
                        Thread.sleep(500);
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

    public void randomChallenge(View view){
        challengebutton.setVisibility(View.INVISIBLE);

        int challengenr = r.nextInt(10);

        if (challengenr == 1){
            getSpeed();
        } else if (challengenr == 2 && accs){
            getAcceleration();
        } else if (challengenr == 3){
            keepSpeed();
        } else if (challengenr == 4 && accs){
            keepAcceleration();
        } else if (challengenr == 5){
            averageSpeed();
        } else if (challengenr == 6 && accs){
            averageAcceleration();
        } else if (challengenr == 7 && temps){
            temperatureDifference();
        } else if (challengenr == 8){
            driveCircle();
        } else if (challengenr == 9 && magns && orients){
            driveDirection();
        } else if (challengenr == 10){
            altitudeDifferenceEasy();
        }
    }
}
