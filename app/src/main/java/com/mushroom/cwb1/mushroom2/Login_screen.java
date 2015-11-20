package com.mushroom.cwb1.mushroom2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class Login_screen extends AppCompatActivity {

    EditText username;
    EditText password;

    public String currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_login_screen);

        Button loginbutton = (Button) findViewById(R.id.button);
        Button registerbutton = (Button) findViewById(R.id.registerbutton);
        username = (EditText) findViewById(R.id.editText);
        password = (EditText) findViewById(R.id.editText2);


        loginbutton.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        UserHandler userHandler = new UserHandler(getApplicationContext());
                        String userName = username.getText().toString();
                        String passWord = password.getText().toString();

                        currentUser = userName;

                        if (userHandler.isRightPassword(userName, passWord)) {
                            Intent i = new Intent(getApplicationContext(), Homescreen.class);
                            i.putExtra("username", currentUser);
                            startActivity(i);
                        }
                    }
                }
        );


//                        Intent start_homescreen = new Intent(getApplicationContext(),Homescreen.class);
//                        startActivity(start_homescreen);
//
//                    }


        registerbutton.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        Intent start_register = new Intent(getApplicationContext(), Register.class);
                        startActivity(start_register);
                    }
                }
        );
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





