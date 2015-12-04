package com.mushroom.cwb1.mushroom2;

import android.support.v7.app.AppCompatActivity;

public class Challenges extends AppCompatActivity {

//    PeopleChallenge current_challenge;
//
//
//    PeopleChallengeHandler peoplechallengehandler;
//    DataBaseHandler2 dbhandler;
//    UserHandler userhandler;
//    ServerConnection conn;
//
//    String currentUser;
//
//    private static final long DAY = 1000 * 60 * 60 * 24;
//
//    private Button GetHighestSpeedButton;
//    private Button GetHighestAccelerationButton;
//    private Button GetHighestAltitudeDifferenceButton;
//    private Button GetLongestDistanceButton;
//    private EditText EnemyEdit;
//    private TextView text_current_challenge;
//    private Button InviteButton;
//    private TextView InvitePerson;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_challenges2);
//
//        conn = new ServerConnection(getApplicationContext());
//        currentUser = conn.getActiveUser();
//
//        userhandler = new UserHandler(getApplicationContext());
//        peoplechallengehandler = new PeopleChallengeHandler(getApplicationContext());
//
//        // TODO lijst verschillende mogelijke tegenstanders
//
//        GetHighestSpeedButton = (Button)findViewById(R.id.button_highest_speed);
//        GetHighestAccelerationButton = (Button)findViewById(R.id.button_highest_acceleration);
//        GetHighestAltitudeDifferenceButton = (Button)findViewById(R.id.button_highest_altitude_difference);
//        GetLongestDistanceButton = (Button)findViewById(R.id.button_longest_distance);
//        text_current_challenge = (TextView)findViewById(R.id.chosen_challenge);
//        EnemyEdit = (EditText)findViewById(R.id.enemy_name);
//        InviteButton = (Button)findViewById(R.id.invite_button);
//        InvitePerson = (TextView)findViewById(R.id.text_invite);
//        InviteButton.setVisibility(View.INVISIBLE);
//
//
//        if (peoplechallengehandler.isChallenged()) {
//            challenges();
//        } else {
//            final PeopleChallenge challenge = checkServer();
//            if (!challenge.getUser1().isEmpty()) {
//                InvitePerson.setText(challenge.getUser1());
//                InviteButton.setVisibility(View.VISIBLE);
//            } else {
//                ServerConnection.removeChallenge(challengeName);
//            }
//        }
//
//        GetHighestSpeedButton.setOnClickListener(
//                new Button.OnClickListener() {
//                    public void onClick(View v) {
//                        pushNewChallenge("Highest_Speed");
//                        text_current_challenge.setText("Highest speed");
//                    }
//                }
//        );
//
//        GetHighestAccelerationButton.setOnClickListener(
//                new Button.OnClickListener() {
//                    public void onClick(View v) {
//                        pushNewChallenge("Highest_Acceleration");
//                        text_current_challenge.setText("Highest acceleration");
//
//                    }
//                }
//        );
//
//        GetHighestAltitudeDifferenceButton.setOnClickListener(
//                new Button.OnClickListener() {
//                    public void onClick(View v) {
//                        pushNewChallenge("Highest_Altitude");
//                        text_current_challenge.setText("Highest altitude");
//
//                    }
//                }
//        );
//
//        GetLongestDistanceButton.setOnClickListener(
//                new Button.OnClickListener() {
//                    public void onClick(View v) {
//                        pushNewChallenge("Longest_Distance");
//                        text_current_challenge.setText("Longest distance");
//
//                    }
//                }
//        );
//
//        InviteButton.setOnClickListener(
//                new Button.OnClickListener() {
//                    public void onClick(View v) {
//                        challenge.setStatus(1);
//                        long millisec = Calendar.getInstance().getTimeInMillis();
//                        challenge.setStart(millisec);
//                        challenge.setEnd(millisec + DAY);
//                        peoplechallengehandler.addChallenge(challenge);
//                    }
//                }
//        );
//    }
//
//    public void checkWinner() {
//        User user = userhandler.getUserInformation(currentUser);
//        long current_time = Calendar.getInstance().getTimeInMillis();
//
//        if (current_time > current_challenge.getEnd()){
//            if (current_challenge.getChallenge_name().equals("Longest_distance") || current_challenge.getChallenge_name().equals("Highest_Acceleration") || current_challenge.getChallenge_name().equals("Highest_speed")){
//                if (current_challenge.getUser1_float() > current_challenge.getUser2_float()){
//                    current_challenge.setWinner(current_challenge.getUser1());
//                }
//                else {
//                    current_challenge.setWinner(current_challenge.getUser2());
//                }
//            }
//
//            else if (current_challenge.getChallenge_name().equals("Highest_Altitude")){
//                if (current_challenge.getUser1_double() > current_challenge.getUser2_double()){
//                    current_challenge.setWinner(current_challenge.getUser1());
//                }
//                else {
//                    current_challenge.setWinner(current_challenge.getUser2());
//                }
//            }
//
//            String winner  = current_challenge.getWinner();
//
//            if (currentUser.equals(winner)){
//                int prev_points = user.getTotal_points();
//                user.setTotal_points(prev_points + 3000);
//                // TODO ook de challenges tegen andere spelers??
//                user.setNb_won_challenges(user.getNb_won_challenges() + 1);
//
//            }
//            peoplechallengehandler.resetTable();
//            //TODO Verwijder challenge op de server.
//        }
//        userhandler.overWrite(user);
//    }
//
//    public void pushNewChallenge(String challengeName) {
//        PeopleChallenge peeps = new PeopleChallenge();
//
//        String opponent = EnemyEdit.getText().toString();
//        String result = null;
//        try {
//            result = conn.checkForName(opponent);
//            if (result.equals(conn.TAKEN)) {
//                peeps.setUser1(currentUser);
//                peeps.setUser2(opponent);
//                peeps.setChallenge_name(challengeName);
//                peoplechallengehandler.addChallenge(current_challenge);
//                disappearButtons();
//            } else if (result.equals(conn.FAILED)) {
//                //Do something
//            }
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//
//    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_challenges, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
//
//
//    public void update(){
//
//
//        float highest_speed;
//        float highest_acceleration;
//        float longest_distance;
//
//
//        double highest_altitude;
//        double lowest_altitude;
//        double highest_alt_dif;
//
//
//        if (current_challenge.getChallenge_name().equals("Highest_Speed")){
//
//            highest_speed = dbhandler.getRow(dbhandler.getGreatestBetween(dbhandler.COLUMN_GPS_VEL,current_challenge.getStart(),current_challenge.getEnd())).getVelocity();
//
//            if (currentUser.equals(current_challenge.getUser1())){
//                current_challenge.setUser1_float(highest_speed);
//            }
//
//            else if(currentUser.equals(current_challenge.getUser2())){
//                current_challenge.setUser2_float(highest_speed);
//            }
//
//        }
//
//        if (current_challenge.getChallenge_name().equals("Highest_Acceleration")){
//
//            highest_acceleration = dbhandler.getHighestAcceleration(dbhandler.getAllBetween(current_challenge.getStart(),current_challenge.getEnd()));
//
//            if (currentUser.equals(current_challenge.getUser1())){
//                current_challenge.setUser1_float(highest_acceleration);
//            }
//
//            else if(currentUser.equals(current_challenge.getUser2())){
//                current_challenge.setUser2_float(highest_acceleration);
//            }
//
//        }
//
//        if (current_challenge.getChallenge_name().equals("Highest_Altitude")){
//
//            highest_altitude = dbhandler.getRow(dbhandler.getGreatestBetween(dbhandler.COLUMN_GPS_ALT, current_challenge.getStart(), current_challenge.getEnd())).getAltitude();
//            lowest_altitude = dbhandler.getRow(dbhandler.getLowestBetween(dbhandler.COLUMN_GPS_ALT,current_challenge.getStart(),current_challenge.getEnd())).getAltitude();
//
//            highest_alt_dif = highest_altitude-lowest_altitude;
//
//            if (currentUser.equals(current_challenge.getUser1())){
//                current_challenge.setUser1_double(highest_alt_dif);
//            }
//
//            else if(currentUser.equals(current_challenge.getUser2())){
//                current_challenge.setUser2_double(highest_alt_dif);
//            }
//
//
//        }
//
//        if (current_challenge.getChallenge_name().equals("Longest_Distance")){
//
//            longest_distance = (float) dbhandler.getDistance(dbhandler.getAllBetween(current_challenge.getStart(),current_challenge.getEnd()));
//
//            if (currentUser.equals(current_challenge.getUser1())){
//                current_challenge.setUser1_float(longest_distance);
//            }
//
//            else if(currentUser.equals(current_challenge.getUser2())){
//                current_challenge.setUser2_float(longest_distance);
//            }
//
//
//        }
//
//        peoplechallengehandler.overWrite(current_challenge);
//
//    }
//
//    public void disappearButtons(){
//        final Button GetHighestSpeedButton = (Button)findViewById(R.id.button_highest_speed);
//        final Button GetHighestAccelerationButton = (Button)findViewById(R.id.button_highest_acceleration);
//        final Button GetHighestAltitudeDifferenceButton = (Button)findViewById(R.id.button_highest_altitude_difference);
//        final Button GetLongestDistanceButton = (Button)findViewById(R.id.button_longest_distance);
//
//
//        GetHighestSpeedButton.setVisibility(View.INVISIBLE);
//        GetHighestAccelerationButton.setVisibility(View.INVISIBLE);
//        GetHighestAltitudeDifferenceButton.setVisibility(View.INVISIBLE);
//        GetLongestDistanceButton.setVisibility(View.INVISIBLE);
//
//
//    }
//
//    public void challenges(){
//        current_challenge = peoplechallengehandler.getCurrentChallenge();
//        text_current_challenge.setText(current_challenge.getChallenge_name());
//        disappearButtons();
//
//        update();
//        checkWinner();
//        ServerConnection.upload();
//    }


}
