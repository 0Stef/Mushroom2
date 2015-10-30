package com.mushroom.cwb1.mushroom2;

import android.database.Cursor;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class recordAcceleration extends AppCompatActivity implements SensorEventListener {
    TextView acceleration;
    TextView result_acceleration;
    private SensorManager mSensorManager;
    private Sensor mAcceleration;

    DataBaseHandler2 handler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_acceleration);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        handler = new DataBaseHandler2(getApplicationContext());
        handler.onUpgrade(handler.getWritableDatabase(), 0, 0);


        acceleration = (TextView) findViewById(R.id.acceleration);

    }

    public void clickStart(View view){
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mAcceleration = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        mSensorManager.registerListener(this, mAcceleration, SensorManager.SENSOR_DELAY_NORMAL);

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

    public final void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Do something here if sensor accuracy changes.
    }

    public final void onSensorChanged(SensorEvent event) {

        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {

            float g = (float) 9.81;

            float versnellingx = event.values[0];
            float versnellingy = event.values[1];
            float versnellingz = event.values[2] - g;
            acceleration.setText("Versnelling: " + versnellingx + " x " + versnellingy + " y " + versnellingz + " z ");
            dbRow point = new dbRow(555555l,versnellingx,versnellingy,versnellingz,0.0f,0.0d,0.0d,0.0f,0.0f,0.0f,0.0f);
            handler.addPoint(point);

        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    public void clickStop(View view){
        mSensorManager.unregisterListener(this);
        result_acceleration = (TextView) findViewById(R.id.acceleration_result_text);
        Cursor greatestvalue = handler.getGreatestValue("Accelerometer_xValue");
        float showgreatestvalue = greatestvalue.getFloat(2);

        result_acceleration.setText("Your highest acceleration is" + showgreatestvalue);
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

}
