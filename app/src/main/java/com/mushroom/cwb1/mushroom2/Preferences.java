package com.mushroom.cwb1.mushroom2;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class Preferences extends AppCompatActivity {

    private ServerConnection conn;
    private DataBaseHandler2 dbHandler;
    private UserHandler userHandler;
    private String currentUser;

    private Button localButton;
    private boolean localPressed = false;

    private Button allButton;
    private boolean allPressed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);

        conn = new ServerConnection(getApplicationContext());
        dbHandler = new DataBaseHandler2(getApplicationContext());
        userHandler = new UserHandler(getApplicationContext());

        currentUser = conn.getActiveUser();

        localButton = (Button) findViewById(R.id.preferences_remove_local);
        allButton = (Button) findViewById(R.id.preference_remove_all);

        allButton.setEnabled(false);

        localButton.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        if (localPressed) {
                            deleteLocal();
                            stop();
                        } else {
                            localPressed = true;
                            localButton.setBackgroundColor(Color.RED);
                            localButton.setText(R.string.preferences_button_confirm);
                        }
                    }
                }
        );

        allButton.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        if (allPressed) {
                            deleteLocal();
                            deleteServer();
                            stop();
                        } else {
                            allPressed = true;
                            allButton.setBackgroundColor(Color.RED);
                            localButton.setText(R.string.preferences_button_confirm);
                        }
                    }
                }
        );
    }

    private void deleteLocal() {
        if (userHandler.isExistingUser(currentUser)) {
            dbHandler.deleteTable(currentUser);
            userHandler.deleteUser(currentUser);
        }
    }

    private void deleteServer() {
        //TODO
    }

    private void stop() {
        Intent i = getBaseContext().getPackageManager().getLaunchIntentForPackage(getBaseContext().getPackageName());
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);

        conn.setActiveUser(DataBaseHandler2.TABLE_DEFAULT);
        finish();
    }
}
