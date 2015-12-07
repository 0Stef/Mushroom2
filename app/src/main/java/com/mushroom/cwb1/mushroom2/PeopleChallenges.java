package com.mushroom.cwb1.mushroom2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Spinner;
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

    public static final long HOUR = 1000 * 60 * 60;
    public static final long DAY = 1000 * 60 * 60 * 24;
    public static final long WEEK = 1000 * 60 * 60 * 24 * 7;

    public int award = 3000;

    private TextView root_type;
    private TextView root_time;
    private TextView root_opponent;
    private TextView root_opponent_value;
    private TextView root_you;
    private TextView root_you_value;
    private TextView root_status;
    private Button root_drive;
    private Button root_select;
    private Button root_show;

    private TextView inv_heading;
    private TextView inv_status;
    private TextView inv_opponent;
    private TextView inv_type;
    private Button inv_accept;
    private Button inv_refuse;
    private Button inv_prev;
    private Button inv_next;

    private TextView send_status;
    private AutoCompleteTextView send_opponent;
    private Spinner send_type;
    private Button send_send;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_challenges2);

        conn = new ServerConnection(getApplicationContext());
        currentUser = conn.getActiveUser();

        userhandler = new UserHandler(getApplicationContext());
        dbhandler = new DataBaseHandler2(getApplicationContext(), currentUser);
        peoplechallengehandler = new PeopleChallengeHandler(getApplicationContext());


                //Root
        root_type = (TextView) findViewById(R.id.root_challenge_type_value);
        root_time = (TextView) findViewById(R.id.root_challenge_time_value);
        root_status = (TextView) findViewById(R.id.root_status_text);
        root_opponent = (TextView) findViewById(R.id.root_opponent);
        root_opponent_value = (TextView) findViewById(R.id.root_score_opponent);
        root_you = (TextView) findViewById(R.id.root_current_user);
        root_you_value = (TextView) findViewById(R.id.root_score_current_user);

        root_drive = (Button) findViewById(R.id.root_drive_button);
        root_select = (Button) findViewById(R.id.root_selection_button);
        root_show = (Button) findViewById(R.id.root_invitations_button);


                //Invitations
        inv_heading = (TextView) findViewById(R.id.current_invitation);
        inv_status = (TextView) findViewById(R.id.invitation_status);
        inv_opponent = (TextView) findViewById(R.id.invitation_opponent_value);
        inv_type = (TextView) findViewById(R.id.invitation_type_value);

        inv_accept = (Button) findViewById(R.id.invitation_accept_button);
        inv_refuse = (Button) findViewById(R.id.invitation_refuse_button);
        inv_prev = (Button) findViewById(R.id.invitation_previous_button);
        inv_next = (Button) findViewById(R.id.invitation_next_button);


                //Send
        send_status = (TextView) findViewById(R.id.send_status);
        send_opponent = (AutoCompleteTextView) findViewById(R.id.send_opponent_selection);
        send_type = (Spinner) findViewById(R.id.send_type_spinner);
        send_send = (Button) findViewById(R.id.send_button_send);

        //TODO genereer deze arraylist
        String[] COUNTRIES = new String[] {"Pieter-Jan", "Jimmy", "Ted", "Alfred"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, COUNTRIES);
        //send_opponent.setAdapter(adapter);

        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(this, R.array.people_send_array_types, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


                //Buttons

        root_drive.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        //TODO to drive activity
                    }
                }
        );

        root_select.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        if (root_select.getText().toString().equals(getString(R.string.people_root_button_send))) {
                            setContentView(R.layout.activity_people_challenges_send);
                        } else if (root_select.getText().toString().equals(getString(R.string.people_root_button_abord))) {
                            //TODO abord challenge: set to REFUSED?
                        }
                    }
                }
        );

        root_show.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        setContentView(R.layout.activity_people_challenges_invitations);
                        //TODO show invitations
                    }
                }
        );

        inv_accept.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        //TODO accept invitation
                    }
                }
        );

        inv_refuse.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        //TODO refuse invitation
                    }
                }
        );

        inv_prev.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        //TODO previous invitation
                    }
                }
        );

        inv_next.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        //TODO next invitation
                    }
                }
        );

        send_type.setAdapter(spinnerAdapter);

        send_send.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        pushNewChallenge();
                    }
                }
        );

        //And finally..;
        refresh();
    }

    //Functions related to button behavior ---------------------------------------------------------

    public void pushNewChallenge() {
        resetStatus();
        Challenge invitation = new Challenge();

        String opponent = send_opponent.getText().toString();
        int type = send_type.getSelectedItemPosition();
        String challengeName = Challenge.getChallengeType(type);

        try {
            String result = conn.checkForName(opponent);
            if (result.equals(conn.TAKEN)) {
                invitation.setUser1(currentUser);
                invitation.setUser2(opponent);
                invitation.setChallenge_name(challengeName);
                invitation.setStatus(Challenge.CHALLENGED);

                String result2 = conn.createChallenge(invitation);
                if (result2.equals(ServerConnection.ADDED)) {
                    refresh();
                } else {
                    send_status.setText(R.string.people_root_text_failed);
                }
            } else if (result.equals(conn.AVAILABLE)){
                send_opponent.setText("");
                send_opponent.setHint(R.string.people_send_text_not_user);
            } else if (result.equals(conn.FAILED)) {
                send_status.setText(R.string.people_root_text_failed);
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    private void resetStatus() {
        root_status.setText("");
        inv_status.setText("");
        send_status.setText("");
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

    //Main logic -----------------------------------------------------------------------------------

    private void refresh() {
        setContentView(R.layout.activity_people_challenges_root);

        ArrayList<Challenge> serverChallenges = conn.downloadChallenge(currentUser);
        challenge = setInvitations(serverChallenges);

        int status = 42;
        if (challenge != null) status = challenge.getStatus();
        adaptView(status);

        //Je bent bezig met een challenge.
        if (status == Challenge.ACCEPTED) {

            update();
            checkWinner();
            conn.updateChallenge(challenge);

            //Je hebt een challenge verzonden of ontvangen.
        } else if (status == Challenge.CHALLENGED) {
            //TODO Feedback: challenge nog niet geaccepteerd

            //Je challenge is ten einde - de ander is gewonnen.
        } else if (status == Challenge.ENDED) {
            //TODO Feedback: verloren
            conn.deleteChallenge(challenge);

            //De verzonden challenge is geweigerd.
        } else if (status == Challenge.REFUSED) {
            //TODO Feedback: challenge geweigerd
            conn.deleteChallenge(challenge);

            //De verbinding heeft gefaald.
        } else if (status == Challenge.FAILED){
            //TODO Feedback: verbindingsproblemen.
        }
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
        for (Challenge elem : list) {
            if (elem.getStatus() == Challenge.CHALLENGED && !elem.getUser1().equals(currentUser)) {
                invitations.add(elem);
                list.remove(elem);
            }
        }

        if (list.size() > 0) {
            return list.get(0);
        } else {
            return null;
        }
    }

    public void adaptView(int status) {
        int size = invitations.size();
        if (size > 0) {
            root_show.setText(getString(R.string.people_root_button_show) + " (" + size + ")");
            root_show.setEnabled(true);
        } else {
            root_show.setText(R.string.people_root_button_show);
            root_show.setEnabled(false);
        }

        root_select.setEnabled(true);
        if (status == Challenge.REFUSED || status == Challenge.ENDED || status == 42) {
            root_select.setText(R.string.people_root_button_send);
        } else if (status == Challenge.ACCEPTED || status == Challenge.CHALLENGED) {
            root_select.setText(R.string.people_root_button_abord);
        } else {
            root_select.setEnabled(false);
            root_show.setEnabled(false);
        }
    }
}