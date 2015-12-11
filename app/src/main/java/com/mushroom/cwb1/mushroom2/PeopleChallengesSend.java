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
import java.util.concurrent.ExecutionException;

public class PeopleChallengesSend extends AppCompatActivity {

    private String currentUser;
    private ServerConnection conn;

    private TextView send_status;
    private AutoCompleteTextView send_opponent;
    private Spinner send_type;
    private Button send_send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_people_challenges_send);

        conn = new ServerConnection(getApplicationContext());
        currentUser = conn.getActiveUser();

                //Send
        setContentView(R.layout.activity_people_challenges_send);
        send_status = (TextView) findViewById(R.id.send_status);
        send_opponent = (AutoCompleteTextView) findViewById(R.id.send_opponent_selection);
        send_type = (Spinner) findViewById(R.id.send_type_spinner);
        send_send = (Button) findViewById(R.id.send_button_send);

                //Buttons & other
        //TODO genereer deze arraylist
        String[] COUNTRIES = new String[] {"Pieter-Jan", "Jimmy", "Ted", "Alfred"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, COUNTRIES);
        send_opponent.setAdapter(adapter);

        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(this, R.array.people_send_array_types, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        send_type.setAdapter(spinnerAdapter);

        send_send.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        pushNewChallenge();
                    }
                }
        );
    }

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
                System.out.println("    -   challengeName: " + challengeName);
                invitation.setChallenge_name(challengeName);
                invitation.setStatus(Challenge.CHALLENGED);

                String result2 = conn.createChallenge(invitation);
                if (result2.equals(ServerConnection.ADDED)) {
                    finish();
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
        send_status.setText("");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_people_challenge_root, menu);
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
