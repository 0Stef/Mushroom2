package com.mushroom.cwb1.mushroom2;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Polyline;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

public class Persoonlijke_statistieken extends AppCompatActivity {

    private String currentUser;
    private int nbRide;
    private GoogleMap mMap;
    private Polyline route;
    private LinkedList list;
    private int rIntTotalDistance;
    private float rFlHighestVelocity;
    private float rFlHighestAcceleration;
    private double rDbHighestAltitude;
    private long rLgTotatTime;
    private long rLgFirstTime;
    private DataBaseHandler2 handler;
    private float rFlAverageVelocity;


    public String formattedTotalTime;
    private SimpleDateFormat dateF;
    private DecimalFormat decimalF;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_persoonlijke_statistieken);

        currentUser = ServerConnection.getActiveUser();


        dateF = new SimpleDateFormat("EEEE dd MMMM yyyy  HH:mm");
        dateF.setTimeZone(TimeZone.getDefault());

        decimalF = new DecimalFormat("0.00");


        //Global statistics
        UserHandler uHandler = new UserHandler(getApplicationContext());
        User user = uHandler.getUserInformation(currentUser);

        TextView TotalDistance = (TextView) findViewById(R.id.TotalDistance);
        TotalDistance.setText(Float.toString(user.getTotal_distance()/1000f)+" km");

        TextView HighestVelocity = (TextView) findViewById(R.id.HighestVelocity);
        HighestVelocity.setText(Float.toString(user.getHighest_speed())+" km/h");

        //TextView HighestAcceleration = (TextView) findViewById(R.id.HighestAcceleration);
        //HighestAcceleration.setText(Float.toString(user.getHighest_acceleration())+" m/s²");

        TextView HighestAltitudeDiff = (TextView) findViewById(R.id.HighestAltitudeDiff);
        HighestAltitudeDiff.setText(Double.toString(user.getHighest_altitude_diff()) + " m");

        //TextView TotalTime = (TextView) findViewById(R.id.TotalTime);


       /*long millis = user.getTotal_time();
        System.out.println("user.getTotal_time()" + Double.toString(user.getTotal_time()) + millis);
        String globalTotalTime = String.format("%02d:%02d:%02d",
                TimeUnit.MILLISECONDS.toHours(millis),
                TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
                TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
        TotalTime.setText(globalTotalTime);*/

        //TotalTime.setText(Double.toString(user.getTotal_time()/3600000f)+" h");

        TextView TotalPoints = (TextView) findViewById(R.id.TotalPoints);
        TotalPoints.setText(Integer.toString(user.getTotal_points()));

        TextView BikedDays= (TextView) findViewById(R.id.BikedDays);
        BikedDays.setText(Integer.toString(user.getNb_days_biked()));

        handler = new DataBaseHandler2(getApplicationContext(), currentUser);
        nbRide = handler.getGreatestRideID();
        CreateRideStatistics(nbRide);

        Button PreviousRide = (Button)findViewById(R.id.PreviousRide);

        PreviousRide.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        if (nbRide > 0) {
                            nbRide -= 1;
                        }
                        CreateRideStatistics(nbRide);
                    }
                }
        );

        Button NewerRide = (Button)findViewById(R.id.NewerRide);

        NewerRide.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        if (nbRide < handler.getGreatestRideID()) {
                            nbRide += 1;
                        }
                        CreateRideStatistics(nbRide);
                    }
                }
        );

        Button RouteMapping = (Button)findViewById(R.id.RouteMapping);

        RouteMapping.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        Intent i = new Intent(getApplicationContext(), RouteMapping.class);
                        i.putExtra("username", currentUser);
                        i.putExtra("nbRide", nbRide);
                        startActivity(i);
                    }
                }
        );

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_persoonlijke_statistieken, menu);
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

    public void CreateRideStatistics(int nbRideLocal) {
        //Last ride statistics
        list = handler.getList(handler.getAllThisRide(nbRideLocal));
        ArrayList<Integer> distanceList = handler.getDistanceList(handler.getAllThisRide(nbRideLocal));

        ArrayList<String> xVal = new ArrayList<String>();
        ArrayList<Entry> yVelocity = new ArrayList<Entry>();
        ArrayList<Entry> yDistance = new ArrayList<Entry>();
        ArrayList<Entry> yAccelerometerX = new ArrayList<Entry>();
        ArrayList<Entry> yAccelerometerY = new ArrayList<Entry>();
        ArrayList<Entry> yAccelerometerZ = new ArrayList<Entry>();
        ArrayList<Entry> yAltitude = new ArrayList<Entry>();

        rIntTotalDistance = 0;
        rFlHighestVelocity = 0;
        rFlHighestAcceleration = 0;
        rDbHighestAltitude = 0;
        rLgTotatTime = 0;
        rLgFirstTime = 0;
        rFlAverageVelocity = 0;

        if (list.size() != 0){
            dbRow FirstRow = (dbRow) list.get(0);
            dbRow LastRow = (dbRow) list.get(list.size() - 1);
            rLgTotatTime = LastRow.getMillisec() - FirstRow.getMillisec();
            long millis = rLgTotatTime;
            formattedTotalTime = String.format("%02d:%02d:%02d",
                    TimeUnit.MILLISECONDS.toHours(millis),
                    TimeUnit.MILLISECONDS.toMinutes(millis) -
                            TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
                    TimeUnit.MILLISECONDS.toSeconds(millis) -
                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
            rIntTotalDistance = distanceList.get(distanceList.size() - 1);
            rLgFirstTime = FirstRow.getMillisec();

            for (int index = 0; index < list.size(); index++) {
                dbRow row = (dbRow) list.get(index);

                //Genaral Ride Data
                if (row.getVelocity() > rFlHighestVelocity) {
                    rFlHighestVelocity = row.getVelocity();
                }
                if (row.getAltitude() > rDbHighestAltitude) {
                    rDbHighestAltitude = row.getAltitude();
                }

                if (row.getAccelerometer_xValue() > rFlHighestAcceleration) {
                    rFlHighestAcceleration = row.getAccelerometer_xValue();
                }

                rFlAverageVelocity += row.getVelocity();

                //Chart Data
                xVal.add("" + index);
                yVelocity.add(new Entry(row.getVelocity(), index));
                yDistance.add(new Entry(distanceList.get(index), index));
                yAccelerometerX.add(new Entry(row.getAccelerometer_xValue(), index));
                yAccelerometerY.add(new Entry(row.getAccelerometer_yValue(), index));
                yAccelerometerZ.add(new Entry(row.getAccelerometer_zValue(), index));
                yAltitude.add(new Entry((float) row.getAltitude(), index));

            }

            rFlAverageVelocity /= (float) list.size();
        }

        TextView rTotalDistance = (TextView) findViewById(R.id.rTotalDistance);
        if (rIntTotalDistance>1000){
            rTotalDistance.setText(decimalF.format(rIntTotalDistance/1000f)+" km");
        }else{
            rTotalDistance.setText(rIntTotalDistance+" m");
        }

        TextView rHighestVelocity = (TextView) findViewById(R.id.rHighestVelocity);
        rHighestVelocity.setText(decimalF.format(rFlHighestVelocity)+" km/h");

        TextView rAvarageVelocity = (TextView) findViewById(R.id.rAvarageVelocity);
        rAvarageVelocity.setText(decimalF.format(rFlAverageVelocity)+" km/h");

        TextView rHighestAcceleration = (TextView) findViewById(R.id.rHighestAcceleration);
        rHighestAcceleration.setText(decimalF.format(rFlHighestAcceleration)+" m/s²");

        TextView rHighestAltitudeDiff = (TextView) findViewById(R.id.rHighestAltitudeDiff);
        rHighestAltitudeDiff.setText(decimalF.format(rDbHighestAltitude) + " m");

        TextView rTotalTime = (TextView) findViewById(R.id.rTotalTime);
        rTotalTime.setText(formattedTotalTime);

        TextView rFirstTime = (TextView) findViewById(R.id.rDate);

        rFirstTime.setText(dateF.format(rLgFirstTime));


        // Velocity chart
        LineChart chVelocity = (LineChart) findViewById(R.id.chVelocity);
        chVelocity.setDescription("");
        chVelocity.setDrawGridBackground(false);
        chVelocity.setNoDataText(getString(R.string.statistics_not_found_velocity));
        Legend l = chVelocity.getLegend();
        l.setEnabled(false);
        YAxis y12 = chVelocity.getAxisRight();
        y12.setEnabled(false);
        XAxis x1 = chVelocity.getXAxis();
        x1.setDrawGridLines(false);
        x1.setPosition(XAxis.XAxisPosition.BOTTOM);
        YAxis y1 = chVelocity.getAxisLeft();
        y1.setDrawGridLines(false);

        LineDataSet setVelocity = new LineDataSet(yVelocity, "");
        setVelocity.setDrawCubic(true);
        setVelocity.setDrawFilled(true);
        setVelocity.setDrawValues(false);
        setVelocity.setDrawCircles(false);
        setVelocity.setColor(Color.rgb(255, 100, 0));
        setVelocity.setFillColor(Color.rgb(255, 191, 106));
        setVelocity.setCircleColor(Color.rgb(255, 100, 0));
        LineData dataVelocity = new LineData(xVal, setVelocity);
        chVelocity.setData(dataVelocity);
        chVelocity.invalidate();
        chVelocity.animateY(3000);

//        // Distance chart
//        LineChart chDistance = (LineChart) findViewById(R.id.chDistance);
//        chDistance.setDescription("");
//        chDistance.setDrawGridBackground(false);
//        chDistance.setNoDataText(getString(R.string.statistics_not_found_distance));
//        Legend lDistance = chDistance.getLegend();
//        lDistance.setEnabled(false);
//        YAxis y12Distance = chDistance.getAxisRight();
//        y12Distance.setEnabled(false);
//        XAxis x1Distance = chDistance.getXAxis();
//        x1Distance.setDrawGridLines(false);
//        x1Distance.setPosition(XAxis.XAxisPosition.BOTTOM);
//        YAxis y1Distance = chDistance.getAxisLeft();
//        y1Distance.setDrawGridLines(false);
//
//        LineDataSet setDistance = new LineDataSet(yDistance, "");
//        setDistance.setDrawCubic(true);
//        setDistance.setDrawFilled(true);
//        setDistance.setDrawValues(false);
//        setDistance.setDrawCircles(false);
//        setDistance.setColor(Color.rgb(255, 100, 0));
//        setDistance.setFillColor(Color.rgb(255, 191, 106));
//        setDistance.setCircleColor(Color.rgb(255, 100, 0));
//        LineData dataDistance = new LineData(xVal, setDistance);
//        chDistance.setData(dataDistance);
//        chDistance.invalidate();
//        chDistance.animateY(3000);

        // Altitude chart
        LineChart chAltitude = (LineChart) findViewById(R.id.chAltitude);
        chAltitude.setDescription("");
        chAltitude.setDrawGridBackground(false);
        chAltitude.setNoDataText(getString(R.string.statistics_not_found_altitude));
        Legend lAltitude = chAltitude.getLegend();
        lAltitude.setEnabled(false);
        YAxis y12Altitude = chAltitude.getAxisRight();
        y12Altitude.setEnabled(false);
        XAxis x1Altitude= chAltitude.getXAxis();
        x1Altitude.setDrawGridLines(false);
        x1Altitude.setPosition(XAxis.XAxisPosition.BOTTOM);
        YAxis y1Altitude = chAltitude.getAxisLeft();
        y1Altitude.setDrawGridLines(false);

        LineDataSet setAltitude = new LineDataSet(yAltitude, "");
        setAltitude.setDrawCubic(true);
        setAltitude.setDrawFilled(true);
        setAltitude.setDrawValues(false);
        setAltitude.setDrawCircles(false);
        setAltitude.setColor(Color.rgb(255, 100, 0));
        setAltitude.setFillColor(Color.rgb(255, 191, 106));
        setAltitude.setCircleColor(Color.rgb(255, 100, 0));
        LineData dataAltitude = new LineData(xVal, setAltitude);
        chAltitude.setData(dataAltitude);
        chAltitude.invalidate();
        chAltitude.animateY(3000);

        // AccelerometerX
        LineChart chAccelerometerX = (LineChart) findViewById(R.id.chAccelerometerX);
        chAccelerometerX.setDescription("");
        chAccelerometerX.setDrawGridBackground(false);
        chAccelerometerX.setNoDataText(getString(R.string.statistics_not_found_accelerometer));
        Legend lAccelerometerX = chAccelerometerX.getLegend();
        lAccelerometerX.setEnabled(false);
        YAxis y12AccelerometerX = chAccelerometerX.getAxisRight();
        y12AccelerometerX.setEnabled(false);
        XAxis x1AccelerometerX = chAccelerometerX.getXAxis();
        x1AccelerometerX.setDrawGridLines(false);
        x1AccelerometerX.setPosition(XAxis.XAxisPosition.BOTTOM);
        YAxis y1AccelerometerX = chAccelerometerX.getAxisLeft();
        y1AccelerometerX.setDrawGridLines(false);

        LineDataSet setAccelerometerX = new LineDataSet(yAccelerometerX, "");
        setAccelerometerX.setDrawCubic(true);
        setAccelerometerX.setDrawFilled(true);
        setAccelerometerX.setDrawValues(false);
        setAccelerometerX.setDrawCircles(false);
        setAccelerometerX.setColor(Color.rgb(255, 100, 0));
        setAccelerometerX.setFillColor(Color.rgb(255, 191, 106));
        setAccelerometerX.setCircleColor(Color.rgb(255, 100, 0));
        LineData dataAccelerometerX = new LineData(xVal, setAccelerometerX);
        chAccelerometerX.setData(dataAccelerometerX);
        chAccelerometerX.invalidate();
        chAccelerometerX.animateY(3000);

        // AccelerometerY
/*        LineChart chAccelerometerY = (LineChart) findViewById(R.id.chAccelerometerY);
        chAccelerometerY.setDescription("");
        chAccelerometerY.setDrawGridBackground(false);
        chAccelerometerY.setNoDataText(getString(R.string.statistics_not_found_accelerometer));
        Legend lAccelerometerY = chAccelerometerY.getLegend();
        lAccelerometerY.setEnabled(false);
        YAxis y12AccelerometerY = chAccelerometerY.getAxisRight();
        y12AccelerometerY.setEnabled(false);
        XAxis x1AccelerometerY = chAccelerometerY.getXAxis();
        x1AccelerometerY.setDrawGridLines(false);
        x1AccelerometerY.setPosition(XAxis.XAxisPosition.BOTTOM);
        YAxis y1AccelerometerY = chAccelerometerY.getAxisLeft();
        y1AccelerometerY.setDrawGridLines(false);

        LineDataSet setAccelerometerY = new LineDataSet(yAccelerometerY, "");
        setAccelerometerY.setDrawCubic(true);
        setAccelerometerY.setDrawFilled(true);
        setAccelerometerY.setDrawValues(false);
        setAccelerometerY.setDrawCircles(false);
        setAccelerometerY.setColor(Color.rgb(255, 100, 0));
        setAccelerometerY.setFillColor(Color.rgb(255, 191, 106));
        setAccelerometerY.setCircleColor(Color.rgb(255, 100, 0));
        LineData dataAccelerometerY = new LineData(xVal, setAccelerometerY);
        chAccelerometerY.setData(dataAccelerometerY);
        chAccelerometerY.invalidate();
        chAccelerometerY.animateY(3000);*/

/*        // AccelerometerZ
        LineChart chAccelerometerZ = (LineChart) findViewById(R.id.chAccelerometerZ);
        chAccelerometerZ.setDescription("");
        chAccelerometerZ.setDrawGridBackground(false);
        chAccelerometerZ.setNoDataText(getString(R.string.statistics_not_found_accelerometer));
        Legend lAccelerometerZ = chAccelerometerZ.getLegend();
        lAccelerometerZ.setEnabled(false);
        YAxis y12AccelerometerZ = chAccelerometerZ.getAxisRight();
        y12AccelerometerZ.setEnabled(false);
        XAxis x1AccelerometerZ = chAccelerometerZ.getXAxis();
        x1AccelerometerZ.setDrawGridLines(false);
        x1AccelerometerZ.setPosition(XAxis.XAxisPosition.BOTTOM);
        YAxis y1AccelerometerZ = chAccelerometerZ.getAxisLeft();
        y1AccelerometerZ.setDrawGridLines(false);

        LineDataSet setAccelerometerZ = new LineDataSet(yAccelerometerZ, "");
        setAccelerometerZ.setDrawCubic(true);
        setAccelerometerZ.setDrawFilled(true);
        setAccelerometerZ.setDrawValues(false);
        setAccelerometerZ.setDrawCircles(false);
        setAccelerometerZ.setColor(Color.rgb(255, 100, 0));
        setAccelerometerZ.setFillColor(Color.rgb(255, 191, 106));
        setAccelerometerZ.setCircleColor(Color.rgb(255, 100, 0));
        LineData dataAccelerometerZ = new LineData(xVal, setAccelerometerZ);
        chAccelerometerZ.setData(dataAccelerometerZ);
        chAccelerometerZ.invalidate();
        chAccelerometerZ.animateY(3000);*/
    }
}
