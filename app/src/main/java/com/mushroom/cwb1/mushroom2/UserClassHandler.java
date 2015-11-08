package com.mushroom.cwb1.mushroom2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class UserClassHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "UserDataBase.db";
    private String USER_TABLE;
    public static final String TABLE_DEFAULT = "userinfo";

    private static final String CREATE = "CREATE TABLE ";
    private static final String START_COLUMNS = " ( ";
    private static final String STOP_COLUMNS = ");";
    private static final String FLOAT = " REAL";
    private static final String LONG = " INTEGER";
    private static final String INTEGER = " INTEGER";
    private static final String DOUBLE = " REAL";
    private static final String COMMA = ", ";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_USER_ID = "user_id";
    public static final String COLUMN_USER_NAME = "user_name";
    public static final String COLUMN_PASSWORD = "password";
    public static final String COLUMN_LAST_LOGIN = "last_login";
    public static final String COLUMN_FIRST_LOGIN = "first_login";
    public static final String COLUMN_TOTAL_DISTANCE = "total_distance";
    public static final String COLUMN_HIGHEST_SPEED = "highest_speed";
    public static final String COLUMN_NB_WON_CHALLENGES = "nb_won_challenges";
    public static final String COLUMN_HIGHEST_ACCELERATION = "highest_acceleration";
    public static final String COLUMN_AVERAGE_SPEED = "average_speed";
    public static final String COLUMN_TOTAL_TIME = "total_time";
    public static final String COLUMN_HIGHEST_ALTITUDE = "highest altitude";
    public static final String COLUMN_LOWEST_ALTITUDE = "lowest_altitude";
    public static final String COLUMN_HIGHEST_ALTITUDE_DIFF = "highest_altitude_diff";
    public static final String COLUMN_NB_DAYS_BIKED = "nb_days_biked";
    public static final String COLUMN_COUNTRY = "country";
    public static final String COLUMN_CITY = "city";



    public UserClassHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        USER_TABLE = TABLE_DEFAULT;
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        createTable(db, TABLE_DEFAULT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Zolang DATABASE_VERSION niet manueel wordt verhoogd, zal dit niet automatisch gebeuren.
        //deleteTable(TABLE_DEFAULT);
        //onCreate(db);
    }

    public void closeDataBase() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen()) {
            db.close();
        }
    }


    public void createTable(SQLiteDatabase db, String table) {
        table = check(table);
        String querry = CREATE + table + START_COLUMNS +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT" + COMMA +
                COLUMN_USER_ID + INTEGER + COMMA +
                COLUMN_USER_NAME + COMMA +
                COLUMN_PASSWORD  + COMMA +
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
                COLUMN_COUNTRY + COMMA +
                COLUMN_CITY + STOP_COLUMNS ;
        db.execSQL(querry);
    }
    public String getTable() {
        return this.USER_TABLE;
    }

    public void setTable() {
        this.USER_TABLE = TABLE_DEFAULT;
    }

    public void setTable(String table) {
        this.USER_TABLE = check(table);
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


}
