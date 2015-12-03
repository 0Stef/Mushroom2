package com.mushroom.cwb1.mushroom2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.util.Calendar;

public class Challenges extends AppCompatActivity {

    PeopleChallenge current_challenge;


    PeopleChallengeHandler peoplechallengehandler;
    DataBaseHandler2 dbhandler;
    UserHandler userhandler;

    String currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenges2);

        currentUser = getIntent().getStringExtra("username");

        userhandler = new UserHandler(getApplicationContext());
        User user = userhandler.getUserInformation(currentUser);



        // TODO lijst verschillende mogelijke tegenstanders



        final Button GetHighestSpeedButton = (Button)findViewById(R.id.button_highest_speed);
        final Button GetHighestAccelerationButton = (Button)findViewById(R.id.button_highest_acceleration);
        final Button GetHighestAltitudeDifferenceButton = (Button)findViewById(R.id.button_highest_altitude_difference);
        final Button GetLongestDistanceButton = (Button)findViewById(R.id.button_longest_distance);

        GetHighestSpeedButton.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        current_challenge.setChallenge_name("Highest_Speed");

                        disable_buttons();
                    }
                }
        );

        GetHighestAccelerationButton.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        current_challenge.setChallenge_name("Highest_Acceleration");

                        disable_buttons();
                    }
                }
        );

        GetHighestAltitudeDifferenceButton.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        current_challenge.setChallenge_name("Highest_Altitude");


                        disable_buttons();
                    }
                }
        );

        GetLongestDistanceButton.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        current_challenge.setChallenge_name("Longest_distance");

                        disable_buttons();
                    }
                }
        );


        update();



        long current_time = Calendar.getInstance().getTimeInMillis();

        if (current_time > current_challenge.getEnd()){
            if (current_challenge.getChallenge_name().equals("Longest_distance") || current_challenge.getChallenge_name().equals("Highest_Acceleration") || current_challenge.getChallenge_name().equals("Highest_speed")){
                if (current_challenge.getUser1_float() > current_challenge.getUser2_float()){
                    current_challenge.setWinner(current_challenge.getUser1());
                }
                else {
                    current_challenge.setWinner(current_challenge.getUser2());
                }
            }

            else if (current_challenge.getChallenge_name().equals("Highest_Altitude")){
                if (current_challenge.getUser1_double() > current_challenge.getUser2_double()){
                    current_challenge.setWinner(current_challenge.getUser1());
                }
                else {
                    current_challenge.setWinner(current_challenge.getUser2());
                }

            }
        }

        String winner;
        winner  = current_challenge.getWinner();

        if (currentUser.equals(winner)){
            int prev_points = user.getTotal_points();
            user.setTotal_points(prev_points + 3000);
            // ook de challenges tegen andere spelers??
            user.setNb_won_challenges(user.getNb_won_challenges() + 1);

        }

        userhandler.overWrite(user);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_challenges, menu);
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


    public void update(){


        float highest_speed;
        float highest_acceleration;
        float longest_distance;


        double highest_altitude;
        double lowest_altitude;
        double highest_alt_dif;


        if (current_challenge.getChallenge_name().equals("Highest_Speed")){

            highest_speed = dbhandler.getRow(dbhandler.getGreatestBetween(dbhandler.COLUMN_GPS_VEL,current_challenge.getStart(),current_challenge.getEnd())).getVelocity();

            if (currentUser.equals(current_challenge.getUser1())){
                current_challenge.setUser1_float(highest_speed);
            }

            else if(currentUser.equals(current_challenge.getUser2())){
                current_challenge.setUser2_float(highest_speed);
            }

        }

        if (current_challenge.getChallenge_name().equals("Highest_Acceleration")){

            highest_acceleration = dbhandler.getHighestAcceleration(dbhandler.getAllBetween(current_challenge.getStart(),current_challenge.getEnd()));

            if (currentUser.equals(current_challenge.getUser1())){
                current_challenge.setUser1_float(highest_acceleration);
            }

            else if(currentUser.equals(current_challenge.getUser2())){
                current_challenge.setUser2_float(highest_acceleration);
            }

        }

        if (current_challenge.getChallenge_name().equals("Highest_Altitude")){

            highest_altitude = dbhandler.getRow(dbhandler.getGreatestBetween(dbhandler.COLUMN_GPS_ALT, current_challenge.getStart(), current_challenge.getEnd())).getAltitude();
            lowest_altitude = dbhandler.getRow(dbhandler.getLowestBetween(dbhandler.COLUMN_GPS_ALT,current_challenge.getStart(),current_challenge.getEnd())).getAltitude();

            highest_alt_dif = highest_altitude-lowest_altitude;

            if (currentUser.equals(current_challenge.getUser1())){
                current_challenge.setUser1_double(highest_alt_dif);
            }

            else if(currentUser.equals(current_challenge.getUser2())){
                current_challenge.setUser2_double(highest_alt_dif);
            }


        }

        if (current_challenge.getChallenge_name().equals("Longest_Distance")){

            longest_distance = (float) dbhandler.getDistance(dbhandler.getAllBetween(current_challenge.getStart(),current_challenge.getEnd()));

            if (currentUser.equals(current_challenge.getUser1())){
                current_challenge.setUser1_float(longest_distance);
            }

            else if(currentUser.equals(current_challenge.getUser2())){
                current_challenge.setUser2_float(longest_distance);
            }


        }

    }

    public void disable_buttons(){
        final Button GetHighestSpeedButton = (Button)findViewById(R.id.button_highest_speed);
        final Button GetHighestAccelerationButton = (Button)findViewById(R.id.button_highest_acceleration);
        final Button GetHighestAltitudeDifferenceButton = (Button)findViewById(R.id.button_highest_altitude_difference);
        final Button GetLongestDistanceButton = (Button)findViewById(R.id.button_longest_distance);


        GetHighestSpeedButton.setEnabled(false);
        GetHighestAccelerationButton.setEnabled(false);
        GetHighestAltitudeDifferenceButton.setEnabled(false);
        GetLongestDistanceButton.setEnabled(false);


    }


}
