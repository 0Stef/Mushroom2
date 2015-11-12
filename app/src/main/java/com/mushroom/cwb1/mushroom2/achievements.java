package com.mushroom.cwb1.mushroom2;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;

public class achievements extends AppCompatActivity {


    UserHandler handler;
    User user;
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

    ProgressBar progress_below_seelvl;
    ProgressBar progress_above_1000;
    ProgressBar progress_alt_diff_10;
    ProgressBar progress_alt_diff_25;
    ProgressBar progress_alt_diff_50;
    ProgressBar progress_alt_diff_100;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achievements);

        //handler = new UserHandler(getApplicationContext());
        //handler.onUpgrade(handler.getWritableDatabase(), 0, 0);

        progress_1_km = (ProgressBar) findViewById(R.id.progress_1_km);
        progress_5_km = (ProgressBar) findViewById(R.id.progress_5_km);
        progress_10_km = (ProgressBar) findViewById(R.id.progress_10_km);
        progress_50_km = (ProgressBar) findViewById(R.id.progress_50_km);
        progress_100_km = (ProgressBar) findViewById(R.id.progress_100_km);
        progress_250_km = (ProgressBar) findViewById(R.id.progress_250_km);
        progress_500_km = (ProgressBar) findViewById(R.id.progress_500_km);
        progress_1000_km = (ProgressBar) findViewById(R.id.progress_1000_km);
        progress_5000_km = (ProgressBar) findViewById(R.id.progress_5000_km);

        progress_topspeed_30 = (ProgressBar) findViewById(R.id.progress_topspeed_30);
        progress_topspeed_35 = (ProgressBar) findViewById(R.id.progress_topspeed_35);
        progress_topspeed_40 = (ProgressBar) findViewById(R.id.progress_topspeed_40);
        progress_topspeed_45 = (ProgressBar) findViewById(R.id.progress_topspeed_45);
        progress_topspeed_50 = (ProgressBar) findViewById(R.id.progress_topspeed_50);

        progress_nb_challenge_1 = (ProgressBar) findViewById(R.id.progress_nb_challenge_1);
        progress_nb_challenge_5 = (ProgressBar) findViewById(R.id.progress_nb_challenge_5);
        progress_nb_challenge_10 = (ProgressBar) findViewById(R.id.progress_nb_challenge_10);
        progress_nb_challenge_50 = (ProgressBar) findViewById(R.id.progress_nb_challenge_50);
        progress_nb_challenge_200 = (ProgressBar) findViewById(R.id.progress_nb_challenge_200);
        progress_nb_challenge_500 = (ProgressBar) findViewById(R.id.progress_nb_challenge_500);

        progress_days_biked_1  = (ProgressBar) findViewById(R.id.progress_days_biked_1);
        progress_days_biked_2  = (ProgressBar) findViewById(R.id.progress_days_biked_2);
        progress_days_biked_5  = (ProgressBar) findViewById(R.id.progress_days_biked_5);
        progress_days_biked_7  = (ProgressBar) findViewById(R.id.progress_days_biked_7);
        progress_days_biked_14  = (ProgressBar) findViewById(R.id.progress_days_biked_14);
        progress_days_biked_31  = (ProgressBar) findViewById(R.id.progress_days_biked_31);
        progress_days_biked_100  = (ProgressBar) findViewById(R.id.progress_days_biked_100);

        progress_below_seelvl = (ProgressBar) findViewById(R.id.progress_below_seelvl);
        progress_above_1000 = (ProgressBar) findViewById(R.id.progress_above_1000);
        progress_alt_diff_10 = (ProgressBar) findViewById(R.id.progress_alt_diff_10);
        progress_alt_diff_25 = (ProgressBar) findViewById(R.id.progress_alt_diff_25);
        progress_alt_diff_50 = (ProgressBar) findViewById(R.id.progress_alt_diff_50);
        progress_alt_diff_100 = (ProgressBar) findViewById(R.id.progress_alt_diff_100);

        //EXAMPLE
        //progress_1_km.setProgress((int)(999.9/10.0));
        //progress_1_km.getProgressDrawable().setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_IN);

        //TODO verder uitwerken
/*        if (user.getTotal_distance() >= 1000){
            user.setDrive_1_km(1);
        }*/

        // DISTANCE ACHIEVEMENTS

        if (user.getDrive_1_km() == 1 ){
            progress_1_km.getProgressDrawable().setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_IN);
        }
        if (user.getDrive_5_km() == 1 ){
            progress_1_km.getProgressDrawable().setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_IN);
        }
        if (user.getDrive_10_km() == 1 ){
            progress_1_km.getProgressDrawable().setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_IN);
        }
        if (user.getDrive_50_km() == 1 ){
            progress_1_km.getProgressDrawable().setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_IN);
        }
        if (user.getDrive_100_km() == 1 ){
            progress_1_km.getProgressDrawable().setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_IN);
        }
        if (user.getDrive_250_km() == 1 ){
            progress_1_km.getProgressDrawable().setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_IN);
        }
        if (user.getDrive_500_km() == 1 ){
            progress_1_km.getProgressDrawable().setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_IN);
        }
        if (user.getDrive_1000_km() == 1 ){
            progress_1_km.getProgressDrawable().setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_IN);
        }
        if (user.getDrive_5000_km() == 1 ){
            progress_1_km.getProgressDrawable().setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_IN);
        }

        progress_1_km.setProgress((int) (user.getTotal_distance()/10.0));
        progress_5_km.setProgress((int) (user.getTotal_distance()/10.0));
        progress_10_km.setProgress((int)(user.getTotal_distance()/10.0));
        progress_50_km.setProgress((int)(user.getTotal_distance()/10.0));
        progress_100_km.setProgress((int)(user.getTotal_distance()/10.0));
        progress_250_km.setProgress((int)(user.getTotal_distance()/10.0));
        progress_500_km.setProgress((int)(user.getTotal_distance()/10.0));
        progress_1000_km.setProgress((int)(user.getTotal_distance()/10.0));
        progress_5000_km.setProgress((int)(user.getTotal_distance()/10.0));

        if (user.getTopspeed_30() == 1){
            progress_topspeed_30.getProgressDrawable().setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_IN);
        }
        if (user.getTopspeed_35() == 1){
            progress_topspeed_35.getProgressDrawable().setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_IN);
        }
        if (user.getTopspeed_40() == 1){
            progress_topspeed_40.getProgressDrawable().setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_IN);
        }
        if (user.getTopspeed_45() == 1){
            progress_topspeed_45.getProgressDrawable().setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_IN);
        }
        if (user.getTopspeed_50() == 1){
            progress_topspeed_50.getProgressDrawable().setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_IN);
        }
        // moet dit erbij??
//        progress_topspeed_30.setProgress((int) ((user.getHighest_speed()/30.0)*100));
//        progress_topspeed_35.setProgress((int) ((user.getHighest_speed()/35.0)*100));
//        progress_topspeed_40.setProgress((int) ((user.getHighest_speed()/40.0)*100));
//        progress_topspeed_45.setProgress((int) ((user.getHighest_speed()/45.0)*100));
//        progress_topspeed_50.setProgress((int) ((user.getHighest_speed()/50.0)*100));
//

        if (user.getNb_challenge_1() == 1){
            progress_nb_challenge_1.getProgressDrawable().setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_IN);
        }
        if (user.getNb_challenge_5() == 1){
            progress_nb_challenge_5.getProgressDrawable().setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_IN);
        }
        if (user.getNb_challenge_10() == 1){
            progress_nb_challenge_10.getProgressDrawable().setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_IN);
        }
        if (user.getNb_challenge_50() == 1){
            progress_nb_challenge_50.getProgressDrawable().setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_IN);
        }
        if (user.getNb_challenge_200() == 1){
            progress_nb_challenge_200.getProgressDrawable().setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_IN);
        }
        if (user.getNb_challenge_500() == 1){
            progress_nb_challenge_500.getProgressDrawable().setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_IN);
        }

        progress_nb_challenge_1.setProgress((int) ((user.getNb_won_challenges()/1.0)*100));
        progress_nb_challenge_5.setProgress((int) ((user.getNb_won_challenges()/5.0)*100));
        progress_nb_challenge_10.setProgress((int) ((user.getNb_won_challenges()/10.0)*100));
        progress_nb_challenge_50.setProgress((int) ((user.getNb_won_challenges()/50.0)*100));
        progress_nb_challenge_200.setProgress((int) ((user.getNb_won_challenges()/200.0)*100));
        progress_nb_challenge_500.setProgress((int) ((user.getNb_won_challenges()/500.0)*100));

        if (user.getBiked_days_1() == 1){
            progress_days_biked_1.getProgressDrawable().setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_IN);
        }
        if (user.getBiked_days_2() == 1){
            progress_days_biked_2.getProgressDrawable().setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_IN);
        }
        if (user.getBiked_days_5() == 1){
            progress_days_biked_5.getProgressDrawable().setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_IN);
        }
        if (user.getBiked_days_7() == 1){
            progress_days_biked_7.getProgressDrawable().setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_IN);
        }
        if (user.getBiked_days_14() == 1){
            progress_days_biked_14.getProgressDrawable().setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_IN);
        }
        if (user.getBiked_days_31() == 1){
            progress_days_biked_31.getProgressDrawable().setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_IN);
        }
        if (user.getBiked_days_100() == 1){
            progress_days_biked_100.getProgressDrawable().setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_IN);
        }

        progress_days_biked_1.setProgress((int) ((user.getNb_days_biked()/1.0)*100));
        progress_days_biked_2.setProgress((int) ((user.getNb_days_biked()/2.0)*100));
        progress_days_biked_5.setProgress((int) ((user.getNb_days_biked()/5.0)*100));
        progress_days_biked_7.setProgress((int) ((user.getNb_days_biked()/7.0)*100));
        progress_days_biked_14.setProgress((int) ((user.getNb_days_biked()/14.0)*100));
        progress_days_biked_31.setProgress((int) ((user.getNb_days_biked()/31.0)*100));
        progress_days_biked_100.setProgress((int) ((user.getNb_days_biked()/100.0)*100));

        if (user.getBelow_seelvl() == 1){
            progress_below_seelvl.getProgressDrawable().setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_IN);
        }
        if (user.getAbove_1000m() == 1){
            progress_above_1000.getProgressDrawable().setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_IN);
        }
        if (user.getAlt_diff_10m() == 1){
            progress_alt_diff_10.getProgressDrawable().setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_IN);
        }
        if (user.getAlt_diff_25m() == 1){
            progress_alt_diff_25.getProgressDrawable().setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_IN);
        }
        if (user.getAlt_diff_50m() == 1){
            progress_alt_diff_50.getProgressDrawable().setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_IN);
        }
        if (user.getAlt_diff_100m() == 1){
            progress_alt_diff_100.getProgressDrawable().setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_IN);
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
}
