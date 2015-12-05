package com.mushroom.cwb1.mushroom2;

import android.support.v7.app.AppCompatActivity;

public class Challenges extends AppCompatActivity {

    /*private String currentUser;
    private Challenge challenge;

    private PeopleChallengeHandler peoplechallengehandler;
    private DataBaseHandler2 dbhandler;
    private UserHandler userhandler;
    private ServerConnection conn;

    public static final long HOUR = 1000 * 60 * 60;
    public static final long DAY = 1000 * 60 * 60 * 24;
    public static final long WEEK = 1000 * 60 * 60 * 24 * 7;

    public int award = 3000;

    private Button GetHighestSpeedButton;
    private Button GetHighestAccelerationButton;
    private Button GetHighestAltitudeDifferenceButton;
    private Button GetLongestDistanceButton;
    private EditText enemyEdit;
    private TextView textCurrentChallenge;
    private Button inviteButton;
    private TextView invitePerson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenges2);

        conn = new ServerConnection(getApplicationContext());
        currentUser = conn.getActiveUser();

        userhandler = new UserHandler(getApplicationContext());
        peoplechallengehandler = new PeopleChallengeHandler(getApplicationContext());

        // TODO lijst verschillende mogelijke tegenstanders

        GetHighestSpeedButton = (Button)findViewById(R.id.button_highest_speed);
        GetHighestAccelerationButton = (Button)findViewById(R.id.button_highest_acceleration);
        GetHighestAltitudeDifferenceButton = (Button)findViewById(R.id.button_highest_altitude_difference);
        GetLongestDistanceButton = (Button)findViewById(R.id.button_longest_distance);
        textCurrentChallenge = (TextView)findViewById(R.id.chosen_challenge);
        enemyEdit = (EditText)findViewById(R.id.enemy_name);
        inviteButton = (Button)findViewById(R.id.invite_button);
        invitePerson = (TextView)findViewById(R.id.text_invite);
        inviteButton.setVisibility(View.INVISIBLE);


        challenge = conn.downloadChallenge(currentUser);

        //Je bent bezig met een challenge.
        int status = challenge.getStatus();
        if (status == Challenge.ACCEPTED) {
            textCurrentChallenge.setText(challenge.getChallenge_name());
            disappearButtons();

            update();
            checkWinner();
            conn.updateChallenge(challenge);

            //Je hebt een challenge verzonden of ontvangen.
        } else if (status == Challenge.CHALLENGED) {
            if (challenge.getUser1().equals(currentUser)) {
                //TODO Feedback: challenge nog niet geaccepteerd
                disappearButtons();
            } else {
                //TODO Feedback: er zijn challenges voor de gebruiker
                //FIXME wat als er meerdere challenges zijn?
                //TODO challene accepteren of weigeren
                conn.updateChallenge(challenge);
            }

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
            disappearButtons();
        }

        GetHighestSpeedButton.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        pushNewChallenge(Challenge.HIGHEST_SPEED, R.string.peeps_highest_speed);
                    }
                }
        );

        GetHighestAccelerationButton.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        pushNewChallenge(Challenge.HIGHEST_ACCELERATION, R.string.peeps_highest_acceleration);
                                            }
                }
        );

        GetHighestAltitudeDifferenceButton.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        pushNewChallenge(Challenge.HIGHEST_ALTITUDE, R.string.peeps_highest_altitude);
                    }
                }
        );

        GetLongestDistanceButton.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        pushNewChallenge(Challenge.GREATEST_DISTANCE, R.string.peeps_greatest_distance);
                    }
                }
        );

        inviteButton.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        challenge.setStatus(1);
                        long millisec = Calendar.getInstance().getTimeInMillis();
                        challenge.setStart(millisec);
                        challenge.setEnd(millisec + DAY);
                        peoplechallengehandler.addChallenge(challenge);
                    }
                }
        );
    }

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

            String winner  = challenge.getWinner();
            if (currentUser.equals(winner)){
                int prev_points = user.getTotal_points();
                user.setTotal_points(prev_points + award);
                user.setNb_won_challenges(user.getNb_won_challenges() + 1);
            }
            challenge.setStatus(Challenge.ENDED);
        }
    }

    public void pushNewChallenge(String challengeName, int text) {
        Challenge peeps = new Challenge();

        String opponent = enemyEdit.getText().toString();

        try {
            String result = conn.checkForName(opponent);
            if (result.equals(conn.TAKEN)) {
                peeps.setUser1(currentUser);
                peeps.setUser2(opponent);
                peeps.setChallenge_name(challengeName);
                peeps.setStatus(Challenge.CHALLENGED);

                disappearButtons();
                textCurrentChallenge.setText(text);
                conn.uploadChallenge();
                //TODO Feedback: challenge verzonden naar server.
            } else if (result.equals(conn.FAILED)) {
                //Do something
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
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

        if (challenge.getChallenge_name().equals("Highest_Speed")){

            highest_speed = dbhandler.getRow(dbhandler.getGreatestBetween(dbhandler.COLUMN_GPS_VEL, challenge.getStart(), challenge.getEnd())).getVelocity();

            if (currentUser.equals(challenge.getUser1())){
                challenge.setUser1_float(highest_speed);
            }

            else if(currentUser.equals(challenge.getUser2())){
                challenge.setUser2_float(highest_speed);
            }
        }

        if (challenge.getChallenge_name().equals("Highest_Acceleration")){

            highest_acceleration = dbhandler.getHighestAcceleration(dbhandler.getAllBetween(challenge.getStart(), challenge.getEnd()));

            if (currentUser.equals(challenge.getUser1())){
                challenge.setUser1_float(highest_acceleration);
            }

            else if(currentUser.equals(challenge.getUser2())){
                challenge.setUser2_float(highest_acceleration);
            }
        }

        if (challenge.getChallenge_name().equals("Highest_Altitude")){

            highest_altitude = dbhandler.getRow(dbhandler.getGreatestBetween(dbhandler.COLUMN_GPS_ALT, challenge.getStart(), challenge.getEnd())).getAltitude();
            lowest_altitude = dbhandler.getRow(dbhandler.getLowestBetween(dbhandler.COLUMN_GPS_ALT, challenge.getStart(), challenge.getEnd())).getAltitude();

            highest_alt_dif = highest_altitude-lowest_altitude;

            if (currentUser.equals(challenge.getUser1())){
                challenge.setUser1_double(highest_alt_dif);
            }

            else if(currentUser.equals(challenge.getUser2())){
                challenge.setUser2_double(highest_alt_dif);
            }
        }

        if (challenge.getChallenge_name().equals("Longest_Distance")){

            longest_distance = (float) dbhandler.getDistance(dbhandler.getAllBetween(challenge.getStart(), challenge.getEnd()));

            if (currentUser.equals(challenge.getUser1())){
                challenge.setUser1_float(longest_distance);
            }

            else if(currentUser.equals(challenge.getUser2())){
                challenge.setUser2_float(longest_distance);
            }
        }

        peoplechallengehandler.overWrite(challenge);
    }

    public void disappearButtons(){
        final Button GetHighestSpeedButton = (Button)findViewById(R.id.button_highest_speed);
        final Button GetHighestAccelerationButton = (Button)findViewById(R.id.button_highest_acceleration);
        final Button GetHighestAltitudeDifferenceButton = (Button)findViewById(R.id.button_highest_altitude_difference);
        final Button GetLongestDistanceButton = (Button)findViewById(R.id.button_longest_distance);


        GetHighestSpeedButton.setVisibility(View.INVISIBLE);
        GetHighestAccelerationButton.setVisibility(View.INVISIBLE);
        GetHighestAltitudeDifferenceButton.setVisibility(View.INVISIBLE);
        GetLongestDistanceButton.setVisibility(View.INVISIBLE);
    }*/
}
