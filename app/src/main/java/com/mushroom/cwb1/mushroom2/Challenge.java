package com.mushroom.cwb1.mushroom2;


import java.io.Serializable;
import java.util.Calendar;

public class Challenge implements Serializable {

    public static final int NOT_ACTIVE = 42;

    public static final int ENDED = 2;
    public static final int ACCEPTED = 1;
    public static final int CHALLENGED = 0;
    public static final int REFUSED = -1;
    public static final int FAILED = -2;

    public static final String HIGHEST_SPEED = "Highest_Speed";
    public static final String HIGHEST_ACCELERATION = "Highest_Acceleration";
    public static final String HIGHEST_ALTITUDE = "Highest_Altitude";
    public static final String GREATEST_DISTANCE = "Longest_Distance";

    private String user1;
    private String user2;
    private String challenge_name;
    private int status;

    private float user1_float;
    private double user1_double;

    private float user2_float;
    private double user2_double;

    private long start;
    private long end;

    private String winner;


    // CONSTRUCTORS

    public Challenge(){
        //Sometimes, an empty line is all you need.
    }

    public Challenge(int status) {
        this.status = status;
    }

    public Challenge(String user1, String user2, String challenge_name, int status) {
        this.user1 = user1;
        this.user2 = user2;
        this.challenge_name = challenge_name;
        this.status = status;
    }

    public Challenge(String user1, String user2, String challenge_name, int status, float user1_float,
                     double user1_double, float user2_float, double user2_double, long start, long end,
                     String winner) {

        this.user1 = user1;
        this.user2 = user2;
        this.challenge_name = challenge_name;
        this.status = status;
        this.user1_float = user1_float;
        this.user1_double = user1_double;
        this.user2_float = user2_float;
        this.user2_double = user2_double;
        this.start = start;
        this.end = end;
        this.winner = winner;
    }

    // GETTERS AND SETTERS

    public String getUser1() {
        return user1;
    }

    public void setUser1(String user1) {
        this.user1 = user1;
    }

    public String getUser2() {
        return user2;
    }

    public void setUser2(String user2) {
        this.user2 = user2;
    }


    public String getChallenge_name() {
        return challenge_name;
    }

    public void setChallenge_name(String challenge_name) {
        this.challenge_name = challenge_name;
    }



    public long getStart() {
        return start;
    }

    public void setStart(long start) {
        this.start = start;
    }

    public long getEnd() {
        return end;
    }

    public void setEnd(long end) {
        this.end = end;
    }

    public void initialiseTime(long duration) {
        this.start = Calendar.getInstance().getTimeInMillis();
        this.end = start + duration;
    }

    public long getTimeLeft() {
        return end - start;
    }



    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public float getUser1_float() {
        return user1_float;
    }

    public void setUser1_float(float user1_float) {
        this.user1_float = user1_float;
    }

    public double getUser1_double() {
        return user1_double;
    }

    public void setUser1_double(double user1_double) {
        this.user1_double = user1_double;
    }

    public float getUser2_float() {
        return user2_float;
    }

    public void setUser2_float(float user2_float) {
        this.user2_float = user2_float;
    }

    public double getUser2_double() {
        return user2_double;
    }

    public void setUser2_double(double user2_double) {
        this.user2_double = user2_double;
    }

    //Extra

    public static String getChallengeType(int position) {
        if (position == 0) {
            return HIGHEST_SPEED;
        } else if (position == 1) {
            return HIGHEST_ACCELERATION;
        } else if (position == 2) {
            return HIGHEST_ALTITUDE;
        } else if (position == 3) {
            return GREATEST_DISTANCE;
        } else {
            return new String();
        }
    }

    public static int getChallengeDescription(String type) {
        if (type.equals(HIGHEST_SPEED)) {
            return R.string.people_inv_text_h_speed;
        } else if (type.equals(HIGHEST_ACCELERATION)) {
            return R.string.people_inv_text_h_acceleration;
        } else if (type.equals(HIGHEST_ALTITUDE)) {
            return R.string.people_inv_text_h_altitude;
        } else if (type.equals(GREATEST_DISTANCE)) {
            return R.string.people_inv_text_g_distance;
        } else {
            return Integer.parseInt(null);
        }
    }

    @Override
    public String toString() {
        return "challenger: " + user1 +
                ", opponent: " + user2 +
                ", type: " + challenge_name +
                ", status: " + status;
    }
}


