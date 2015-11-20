package com.mushroom.cwb1.mushroom2;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.highlight.Highlight;

import java.util.ArrayList;
import java.util.LinkedList;

public class Persoonlijke_statistieken extends AppCompatActivity {

    private int nbRide;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_persoonlijke_statistieken);

        DataBaseHandler2 handler = new DataBaseHandler2(getApplicationContext());
        nbRide=handler.getGreatestRideID();
        LinkedList list = handler.getList(handler.getAllThisRide(nbRide));

        ArrayList<String> xVal = new ArrayList<String>();
        ArrayList<Entry> yVelocity = new ArrayList<Entry>();
        ArrayList<Entry> yAccelerometerX = new ArrayList<Entry>();
        ArrayList<Entry> yAccelerometerY = new ArrayList<Entry>();
        ArrayList<Entry> yAccelerometerZ = new ArrayList<Entry>();

        if (list.size() != 0){
            for (int index = 0; index < list.size(); index++) {
                dbRow row = (dbRow) list.get(index);
                xVal.add("" + index);
                yVelocity.add(new Entry(row.getVelocity(), index));
                yAccelerometerX.add(new Entry(row.getAccelerometer_xValue(), index));
                yAccelerometerY.add(new Entry(row.getAccelerometer_yValue(), index));
                yAccelerometerZ.add(new Entry(row.getAccelerometer_zValue(), index));
            }
        }
        // Velocity chart
        LineChart chVelocity = (LineChart) findViewById(R.id.chVelocity);
        chVelocity.setDescription("");
        chVelocity.setDrawGridBackground(false);
        chVelocity.setNoDataText("Geen snelheidswaarden!");
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

        // AccelerometerX
        LineChart chAccelerometerX = (LineChart) findViewById(R.id.chAccelerometerX);
        chAccelerometerX.setDescription("");
        chAccelerometerX.setDrawGridBackground(false);
        chAccelerometerX.setNoDataText("Geen accelerometerwaarden!");
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
        chAccelerometerY.setNoDataText("Geen accelerometerwaarden!");
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
        chAccelerometerZ.setNoDataText("Geen accelerometerwaarden!");
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
