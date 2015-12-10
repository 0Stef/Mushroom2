package com.mushroom.cwb1.mushroom2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.ExecutionException;

public class PeopleChallenges extends AppCompatActivity {

    private String currentUser;
    private Challenge challenge;
    private ArrayList<Challenge> invitations;

    private PeopleChallengeHandler peoplechallengehandler;
    private DataBaseHandler2 dbhandler;
    private UserHandler userhandler;
    private ServerConnection conn;

    public int award = 3000;
    public boolean isActive = true;

    private TextView root_type;
    private TextView root_time;
    private TextView root_user1;
    private TextView root_user1_value;
    private TextView root_user2;
    private TextView root_user2_value;
    private TextView root_status;
    private TextView root_com;
    private Button root_drive;
    private Button root_select;
    private Button root_show;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_people_challenges_root);

        conn = new ServerConnection(getApplicationContext());
        currentUser = conn.getActiveUser();

        userhandler = new UserHandler(getApplicationContext());
        dbhandler = new DataBaseHandler2(getApplicationContext(), currentUser);
        peoplechallengehandler = new PeopleChallengeHandler(getApplicationContext());

                //Root
        root_type = (TextView) findViewById(R.id.root_challenge_type_value);
        root_time = (TextView) findViewById(R.id.root_challenge_time_value);
        root_status = (TextView) findViewById(R.id.root_status_text);
        root_user2 = (TextView) findViewById(R.id.root_opponent);
        root_user2_value = (TextView) findViewById(R.id.root_score_opponent);
        root_user1 = (TextView) findViewById(R.id.root_current_user);
        root_user1_value = (TextView) findViewById(R.id.root_score_current_user);
        root_com = (TextView) findViewById(R.id.root_comment);

        root_drive = (Button) findViewById(R.id.root_drive_button);
        root_select = (Button) findViewById(R.id.root_selection_button);
        root_show = (Button) findViewById(R.id.root_invitations_button);

        root_select.setText(R.string.people_root_button_send);
        root_show.setText(R.string.people_root_button_show);

                //Buttons
        root_drive.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        Intent i = new Intent(getApplicationContext(), RideActivity.class);
                        startActivity(i);
                        System.out.println("    -   drive");
                    }
                }
        );

        root_select.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        System.out.println("    -   select");
                        if (root_select.getText().toString().equals(getString(R.string.people_root_button_send))) {
                            System.out.println("    -   send");
                            Intent i = new Intent(getApplicationContext(), PeopleChallengesSend.class);
                            startActivity(i);

                        } else if (root_select.getText().toString().equals(getString(R.string.people_root_button_abord))) {
                            System.out.println("    -   abord");
                            challenge.setStatus(Challenge.REFUSED);
                            if (conn.updateChallenge(challenge).equals(conn.FAILED)) {
                                root_status.setText(R.string.people_root_text_failed);
                            }
                        }
                    }
                }
        );

        root_show.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        System.out.println("    -   show");
                        Intent i = new Intent(getApplicationContext(), PeopleChallengesInvitations.class);
                        i.putExtra("invitations", invitations);
                        i.putExtra("isActive", isActive);
                        startActivity(i);
                    }
                }
        );

        //And finally..;
        refresh();
    }

    private void resetStatus() {
        root_status.setText("");
    }

    //@Override ------------------------------------------------------------------------------------

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

    @Override
    public void onStart() {
        super.onStart();

        refresh();
    }

    //Main logic -----------------------------------------------------------------------------------

    private void refresh() {
        resetChallenge();

        ArrayList<Challenge> serverChallenges = null;
        try {
            serverChallenges = conn.downloadChallenge(currentUser);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        serverChallenges = placeholdeList();
        challenge = setInvitations(serverChallenges);

        int status = challenge.getStatus();
        adaptView(status);

            //Je bent bezig met een challenge.
        if (status == Challenge.ACCEPTED) {
            update();
            checkWinner();
            conn.updateChallenge(challenge);
            showChallenge();
            root_com.setText(Challenge.getChallengeDescription(challenge.getChallenge_name()));

            //Je hebt een challenge verzonden of ontvangen.
        } else if (status == Challenge.CHALLENGED) {
            showChallenge();
            root_com.setText(R.string.people_root_text_not_accepted);

            //Je challenge is ten einde - de ander is gewonnen.
        } else if (status == Challenge.ENDED) {
            showChallenge();
            root_com.setText(R.string.people_root_text_lost);
            conn.deleteChallenge(challenge);

            //De verzonden challenge is geweigerd.
        } else if (status == Challenge.REFUSED) {
            root_com.setText(R.string.people_root_text_refused);
            conn.deleteChallenge(challenge);

            //De verbinding heeft gefaald.
        } else if (status == Challenge.FAILED){
            root_com.setText(R.string.people_root_text_fail);

            //Je hebt momenteel geen challenge.
        } else if (status == Challenge.NOT_ACTIVE) {
            root_com.setText(R.string.people_root_text_challenge);
        }
    }

    private ArrayList<Challenge> placeholdeList() {
        ArrayList<Challenge> list = new ArrayList<>();

        list.add(new Challenge(currentUser, "Adriaan", Challenge.GREATEST_DISTANCE, Challenge.NOT_ACTIVE));
        list.add(new Challenge("Bart", currentUser, Challenge.HIGHEST_ACCELERATION, Challenge.CHALLENGED));
        list.add(new Challenge("Gérard", currentUser, Challenge.HIGHEST_ALTITUDE, Challenge.CHALLENGED));
        list.add(new Challenge("Cato", currentUser, Challenge.GREATEST_DISTANCE, Challenge.CHALLENGED));

        Challenge challenge1 = new Challenge(currentUser, "Louis", Challenge.HIGHEST_ALTITUDE, Challenge.ENDED);
        challenge1.setWinner(currentUser);
        list.add(challenge1);

        Challenge challenge2 = new Challenge("Sebastian", currentUser, Challenge.GREATEST_DISTANCE, Challenge.ACCEPTED);
        challenge2.setUser1_float(200f);
        challenge2.initialiseTime(50000000l);
        //list.add(challenge2);

        return list;
    }

    //Main functions -------------------------------------------------------------------------------

    public void checkWinner() {
        User user = userhandler.getUserInformation(currentUser);
        long current_time = Calendar.getInstance().getTimeInMillis();

        if (current_time > challenge.getEnd()){

            String challengeName = challenge.getChallenge_name();
            if (challengeName.equals(Challenge.GREATEST_DISTANCE) ||
                    challengeName.equals(Challenge.HIGHEST_ACCELERATION) ||
                    challengeName.equals(Challenge.HIGHEST_SPEED)){

                if (challenge.getUser1_float() > challenge.getUser2_float()){
                    challenge.setWinner(challenge.getUser1());
                }
                else {
                    challenge.setWinner(challenge.getUser2());
                }
            }

            else if (challengeName.equals(Challenge.HIGHEST_ALTITUDE)){
                if (challenge.getUser1_double() > challenge.getUser2_double()){
                    challenge.setWinner(challenge.getUser1());
                }
                else {
                    challenge.setWinner(challenge.getUser2());
                }
            }

            String winner = challenge.getWinner();
            if (currentUser.equals(winner)){
                int prev_points = user.getTotal_points();
                user.setTotal_points(prev_points + award);
                user.setNb_won_challenges(user.getNb_won_challenges() + 1);
            }
            challenge.setStatus(Challenge.ENDED);
        }
    }

    public void update(){

        String name = challenge.getChallenge_name();
        long start = challenge.getStart();
        long end = challenge.getEnd();

        float highest_speed;
        float highest_acceleration;
        float longest_distance;

        double highest_altitude;
        double lowest_altitude;
        double highest_alt_dif;

        if (name.equals(Challenge.HIGHEST_SPEED)){

            highest_speed = dbhandler.getRow(dbhandler.getGreatestBetween(dbhandler.COLUMN_GPS_VEL, challenge.getStart(), challenge.getEnd())).getVelocity();

            if (currentUser.equals(challenge.getUser1())){
                challenge.setUser1_float(highest_speed);
            } else if(currentUser.equals(challenge.getUser2())){
                challenge.setUser2_float(highest_speed);
            }
        }

        if (name.equals(Challenge.HIGHEST_ACCELERATION)){

            highest_acceleration = dbhandler.getHighestAcceleration(dbhandler.getAllBetween(start, end));

            if (currentUser.equals(challenge.getUser1())){
                challenge.setUser1_float(highest_acceleration);
            } else if(currentUser.equals(challenge.getUser2())){
                challenge.setUser2_float(highest_acceleration);
            }
        }

        if (name.equals(Challenge.HIGHEST_ALTITUDE)){

            highest_altitude = dbhandler.getRow(dbhandler.getGreatestBetween(dbhandler.COLUMN_GPS_ALT, start, end)).getAltitude();
            lowest_altitude = dbhandler.getRow(dbhandler.getLowestBetween(dbhandler.COLUMN_GPS_ALT, start, end)).getAltitude();

            highest_alt_dif = highest_altitude - lowest_altitude;

            if (currentUser.equals(challenge.getUser1())){
                challenge.setUser1_double(highest_alt_dif);
            } else if(currentUser.equals(challenge.getUser2())){
                challenge.setUser2_double(highest_alt_dif);
            }
        }

        if (name.equals(Challenge.GREATEST_DISTANCE)){

            longest_distance = (float) dbhandler.getDistance(dbhandler.getAllBetween(start, end));

            if (currentUser.equals(challenge.getUser1())){
                challenge.setUser1_float(longest_distance);
            } else if(currentUser.equals(challenge.getUser2())){
                challenge.setUser2_float(longest_distance);
            }
        }
    }

    public Challenge setInvitations(ArrayList<Challenge> list) {
        invitations = new ArrayList<>();
        ArrayList<Challenge> other = new ArrayList<>();

        for (Challenge elem : list) {
            System.out.println(elem.toString());
            if (elem.getStatus() == Challenge.CHALLENGED && elem.getUser2().equals(currentUser)) {
                System.out.println("    -   Added invitation");
                invitations.add(elem);
            } else if (elem.getStatus() == Challenge.ENDED && elem.getWinner().equals(currentUser)) {
                System.out.println("    -   You won this one.");
                //Do nothing. You already know you won. Selbstverständlich.
            } else {
                other.add(elem);
            }
        }

        if (other.size() > 0) {
            System.out.println("other: " + other.get(0).toString());
            return other.get(0);
        } else {
            Challenge stub = new Challenge();
            stub.setStatus(Challenge.NOT_ACTIVE);
            return stub;
        }
    }

    public void adaptView(int status) {
        int size = invitations.size();
        System.out.println("    -   Size:" + size);
        if (size > 0) {
            root_show.setText(getString(R.string.people_root_button_show) + " (" + size + ")");
            System.out.println("    -   show");
        } else {
            root_show.setText(R.string.people_root_button_show);
            System.out.println("    -   don't show");
        }

        root_select.setEnabled(true);
        root_show.setEnabled(true);

        if (status == Challenge.REFUSED || status == Challenge.ENDED || status == Challenge.NOT_ACTIVE) {
            root_select.setText(R.string.people_root_button_send);
            isActive = false;
            System.out.println("    -   send");
        } else if (status == Challenge.ACCEPTED || (status == Challenge.CHALLENGED)) {
            root_select.setText(R.string.people_root_button_abord);
            isActive = true;
            System.out.println("    -   don't send");
        } else {
            if (status == Challenge.FAILED) {
                root_status.setText(R.string.people_root_text_failed);
            }
            root_select.setEnabled(false);
            root_show.setEnabled(false);
            System.out.println("    -   failed or else");
        }
    }

    private void showChallenge() {
        String challengeName = challenge.getChallenge_name();

        root_type.setText(challengeName);
        root_time.setText(Long.toString(challenge.getTimeLeft()));

        root_user1.setText(challenge.getUser1());
        root_user2.setText(challenge.getUser2());


        if (challengeName.equals(Challenge.GREATEST_DISTANCE) ||
                challengeName.equals(Challenge.HIGHEST_ACCELERATION) ||
                challengeName.equals(Challenge.HIGHEST_SPEED)){

            root_user1_value.setText(Float.toString(challenge.getUser1_float()));
            root_user2_value.setText(Float.toString(challenge.getUser2_float()));
        }

        else if (challengeName.equals(Challenge.HIGHEST_ALTITUDE)){
            root_user1_value.setText(Double.toString(challenge.getUser1_double()));
            root_user2_value.setText(Double.toString(challenge.getUser2_double()));
        }
    }

    private void resetChallenge() {
        root_type.setText("/");
        root_time.setText("/");
        root_com.setText("");

        root_user1.setText("Challenger");
        root_user2.setText("Opponent");

        root_user1_value.setText("/");
        root_user2_value.setText("/");
    }
}