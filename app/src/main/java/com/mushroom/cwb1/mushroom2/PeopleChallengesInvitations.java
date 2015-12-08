package com.mushroom.cwb1.mushroom2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class PeopleChallengesInvitations extends AppCompatActivity {

    private TextView inv_heading;
    private TextView inv_status;
    private TextView inv_opponent;
    private TextView inv_type;
    private Button inv_accept;
    private Button inv_refuse;
    private Button inv_prev;
    private Button inv_next;

    public static final long HOUR = 1000 * 60 * 60;
    public static final long DAY = 1000 * 60 * 60 * 24;
    public static final long WEEK = 1000 * 60 * 60 * 24 * 7;

    private ServerConnection conn;
    private ArrayList<Challenge> invitations;
    private Challenge showing;
    private int position;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_people_challenges_invitations);

        conn = new ServerConnection(getApplicationContext());

                //Invitations
        inv_heading = (TextView) findViewById(R.id.current_invitation);
        inv_status = (TextView) findViewById(R.id.invitation_status);
        inv_opponent = (TextView) findViewById(R.id.invitation_opponent_value);
        inv_type = (TextView) findViewById(R.id.invitation_type_value);

        inv_accept = (Button) findViewById(R.id.invitation_accept_button);
        inv_refuse = (Button) findViewById(R.id.invitation_refuse_button);
        inv_prev = (Button) findViewById(R.id.invitation_previous_button);
        inv_next = (Button) findViewById(R.id.invitation_next_button);

                //Buttons
        inv_accept.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        resetStatus();
                        if (showing != null) {
                            showing.setStatus(Challenge.ACCEPTED);
                            showing.initialiseTime(DAY);

                            String result = conn.updateChallenge(showing);
                            if (result.equals(conn.FAILED)) {
                                inv_status.setText("The connection failed.");
                            }
                        }
                    }
                }
        );

        inv_refuse.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        resetStatus();
                        if (showing != null) {
                            showing.setStatus(Challenge.REFUSED);
                            String result = conn.updateChallenge(showing);

                            if (result.equals(conn.FAILED)) {
                                inv_status.setText("The connection failed.");
                                showing.setStatus(Challenge.CHALLENGED);
                            } else if (result.equals(conn.SUCCES)) {
                                invitations.remove(showing);
                                position--;
                            }
                        }
                        refreshView();
                    }
                }
        );

        inv_prev.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        resetStatus();
                        position--;
                        refreshView();
                    }
                }
        );

        inv_next.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        resetStatus();
                        position++;
                        refreshView();
                    }
                }
        );

        refreshView();
    }

    private void refreshView() {
        int size = invitations.size();

        if (position < 0) position = 0;
        if (position >= size) position = size - 1;

        if (invitations.size() > 0) {
            showing = invitations.get(position);
            show(position);
        } else {
            inv_heading.setText("You don't have any invitations at the moment.");
        }
    }

    private void show(int position) {
        String separator = getString(R.string.people_inv_from);
        inv_heading.setText(position + separator + invitations.size());

        inv_opponent.setText(showing.getUser1());
        inv_type.setText(showing.getChallenge_name());
    }

    private void resetStatus() {
        inv_status.setText("");
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
}
