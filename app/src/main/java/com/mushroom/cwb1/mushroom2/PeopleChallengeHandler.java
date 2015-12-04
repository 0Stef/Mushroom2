package com.mushroom.cwb1.mushroom2;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class PeopleChallengeHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "PeopleChallengeDataBase.db";


    private static final String CREATE = "CREATE TABLE ";
    private static final String START_COLUMNS = " ( ";
    private static final String STOP_COLUMNS = ");";
    private static final String FLOAT = " REAL";
    private static final String LONG = " INTEGER";
    private static final String INTEGER = " INTEGER";
    private static final String DOUBLE = " REAL";
    private static final String STRING = " TEXT";
    private static final String COMMA = ", ";


    public static final String CHALLENGE_TABLE = "peoplechallengedata";

    public static final String COLUMN_USER1 = "user1";
    public static final String COLUMN_USER2 = "user2";
    public static final String COLUMN_STATUS = "status";
    public static final String COLUMN_CHALLENGE_NAME = "challenge_name";
    public static final String COLUMN_USER1_FLOAT = "user1_float";
    public static final String COLUMN_USER2_FLOAT = "user2_float";
    public static final String COLUMN_USER1_DOUBLE = "user1_double";
    public static final String COLUMN_USER2_DOUBLE = "user2_double";
    public static final String COLUMN_START = "start";
    public static final String COLUMN_END = "end";
    public static final String COLUMN_WINNER = "winner";


    public PeopleChallengeHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String querry = CREATE + CHALLENGE_TABLE+ START_COLUMNS +
                COLUMN_USER1 + STRING + COMMA +
                COLUMN_USER2 + STRING + COMMA +
                COLUMN_STATUS + INTEGER + COMMA +
                COLUMN_CHALLENGE_NAME + STRING + COMMA +

                COLUMN_USER1_FLOAT + FLOAT + COMMA +
                COLUMN_USER1_DOUBLE + DOUBLE + COMMA +
                COLUMN_USER2_FLOAT + FLOAT + COMMA +
                COLUMN_USER2_DOUBLE + DOUBLE + COMMA +

                COLUMN_START + LONG + COMMA +
                COLUMN_END + LONG + COMMA +
                COLUMN_WINNER + STRING +
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

    private ContentValues getContentValues(PeopleChallenge challenge) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER1,challenge.getUser1());
        values.put(COLUMN_USER2,challenge.getUser2());
        values.put(COLUMN_STATUS,challenge.getStatus());
        values.put(COLUMN_CHALLENGE_NAME,challenge.getChallenge_name());
        values.put(COLUMN_USER1_FLOAT,challenge.getUser1_float());
        values.put(COLUMN_USER2_FLOAT,challenge.getUser2_float());
        values.put(COLUMN_USER1_DOUBLE,challenge.getUser1_double());
        values.put(COLUMN_USER2_DOUBLE,challenge.getUser2_double());
        values.put(COLUMN_START,challenge.getStart());
        values.put(COLUMN_END,challenge.getEnd());
        values.put(COLUMN_WINNER,challenge.getWinner());

        return values;
    }

    public void overWrite(PeopleChallenge challenge) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = getContentValues(challenge);
        String challenge_name = challenge.getChallenge_name();
        db.update(CHALLENGE_TABLE, values, COLUMN_CHALLENGE_NAME + " = " + "'" + challenge_name + "'", null);

        db.close();
    }

    public void eraseTable() {
        //Alle opgeslagen metingen worden gewist; _id telt verder.
        SQLiteDatabase db = this.getReadableDatabase();
        db.delete(CHALLENGE_TABLE, null, null);
    }

    public void resetTable() {
        //Alle opgeslagen elementen worden gewist; _id begint weer vanaf 0.
        deleteTable();
        onCreate(this.getWritableDatabase());
    }

    public void deleteTable() {
        //De tabel wordt verwijderd uit de database.
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + CHALLENGE_TABLE);
    }

    // TODO deze functie afwerken
    public void CheckTableExist() {
        SQLiteDatabase db = this.getReadableDatabase();

    }


}
