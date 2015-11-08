package com.mushroom.cwb1.mushroom2;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class RecordMagneticField extends AppCompatActivity implements SensorEventListener{

    //    TextView magnetic_field;
    TextView orientation;
    private SensorManager mSensorManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_magnetic_field);
    }

    @Deprecated
    public void clickStart(View view){
//        Sensor mMagneticField;
//        magnetic_field = (TextView) findViewById(R.id.magnetic_field);
//        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
//        mMagneticField = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
//
//        mSensorManager.registerListener(this, mMagneticField, SensorManager.SENSOR_DELAY_NORMAL);
        Sensor mOrientation;
        orientation = (TextView) findViewById(R.id.magnetic_field);
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mOrientation = mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);

        mSensorManager.registerListener(this, mOrientation, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_record_magnetic_field, menu);
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

    public final void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Do something here if sensor accuracy changes.
    }

    @Deprecated
    public final void onSensorChanged(SensorEvent event) {

        if (event.sensor.getType() == Sensor.TYPE_ORIENTATION) {
            String windrichting = "Noorden ";
            float richting = event.values[0];
            if (richting > 45 && richting <= 135){
                windrichting = "Oosten ";
            } else if (richting > 135 && richting <= 225){
                windrichting = "Zuiden ";
            } else if (richting > 225 && richting <= 315){
                windrichting = "Westen ";
            }
            orientation.setText("Windrichting: " + windrichting + "Afwijking: " + richting);
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    public void clickStop(View view){
        mSensorManager.unregisterListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}