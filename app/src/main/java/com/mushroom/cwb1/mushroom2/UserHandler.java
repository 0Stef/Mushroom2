package com.mushroom.cwb1.mushroom2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
    private static final String STRING = " TEXT";
    private static final String COMMA = ", ";

        //Algemene data
    public static final String USER_TABLE = "userinfo";
    public static final String COLUMN_ID = "_id";


        //User
    public static final String COLUMN_USER_NAME = "user_name";
    public static final String COLUMN_PASSWORD = "password";
    public static final String COLUMN_COUNTRY = "country";
    public static final String COLUMN_CITY = "city";
    public static final String COLUMN_LAST_NAME = "last_name";
    public static final String COLUMN_FIRST_NAME = "first_name";


        //Login
    public static final String COLUMN_FIRST_LOGIN = "first_login";
    public static final String COLUMN_LAST_LOGIN = "last_login";


        //Information
    public static final String COLUMN_TOTAL_DISTANCE = "total_distance";
    public static final String COLUMN_TOTAL_TIME = "total_time";

    public static final String COLUMN_HIGHEST_SPEED = "highest_speed";
    public static final String COLUMN_HIGHEST_ACCELERATION = "highest_acceleration";

    public static final String COLUMN_HIGHEST_ALTITUDE_DIFF = "highest_altitude_diff";

    public static final String COLUMN_NB_WON_CHALLENGES = "nb_won_challenges";
    public static final String COLUMN_NB_DAYS_BIKED = "nb_days_biked";
    public static final String COLUMN_TOTAL_POINTS = "total_points";
    public static final String COLUMN_DAILY_POINTS = "daily_points";
    public static final String COLUMN_WEEKLY_POINTS = "weekly_points";


    //Achievements
    public static final String COLUMN_DRIVE_1_KM = "Drive_1_km";
    public static final String COLUMN_DRIVE_5_KM = "Drive_5_km";
    public static final String COLUMN_DRIVE_10_KM = "Drive_10_km";
    public static final String COLUMN_DRIVE_50_KM = "Drive_50_km";
    public static final String COLUMN_DRIVE_100_KM = "Drive_100_km";
    public static final String COLUMN_DRIVE_250_KM = "Drive_250_km";
    public static final String COLUMN_DRIVE_500_KM = "Drive_500_km";
    public static final String COLUMN_DRIVE_1000_KM = "Drive_1000_km";
    public static final String COLUMN_DRIVE_5000_KM = "Drive_5000_km";

    public static final String COLUMN_TOPSPEED_30 = "topspeed_30";
    public static final String COLUMN_TOPSPEED_35 = "topspeed_35";
    public static final String COLUMN_TOPSPEED_40 = "topspeed_40";
    public static final String COLUMN_TOPSPEED_45 = "topspeed_45";
    public static final String COLUMN_TOPSPEED_50 = "topspeed_50";

    public static final String COLUMN_NB_CHALLENGES_1 = "nb_challenges_1";
    public static final String COLUMN_NB_CHALLENGES_5 = "nb_challenges_5";
    public static final String COLUMN_NB_CHALLENGES_10 = "nb_challenges_10";
    public static final String COLUMN_NB_CHALLENGES_50 = "nb_challenges_50";
    public static final String COLUMN_NB_CHALLENGES_200 = "nb_challenges_200";
    public static final String COLUMN_NB_CHALLENGES_500 = "nb_challenges_500";

    public static final String COLUMN_DAYS_BIKED_1 = "days_biked_1";
    public static final String COLUMN_DAYS_BIKED_2 = "days_biked_2";
    public static final String COLUMN_DAYS_BIKED_5 = "days_biked_5";
    public static final String COLUMN_DAYS_BIKED_7 = "days_biked_7";
    public static final String COLUMN_DAYS_BIKED_14 = "days_biked_14";
    public static final String COLUMN_DAYS_BIKED_31 = "days_biked_31";
    public static final String COLUMN_DAYS_BIKED_100 = "days_biked_100";


    public static final String COLUMN_ALT_DIFF_10 = "alt_diff_10";
    public static final String COLUMN_ALT_DIFF_25 = "alt_diff_25";
    public static final String COLUMN_ALT_DIFF_50 = "alt_diff_50";
    public static final String COLUMN_ALT_DIFF_100 = "alt_diff_100";

    public static final String COLUMN_APP_STARTED = "app_started";
    public static final String COLUMN_ALL_ACHIEVEMENTS = "got_all_achievements";


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
                COLUMN_LAST_NAME + STRING + COMMA +
                COLUMN_FIRST_NAME + STRING + COMMA +

                COLUMN_LAST_LOGIN + LONG + COMMA +
                COLUMN_FIRST_LOGIN + LONG + COMMA +
                COLUMN_TOTAL_DISTANCE + FLOAT + COMMA +
                COLUMN_HIGHEST_SPEED + FLOAT + COMMA +
                COLUMN_NB_WON_CHALLENGES + INTEGER + COMMA +
                COLUMN_HIGHEST_ACCELERATION + FLOAT + COMMA +

                COLUMN_TOTAL_TIME + LONG + COMMA +
                COLUMN_TOTAL_POINTS + INTEGER + COMMA +
                COLUMN_DAILY_POINTS + INTEGER + COMMA +
                COLUMN_WEEKLY_POINTS + INTEGER + COMMA +

                COLUMN_HIGHEST_ALTITUDE_DIFF + DOUBLE + COMMA +
                COLUMN_NB_DAYS_BIKED + INTEGER + COMMA +
                COLUMN_COUNTRY + STRING + COMMA +
                COLUMN_CITY + STRING + COMMA +

                COLUMN_DRIVE_1_KM + INTEGER + COMMA +
                COLUMN_DRIVE_5_KM + INTEGER + COMMA +
                COLUMN_DRIVE_10_KM + INTEGER + COMMA +
                COLUMN_DRIVE_50_KM + INTEGER + COMMA +
                COLUMN_DRIVE_100_KM + INTEGER + COMMA +
                COLUMN_DRIVE_250_KM + INTEGER + COMMA +
                COLUMN_DRIVE_500_KM + INTEGER + COMMA +
                COLUMN_DRIVE_1000_KM + INTEGER + COMMA +
                COLUMN_DRIVE_5000_KM + INTEGER + COMMA +

                COLUMN_TOPSPEED_30 + INTEGER + COMMA +
                COLUMN_TOPSPEED_35 + INTEGER + COMMA +
                COLUMN_TOPSPEED_40 + INTEGER + COMMA +
                COLUMN_TOPSPEED_45 + INTEGER + COMMA +
                COLUMN_TOPSPEED_50 + INTEGER + COMMA +

                COLUMN_NB_CHALLENGES_1 + INTEGER + COMMA +
                COLUMN_NB_CHALLENGES_5 + INTEGER + COMMA +
                COLUMN_NB_CHALLENGES_10 + INTEGER + COMMA +
                COLUMN_NB_CHALLENGES_50 + INTEGER + COMMA +
                COLUMN_NB_CHALLENGES_200 + INTEGER + COMMA +
                COLUMN_NB_CHALLENGES_500 + INTEGER + COMMA +

                COLUMN_DAYS_BIKED_1 + INTEGER + COMMA +
                COLUMN_DAYS_BIKED_2 + INTEGER + COMMA +
                COLUMN_DAYS_BIKED_5 + INTEGER + COMMA +
                COLUMN_DAYS_BIKED_7 + INTEGER + COMMA +
                COLUMN_DAYS_BIKED_14 + INTEGER + COMMA +
                COLUMN_DAYS_BIKED_31 + INTEGER + COMMA +
                COLUMN_DAYS_BIKED_100 + INTEGER + COMMA +


                COLUMN_ALT_DIFF_10 + INTEGER + COMMA +
                COLUMN_ALT_DIFF_25 + INTEGER + COMMA +
                COLUMN_ALT_DIFF_50 + INTEGER + COMMA +
                COLUMN_ALT_DIFF_100 + INTEGER + COMMA +

                COLUMN_APP_STARTED + INTEGER + COMMA +
                COLUMN_ALL_ACHIEVEMENTS + INTEGER +
                STOP_COLUMNS;
        db.execSQL(querry);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Zolang DATABASE_VERSION niet manueel wordt verhoogd, zal dit niet automatisch gebeuren.
        deleteTable();
        onCreate(db);
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
        db.update(USER_TABLE, values, COLUMN_USER_NAME + " = " + "'" + userName + "'", null);

        db.close();
    }

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

        db.update(USER_TABLE, values, COLUMN_USER_NAME + " = " + "'" + userName + "'", null);

        db.close();
    }

    private ContentValues getContentValues(User user) {
        ContentValues values = new ContentValues();

        values.put(COLUMN_USER_NAME, user.getUser_name());
        values.put(COLUMN_PASSWORD, user.getPassword());
        values.put(COLUMN_LAST_NAME, user.getLast_name());
        values.put(COLUMN_FIRST_NAME, user.getFirst_name());
        values.put(COLUMN_COUNTRY, user.getCountry());
        values.put(COLUMN_CITY, user.getCity());


        values.put(COLUMN_FIRST_LOGIN, user.getFirst_login());
        values.put(COLUMN_LAST_LOGIN, user.getLast_login());

        values.put(COLUMN_TOTAL_DISTANCE, user.getTotal_distance());
        values.put(COLUMN_TOTAL_TIME, user.getTotal_time());

        values.put(COLUMN_HIGHEST_SPEED, user.getHighest_speed());
        values.put(COLUMN_HIGHEST_ACCELERATION, user.getHighest_acceleration());
        values.put(COLUMN_HIGHEST_ALTITUDE_DIFF, user.getHighest_altitude_diff());

        values.put(COLUMN_NB_WON_CHALLENGES, user.getNb_won_challenges());
        values.put(COLUMN_NB_DAYS_BIKED, user.getNb_days_biked());
        values.put(COLUMN_TOTAL_POINTS, user.getTotal_points());
        values.put(COLUMN_DAILY_POINTS, user.getDaily_points());
        values.put(COLUMN_WEEKLY_POINTS, user.getWeekly_points());

        values.put(COLUMN_DRIVE_1_KM,user.getDrive_1_km());
        values.put(COLUMN_DRIVE_5_KM,user.getDrive_5_km());
        values.put(COLUMN_DRIVE_10_KM,user.getDrive_10_km());
        values.put(COLUMN_DRIVE_50_KM,user.getDrive_50_km());
        values.put(COLUMN_DRIVE_100_KM,user.getDrive_100_km());
        values.put(COLUMN_DRIVE_250_KM,user.getDrive_250_km());
        values.put(COLUMN_DRIVE_500_KM,user.getDrive_500_km());
        values.put(COLUMN_DRIVE_1000_KM,user.getDrive_1000_km());
        values.put(COLUMN_DRIVE_5000_KM,user.getDrive_5000_km());

        values.put(COLUMN_TOPSPEED_30,user.getTopspeed_30());
        values.put(COLUMN_TOPSPEED_35,user.getTopspeed_35());
        values.put(COLUMN_TOPSPEED_40,user.getTopspeed_40());
        values.put(COLUMN_TOPSPEED_45,user.getTopspeed_45());
        values.put(COLUMN_TOPSPEED_50,user.getTopspeed_50());

        values.put(COLUMN_NB_CHALLENGES_1,user.getNb_challenge_1());
        values.put(COLUMN_NB_CHALLENGES_5,user.getNb_challenge_5());
        values.put(COLUMN_NB_CHALLENGES_10,user.getNb_challenge_10());
        values.put(COLUMN_NB_CHALLENGES_50,user.getNb_challenge_50());
        values.put(COLUMN_NB_CHALLENGES_200,user.getNb_challenge_200());
        values.put(COLUMN_NB_CHALLENGES_500,user.getNb_challenge_500());

        values.put(COLUMN_DAYS_BIKED_1,user.getBiked_days_1());
        values.put(COLUMN_DAYS_BIKED_2,user.getBiked_days_2());
        values.put(COLUMN_DAYS_BIKED_5,user.getBiked_days_5());
        values.put(COLUMN_DAYS_BIKED_7,user.getBiked_days_7());
        values.put(COLUMN_DAYS_BIKED_14,user.getBiked_days_14());
        values.put(COLUMN_DAYS_BIKED_31,user.getBiked_days_31());
        values.put(COLUMN_DAYS_BIKED_100,user.getBiked_days_100());


        values.put(COLUMN_ALT_DIFF_10,user.getAlt_diff_10m());
        values.put(COLUMN_ALT_DIFF_25,user.getAlt_diff_25m());
        values.put(COLUMN_ALT_DIFF_50,user.getAlt_diff_50m());
        values.put(COLUMN_ALT_DIFF_100,user.getAlt_diff_100m());

        values.put(COLUMN_APP_STARTED, user.getStart_the_game());
        values.put(COLUMN_ALL_ACHIEVEMENTS, user.getGet_all_achievements());

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

    public User setUser(Cursor cursor) {
        User user = new User();

        user.set_id(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
        user.setUser_name(cursor.getString(cursor.getColumnIndex(COLUMN_USER_NAME)));
        user.setPassword(cursor.getString(cursor.getColumnIndex(COLUMN_PASSWORD)));
        user.setLast_name(cursor.getString(cursor.getColumnIndex(COLUMN_LAST_NAME)));
        user.setFirst_name(cursor.getString(cursor.getColumnIndex(COLUMN_FIRST_NAME)));

        user.setCountry(cursor.getString(cursor.getColumnIndex(COLUMN_COUNTRY)));
        user.setCity(cursor.getString(cursor.getColumnIndex(COLUMN_CITY)));
        user.setFirst_login(cursor.getLong(cursor.getColumnIndex(COLUMN_FIRST_LOGIN)));
        user.setLast_login(cursor.getLong(cursor.getColumnIndex(COLUMN_LAST_LOGIN)));

        user.setTotal_distance(cursor.getFloat(cursor.getColumnIndex(COLUMN_TOTAL_DISTANCE)));
        user.setTotal_time(cursor.getLong(cursor.getColumnIndex(COLUMN_TOTAL_TIME)));
        user.setTotal_points(cursor.getInt(cursor.getColumnIndex(COLUMN_TOTAL_POINTS)));
        user.setDaily_points(cursor.getInt(cursor.getColumnIndex(COLUMN_DAILY_POINTS)));
        user.setWeekly_points(cursor.getInt(cursor.getColumnIndex(COLUMN_WEEKLY_POINTS)));

        user.setHighest_speed(cursor.getFloat(cursor.getColumnIndex(COLUMN_HIGHEST_SPEED)));
        user.setHighest_acceleration(cursor.getFloat(cursor.getColumnIndex(COLUMN_HIGHEST_ACCELERATION)));
        user.setHighest_altitude_diff(cursor.getDouble(cursor.getColumnIndex(COLUMN_HIGHEST_ALTITUDE_DIFF)));

        user.setNb_won_challenges(cursor.getInt(cursor.getColumnIndex(COLUMN_NB_WON_CHALLENGES)));
        user.setNb_days_biked(cursor.getInt(cursor.getColumnIndex(COLUMN_NB_DAYS_BIKED)));

        //FIXME Wenen?

        user.setDrive_1_km(cursor.getInt(cursor.getColumnIndex(COLUMN_DRIVE_1_KM)));
        user.setDrive_5_km(cursor.getInt(cursor.getColumnIndex(COLUMN_DRIVE_5_KM)));
        user.setDrive_10_km(cursor.getInt(cursor.getColumnIndex(COLUMN_DRIVE_10_KM)));
        user.setDrive_50_km(cursor.getInt(cursor.getColumnIndex(COLUMN_DRIVE_50_KM)));
        user.setDrive_100_km(cursor.getInt(cursor.getColumnIndex(COLUMN_DRIVE_100_KM)));
        user.setDrive_250_km(cursor.getInt(cursor.getColumnIndex(COLUMN_DRIVE_250_KM)));
        user.setDrive_500_km(cursor.getInt(cursor.getColumnIndex(COLUMN_DRIVE_500_KM)));
        user.setDrive_1000_km(cursor.getInt(cursor.getColumnIndex(COLUMN_DRIVE_1000_KM)));
        user.setDrive_5000_km(cursor.getInt(cursor.getColumnIndex(COLUMN_DRIVE_5000_KM)));

        user.setTopspeed_30(cursor.getInt(cursor.getColumnIndex(COLUMN_TOPSPEED_30)));
        user.setTopspeed_35(cursor.getInt(cursor.getColumnIndex(COLUMN_TOPSPEED_35)));
        user.setTopspeed_40(cursor.getInt(cursor.getColumnIndex(COLUMN_TOPSPEED_40)));
        user.setTopspeed_45(cursor.getInt(cursor.getColumnIndex(COLUMN_TOPSPEED_45)));
        user.setTopspeed_50(cursor.getInt(cursor.getColumnIndex(COLUMN_TOPSPEED_50)));

        user.setNb_challenge_1(cursor.getInt(cursor.getColumnIndex(COLUMN_NB_CHALLENGES_1)));
        user.setNb_challenge_5(cursor.getInt(cursor.getColumnIndex(COLUMN_NB_CHALLENGES_5)));
        user.setNb_challenge_10(cursor.getInt(cursor.getColumnIndex(COLUMN_NB_CHALLENGES_10)));
        user.setNb_challenge_50(cursor.getInt(cursor.getColumnIndex(COLUMN_NB_CHALLENGES_50)));
        user.setNb_challenge_200(cursor.getInt(cursor.getColumnIndex(COLUMN_NB_CHALLENGES_200)));
        user.setNb_challenge_500(cursor.getInt(cursor.getColumnIndex(COLUMN_NB_CHALLENGES_500)));

        user.setBiked_days_1(cursor.getInt(cursor.getColumnIndex(COLUMN_DAYS_BIKED_1)));
        user.setBiked_days_2(cursor.getInt(cursor.getColumnIndex(COLUMN_DAYS_BIKED_2)));
        user.setBiked_days_5(cursor.getInt(cursor.getColumnIndex(COLUMN_DAYS_BIKED_5)));
        user.setBiked_days_7(cursor.getInt(cursor.getColumnIndex(COLUMN_DAYS_BIKED_7)));
        user.setBiked_days_14(cursor.getInt(cursor.getColumnIndex(COLUMN_DAYS_BIKED_14)));
        user.setBiked_days_31(cursor.getInt(cursor.getColumnIndex(COLUMN_DAYS_BIKED_31)));
        user.setBiked_days_100(cursor.getInt(cursor.getColumnIndex(COLUMN_DAYS_BIKED_100)));


        user.setAlt_diff_10m(cursor.getInt(cursor.getColumnIndex(COLUMN_ALT_DIFF_10)));
        user.setAlt_diff_25m(cursor.getInt(cursor.getColumnIndex(COLUMN_ALT_DIFF_25)));
        user.setAlt_diff_50m(cursor.getInt(cursor.getColumnIndex(COLUMN_ALT_DIFF_50)));
        user.setAlt_diff_100m(cursor.getInt(cursor.getColumnIndex(COLUMN_ALT_DIFF_100)));

        user.setStart_the_game(cursor.getInt(cursor.getColumnIndex(COLUMN_APP_STARTED)));
        user.setGet_all_achievements(cursor.getInt(cursor.getColumnIndex(COLUMN_ALL_ACHIEVEMENTS)));

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

    public boolean isExistingUser(String userName) {
        Cursor cursor = getUser(userName);
        return cursor.moveToFirst();
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

    public User getUserInformation(String username) {
        return getRow(getUser(username));
    }

    // Cursor

    public Cursor getAll() {
        String searchQuery = "SELECT * FROM " + USER_TABLE;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(searchQuery, null);

        return cursor;
    }

    public Cursor getUser(String userName) {
        String searchQuery = "SELECT * FROM " + USER_TABLE
                + " WHERE " + COLUMN_USER_NAME + " = " + "'" + userName + "'";

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

    // Json

    public JSONArray createJsonDatabase() throws JSONException {
        JSONArray array = new JSONArray();

        LinkedList<User> list = getList(getAll());
        for (User user : list) {
            JSONObject object = createJsonObject(user);
            array.put(object);
        }

        return array;
    }

    private JSONObject createJsonObject(User user) throws JSONException {
        JSONObject object = new JSONObject();

        object.put(COLUMN_USER_NAME, user.getUser_name());
        object.put(COLUMN_PASSWORD, user.getPassword());
        object.put(COLUMN_COUNTRY, user.getCountry());
        object.put(COLUMN_CITY, user.getCity());

        object.put(COLUMN_FIRST_LOGIN, user.getFirst_login());
        object.put(COLUMN_LAST_LOGIN, user.getLast_login());

        object.put(COLUMN_TOTAL_DISTANCE, user.getTotal_distance());
        object.put(COLUMN_TOTAL_TIME, user.getTotal_time());

        object.put(COLUMN_HIGHEST_SPEED, user.getHighest_speed());

        object.put(COLUMN_HIGHEST_ACCELERATION, user.getHighest_acceleration());

        object.put(COLUMN_HIGHEST_ALTITUDE_DIFF, user.getHighest_altitude_diff());

        object.put(COLUMN_NB_WON_CHALLENGES, user.getNb_won_challenges());
        object.put(COLUMN_NB_DAYS_BIKED, user.getNb_days_biked());

        return object;
    }
}
