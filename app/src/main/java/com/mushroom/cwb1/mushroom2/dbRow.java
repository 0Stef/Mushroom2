package cwb1.mushroom.com.mushroom;

import java.util.Calendar;

public class dbRow {

    private int _id;
    private int ride_id;
    private long millisec;

    private float Accelerometer_xValue;
    private float Accelerometer_yValue;
    private float Accelerometer_zValue;

    private float velocity;
    private double longitude;
    private double latitude;
    private float altitude;

    private float Magnetic_xValue;
    private float Magnetic_yValue;
    private float Magnetic_zValue;


    public dbRow() {
        //Nothing here.
    }

    public dbRow(int ride_id, long millisec,
                 float Accelerometer_xValue, float Accelerometer_yValue, float Accelerometer_zValue,
                 float velocity, double longitude, double latitude, float altitude,
                 float Magnetic_xValue, float Magnetic_yValue, float Magnetic_zValue) {

        this.ride_id = ride_id;
        this.millisec = millisec;

        this.Accelerometer_xValue = Accelerometer_xValue;
        this.Accelerometer_yValue = Accelerometer_yValue;
        this.Accelerometer_zValue = Accelerometer_zValue;

        this.velocity = velocity;
        this.longitude = longitude;
        this.latitude = latitude;
        this.altitude = altitude;

        this.Magnetic_xValue = Magnetic_xValue;
        this.Magnetic_yValue = Magnetic_yValue;
        this.Magnetic_zValue = Magnetic_zValue;
    }

    // General

    protected void set_id(int _id) {
        /**
         * Deze functie wordt alleen geacht gebruikt te worden door databasehandler. De desbetreffende _id
         * wordt door databasehandler automatisch gegenereerd en opgeslagen. Deze waarde is default 0.
         * Deze functie elders gebruiken zal waarschijnlijk tot problemen zorgen.
         */
        this._id = _id;
    }

    public int get_id() {
        return _id;
    }

    public void setRide_id(int ride_id) {
        this.ride_id = ride_id;
    }

    public int getRide_id() {
        return  ride_id;
    }

    public void setMillisec() {
        Calendar calendar = Calendar.getInstance();
        this.millisec = calendar.getTimeInMillis();
    }

    public void setMillisec(long millisec) {
        this.millisec = millisec;
    }

    public long getMillisec() {
        return millisec;
    }

    // Accelerometer

    public void setAccelerometer(float xValue, float yValue, float zValue) {
        setAccelerometer_xValue(xValue);
        setAccelerometer_yValue(yValue);
        setAccelerometer_zValue(zValue);
    }

    public void setAccelerometer_xValue(float Accelerometer_xValue) {
        this.Accelerometer_xValue = Accelerometer_xValue;
    }

    public float getAccelerometer_xValue() {
        return Accelerometer_xValue;
    }

    public void setAccelerometer_yValue(float Accelerometer_yValue) {
        this.Accelerometer_yValue = Accelerometer_yValue;
    }

    public float getAccelerometer_yValue() {
        return Accelerometer_yValue;
    }

    public void setAccelerometer_zValue(float Accelerometer_zValue) {
        this.Accelerometer_zValue = Accelerometer_zValue;
    }

    public float getAccelerometer_zValue() {
        return Accelerometer_zValue;
    }

    // GPS

    public void setGps(float velocity, double longitude, double latitude, float altitude) {
        setVelocity(velocity);
        setLongitude(longitude);
        setLatitude(latitude);
        setAltitude(altitude);
    }

    public void setVelocity(float velocity) {
        this.velocity = velocity;
    }

    public float getVelocity() {
        return velocity;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setAltitude(float altitude) {
        this.altitude = altitude;
    }

    public float getAltitude() {
        return altitude;
    }

    // Magnetic

    public void setMagnetic(float xValue, float yValue, float zValue) {
        setMagnetic_xValue(xValue);
        setMagnetic_yValue(yValue);
        setMagnetic_zValue(zValue);
    }

    public void setMagnetic_xValue(float Magnetic_xValue) {
        this.Magnetic_xValue = Magnetic_xValue;
    }

    public float getMagnetic_xValue() {
        return Magnetic_xValue;
    }

    public void setMagnetic_yValue(float Magnetic_yValue) {
        this.Magnetic_yValue = Magnetic_yValue;
    }

    public float getMagnetic_yValue() {
        return Magnetic_yValue;
    }

    public void setMagnetic_zValue(float Magnetic_zValue) {
        this.Magnetic_zValue = Magnetic_zValue;
    }

    public float getMagnetic_zValue() {
        return Magnetic_zValue;
    }

    // Other

    @Override
    public String toString() {
        return "DataPoint: id=" +  _id +
                " ride_id=" + ride_id +
                " time=" + millisec +
                " latitude=" + latitude +
                " longitude=" + longitude;
    }
}
