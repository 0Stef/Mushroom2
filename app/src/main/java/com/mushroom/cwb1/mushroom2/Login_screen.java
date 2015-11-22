package com.mushroom.cwb1.mushroom2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.LinkedList;


public class Login_screen extends AppCompatActivity {

    private UserHandler userHandler;
    private EditText usernameEdit;
    private EditText passwordEdit;
    private TextView wrongPasswordEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        userHandler = new UserHandler(getApplicationContext());

        Button loginbutton = (Button) findViewById(R.id.button);
        Button registerbutton = (Button) findViewById(R.id.registerbutton);
        usernameEdit = (EditText) findViewById(R.id.editText);
        passwordEdit = (EditText) findViewById(R.id.editText2);
        wrongPasswordEdit = (TextView) findViewById(R.id.wrong_password);


        loginbutton.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        login();
                    }
                }
        );


        registerbutton.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        Intent start_register = new Intent(getApplicationContext(), Register.class);
                        startActivity(start_register);
                    }
                }
        );
    }

    public void login() {
        String userName = usernameEdit.getText().toString();
        String passWord = passwordEdit.getText().toString();

        if (!userName.isEmpty()) {

            if (userHandler.isExistingUser(userName)) {

                if (userHandler.isRightPassword(userName, passWord)) {
                    Intent i = new Intent(getApplicationContext(), Homescreen.class);
                    i.putExtra("username", userName);
                    startActivity(i);

                    System.out.println("    -   User is logged in: " + userName);
                    wrongPasswordEdit.setText("");
                } else {
                    wrongPasswordEdit.setText("Foutief wachtwoord!");
                    System.out.println("    -   Incorrect password: " + userName + ", " + passWord);
                }

            } else {
                System.out.println("    -   Not existing username");
                wrongPasswordEdit.setText("Foutieve gebruikersnaam!");
            }
        } else {
            System.out.println("    -   Empty username");
            wrongPasswordEdit.setText("Vul gebruikersnaam in.");
        }

        debug(userName, passWord);
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

    private void debug(String command, String parameter) {
        if (command.equals("reset userhandler")) {
            userHandler.resetTable();
        }
        if (command.equals("reset databasehandler")) {
            DataBaseHandler2 dbHandler = new DataBaseHandler2(getApplicationContext());
            dbHandler.resetTable(parameter);
        }
        if (command.equals("get userlist")) {
            LinkedList<User> list = userHandler.getList(userHandler.getAll());
            for (User user : list) {
                System.out.println("    -   " + user.toString());
            }
        }
        if (command.equals("delete user")) {
            User user = new User();
            userHandler.overWrite(user);
        }
        if (command.equals("list")) {
            System.out.println("");
            System.out.println("");

            System.out.println("    Commands:");
            System.out.println("        reset userhandler");
            System.out.println("        reset databasehandler & parameter");
            System.out.println("        get userlist");
            System.out.println("        delete user");

            System.out.println("");
            System.out.println("");
        }
    }
}





