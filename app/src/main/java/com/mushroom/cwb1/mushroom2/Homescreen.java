package com.mushroom.cwb1.mushroom2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.io.UnsupportedEncodingException;
import java.util.concurrent.ExecutionException;

public class Homescreen extends AppCompatActivity {

    private String currentUser;
    private ServerConnection conn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homescreen);

        conn = new ServerConnection(getApplicationContext());
        currentUser = conn.getActiveUser();

        Button mpbutton = (Button)findViewById(R.id.button4);
        mpbutton.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                    }
                }
        );

        Button Achievementsbutton = (Button)findViewById(R.id.button2);
        Achievementsbutton.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        Intent i = new Intent(getApplicationContext(), achievements.class);
                        startActivity(i);
                    }
                }
        );

        Button rankingbutton  = (Button)findViewById(R.id.ranglijsten);
        rankingbutton.setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View v) {
                        Intent i = new Intent(getApplicationContext(), Ranking.class);
                        startActivity(i);
                    }
                }
        );

        Button rideActivitybutton = (Button)findViewById(R.id.rideActivityButton);
        rideActivitybutton.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        Intent i = new Intent(getApplicationContext(), RideActivity.class);
                        startActivity(i);
                    }
                }
        );

        Button ChallengesButton = (Button)findViewById(R.id.ChallengesButton);
        ChallengesButton.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        Intent i = new Intent(getApplicationContext(), PeopleChallenges.class);
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
                        finish();
                        conn.setActiveUser(DataBaseHandler2.TABLE_DEFAULT);
                        try {
                            conn.updateUser(currentUser);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        } finally {
                            System.out.println("    -   User is logged out: " + currentUser);
                        }
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

    @Override
    public void onStart() {
        super.onStart();

        new Thread(new Runnable() {
            public void run() {
                try {
                    conn.updateUser(currentUser);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}