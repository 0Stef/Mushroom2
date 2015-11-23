package com.mushroom.cwb1.mushroom2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class Homescreen extends AppCompatActivity {

    public String currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homescreen);

        currentUser = getIntent().getStringExtra("username");

        Button mpbutton = (Button)findViewById(R.id.button4);

        mpbutton.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        Intent i = new Intent(getApplicationContext(), MapsActivity.class);
                        startActivity(i);
                    }
                }
        );

        Button ch1button = (Button)findViewById(R.id.button3);

        ch1button.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        Intent i = new Intent(getApplicationContext(), Challenges.class);
                        startActivity(i);
                    }
                }
        );

        Button Achievementsbutton = (Button)findViewById(R.id.button2);

        Achievementsbutton.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        Intent i = new Intent(getApplicationContext(), achievements.class);
                        i.putExtra("username", currentUser);
                        startActivity(i);
                    }
                }
        );

        Button rideActivitybutton = (Button)findViewById(R.id.rideActivityButton);

        rideActivitybutton.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        Intent i = new Intent(getApplicationContext(), RideActivity.class);
                        i.putExtra("username", currentUser);
                        startActivity(i);
                    }
                }
        );

        Button persoonlijkeActivitybutton = (Button)findViewById(R.id.persoonlijkestatistieken);

        persoonlijkeActivitybutton.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        Intent i = new Intent(getApplicationContext(), Persoonlijke_statistieken.class);
                        startActivity(i);
                    }
                }
        );

        Button  LogOutButton = (Button)findViewById(R.id.logout);

        LogOutButton.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        Intent i = new Intent(getApplicationContext(), Login_screen.class);
                        startActivity(i);
                    }
                }
        );
    }

    @Override
    public void onBackPressed() {
        System.out.println("    -   I don't think so!");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_homescreen, menu);
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