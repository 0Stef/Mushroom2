package com.mushroom.cwb1.mushroom2;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.PasswordTransformationMethod;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;


public class Login_screen extends AppCompatActivity {

    private UserHandler userHandler;
    private DataBaseHandler2 dbHandler;
    private Debug debug;
    private ServerConnection conn;

    private Button loginbutton;
    private Button registerbutton;
    private EditText usernameEdit;
    private EditText passwordEdit;
    private TextView debugView;
    private TextView statusView;

    private String dataToPut;
    private static ArrayList<String> serverCheckResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        loginbutton = (Button) findViewById(R.id.button);
        registerbutton = (Button) findViewById(R.id.registerbutton);
        usernameEdit = (EditText) findViewById(R.id.editText);
        passwordEdit = (EditText) findViewById(R.id.editText2);
        debugView = (TextView) findViewById(R.id.debugView);
        statusView = (TextView) findViewById(R.id.wrong_password);

        userHandler = new UserHandler(getApplicationContext());
        dbHandler = new DataBaseHandler2(getApplicationContext());
        debug = new Debug(getApplicationContext(), debugView);
        conn = new ServerConnection(getApplicationContext());

        usernameEdit.setSingleLine();
        passwordEdit.setSingleLine();
        passwordEdit.setTransformationMethod(new PasswordTransformationMethod());
        passwordEdit.setTypeface(Typeface.DEFAULT);

        debugView.setMovementMethod(new ScrollingMovementMethod());

        loginbutton.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        reset();
                        try {
                            login();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
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

    public void login() throws ExecutionException, InterruptedException, UnsupportedEncodingException {
        String userName = usernameEdit.getText().toString().replaceAll("[^a-zA-Z0-9]+", "_");
        String passWord = passwordEdit.getText().toString().replaceAll("[^a-zA-Z0-9]+", "_");

        if (!userName.isEmpty()) {
            if (userHandler.isExistingUser(userName)) {
                if (userHandler.isRightPassword(userName, passWord)) {
                    //conn.updateUser(userName);
                    conn.setActiveUser(userName);
                    userHandler.checkDay(userName);
                    Intent i = new Intent(getApplicationContext(), Homescreen.class);
                    startActivity(i);
                    System.out.println("    -   User is logged in: " + userName);
                    finish();
                } else {
                    passwordEdit.setText("");
                    passwordEdit.setHint(R.string.login_text_incorrect);
                    passwordEdit.requestFocus();
                    System.out.println("    -   Incorrect password: " + userName + ", " + passWord);
                }
            } else {
                if (debug.execute(userName, passWord)) {
                    System.out.println("    -   " + passWord);
                    usernameEdit.setText("");
                    passwordEdit.setText("");
                    usernameEdit.requestFocus();
                    //System.out.println("    -   Cmd");
                } else {
                    String result = conn.installUser(userName);
                    if (result.equals(conn.ADDED)) {
                        statusView.setText(R.string.login_text_found);
                        passwordEdit.requestFocus();
                        System.out.println("    -   [Server] User found");
                    } else if (result.equals(conn.NOT_FOUND)) {
                        usernameEdit.setText("");
                        usernameEdit.setHint(R.string.login_text_exist);
                        passwordEdit.setText("");
                        registerbutton.setBackgroundColor(Color.RED);
                        registerbutton.requestFocus();
                        System.out.println("    -   [Server] User does not exist");
                    } else if (result.equals(conn.FAILED)){
                        usernameEdit.setText("");
                        passwordEdit.setText("");
                        statusView.setText(R.string.login_text_wrong);
                        System.out.println("    -   [Server] Connection failed");
                    }

                }
            }
        } else {
            usernameEdit.setHint(R.string.login_text_needed);
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
        statusView.setText("");
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

    @Override
    public void onBackPressed() {
        System.out.println("    -   I don't think so!");
    }
}