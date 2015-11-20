package com.mushroom.cwb1.mushroom2;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;

public class Persoonlijke_statistieken extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_persoonlijke_statistieken);

        // BarChart - Test

        ArrayList<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(4f, 0));
        entries.add(new BarEntry(8f, 1));
        entries.add(new BarEntry(6f, 2));
        entries.add(new BarEntry(12f, 3));
        entries.add(new BarEntry(18f, 4));
        entries.add(new BarEntry(9f, 5));

        BarDataSet dataset = new BarDataSet(entries, "# of Calls");

        ArrayList<String> labels = new ArrayList<String>();
        labels.add("January");
        labels.add("February");
        labels.add("March");
        labels.add("April");
        labels.add("May");
        labels.add("June");

        BarChart chart = (BarChart) findViewById(R.id.chart);

        BarData data = new BarData(labels, dataset);
        chart.setData(data);

        chart.setDescription("# of times Alice called Bob");

        dataset.setColor(Color.rgb(255, 191, 106));

        chart.animateY(3000);

        // Line Chart

        LineChart chart1 = (LineChart) findViewById(R.id.chart1);
        chart1.setDescription("# of times Alice called Bob");
        chart1.setDrawGridBackground(false);
        YAxis y12 = chart1.getAxisRight();
        y12.setEnabled(false);
        XAxis x1 = chart1.getXAxis();
        x1.setDrawGridLines(false);
        YAxis y1 = chart1.getAxisLeft();
        y1.setDrawGridLines(false);

        ArrayList<Entry> entries1 = new ArrayList<Entry>();
        entries1.add(new Entry(4f, 0));
        entries1.add(new Entry(8f, 1));
        entries1.add(new Entry(6f, 2));
        entries1.add(new Entry(12f, 3));
        entries1.add(new Entry(18f, 4));
        entries1.add(new Entry(9f, 5));

        ArrayList<String> xVal = new ArrayList<String>();
        xVal.add("0");
        xVal.add("1");
        xVal.add("2");
        xVal.add("3");
        xVal.add("4");
        xVal.add("5");

        LineDataSet set1 = new LineDataSet(entries1,"# of calls");
        set1.setDrawCubic(true);
        set1.setDrawFilled(true);
        set1.setColor(Color.rgb(255, 100, 0));
        set1.setFillColor(Color.rgb(255, 191, 106));
        set1.setCircleColor(Color.rgb(255, 100, 0));
        LineData data1 = new LineData(xVal, set1);
        chart1.setData(data1);
        chart1.invalidate();

        chart1.animateY(3000);
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
