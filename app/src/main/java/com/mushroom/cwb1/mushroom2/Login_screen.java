package com.mushroom.cwb1.mushroom2;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;
import java.util.LinkedList;


public class Login_screen extends AppCompatActivity {

    private UserHandler userHandler;

    private Button loginbutton;
    private Button registerbutton;
    private EditText usernameEdit;
    private EditText passwordEdit;
    private TextView debugView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        userHandler = new UserHandler(getApplicationContext());

        loginbutton = (Button) findViewById(R.id.button);
        registerbutton = (Button) findViewById(R.id.registerbutton);
        usernameEdit = (EditText) findViewById(R.id.editText);
        passwordEdit = (EditText) findViewById(R.id.editText2);
        debugView = (TextView) findViewById(R.id.debugView);

        usernameEdit.setSingleLine();
        passwordEdit.setSingleLine();
        debugView.setMovementMethod(new ScrollingMovementMethod());

        loginbutton.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        reset();
                        login();
                    }
                }
        );

        registerbutton.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        reset();
                        register();
                    }
                }
        );
    }

    public void login() {
        String userName = usernameEdit.getText().toString().replaceAll(" ", "_");
        String passWord = passwordEdit.getText().toString().replaceAll(" ", "_");

        if (!userName.isEmpty()) {
            if (userHandler.isExistingUser(userName)) {
                if (userHandler.isRightPassword(userName, passWord)) {
                    checkDay(userName);
                    Intent i = new Intent(getApplicationContext(), Homescreen.class);
                    i.putExtra("username", userName);
                    startActivity(i);
                    System.out.println("    -   User is logged in: " + userName);
                    finish();
                } else {
                    passwordEdit.setText("");
                    passwordEdit.setHint("Incorrect password.");
                    passwordEdit.requestFocus();
                    System.out.println("    -   Incorrect password: " + userName + ", " + passWord);
                }
            } else {
                if (debug(userName, passWord)) {
                    usernameEdit.setText("");
                    usernameEdit.requestFocus();
                    System.out.println("    -   Cmd");
                } else {
                    usernameEdit.setText("");
                    usernameEdit.setHint("Username does not exist.");
                    registerbutton.setBackgroundColor(Color.RED);
                    registerbutton.requestFocus();
                    System.out.println("    -   Not existing username");
                }
            }
        } else {
            usernameEdit.setHint("Username is needed.");
            usernameEdit.requestFocus();
            System.out.println("    -   Empty username");
        }
    }

    public void register() {
        Intent start_register = new Intent(getApplicationContext(), Register.class);
        startActivity(start_register);
    }

    private void reset() {
        usernameEdit.setHint("");
        passwordEdit.setHint("");
        registerbutton.setBackgroundColor(Color.parseColor("#2d95ff"));
    }

    private void checkDay(String userName) {
        User user = userHandler.getUserInformation(userName);

        Calendar calendar = Calendar.getInstance();
        long millisec = calendar.getTimeInMillis();

        long lastMillisec = user.getLast_login();
        Calendar lastCalendar = Calendar.getInstance();
        lastCalendar.setTimeInMillis(lastMillisec);

        boolean sameDay = calendar.get(Calendar.YEAR) == lastCalendar.get(Calendar.YEAR) &&
                calendar.get(Calendar.DAY_OF_YEAR) == lastCalendar.get(Calendar.DAY_OF_YEAR);

        if (!sameDay) {
            int nb = user.getNb_days_biked();
            user.setNb_days_biked(nb + 1);
        }
        user.setLast_login(millisec);

        userHandler.overWrite(user);
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

    private boolean debug(String command, String parameter) {
        if (command.equals("reset_app") || command.equals("reset") || command.equals("rst")) {
            DataBaseHandler2 dbHandler = new DataBaseHandler2(getApplicationContext());
            LinkedList<User> list = userHandler.getList(userHandler.getAll());
            for (User user : list) {
                dbHandler.deleteTable(user.getUser_name());
            }
            dbHandler.resetTable(dbHandler.TABLE_DEFAULT);
            userHandler.resetTable();
            return true;
        }
        if (command.equals("get_userlist") || command.equals("list") || command.equals("lst")) {
            LinkedList<User> list = userHandler.getList(userHandler.getAll());
            debugView.append("\n");
            debugView.append("List: \n");
            for (User user : list) {
                debugView.append("  - " + user.toString() + "\n");
            }
            return true;
        }
        if (command.equals("cmd") || command.equals("?") || command.equals("help")) {
            debugView.append("\n");
            debugView.append("Commands:\n");
            debugView.append("  - reset app\n");
            debugView.append("  - get userlist\n");
            debugView.append("  - clear screen\n");
            return true;
        }
        if (command.equals("clear screen") || command.equals("cls")){
            debugView.setText("");
            return true;
        }
        return false;
    }
}





