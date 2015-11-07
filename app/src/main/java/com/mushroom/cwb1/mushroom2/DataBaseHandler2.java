package com.mushroom.cwb1.mushroom2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.LinkedList;

public class DataBaseHandler2 extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "GeneralDatabase2.db";
    private String TABLE;


    private static final String CREATE = "CREATE TABLE ";
    private static final String START_COLUMNS = " ( ";
    private static final String STOP_COLUMNS = ");";
    private static final String FLOAT = " REAL";
    private static final String LONG = " INTEGER";
    private static final String INTEGER = " INTEGER";
    private static final String DOUBLE = " REAL";
    private static final String COMMA = ", ";


    // Algemene data
    public static final String TABLE_DEFAULT = "rides";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_RIDE_ID = "ride_id";
    public static final String COLUMN_TIME = "time";


    // Accelerometer data
    public static final String COLUMN_ACC_X = "Accelerometer_xValue";
    public static final String COLUMN_ACC_Y = "Accelerometer_yValue";
    public static final String COLUMN_ACC_Z = "Accelerometer_zValue";


    // Gps data
    public static final String COLUMN_GPS_VEL  = "velocity";
    public static final String COLUMN_GPS_LONG = "longitude";
    public static final String COLUMN_GPS_LAT = "latitude";
    public static final String COLUMN_GPS_ALT= "altitude";


    // Magneticfield data
    public static final String COLUMN_MAGN_X = "Magnetic_xValue";
    public static final String COLUMN_MAGN_Y = "Magnetic_yValue";
    public static final String COLUMN_MAGN_Z = "Magnetic_zValue";




    // Database

    public DataBaseHandler2(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        TABLE = TABLE_DEFAULT;
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        createTable(db, TABLE_DEFAULT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Zolang DATABASE_VERSION niet manueel wordt verhoogd, zal dit niet automatisch gebeuren.
        deleteTable(TABLE_DEFAULT);
        onCreate(db);
    }

    public void closeDataBase() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen()) {
            db.close();
        }
    }

    // Table

    public void createTable(SQLiteDatabase db, String table) {
        table = check(table);
        String db2querry = CREATE + table + START_COLUMNS +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT" + COMMA +
                COLUMN_RIDE_ID + INTEGER + COMMA +
                COLUMN_TIME + " TIMESTAMP" + COMMA +
                COLUMN_ACC_X + FLOAT + COMMA +
                COLUMN_ACC_Y + FLOAT + COMMA +
                COLUMN_ACC_Z + FLOAT + COMMA +
                COLUMN_MAGN_X + FLOAT + COMMA +
                COLUMN_MAGN_Y + FLOAT + COMMA +
                COLUMN_MAGN_Z + FLOAT + COMMA +
                COLUMN_GPS_VEL + FLOAT + COMMA +
                COLUMN_GPS_LONG + DOUBLE + COMMA +
                COLUMN_GPS_LAT + DOUBLE + COMMA +
                COLUMN_GPS_ALT + FLOAT  + STOP_COLUMNS ;
        db.execSQL(db2querry);
    }

    public String getTable() {
        return this.TABLE;
    }

    public void setTable() {
        this.TABLE = TABLE_DEFAULT;
    }

    public void setTable(String table) {
        this.TABLE = check(table);
    }

    public void eraseTable(String table) {
        //Alle opgeslagen metingen worden gewist; _id telt verder.
        SQLiteDatabase db = this.getReadableDatabase();
        db.delete(check(table), null, null);
    }

    public void resetTable(String table) {
        //Alle opgeslagen elementen worden gewist; _id begint weer vanaf 0.
        deleteTable(table);
        createTable(this.getWritableDatabase(), check(table));
    }

    public void deleteTable(String table) {
        //De tabel wordt verwijderd uit de database.
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + check(table));
    }

    private String check(String table) {
        return table.replaceAll(" ", "_");
    }

    // Row

    public void addPoint(dbRow point) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(COLUMN_RIDE_ID, point.getRide_id());
        values.put(COLUMN_TIME, point.getMillisec());
        values.put(COLUMN_ACC_X, point.getAccelerometer_xValue());
        values.put(COLUMN_ACC_Y, point.getAccelerometer_yValue());
        values.put(COLUMN_ACC_Z, point.getAccelerometer_xValue());

        values.put(COLUMN_GPS_ALT, point.getAltitude());
        values.put(COLUMN_GPS_LAT, point.getLatitude());
        values.put(COLUMN_GPS_LONG, point.getLongitude());
        values.put(COLUMN_GPS_VEL, point.getVelocity());

        values.put(COLUMN_MAGN_X, point.getMagnetic_xValue());
        values.put(COLUMN_MAGN_Y, point.getMagnetic_yValue());
        values.put(COLUMN_MAGN_Z, point.getMagnetic_zValue());

        db.insert(TABLE, null, values);

        db.close();
    }

    public LinkedList getList(Cursor cursor) {

        LinkedList list = new LinkedList();

        if (cursor.moveToFirst()) {
            do {
                dbRow row = setRow(cursor);
                list.add(row);
            } while (cursor.moveToNext());
        }

        return list;
    }

    public dbRow getRow(Cursor cursor) {

        if (cursor.moveToFirst()) {
            dbRow row = setRow(cursor);
            return row;
        } else {
            return new dbRow();
        }
    }

    private dbRow setRow(Cursor cursor) {
        dbRow row = new dbRow();

        row.set_id(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
        row.setRide_id(cursor.getInt(cursor.getColumnIndex(COLUMN_RIDE_ID)));
        row.setMillisec(cursor.getLong(cursor.getColumnIndex(COLUMN_TIME)));

        row.setAccelerometer_xValue(cursor.getFloat(cursor.getColumnIndex(COLUMN_ACC_X)));
        row.setAccelerometer_yValue(cursor.getFloat(cursor.getColumnIndex(COLUMN_ACC_Y)));
        row.setAccelerometer_zValue(cursor.getFloat(cursor.getColumnIndex(COLUMN_ACC_Z)));

        row.setVelocity(cursor.getFloat(cursor.getColumnIndex(COLUMN_GPS_VEL)));
        row.setLatitude(cursor.getDouble(cursor.getColumnIndex(COLUMN_GPS_LAT)));
        row.setLongitude(cursor.getDouble(cursor.getColumnIndex(COLUMN_GPS_LONG)));
        row.setAltitude(cursor.getFloat(cursor.getColumnIndex(COLUMN_GPS_ALT)));

        return row;
    }

    // Prefabricated functions

    public LinkedList getAllDataPoints() {
        LinkedList list = getList(getAll());

        return list;
    }

    public dbRow getGreatestValue(String column) {
        dbRow row = getRow(getGreatest(column));

        return row;
    }

    // Searchquery

    public Cursor customSearch(String searchQuery) {

        /**
         *Voorbeelden:
         *String searchQuery = "SELECT * FROM " + TABLE_ACCELEROMETER;
         *String searchQuery = "SELECT * FROM " + TABLE_ACCELEROMETER + " WHERE " + COLUMN_XVALUE + " >= 10 AND " + COLUMN_ZVALUE + " < 8";
         */

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(searchQuery, null);

        return cursor;
    }

    public Cursor getGreatest(String column) {
        //SELECT * FROM TABLE WHERE COLUMN = (SELECT MAX(COLUMN) FROM TABLE)
        String searchQuery = "SELECT * FROM " + TABLE +
                " WHERE " + column + " = (SELECT MAX(" + column + ") FROM " + TABLE + ")";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(searchQuery, null);

        return cursor;
    }

    @Deprecated
    public Cursor getGreatestAfter(String column, long millisec) {
        //FIXME KLopt niet.
        String searchQuery = "SELECT * FROM " + TABLE +
                " WHERE " + column + " = (SELECT MAX(" + column + ") FROM " + TABLE + ")" +
                " AND " + COLUMN_TIME + " > " + millisec;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(searchQuery, null);

        return cursor;
    }

    @Deprecated
    public Cursor getGreatestThisRide(String column, int rideID) {
        String searchQuery = "SELECT * FROM " + TABLE +
                " WHERE " + column + " = (SELECT MAX(" + column + ") FROM " + TABLE + ")" +
                " AND " + COLUMN_RIDE_ID + " = " + rideID;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(searchQuery, null);

        return cursor;
    }

    public Cursor getAll() {
        String searchQuery = "SELECT * FROM " + TABLE;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(searchQuery, null);

        return cursor;
    }

    public Cursor getAllAfter(long millisec) {
        String searchQuery = "SELECT * FROM " + TABLE +
                " WHERE " + COLUMN_TIME + " > " + millisec;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(searchQuery, null);

        return cursor;
    }

    public Cursor getAllBetween(long millisec1, long millisec2) {
        String searchQuery = "SELECT * FROM " + TABLE +
                " WHERE " + COLUMN_TIME + " > " + millisec1 +
                " AND " + COLUMN_TIME + " < " + millisec2;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(searchQuery, null);

        return cursor;
    }

    public Cursor getAllThisRide(int rideID) {
        String searchQuery = "SELECT * FROM " + TABLE +
                " WHERE " + COLUMN_RIDE_ID + " = " + rideID;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(searchQuery, null);

        return cursor;
    }
}
