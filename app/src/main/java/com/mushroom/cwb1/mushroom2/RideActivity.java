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
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.TimeZone;

public class RideActivity extends AppCompatActivity implements SensorEventListener, AdapterView.OnItemSelectedListener {

    private GoogleMap mMap;

    TextView textCurrentSpeed;
    TextView textAverageSpeed;
//    TextView textMaximumSpeed;
    TextView textElapsedTime;
    TextView textDistance;
    TextView Succes;
    TextView challenge2;
    TextView challenge1;
    TextView uitleg;
    TextView wachten;

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
    private int moeilijkheid;


    private double highest_altitude;
    private double lowest_altitude;

    private boolean temps = false;
    private boolean accs = false;
    private boolean magns = false;
    private boolean orients = false;



    private boolean eerstekeer = true;
    private long startTime = 0L;

    private android.os.Handler customHandler = new android.os.Handler();


    //TODO timer met running weergeven
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
    Button stoprecordingbutton;
    Button challengebutton;

    Spinner moeilijkheidsgraad;

    public String currentUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ride);

        currentUser = ServerConnection.getActiveUser();

        setUpMapIfNeeded();

        handler = new DataBaseHandler2(getApplicationContext(), currentUser);
        //handler.onUpgrade(handler.getWritableDatabase(), 0, 0);




        textCurrentSpeed = (TextView) findViewById(R.id.currentSpeed);
        textAverageSpeed = (TextView) findViewById(R.id.averageSpeed);
//        textMaximumSpeed = (TextView) findViewById(R.id.maximumSpeed);
        textDistance = (TextView) findViewById(R.id.distance);
        textElapsedTime = (TextView) findViewById(R.id.elapsedTime);
        Succes = (TextView) findViewById(R.id.succes);
        challenge1 = (TextView) findViewById(R.id.challenge1);
        challenge2 = (TextView) findViewById(R.id.challenge2);
        uitleg = (TextView) findViewById(R.id.uitleg);
        wachten = (TextView) findViewById(R.id.wachten);
        Succes.setVisibility(View.INVISIBLE);
        challenge1.setVisibility(View.INVISIBLE);
        challenge2.setVisibility(View.INVISIBLE);
        uitleg.setVisibility(View.INVISIBLE);



        startrecordingbutton = (Button) findViewById(R.id.startrecordingbutton);
        stoprecordingbutton = (Button) findViewById(R.id.stoprecordingbutton);
        challengebutton = (Button) findViewById(R.id.challengebutton);
        startrecordingbutton.setVisibility(View.VISIBLE);
        stoprecordingbutton.setVisibility(View.INVISIBLE);
        challengebutton.setVisibility(View.INVISIBLE);

        moeilijkheidsgraad = (Spinner) findViewById(R.id.moeilijkheidsgraad);
        moeilijkheidsgraad.setVisibility(View.INVISIBLE);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.ride_difficulty, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        moeilijkheidsgraad.setAdapter(adapter);
        moeilijkheidsgraad.setOnItemSelectedListener(this);

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
        stoprecordingbutton.setVisibility(View.VISIBLE);


        wachten.setText("wachten op gps signaal");
        wachten.setVisibility(View.VISIBLE);

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
                    moeilijkheidsgraad.setVisibility(View.VISIBLE);
                    wachten.setVisibility(View.INVISIBLE);
                    eerstekeer = false;
                }

                time = location.getTime();
                Date dateUnformatted = new Date(time);
                String date = dateF.format(dateUnformatted);

                float msTokmu = 3.6f;
                speed = location.getSpeed() * msTokmu;

//                if (maxSpeed < speed) {
//                    maxSpeed = speed;
//                    textMaximumSpeed.setText(decimalF.format(maxSpeed));
//                }
                altitude = location.getAltitude();
                latitude = location.getLatitude();
                longitude = location.getLongitude();


                if (elapsedTime != 0 || timeToPrev != 0) {
                    averageSpeed = (averageSpeed * elapsedTime + speed * timeToPrev) / (elapsedTime + timeToPrev);
                    averageAcceleration = (averageAcceleration * elapsedTime + accy * timeToPrev) / (elapsedTime + timeToPrev);
                }

                textCurrentSpeed.setText(decimalF.format(speed));
                textAverageSpeed.setText(decimalF.format(averageSpeed));



                //GPS controle acceleratie
                if (eerstekeer = false){
                    float accGps = (location.getSpeed()+previousLocation.getSpeed())/timeToPrev;
                    System.out.println("gps acc "+Float.toString(accGps)+" accelerometer "+accy);

                }


                elapsedTime = elapsedTime + timeToPrev;
                distance = distance + distanceToPrev;
                Date elapsed;
                elapsed = new Date(elapsedTime/1000);

                textDistance.setText(decimalF.format(distance));
                textElapsedTime.setText(timeF.format(elapsed));

                lastPoint = new LatLng(latitude, longitude);

                gpsPoints = route.getPoints();
                gpsPoints.add(lastPoint);
                route.setPoints(gpsPoints);

                //TODO zoom zodat alles in beeld is zie http://stackoverflow.com/questions/5114710/android-setting-zoom-level-in-google-maps-to-include-all-marker-points
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(lastPoint, 16.0f));




                dbRow punt = new dbRow(currentRideId, time, accx, accy, accz, speed, longitude, latitude, altitude, magnfx, magnfy, magnfz);
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
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);


    }

    public void stoprecording(View view) {


        startrecordingbutton.setVisibility(View.VISIBLE);
        stoprecordingbutton.setVisibility(View.INVISIBLE);
        challengebutton.setVisibility(View.INVISIBLE);


        mSensorManager.unregisterListener(this);
        locationManager.removeUpdates(locationListener);




// userhandler schrijven van de eigenschappen van de user.

        userhandler = new UserHandler(getApplicationContext());
        User user =userhandler.getUserInformation(currentUser);



        // TOTAL DISTANCE
        float total_prev_dist = user.getTotal_distance();
        float current_ride_dist = distance;
        user.setTotal_distance(total_prev_dist + current_ride_dist);

        // HIGHEST SPEED
        float highest_prev_speed = user.getHighest_speed();
        float current_highest_speed = maxSpeed;
        if (current_highest_speed >= highest_prev_speed)
            user.setHighest_speed(maxSpeed);

        // HIGHEST ALTITUDE DIFFERENCE
        highest_altitude = handler.getRow(handler.getGreatestThisRide(handler.COLUMN_GPS_ALT,currentRideId)).getAltitude();
        lowest_altitude = handler.getRow(handler.getLowestThisRide(handler.COLUMN_GPS_ALT,currentRideId)).getAltitude();

        double prev_highest_alt_diff = user.getHighest_altitude_diff();
        double current_highest_alt_diff = highest_altitude - lowest_altitude;
        if (current_highest_alt_diff >= prev_highest_alt_diff)
            user.setHighest_altitude_diff(current_highest_alt_diff);

        // OVERWRITE TO DATABASE
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
                windrichting = getString(R.string.magnetic_north);
            } else if (richting > 45 && richting <= 135) {
                windrichting = getString(R.string.magnetic_east);
            } else if (richting > 135 && richting <= 225) {
                windrichting = getString(R.string.magnetic_south);
            } else if (richting > 225 && richting <= 315) {
                windrichting = getString(R.string.magnetic_west);
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
        dbRow LastPoint = new dbRow(10, 22222, 1f, 1f, 1f, 0.0f, 0.0d, 0.0d, 0.0f, 0f, 0f, 0.0f);


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

    public void keepSpeed(final int moeilijkheidsgraad) {
        final int snelheid;
        if (moeilijkheidsgraad == 1){
            snelheid = 20;
        } else if (moeilijkheidsgraad == 2){
            snelheid = 25;
        }else {
            snelheid = 35;
        }
        uitleg.setText(getString(R.string.challenges_expl_keepspeed1) + " " + snelheid + " " + getString(R.string.challenges_expl_keepspeed2));
        uitleg.setVisibility(View.VISIBLE);

        new Thread(new Runnable() {
            public void run() {
                challenge1.post(new Runnable() {
                    public void run() {
                        challenge1.setText(getString(R.string.challenges_time) + " 0 s");
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
                                    challenge1.setText(getString(R.string.challenges_time) + " " + eltime / 1000 + " s");
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
                    userhandler = new UserHandler(getApplicationContext());
                    User user =userhandler.getUserInformation(currentUser);
                    int points;
                    if (moeilijkheidsgraad == 1){
                        points = 100;
                    } else if (moeilijkheidsgraad == 2){
                        points = 400;
                    }else {
                        points = 1000;
                    }
                    user.setTotal_points(user.getTotal_points() + points);
                    user.setNb_won_challenges(user.getNb_won_challenges() + 1);
                    userhandler.overWrite(user);
                } catch (InterruptedException e) {

                }
            }
        }).start();

    }


    double acct;

    public void keepAcceleration(final int moeilijkheidsgraad) {
        final int versnelling;
        if (moeilijkheidsgraad == 1){
            versnelling = 2;
        } else if (moeilijkheidsgraad == 2) {
            versnelling = 5;
        }else {
            versnelling = 10;
        }
        eltime = 0l;
        uitleg.setText(getString(R.string.challenges_expl_keepacc1) + " " + versnelling + " " + getString(R.string.challenges_expl_keepacc2));
        uitleg.setVisibility(View.VISIBLE);
        challenge2.setText(getString(R.string.challenges_huidige_versnelling) + " " + acct + " m/s²");
        challenge2.setVisibility(View.VISIBLE);
        new Thread(new Runnable() {
            public void run() {
                challenge1.post(new Runnable() {
                    public void run() {
                        challenge1.setText(getString(R.string.challenges_time) + " 0 s");
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
                                    challenge1.setText(getString(R.string.challenges_time) + " " + eltime / 1000 + " s");
                                }
                            });
                        } else {
                            eltime = 0l;
                            gestart = SystemClock.uptimeMillis();
                        }
                        challenge2.post(new Runnable() {
                            public void run() {
                                challenge2.setText(getString(R.string.challenges_huidige_versnelling) + " " + acct + " m/s²");
                            }
                        });
                        Thread.sleep(500);
                    }
                    Succes.post(new Runnable() {
                        public void run() {
                            Succes.setVisibility(View.VISIBLE);
                        }
                    });
                } catch (InterruptedException e) {

                }
                userhandler = new UserHandler(getApplicationContext());
                User user =userhandler.getUserInformation(currentUser);
                int points;
                if (moeilijkheidsgraad == 1){
                    points = 100;
                } else if (moeilijkheidsgraad == 2){
                    points = 300;
                }else {
                    points = 800;
                }
                user.setTotal_points(user.getTotal_points() + points);
                user.setNb_won_challenges(user.getNb_won_challenges() + 1);
                userhandler.overWrite(user);
            }
        }).start();
    }

    public void temperatureDifference(final int moeilijkheidsgraad) {
        final int temperatuurverschil;
        if (moeilijkheidsgraad == 1){
            temperatuurverschil = 1;
        } else if (moeilijkheidsgraad == 2){
            temperatuurverschil = 2;
        }else {
            temperatuurverschil = 4;
        }
        uitleg.setText(getString(R.string.challenges_expl_tempdiff1) + " " + temperatuurverschil + " " + getString(R.string.challenges_unit_temperature));
        uitleg.setVisibility(View.VISIBLE);
        new Thread(new Runnable() {
            public void run() {
                final float starttemperatuur = temperature;
                challenge2.post(new Runnable() {
                    public void run() {
                        challenge2.setText(getString(R.string.challenges_curr_temp) + " " + String.valueOf(temperature) + " " + getString(R.string.challenges_unit_temperature));
                        challenge2.setVisibility(View.VISIBLE);
                    }
                });
                challenge1.post(new Runnable() {
                    public void run() {
                        challenge1.setText(getString(R.string.challenges_start_temp) + " " + String.valueOf(starttemperatuur) + " " + getString(R.string.challenges_unit_temperature));
                        challenge1.setVisibility(View.VISIBLE);
                    }
                });
                try {
                    while (Math.abs(temperature - starttemperatuur) < 1) {
                        challenge2.post(new Runnable() {
                            public void run() {
                                challenge2.setText(getString(R.string.challenges_curr_temp) + " " + String.valueOf(temperature) + " " + getString(R.string.challenges_unit_temperature));
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
                userhandler = new UserHandler(getApplicationContext());
                User user =userhandler.getUserInformation(currentUser);
                int points;
                if (moeilijkheidsgraad == 1){
                    points = 100;
                } else if (moeilijkheidsgraad == 2){
                    points = 300;
                }else {
                    points = 800;
                }
                user.setTotal_points(user.getTotal_points() + points);
                user.setNb_won_challenges(user.getNb_won_challenges() + 1);
                userhandler.overWrite(user);
            }
        }).start();
    }

    public void averageSpeed(final int moeilijkheidsgraad) {
        final int snelheid;
        if (moeilijkheidsgraad == 1){
            snelheid = 15;
        } else if (moeilijkheidsgraad == 2){
            snelheid = 20;
        }else {
            snelheid = 30;
        }
        uitleg.setText(getString(R.string.challenges_expl_averagespeed) + " " + snelheid + " " + getString(R.string.challenges_unit_velocity));
        uitleg.setVisibility(View.VISIBLE);
        new Thread(new Runnable() {
            public void run() {
                try {
                    while (elapsedTime < 5 * 60000 || averageSpeed < snelheid) {
                        Thread.sleep(1000);
                    }
                }catch (InterruptedException e){

                }
                Succes.post(new Runnable() {
                    public void run() {
                        Succes.setVisibility(View.VISIBLE);
                    }
                });
                userhandler = new UserHandler(getApplicationContext());
                User user =userhandler.getUserInformation(currentUser);
                int points;
                if (moeilijkheidsgraad == 1){
                    points = 75;
                } else if (moeilijkheidsgraad == 2){
                    points = 300;
                }else {
                    points = 1000;
                }
                user.setTotal_points(user.getTotal_points() + points);
                user.setNb_won_challenges(user.getNb_won_challenges() + 1);
                userhandler.overWrite(user);
            }
        }).start();
    }

    public void averageAcceleration(final int moeilijkheidsgraad) {
        final int versnelling;
        if (moeilijkheidsgraad == 1){
            versnelling = 1;
        } else if (moeilijkheidsgraad == 2){
            versnelling = 2;
        }else {
            versnelling = 4;
        }
        uitleg.setText(getString(R.string.challenges_expl_averageacc) + " " + versnelling + " " + getString(R.string.challenges_unit_acceleration));
        uitleg.setVisibility(View.VISIBLE);
        challenge1.setText(getString(R.string.challenges_curr_acceleration) + " " + String.valueOf(acct) + " " + getString(R.string.challenges_unit_acceleration));
        challenge1.setVisibility(View.VISIBLE);
        challenge2.setText(getString(R.string.challenges_average_acceleration) + " " + String.valueOf(averageAcceleration) + " " + getString(R.string.challenges_unit_acceleration));
        challenge2.setVisibility(View.VISIBLE);
        new Thread(new Runnable() {
            public void run() {
                try {
                    while (elapsedTime < 5 * 60000 || averageAcceleration < versnelling) {
                        challenge1.post(new Runnable() {
                            public void run() {
                                challenge1.setText(getString(R.string.challenges_curr_acceleration) + " " + String.valueOf(acct) + " " + getString(R.string.challenges_unit_acceleration));
                            }
                        });
                        challenge2.post(new Runnable() {
                            public void run() {
                                challenge2.setText(getString(R.string.challenges_average_acceleration) + " " + String.valueOf(averageAcceleration) + " " + getString(R.string.challenges_unit_acceleration));
                            }
                        });
                        Thread.sleep(1000);
                    }
                }catch (InterruptedException e){

                }
                Succes.post(new Runnable() {
                    public void run() {
                        Succes.setVisibility(View.VISIBLE);
                    }
                });
                userhandler = new UserHandler(getApplicationContext());
                User user =userhandler.getUserInformation(currentUser);
                int points;
                if (moeilijkheidsgraad == 1){
                    points = 50;
                } else if (moeilijkheidsgraad == 2){
                    points = 200;
                }else {
                    points = 500;
                }
                user.setTotal_points(user.getTotal_points() + points);
                user.setNb_won_challenges(user.getNb_won_challenges() + 1);
                userhandler.overWrite(user);
            }
        }).start();
    }

    public void driveCircle(final int moeilijkheidsgraad){
        final int doel;
        if (moeilijkheidsgraad == 1){
            doel = 500;
        } else if (moeilijkheidsgraad == 2){
            doel = 1000;
        }else {
            doel = 3000;
        }
        uitleg.setText(getString(R.string.challenges_expl_drivecircle) + " " + doel/1000 + " " + getString(R.string.challenges_unit_distance));
        uitleg.setVisibility(View.VISIBLE);
        final double startbreedte = latitude;
        final double startlengte = longitude;
        results = new float[1];

        Location.distanceBetween(startbreedte, startlengte, latitude, longitude, results);
        afwijking = results[0];
        final float startafstand = distance;
        afstand = 0f;

        challenge1.setText(getString(R.string.challenges_distance_travelled) + " 0 m");
        challenge1.setVisibility(View.VISIBLE);
        challenge2.setText(getString(R.string.challenges_start_point) + " 0 m");
        challenge2.setVisibility(View.VISIBLE);

        new Thread(new Runnable() {
            public void run() {
                try {
                    while(afwijking > 10 || afstand < doel){
                        Thread.sleep(1000);
                        afstand = distance - startafstand;
                        challenge1.post(new Runnable() {
                            public void run() {
                                challenge1.setText(getString(R.string.challenges_distance_travelled) + " " + afstand + " m");
                            }
                        });
                        Location.distanceBetween(startbreedte, startlengte, latitude, longitude, results);
                        afwijking = results[0];
                        challenge2.post(new Runnable() {
                            public void run() {
                                challenge2.setText(getString(R.string.challenges_start_point) + " " + afwijking + " m");
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
                userhandler = new UserHandler(getApplicationContext());
                User user =userhandler.getUserInformation(currentUser);
                int points;
                if (moeilijkheidsgraad == 1){
                    points = 75;
                } else if (moeilijkheidsgraad == 2){
                    points = 200;
                }else {
                    points = 500;
                }
                user.setTotal_points(user.getTotal_points() + points);
                user.setNb_won_challenges(user.getNb_won_challenges() + 1);
                userhandler.overWrite(user);
            }
        }).start();
    }

    public void getAcceleration(final int moeilijkheidsgraad){
//    public void getAcceleration(View view){
//        int moeilijkheidsgraad = 1;
        final int doel;
        if (moeilijkheidsgraad == 1) {
            doel = 5;
        } else if (moeilijkheidsgraad == 2) {
            doel = 10;
        }else {
            doel = 15;
        }
        uitleg.setText(getString(R.string.challenges_expl_getacc) + " " + doel + " m/s²");
        uitleg.setVisibility(View.VISIBLE);
        challenge1.setText(getString(R.string.challenges_curr_acceleration) + " " + acct + " " + getString(R.string.challenges_unit_acceleration));
        challenge1.setVisibility(View.VISIBLE);
        new Thread(new Runnable() {
            public void run() {
                try {
                    while (acct < doel) {
                        challenge1.post(new Runnable() {
                            public void run() {
                                challenge1.setText(getString(R.string.challenges_curr_acceleration) + " " + acct + " m/s²");
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
                userhandler = new UserHandler(getApplicationContext());
                User user =userhandler.getUserInformation(currentUser);
                int points;
                if (moeilijkheidsgraad == 1){
                    points = 50;
                } else if (moeilijkheidsgraad == 2){
                    points = 150;
                }else {
                    points = 300;
                }
                user.setTotal_points(user.getTotal_points() + points);
                user.setNb_won_challenges(user.getNb_won_challenges() + 1);
                userhandler.overWrite(user);
            }
        }).start();
    }


    public void altitudeDifference(final int moeilijkheidsgraad) {
        final int doel;
        if (moeilijkheidsgraad == 1){
            doel = 10;
        } else if (moeilijkheidsgraad == 2){
            doel = 25;
        }else {
            doel = 50;
        }
        uitleg.setText(getString(R.string.challenges_expl_altitudedifference1) + " " + doel + " " + getString(R.string.challenges_expl_altitudedifference2));
        uitleg.setVisibility(View.VISIBLE);
        new Thread(new Runnable() {

            public void run() {
                final double starthoogte = altitude;
                challenge1.post(new Runnable() {
                    public void run() {
                        challenge1.setText(getString(R.string.challenges_curr_height_diff) + " 0 m");
                        challenge1.setVisibility(View.VISIBLE);
                    }
                });
                try {
                    while (altitude - starthoogte <= doel) {
                        challenge1.post(new Runnable() {
                            public void run() {
                                challenge1.setText(getString(R.string.challenges_curr_height_diff) + " " + (altitude - starthoogte) + " m");
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
                userhandler = new UserHandler(getApplicationContext());
                User user =userhandler.getUserInformation(currentUser);
                int points;
                if (moeilijkheidsgraad == 1){
                    points = 75;
                } else if (moeilijkheidsgraad == 2){
                    points = 200;
                }else {
                    points = 500;
                }
                user.setTotal_points(user.getTotal_points() + points);
                user.setNb_won_challenges(user.getNb_won_challenges() + 1);
                userhandler.overWrite(user);
            }
        }).start();
    }


    public void getSpeed(final int moeilijkheidsgraad){
        final int doel;
        if (moeilijkheidsgraad == 1){
            doel = 20;
        } else if (moeilijkheidsgraad == 2){
            doel = 25;
        }else {
            doel = 35;
        }
        uitleg.setText(getString(R.string.challenges_expl_getspeed) + " " + doel + " km/h");
        uitleg.setVisibility(View.VISIBLE);
        new Thread(new Runnable() {
            public void run() {
                try {
                    while (speed < doel) {
                        Thread.sleep(500);
                    }
                }catch (InterruptedException e){

                }
                Succes.post(new Runnable() {
                    public void run() {
                        Succes.setVisibility(View.VISIBLE);
                    }
                });
                userhandler = new UserHandler(getApplicationContext());
                User user =userhandler.getUserInformation(currentUser);
                int points;
                if (moeilijkheidsgraad == 1){
                    points = 100;
                } else if (moeilijkheidsgraad == 2){
                    points = 200;
                }else {
                    points = 800;
                }
                user.setTotal_points(user.getTotal_points() + points);
                user.setNb_won_challenges(user.getNb_won_challenges() + 1);
                userhandler.overWrite(user);
            }
        }).start();
    }

    public void driveDirection(final int moeilijkheidsgraad){
        final int doel;
        if (moeilijkheidsgraad == 1){
            doel = 1;
        } else if (moeilijkheidsgraad == 2){
            doel = 2;
        }else {
            doel = 4;
        }
        String[] windrichtingen = {getString(R.string.magnetic_north), getString(R.string.magnetic_east), getString(R.string.magnetic_south), getString(R.string.magnetic_west)};
        int idx = r.nextInt(windrichtingen.length);
        zoekrichting = (windrichtingen[idx]);

        uitleg.setText(getString(R.string.challenges_expl_drive) + " " + doel + " " + getString(R.string.challenges_expl_drivedirection) + " " + zoekrichting);
        uitleg.setVisibility(View.VISIBLE);
        new Thread(new Runnable() {
            public void run() {
                afstand = 0f;
                challenge1.post(new Runnable() {
                    public void run() {
                        challenge1.setText(getString(R.string.challenges_winddirection) + " " + windrichting + " " + getString(R.string.challenges_deviation) + " " + richting);
                        challenge1.setVisibility(View.VISIBLE);
                    }
                });
                challenge2.post(new Runnable() {
                    public void run() {
                        challenge2.setText(getString(R.string.challenges_distance_travelled) + " " + afstand + " m");
                        challenge2.setVisibility(View.VISIBLE);
                    }
                });

                try {
                    float startafstand = distance;
                    while (afstand < doel * 1000) {
                        if (windrichting.equals(zoekrichting)){
                            afstand = distance - startafstand;
                        }
                        else {
                            startafstand = distance;
                            afstand = 0f;
                        }
                        challenge1.post(new Runnable() {
                            public void run() {
                                challenge1.setText(getString(R.string.challenges_winddirection) + " " + windrichting + " " + getString(R.string.challenges_deviation) + " " + richting);
                            }
                        });
                        challenge2.post(new Runnable() {
                            public void run() {
                                challenge2.setText(getString(R.string.challenges_distance_travelled) + " " + afstand + " m");
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
                userhandler = new UserHandler(getApplicationContext());
                User user =userhandler.getUserInformation(currentUser);
                int points;
                if (moeilijkheidsgraad == 1){
                    points = 100;
                } else if (moeilijkheidsgraad == 2){
                    points = 300;
                }else {
                    points = 900;
                }
                user.setTotal_points(user.getTotal_points() + points);
                user.setNb_won_challenges(user.getNb_won_challenges() + 1);
                userhandler.overWrite(user);
            }
        }).start();
    }

    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        moeilijkheid = pos + 1;
    }

    public void onNothingSelected(AdapterView<?> parent) {
        moeilijkheid = 1;
    }

    public void challengeButton(View view){
        challengebutton.setVisibility(View.INVISIBLE);
        moeilijkheidsgraad.setVisibility(View.INVISIBLE);
        randomChallenge();
    }

    public void randomChallenge(){
        int challengenr = r.nextInt(10);

        if (challengenr == 0){
            getSpeed(moeilijkheid);
        } else if (challengenr == 1 && accs){
            getAcceleration(moeilijkheid);
        } else if (challengenr == 2){
            keepSpeed(moeilijkheid);
        } else if (challengenr == 3 && accs){
            keepAcceleration(moeilijkheid);
        } else if (challengenr == 4){
            averageSpeed(moeilijkheid);
        } else if (challengenr == 5 && accs){
            averageAcceleration(moeilijkheid);
        } else if (challengenr == 6 && temps){
            temperatureDifference(moeilijkheid);
        } else if (challengenr == 7){
            driveCircle(moeilijkheid);
        } else if (challengenr == 8 && magns && orients){
            driveDirection(moeilijkheid);
        } else if (challengenr == 9){
            altitudeDifference(moeilijkheid);
        } else randomChallenge();
    }
}
