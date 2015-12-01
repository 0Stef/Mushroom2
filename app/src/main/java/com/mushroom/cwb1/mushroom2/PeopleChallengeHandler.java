package com.mushroom.cwb1.mushroom2;

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
    public static final String COLUMN_USER1_STATUS = "user1_status";
    public static final String COLUMN_USER2_STATUS = "user2_status";
    public static final String COLUMN_CHALLENGE_NAME = "challenge_name";
    public static final String COLUMN_USER1_RESULT = "user1_result";
    public static final String COLUMN_USER2_RESULT = "user2_result";
    public static final String COLUMN_START = "start";
    public static final String COLUMN_END = "end";
    public static final String COLUMN_USER1_ENDED = "user1_ended";
    public static final String COLUMN_USER2_ENDED = "user2_ended";
    public static final String COLUMN_WINNER = "winner";


    public PeopleChallengeHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String querry = CREATE + CHALLENGE_TABLE+ START_COLUMNS +
                COLUMN_USER1 + STRING + COMMA +
                COLUMN_USER2 + STRING + COMMA +
                COLUMN_USER1_STATUS + INTEGER + COMMA +
                COLUMN_USER2_STATUS + INTEGER + COMMA +
                COLUMN_CHALLENGE_NAME + STRING + COMMA +
                COLUMN_USER1_RESULT + STRING + COMMA +
                COLUMN_USER2_RESULT + STRING + COMMA +
                COLUMN_START + LONG + COMMA +
                COLUMN_END + LONG + COMMA +
                COLUMN_USER1_ENDED + LONG + COMMA +
                COLUMN_USER2_ENDED + LONG + COMMA +
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

    // Table

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


}
