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

import java.util.ArrayList;
import java.util.LinkedList;

public class Persoonlijke_statistieken extends AppCompatActivity {

    private String currentUser;
    private int nbRide;
    private GoogleMap mMap;
    private Polyline route;
    private LinkedList list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_persoonlijke_statistieken);

        currentUser = getIntent().getStringExtra("username");

        //Last ride statistics
        UserHandler uHandler = new UserHandler(getApplicationContext());
        User user = uHandler.getUserInformation(currentUser);

        TextView TotalDistance = (TextView) findViewById(R.id.TotalDistance);
        TotalDistance.setText("" + user.getTotal_distance());

        TextView HighestVelocity = (TextView) findViewById(R.id.HighestVelocity);
        HighestVelocity.setText("" + user.getHighest_speed());

        TextView HighestAcceleration = (TextView) findViewById(R.id.HighestAcceleration);
        HighestAcceleration.setText("" + user.getHighest_acceleration());

        TextView HighestAltitudeDiff = (TextView) findViewById(R.id.HighestAltitudeDiff);
        HighestAltitudeDiff.setText("" + user.getHighest_altitude_diff());

        TextView TotalTime = (TextView) findViewById(R.id.TotalTime);
        TotalTime.setText("" + user.getTotal_time());

        TextView TotalPoints = (TextView) findViewById(R.id.TotalPoints);
        TotalPoints.setText("" + user.getTotal_points());

        TextView BikedDays= (TextView) findViewById(R.id.BikedDays);
        BikedDays.setText("" + user.getNb_days_biked());





        //Last ride statistics
        DataBaseHandler2 handler = new DataBaseHandler2(getApplicationContext());
        nbRide=handler.getGreatestRideID();
        list = handler.getList(handler.getAllThisRide(nbRide));
        ArrayList<Integer> distanceList = handler.getDistanceList(handler.getAllThisRide(nbRide));

        ArrayList<String> xVal = new ArrayList<String>();
        ArrayList<Entry> yVelocity = new ArrayList<Entry>();
        ArrayList<Entry> yDistance = new ArrayList<Entry>();
        ArrayList<Entry> yAccelerometerX = new ArrayList<Entry>();
        ArrayList<Entry> yAccelerometerY = new ArrayList<Entry>();
        ArrayList<Entry> yAccelerometerZ = new ArrayList<Entry>();

        if (list.size() != 0){
            for (int index = 0; index < list.size(); index++) {
                dbRow row = (dbRow) list.get(index);
                xVal.add("" + index);
                yVelocity.add(new Entry(row.getVelocity(), index));
                yDistance.add(new Entry(distanceList.get(index), index));
                yAccelerometerX.add(new Entry(row.getAccelerometer_xValue(), index));
                yAccelerometerY.add(new Entry(row.getAccelerometer_yValue(), index));
                yAccelerometerZ.add(new Entry(row.getAccelerometer_zValue(), index));
            }
        }
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

        // Distance chart
        LineChart chDistance = (LineChart) findViewById(R.id.chDistance);
        chDistance.setDescription("");
        chDistance.setDrawGridBackground(false);
        chDistance.setNoDataText(getString(R.string.statistics_not_found_distance));
        Legend lDistance = chDistance.getLegend();
        lDistance.setEnabled(false);
        YAxis y12Distance = chDistance.getAxisRight();
        y12Distance.setEnabled(false);
        XAxis x1Distance = chDistance.getXAxis();
        x1Distance.setDrawGridLines(false);
        x1Distance.setPosition(XAxis.XAxisPosition.BOTTOM);
        YAxis y1Distance = chDistance.getAxisLeft();
        y1Distance.setDrawGridLines(false);

        LineDataSet setDistance = new LineDataSet(yDistance, "");
        setDistance.setDrawCubic(true);
        setDistance.setDrawFilled(true);
        setDistance.setDrawValues(false);
        setDistance.setDrawCircles(false);
        setDistance.setColor(Color.rgb(255, 100, 0));
        setDistance.setFillColor(Color.rgb(255, 191, 106));
        setDistance.setCircleColor(Color.rgb(255, 100, 0));
        LineData dataDistance = new LineData(xVal, setDistance);
        chDistance.setData(dataDistance);
        chDistance.invalidate();
        chDistance.animateY(3000);

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
        LineChart chAccelerometerY = (LineChart) findViewById(R.id.chAccelerometerY);
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
        chAccelerometerY.animateY(3000);

        // AccelerometerZ
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
        chAccelerometerZ.animateY(3000);

        Button RouteMapping = (Button)findViewById(R.id.RouteMapping);

        RouteMapping.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        Intent i = new Intent(getApplicationContext(), RouteMapping.class);
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

}
