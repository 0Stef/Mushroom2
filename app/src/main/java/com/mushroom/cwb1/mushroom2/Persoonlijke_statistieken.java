package com.mushroom.cwb1.mushroom2;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.LineData;
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

        if (list.size() != 0){
            for (int index = 0; index < list.size() + 1; index++) {
                dbRow row = (dbRow) list.get(index);
                xVal.add("" + index);
                yVelocity.add(new Entry(row.getVelocity(), index));
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
        setVelocity.setColor(Color.rgb(255, 100, 0));
        setVelocity.setFillColor(Color.rgb(255, 191, 106));
        setVelocity.setCircleColor(Color.rgb(255, 100, 0));
        LineData dataVelocity = new LineData(xVal, setVelocity);
        chVelocity.setData(dataVelocity);
        chVelocity.invalidate();
        chVelocity.animateY(3000);

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
