package com.mushroom.cwb1.mushroom2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.LinkedList;

public class UserHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "UserDataBase.db";

    private static final String CREATE = "CREATE TABLE ";
    private static final String START_COLUMNS = " ( ";
    private static final String STOP_COLUMNS = ");";
    private static final String FLOAT = " REAL";
    private static final String LONG = " INTEGER";
    private static final String INTEGER = " INTEGER";
    private static final String DOUBLE = " REAL";
    private static final String STRING = " TEXT ";
    private static final String COMMA = ", ";

    //Algemene data
    public static final String USER_TABLE = "userinfo";
    public static final String COLUMN_ID = "_id";


    //User
    public static final String COLUMN_USER_NAME = "user_name";
    public static final String COLUMN_PASSWORD = "password";
    public static final String COLUMN_COUNTRY = "country";
    public static final String COLUMN_CITY = "city";


    //Login
    public static final String COLUMN_FIRST_LOGIN = "first_login";
    public static final String COLUMN_LAST_LOGIN = "last_login";


    //Information
    public static final String COLUMN_TOTAL_DISTANCE = "total_distance";
    public static final String COLUMN_TOTAL_TIME = "total_time";

    public static final String COLUMN_HIGHEST_SPEED = "highest_speed";
    public static final String COLUMN_AVERAGE_SPEED = "average_speed";
    public static final String COLUMN_HIGHEST_ACCELERATION = "highest_acceleration";

    public static final String COLUMN_HIGHEST_ALTITUDE = "highest altitude";
    public static final String COLUMN_LOWEST_ALTITUDE = "lowest_altitude";
    public static final String COLUMN_HIGHEST_ALTITUDE_DIFF = "highest_altitude_diff";

    public static final String COLUMN_NB_WON_CHALLENGES = "nb_won_challenges";
    public static final String COLUMN_NB_DAYS_BIKED = "nb_days_biked";


    // Database

    public UserHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String querry = CREATE + USER_TABLE + START_COLUMNS +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT" + COMMA +
                COLUMN_USER_NAME + STRING + COMMA +
                COLUMN_PASSWORD + STRING + COMMA +
                COLUMN_LAST_LOGIN + LONG + COMMA +
                COLUMN_FIRST_LOGIN + LONG + COMMA +
                COLUMN_TOTAL_DISTANCE + FLOAT + COMMA +
                COLUMN_HIGHEST_SPEED + FLOAT + COMMA +
                COLUMN_NB_WON_CHALLENGES + INTEGER + COMMA +
                COLUMN_HIGHEST_ACCELERATION + FLOAT + COMMA +
                COLUMN_AVERAGE_SPEED + FLOAT + COMMA +
                COLUMN_TOTAL_TIME + LONG + COMMA +
                COLUMN_HIGHEST_ALTITUDE + DOUBLE + COMMA +
                COLUMN_LOWEST_ALTITUDE + DOUBLE + COMMA +
                COLUMN_HIGHEST_ALTITUDE_DIFF + DOUBLE + COMMA +
                COLUMN_NB_DAYS_BIKED + INTEGER + COMMA +
                COLUMN_COUNTRY + STRING + COMMA +
                COLUMN_CITY + STRING + STOP_COLUMNS;
        db.execSQL(querry);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Zolang DATABASE_VERSION niet manueel wordt verhoogd, zal dit niet automatisch gebeuren.
        //deleteTable();
        //onCreate(db);
    }

    public void closeDataBase() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen()) {
            db.close();
        }
    }

    // Table

    public void eraseTable() {
        //Alle opgeslagen metingen worden gewist; _id telt verder.
        SQLiteDatabase db = this.getReadableDatabase();
        db.delete(USER_TABLE, null, null);
    }

    public void resetTable() {
        //Alle opgeslagen elementen worden gewist; _id begint weer vanaf 0.
        deleteTable();
        onCreate(this.getWritableDatabase());
    }

    public void deleteTable() {
        //De tabel wordt verwijderd uit de database.
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + USER_TABLE);
    }

    // Row

    public void addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = getContentValues(user);
        db.insert(USER_TABLE, null, values);

        db.close();
    }

    public void overWrite(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = getContentValues(user);
        String userName = user.getUser_name();
        db.update(USER_TABLE, values, COLUMN_USER_NAME + " = " + userName, null);

        db.close();
    }

    @Deprecated
    public void overWrite(String userName, String column, Object object) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        if (object instanceof String) {
            values.put(column, (String) object);
        } else if (object instanceof Long) {
            values.put(column, (Long) object);
        } else if (object instanceof Float) {
            values.put(column, (Float) object);
        } else if (object instanceof Double) {
            values.put(column, (Double) object);
        } else if (object instanceof Integer) {
            values.put(column, (Integer) object);
        }

        db.update(USER_TABLE, values, COLUMN_USER_NAME + " = " + userName, null);

        db.close();
    }

    private ContentValues getContentValues(User user) {
        ContentValues values = new ContentValues();

        values.put(COLUMN_USER_NAME, user.getUser_name());
        values.put(COLUMN_PASSWORD, user.getPassword());
        values.put(COLUMN_COUNTRY, user.getCountry());
        values.put(COLUMN_CITY, user.getCity());

        values.put(COLUMN_FIRST_LOGIN, user.getFirst_login());
        values.put(COLUMN_LAST_LOGIN, user.getLast_login());

        values.put(COLUMN_HIGHEST_SPEED, user.getHighest_speed());
        values.put(COLUMN_AVERAGE_SPEED, user.getAverage_speed());
        values.put(COLUMN_HIGHEST_ACCELERATION, user.getHighest_acceleration());

        values.put(COLUMN_HIGHEST_ALTITUDE, user.getHighest_altitude());
        values.put(COLUMN_LOWEST_ALTITUDE, user.getLowest_altitude());
        values.put(COLUMN_HIGHEST_ALTITUDE_DIFF, user.getHighest_altitude_diff());

        values.put(COLUMN_NB_WON_CHALLENGES, user.getNb_won_challenges());
        values.put(COLUMN_NB_DAYS_BIKED, user.getNb_days_biked());

        return values;
    }

    public LinkedList getList(Cursor cursor) {

        LinkedList list = new LinkedList();

        if (cursor.moveToFirst()) {
            do {
                User row = setUser(cursor);
                list.add(row);
            } while (cursor.moveToNext());
        }

        return list;
    }

    public User getRow(Cursor cursor) {

        if (cursor.moveToFirst()) {
            User row = setUser(cursor);
            return row;
        } else {
            return new User();
        }
    }

    private User setUser(Cursor cursor) {
        User user = new User();

        user.set_id(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
        user.setUser_name(cursor.getString(cursor.getColumnIndex(COLUMN_USER_NAME)));
        user.setPassword(cursor.getString(cursor.getColumnIndex(COLUMN_PASSWORD)));
        user.setCountry(cursor.getString(cursor.getColumnIndex(COLUMN_COUNTRY)));
        user.setCity(cursor.getString(cursor.getColumnIndex(COLUMN_CITY)));

        user.setFirst_login(cursor.getLong(cursor.getColumnIndex(COLUMN_FIRST_LOGIN)));
        user.setLast_login(cursor.getLong(cursor.getColumnIndex(COLUMN_LAST_LOGIN)));

        user.setTotal_distance(cursor.getFloat(cursor.getColumnIndex(COLUMN_TOTAL_DISTANCE)));
        user.setTotal_time(cursor.getLong(cursor.getColumnIndex(COLUMN_TOTAL_TIME)));

        user.setHighest_speed(cursor.getFloat(cursor.getColumnIndex(COLUMN_HIGHEST_SPEED)));
        user.setAverage_speed(cursor.getFloat(cursor.getColumnIndex(COLUMN_AVERAGE_SPEED)));
        user.setHighest_acceleration(cursor.getFloat(cursor.getColumnIndex(COLUMN_HIGHEST_ACCELERATION)));

        user.setHighest_altitude(cursor.getDouble(cursor.getColumnIndex(COLUMN_HIGHEST_ALTITUDE)));
        user.setLowest_altitude(cursor.getDouble(cursor.getColumnIndex(COLUMN_LOWEST_ALTITUDE)));
        user.setHighest_altitude_diff(cursor.getDouble(cursor.getColumnIndex(COLUMN_HIGHEST_ALTITUDE_DIFF)));

        user.setNb_won_challenges(cursor.getInt(cursor.getColumnIndex(COLUMN_NB_WON_CHALLENGES)));
        user.setNb_days_biked(cursor.getInt(cursor.getColumnIndex(COLUMN_NB_DAYS_BIKED)));

        return user;
    }

    // User

    public boolean isRightPassword(String userName, String password) {
        User user = getRow(getUser(userName));

        if (user.getPassword().equals(password)) {
            return true;
        } else {
            return false;
        }
    }

    public void updateHighestFloat(DataBaseHandler2 handler, String userName, String searchColumn, String updateColumn) {
        String activeTable = handler.getTable();
        handler.setTable(userName);

        Cursor cursor = handler.getGreatest(searchColumn);
        Float value = cursor.getFloat(cursor.getColumnIndex(searchColumn));

        overWrite(userName, updateColumn, value);

        handler.setTable(activeTable);
    }

    public void updateHighestDouble(DataBaseHandler2 handler, String userName, String searchColumn, String updateColumn) {
        String activeTable = handler.getTable();
        handler.setTable(userName);

        Cursor cursor = handler.getGreatest(searchColumn);
        Double value = cursor.getDouble(cursor.getColumnIndex(searchColumn));

        overWrite(userName, updateColumn, value);

        handler.setTable(activeTable);
    }

    // Prefabricated functions

    public User getUserInformation(String userName) {
        return getRow(getUser(userName));
    }

    // Cursor

    public Cursor getUser(String userName) {
        String searchQuery = "SELECT * FROM " + USER_TABLE
                + " WHERE " + COLUMN_USER_NAME + " = " + userName;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(searchQuery, null);

        return cursor;
    }

    public Cursor getGreatest(String column) {
        String searchQuery = "SELECT * FROM " + USER_TABLE +
                " WHERE " + column + " = (SELECT MAX(" + column + ") FROM " + USER_TABLE + ")";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(searchQuery, null);

        return cursor;
    }
}
