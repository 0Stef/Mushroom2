package com.mushroom.cwb1.mushroom2;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;

import java.util.Calendar;

public class achievements extends AppCompatActivity {

    UserHandler handler;

    ProgressBar progress_1_km;
    ProgressBar progress_5_km;
    ProgressBar progress_10_km;
    ProgressBar progress_50_km;
    ProgressBar progress_100_km;
    ProgressBar progress_250_km;
    ProgressBar progress_500_km;
    ProgressBar progress_1000_km;
    ProgressBar progress_5000_km;

    ProgressBar progress_topspeed_30;
    ProgressBar progress_topspeed_35;
    ProgressBar progress_topspeed_40;
    ProgressBar progress_topspeed_45;
    ProgressBar progress_topspeed_50;

    ProgressBar progress_nb_challenge_1;
    ProgressBar progress_nb_challenge_5;
    ProgressBar progress_nb_challenge_10;
    ProgressBar progress_nb_challenge_50;
    ProgressBar progress_nb_challenge_200;
    ProgressBar progress_nb_challenge_500;

    ProgressBar progress_days_biked_1;
    ProgressBar progress_days_biked_2;
    ProgressBar progress_days_biked_5;
    ProgressBar progress_days_biked_7;
    ProgressBar progress_days_biked_14;
    ProgressBar progress_days_biked_31;
    ProgressBar progress_days_biked_100;


    ProgressBar progress_alt_diff_10;
    ProgressBar progress_alt_diff_25;
    ProgressBar progress_alt_diff_50;
    ProgressBar progress_alt_diff_100;

    ProgressBar progress_start_the_game;
    ProgressBar progress_get_all_achievements;

    public String currentUser;

    private int nb_compl_achievements;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achievements);
        currentUser = getIntent().getStringExtra("username");


        handler = new UserHandler(getApplicationContext());
        User user = handler.getUserInformation(currentUser);

        System.out.println(user.getTotal_distance());
        System.out.println(user.getHighest_speed());
        nb_compl_achievements = 0;

        System.out.println(user.getNb_won_challenges());


        //Creating the progressbars
        progress_1_km = (ProgressBar) findViewById(R.id.progress_1_km);
        progress_1_km.getProgressDrawable().setColorFilter(Color.rgb(255,191,106), PorterDuff.Mode.SRC_IN);
        progress_5_km = (ProgressBar) findViewById(R.id.progress_5_km);
        progress_5_km.getProgressDrawable().setColorFilter(Color.rgb(255,191,106), PorterDuff.Mode.SRC_IN);
        progress_10_km = (ProgressBar) findViewById(R.id.progress_10_km);
        progress_10_km.getProgressDrawable().setColorFilter(Color.rgb(255,191,106), PorterDuff.Mode.SRC_IN);
        progress_50_km = (ProgressBar) findViewById(R.id.progress_50_km);
        progress_50_km.getProgressDrawable().setColorFilter(Color.rgb(255,191,106), PorterDuff.Mode.SRC_IN);
        progress_100_km = (ProgressBar) findViewById(R.id.progress_100_km);
        progress_100_km.getProgressDrawable().setColorFilter(Color.rgb(255,191,106), PorterDuff.Mode.SRC_IN);
        progress_250_km = (ProgressBar) findViewById(R.id.progress_250_km);
        progress_250_km.getProgressDrawable().setColorFilter(Color.rgb(255,191,106), PorterDuff.Mode.SRC_IN);
        progress_500_km = (ProgressBar) findViewById(R.id.progress_500_km);
        progress_500_km.getProgressDrawable().setColorFilter(Color.rgb(255,191,106), PorterDuff.Mode.SRC_IN);
        progress_1000_km = (ProgressBar) findViewById(R.id.progress_1000_km);
        progress_1000_km.getProgressDrawable().setColorFilter(Color.rgb(255,191,106), PorterDuff.Mode.SRC_IN);
        progress_5000_km = (ProgressBar) findViewById(R.id.progress_5000_km);
        progress_5000_km.getProgressDrawable().setColorFilter(Color.rgb(255,191,106), PorterDuff.Mode.SRC_IN);

        progress_topspeed_30 = (ProgressBar) findViewById(R.id.progress_topspeed_30);
        progress_topspeed_30.getProgressDrawable().setColorFilter(Color.rgb(255,191,106), PorterDuff.Mode.SRC_IN);
        progress_topspeed_35 = (ProgressBar) findViewById(R.id.progress_topspeed_35);
        progress_topspeed_35.getProgressDrawable().setColorFilter(Color.rgb(255,191,106), PorterDuff.Mode.SRC_IN);
        progress_topspeed_40 = (ProgressBar) findViewById(R.id.progress_topspeed_40);
        progress_topspeed_40.getProgressDrawable().setColorFilter(Color.rgb(255,191,106), PorterDuff.Mode.SRC_IN);
        progress_topspeed_45 = (ProgressBar) findViewById(R.id.progress_topspeed_45);
        progress_topspeed_45.getProgressDrawable().setColorFilter(Color.rgb(255,191,106), PorterDuff.Mode.SRC_IN);
        progress_topspeed_50 = (ProgressBar) findViewById(R.id.progress_topspeed_50);
        progress_topspeed_50.getProgressDrawable().setColorFilter(Color.rgb(255,191,106), PorterDuff.Mode.SRC_IN);

        progress_nb_challenge_1 = (ProgressBar) findViewById(R.id.progress_nb_challenge_1);
        progress_nb_challenge_1.getProgressDrawable().setColorFilter(Color.rgb(255,191,106), PorterDuff.Mode.SRC_IN);
        progress_nb_challenge_5 = (ProgressBar) findViewById(R.id.progress_nb_challenge_5);
        progress_nb_challenge_5.getProgressDrawable().setColorFilter(Color.rgb(255,191,106), PorterDuff.Mode.SRC_IN);
        progress_nb_challenge_10 = (ProgressBar) findViewById(R.id.progress_nb_challenge_10);
        progress_nb_challenge_10.getProgressDrawable().setColorFilter(Color.rgb(255,191,106), PorterDuff.Mode.SRC_IN);
        progress_nb_challenge_50 = (ProgressBar) findViewById(R.id.progress_nb_challenge_50);
        progress_nb_challenge_50.getProgressDrawable().setColorFilter(Color.rgb(255,191,106), PorterDuff.Mode.SRC_IN);
        progress_nb_challenge_200 = (ProgressBar) findViewById(R.id.progress_nb_challenge_200);
        progress_nb_challenge_200.getProgressDrawable().setColorFilter(Color.rgb(255,191,106), PorterDuff.Mode.SRC_IN);
        progress_nb_challenge_500 = (ProgressBar) findViewById(R.id.progress_nb_challenge_500);
        progress_nb_challenge_500.getProgressDrawable().setColorFilter(Color.rgb(255,191,106), PorterDuff.Mode.SRC_IN);

        progress_days_biked_1  = (ProgressBar) findViewById(R.id.progress_days_biked_1);
        progress_days_biked_1.getProgressDrawable().setColorFilter(Color.rgb(255,191,106), PorterDuff.Mode.SRC_IN);
        progress_days_biked_2  = (ProgressBar) findViewById(R.id.progress_days_biked_2);
        progress_days_biked_2.getProgressDrawable().setColorFilter(Color.rgb(255,191,106), PorterDuff.Mode.SRC_IN);
        progress_days_biked_5  = (ProgressBar) findViewById(R.id.progress_days_biked_5);
        progress_days_biked_5.getProgressDrawable().setColorFilter(Color.rgb(255,191,106), PorterDuff.Mode.SRC_IN);
        progress_days_biked_7  = (ProgressBar) findViewById(R.id.progress_days_biked_7);
        progress_days_biked_7.getProgressDrawable().setColorFilter(Color.rgb(255,191,106), PorterDuff.Mode.SRC_IN);
        progress_days_biked_14  = (ProgressBar) findViewById(R.id.progress_days_biked_14);
        progress_days_biked_14.getProgressDrawable().setColorFilter(Color.rgb(255,191,106), PorterDuff.Mode.SRC_IN);
        progress_days_biked_31  = (ProgressBar) findViewById(R.id.progress_days_biked_31);
        progress_days_biked_31.getProgressDrawable().setColorFilter(Color.rgb(255,191,106), PorterDuff.Mode.SRC_IN);
        progress_days_biked_100  = (ProgressBar) findViewById(R.id.progress_days_biked_100);
        progress_days_biked_100.getProgressDrawable().setColorFilter(Color.rgb(255,191,106), PorterDuff.Mode.SRC_IN);

        progress_alt_diff_10 = (ProgressBar) findViewById(R.id.progress_alt_diff_10);
        progress_alt_diff_10.getProgressDrawable().setColorFilter(Color.rgb(255,191,106), PorterDuff.Mode.SRC_IN);
        progress_alt_diff_25 = (ProgressBar) findViewById(R.id.progress_alt_diff_25);
        progress_alt_diff_25.getProgressDrawable().setColorFilter(Color.rgb(255,191,106), PorterDuff.Mode.SRC_IN);
        progress_alt_diff_50 = (ProgressBar) findViewById(R.id.progress_alt_diff_50);
        progress_alt_diff_50.getProgressDrawable().setColorFilter(Color.rgb(255,191,106), PorterDuff.Mode.SRC_IN);
        progress_alt_diff_100 = (ProgressBar) findViewById(R.id.progress_alt_diff_100);
        progress_alt_diff_100.getProgressDrawable().setColorFilter(Color.rgb(255,191,106), PorterDuff.Mode.SRC_IN);

        progress_start_the_game = (ProgressBar) findViewById(R.id.progress_start_the_game);
        progress_start_the_game.getProgressDrawable().setColorFilter(Color.rgb(255,191,106), PorterDuff.Mode.SRC_IN);
        progress_get_all_achievements = (ProgressBar) findViewById(R.id.progress_get_all_achievements);
        progress_get_all_achievements.getProgressDrawable().setColorFilter(Color.rgb(255,191,106), PorterDuff.Mode.SRC_IN);

        //TODO verder uitwerken
        //TOTAL DISTANCE

        set_achievements(user);





//        if ((user.getDrive_1_km() ==1) && (user.getDrive_5_km() == 1) && (user.getDrive_10_km() == 1) && (user.getDrive_50_km() == 1) && (user.getDrive_100_km() == 1) && (user.getDrive_250_km() == 1) &&
//                (user.getDrive_500_km() == 1) && (user.getDrive_1000_km() ==1) && (user.getDrive_5000_km() == 1) && (user.getTopspeed_30() == 1) && (user.getTopspeed_35() == 1) && (user.getTopspeed_40() == 1) &&
//                (user.getTopspeed_45() == 1) && (user.getTopspeed_50() == 1) && (user.getNb_challenge_1() == 1) && (user.getNb_challenge_5() == 1) && (user.getNb_challenge_10() == 1) && (user.getNb_challenge_50() == 1) &&
//                (user.getNb_challenge_1() == 1) &&


        // DISTANCE ACHIEVEMENTS
        if (user.getDrive_1_km() == 1 ){
            progress_1_km.getProgressDrawable().setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_IN);
            nb_compl_achievements++;
        }
        if (user.getDrive_5_km() == 1 ){
            progress_5_km.getProgressDrawable().setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_IN);
            nb_compl_achievements++;
        }
        if (user.getDrive_10_km() == 1 ){
            progress_10_km.getProgressDrawable().setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_IN);
            nb_compl_achievements++;
        }
        if (user.getDrive_50_km() == 1 ){
            progress_50_km.getProgressDrawable().setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_IN);
            nb_compl_achievements++;
        }
        if (user.getDrive_100_km() == 1 ){
            progress_100_km.getProgressDrawable().setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_IN);
            nb_compl_achievements++;
        }
        if (user.getDrive_250_km() == 1 ){
            progress_250_km.getProgressDrawable().setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_IN);
            nb_compl_achievements++;
        }
        if (user.getDrive_500_km() == 1 ){
            progress_500_km.getProgressDrawable().setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_IN);
            nb_compl_achievements++;
        }
        if (user.getDrive_1000_km() == 1 ){
            progress_1000_km.getProgressDrawable().setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_IN);
            nb_compl_achievements++;
        }
        if (user.getDrive_5000_km() == 1 ){
            progress_5000_km.getProgressDrawable().setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_IN);
            nb_compl_achievements++;
        }

        progress_1_km.setProgress((int) (user.getTotal_distance()/10.0f));
        progress_5_km.setProgress((int) (user.getTotal_distance()/50.0f));
        progress_10_km.setProgress((int)(user.getTotal_distance()/100.0f));
        progress_50_km.setProgress((int)(user.getTotal_distance()/500.0f));
        progress_100_km.setProgress((int)(user.getTotal_distance()/1000.0f));
        progress_250_km.setProgress((int)(user.getTotal_distance()/2500.0f));
        progress_500_km.setProgress((int)(user.getTotal_distance()/5000.0f));
        progress_1000_km.setProgress((int)(user.getTotal_distance()/10000.0f));
        progress_5000_km.setProgress((int)(user.getTotal_distance()/50000.0f));

        //SPEED ACHIEVEMENTS
        if (user.getTopspeed_30() == 1){
            progress_topspeed_30.getProgressDrawable().setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_IN);
            nb_compl_achievements++;
        }
        if (user.getTopspeed_35() == 1){
            progress_topspeed_35.getProgressDrawable().setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_IN);
            nb_compl_achievements++;
        }
        if (user.getTopspeed_40() == 1){
            progress_topspeed_40.getProgressDrawable().setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_IN);
            nb_compl_achievements++;
        }
        if (user.getTopspeed_45() == 1){
            progress_topspeed_45.getProgressDrawable().setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_IN);
            nb_compl_achievements++;
        }
        if (user.getTopspeed_50() == 1){
            progress_topspeed_50.getProgressDrawable().setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_IN);
            nb_compl_achievements++;
        }

        progress_topspeed_30.setProgress((int) ((user.getHighest_speed()/30.0f)*100));
        progress_topspeed_35.setProgress((int) ((user.getHighest_speed()/35.0f)*100));
        progress_topspeed_40.setProgress((int) ((user.getHighest_speed()/40.0f)*100));
        progress_topspeed_45.setProgress((int) ((user.getHighest_speed()/45.0f)*100));
        progress_topspeed_50.setProgress((int) ((user.getHighest_speed()/50.0f)*100));

        //NB CHALLENGES ACHIEVEMENTS
        if (user.getNb_challenge_1() == 1){
            progress_nb_challenge_1.getProgressDrawable().setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_IN);
            nb_compl_achievements++;
        }
        if (user.getNb_challenge_5() == 1){
            progress_nb_challenge_5.getProgressDrawable().setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_IN);
            nb_compl_achievements++;
        }
        if (user.getNb_challenge_10() == 1){
            progress_nb_challenge_10.getProgressDrawable().setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_IN);
            nb_compl_achievements++;
        }
        if (user.getNb_challenge_50() == 1){
            progress_nb_challenge_50.getProgressDrawable().setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_IN);
            nb_compl_achievements++;
        }
        if (user.getNb_challenge_200() == 1){
            progress_nb_challenge_200.getProgressDrawable().setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_IN);
            nb_compl_achievements++;
        }
        if (user.getNb_challenge_500() == 1){
            progress_nb_challenge_500.getProgressDrawable().setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_IN);
            nb_compl_achievements++;
        }

        progress_nb_challenge_1.setProgress((int) ((user.getNb_won_challenges()/1.0f)*100));
        progress_nb_challenge_5.setProgress((int) ((user.getNb_won_challenges()/5.0f)*100));
        progress_nb_challenge_10.setProgress((int) ((user.getNb_won_challenges()/10.0f)*100));
        progress_nb_challenge_50.setProgress((int) ((user.getNb_won_challenges()/50.0f)*100));
        progress_nb_challenge_200.setProgress((int) ((user.getNb_won_challenges()/200.0f)*100));
        progress_nb_challenge_500.setProgress((int) ((user.getNb_won_challenges()/500.0f)*100));

        //DAYS BIKED ACHIEVEMENT
        if (user.getBiked_days_1() == 1){
            progress_days_biked_1.getProgressDrawable().setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_IN);
            nb_compl_achievements++;
        }
        if (user.getBiked_days_2() == 1){
            progress_days_biked_2.getProgressDrawable().setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_IN);
            nb_compl_achievements++;
        }
        if (user.getBiked_days_5() == 1){
            progress_days_biked_5.getProgressDrawable().setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_IN);
            nb_compl_achievements++;
        }
        if (user.getBiked_days_7() == 1){
            progress_days_biked_7.getProgressDrawable().setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_IN);
            nb_compl_achievements++;
        }
        if (user.getBiked_days_14() == 1){
            progress_days_biked_14.getProgressDrawable().setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_IN);
            nb_compl_achievements++;
        }
        if (user.getBiked_days_31() == 1){
            progress_days_biked_31.getProgressDrawable().setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_IN);
            nb_compl_achievements++;
        }
        if (user.getBiked_days_100() == 1){
            progress_days_biked_100.getProgressDrawable().setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_IN);
            nb_compl_achievements++;
        }

        progress_days_biked_1.setProgress((int) ((user.getNb_days_biked()/1.0f)*100));
        progress_days_biked_2.setProgress((int) ((user.getNb_days_biked()/2.0f)*100));
        progress_days_biked_5.setProgress((int) ((user.getNb_days_biked()/5.0f)*100));
        progress_days_biked_7.setProgress((int) ((user.getNb_days_biked()/7.0f)*100));
        progress_days_biked_14.setProgress((int) ((user.getNb_days_biked()/14.0f)*100));
        progress_days_biked_31.setProgress((int) ((user.getNb_days_biked()/31.0f)*100));
        progress_days_biked_100.setProgress((int) ((user.getNb_days_biked()/100.0f)*100));

        //ALT ACHIEVEMENTS
        if (user.getAlt_diff_10m() == 1){
            progress_alt_diff_10.getProgressDrawable().setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_IN);
            nb_compl_achievements++;
        }
        if (user.getAlt_diff_25m() == 1){
            progress_alt_diff_25.getProgressDrawable().setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_IN);
            nb_compl_achievements++;
        }
        if (user.getAlt_diff_50m() == 1){
            progress_alt_diff_50.getProgressDrawable().setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_IN);
            nb_compl_achievements++;
        }
        if (user.getAlt_diff_100m() == 1){
            progress_alt_diff_100.getProgressDrawable().setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_IN);
            nb_compl_achievements++;
        }

        progress_alt_diff_10.setProgress((int) (user.getHighest_altitude_diff()/10.0f)*100);
        progress_alt_diff_25.setProgress((int) (user.getHighest_altitude_diff()/25.0f)*100);
        progress_alt_diff_50.setProgress((int) (user.getHighest_altitude_diff()/50.0f)*100);
        progress_alt_diff_100.setProgress((int) (user.getHighest_altitude_diff()/100.0f)*100);

        //OTHER
        if (user.getStart_the_game() == 1){
            progress_start_the_game.getProgressDrawable().setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_IN);
            nb_compl_achievements++;
        }


        progress_start_the_game.setProgress((int) (user.getStart_the_game()/1.0f)*100);

        progress_get_all_achievements.setProgress((int) (nb_compl_achievements/32.0f)*100);
        if (nb_compl_achievements == 32)
            user.setGet_all_achievements(1);

        if (user.getGet_all_achievements() == 1){
            progress_get_all_achievements.getProgressDrawable().setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_IN);
        }

        handler.overWrite(user);

    }

    private void checkDay(String userName) {
        Calendar calendar = Calendar.getInstance();

        User user = handler.getUserInformation(userName);
        long lastMillisec = user.getLast_login();
        Calendar lastCalendar = Calendar.getInstance();
        lastCalendar.setTimeInMillis(lastMillisec);

        boolean sameDay = calendar.get(Calendar.YEAR) == lastCalendar.get(Calendar.YEAR) &&
                calendar.get(Calendar.DAY_OF_YEAR) == lastCalendar.get(Calendar.DAY_OF_YEAR);

        if (!sameDay) {
            int nb = user.getNb_days_biked();
            handler.overWrite(userName, handler.COLUMN_NB_DAYS_BIKED, nb+1);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_achievements, menu);
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


    private void set_achievement_points(String username) {
        handler = new UserHandler(getApplicationContext());
        User user = handler.getUserInformation(username);

        if (user.getDrive_1_km() == 1) {
            setpoints(user,100);
        }

        if (user.getTopspeed_30() == 1) {
            setpoints(user,1000);
        }
        if (user.getNb_challenge_1() == 1){
            setpoints(user,500);
        }
        if (user.getBiked_days_1() == 1){
            setpoints(user,100);
        }
        if (user.getAlt_diff_10m() == 1){
            setpoints(user,1000);
        }
        if (user.getStart_the_game() == 1){
            setpoints(user,10);
        }
        if (user.getGet_all_achievements() == 1){
            setpoints(user,10000000);
        }
    }

    private void setpoints(User user, int points){
        user.setTotal_points(user.getTotal_points() + points);
        user.setWeekly_points(user.getWeekly_points() + points);
        user.setDaily_points(user.getDaily_points() + points);
    }

    private void set_achievements(User user){
        if (user.getTotal_distance() >= 1000.0f && user.getDrive_1_km() ==0){
            user.setDrive_1_km(1);
            user.setTotal_points(user.getTotal_points() + 100);
        }
        if (user.getTotal_distance() >= 5000.0f && user.getDrive_5_km() == 0){
            user.setDrive_5_km(1);
            user.setTotal_points(user.getTotal_points() + 200);
        }
        if (user.getTotal_distance() >= 10000.0f && user.getDrive_10_km() == 0){
            user.setDrive_10_km(1);
            user.setTotal_points(user.getTotal_points() + 300);
        }
        if (user.getTotal_distance() >= 50000.0f && user.getDrive_50_km() == 0){
            user.setDrive_50_km(1);
            user.setTotal_points(user.getTotal_points() + 600);
        }
        if (user.getTotal_distance() >= 100000.0f && user.getDrive_100_km() == 0){
            user.setDrive_100_km(1);
            user.setTotal_points(user.getTotal_points() + 2000);
        }
        if (user.getTotal_distance() >= 250000.0f && user.getDrive_250_km() == 0){
            user.setDrive_250_km(1);
            user.setTotal_points(user.getTotal_points() + 5000);

        }
        if (user.getTotal_distance() >= 500000.0f && user.getDrive_500_km() == 0){
            user.setDrive_500_km(1);
            user.setTotal_points(user.getTotal_points() + 20000);
        }
        if (user.getTotal_distance() >= 1000000.0f && user.getDrive_1000_km() == 0){
            user.setDrive_1000_km(1);
            user.setTotal_points(user.getTotal_points() + 100000);
        }
        if (user.getTotal_distance() >= 5000000.0f && user.getDrive_5000_km() == 0){
            user.setDrive_5000_km(1);
            user.setTotal_points(user.getTotal_points() + 400000);
        }


        //TOPSPEED
        if (user.getHighest_speed() >=30.0f && user.getTopspeed_30() == 0){
            user.setTopspeed_30(1);
            user.setTotal_points(user.getTotal_points() + 1000);
        }
        if (user.getHighest_speed() >=35.0f && user.getTopspeed_35() == 0){
            user.setTopspeed_35(1);
            user.setTotal_points(user.getTotal_points() + 2000);
        }
        if (user.getHighest_speed() >=40.0f && user.getTopspeed_40() == 0){
            user.setTopspeed_40(1);
            user.setTotal_points(user.getTotal_points() + 5000);
        }
        if (user.getHighest_speed() >=45.0f && user.getTopspeed_45() == 0){
            user.setTopspeed_45(1);
            user.setTotal_points(user.getTotal_points() + 8000);
        }
        if (user.getHighest_speed() >=50.0f && user.getTopspeed_50() == 0){
            user.setTopspeed_50(1);
            user.setTotal_points(user.getTotal_points() + 10000);
        }

        //NB _ CHALLENGES
        if (user.getNb_won_challenges() >= 1 && user.getNb_challenge_1() == 0){
            user.setNb_challenge_1(1);
            user.setTotal_points(user.getTotal_points() + 500);
        }
        if (user.getNb_won_challenges() >= 5 && user.getNb_challenge_5() == 0){
            user.setNb_challenge_5(1);
            user.setTotal_points(user.getTotal_points() + 1000);
        }
        if (user.getNb_won_challenges() >= 10 && user.getNb_challenge_10() == 0){
            user.setNb_challenge_10(1);
            user.setTotal_points(user.getTotal_points() + 10000);
        }
        if (user.getNb_won_challenges() >= 50 && user.getNb_challenge_50() == 0){
            user.setNb_challenge_50(1);
            user.setTotal_points(user.getTotal_points() + 40000);
        }
        if (user.getNb_won_challenges() >= 200 && user.getNb_challenge_200() == 0){
            user.setNb_challenge_200(1);
            user.setTotal_points(user.getTotal_points() + 400000);
        }
        if (user.getNb_won_challenges() >= 500 && user.getNb_challenge_500()==0){
            user.setNb_challenge_500(1);
            user.setTotal_points(user.getTotal_points() + 744000);
        }

        // DAYS BIKED
        if (user.getNb_days_biked() >= 1 && user.getBiked_days_1() == 0) {
            user.setBiked_days_1(1);
            user.setTotal_points(user.getTotal_points() + 100);
        }
        if (user.getNb_days_biked() >= 2 && user.getBiked_days_2() == 0) {
            user.setBiked_days_2(1);
            user.setTotal_points(user.getTotal_points() + 200);

        }
        if (user.getNb_days_biked() >= 5 && user.getBiked_days_5() == 0) {
            user.setBiked_days_5(1);
            user.setTotal_points(user.getTotal_points() + 2500);
        }
        if (user.getNb_days_biked() >= 7 && user.getBiked_days_7() == 0) {
            user.setBiked_days_7(1);
            user.setTotal_points(user.getTotal_points() + 5000);
        }
        if (user.getNb_days_biked() >= 14 && user.getBiked_days_14() == 0) {
            user.setBiked_days_14(1);
            user.setTotal_points(user.getTotal_points() + 8000);
        }
        if (user.getNb_days_biked() >= 31 && user.getBiked_days_31() == 0) {
            user.setBiked_days_31(1);
            user.setTotal_points(user.getTotal_points() + 10000);
        }
        if (user.getNb_days_biked() >= 100 && user.getBiked_days_100() == 0) {
            user.setBiked_days_100(1);
            user.setTotal_points(user.getTotal_points() + 40000);
        }

        // ALTITUDE
        if (user.getHighest_altitude_diff() >= 10.0f && user.getAlt_diff_10m() == 0){
            user.setAlt_diff_10m(1);
            user.setTotal_points(user.getTotal_points() + 1000);
        }
        if (user.getHighest_altitude_diff() >= 25.0f && user.getAlt_diff_25m() == 0){
            user.setAlt_diff_25m(1);
            user.setTotal_points(user.getTotal_points() + 2500);
        }
        if (user.getHighest_altitude_diff() >= 50.0f && user.getAlt_diff_50m() == 0){
            user.setAlt_diff_50m(1);
            user.setTotal_points(user.getTotal_points() + 5000);
        }
        if (user.getHighest_altitude_diff() >= 100.0f && user.getAlt_diff_100m() == 0){
            user.setAlt_diff_100m(1);
            user.setTotal_points(user.getTotal_points() + 10000);
        }

        if (user.getStart_the_game() == 0){
            user.setStart_the_game(1);
            user.setTotal_points(user.getTotal_points() + 10);
        }
        handler.overWrite(user);


    }
}



