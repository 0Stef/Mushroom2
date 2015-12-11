package com.mushroom.cwb1.mushroom2;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.PasswordTransformationMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.ExecutionException;

public class Register extends AppCompatActivity {

    public static final String DEFAULT_ENTRY = "/";

    private EditText userNameField;
    private EditText passWordField;
    private EditText countryField;
    private EditText cityField;
    private EditText firstNameField;
    private EditText lastnameField;
    private TextView statusField;

    private Button registerButton;

    private UserHandler userHandler;
    private DataBaseHandler2 dbHandler;
    private ServerConnection conn;

    private static ArrayList<String> serverCheckResult;
    private String userName;
    private String password;
    private String country;
    private String city;
    private String firstName;
    private String lastName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        userHandler = new UserHandler(getApplicationContext());
        dbHandler = new DataBaseHandler2(getApplicationContext());
        conn = new ServerConnection(getApplicationContext());

        userNameField = (EditText) findViewById(R.id.editText_register_username);
        passWordField = (EditText) findViewById(R.id.editText_register_password);
        passWordField.setTypeface(Typeface.DEFAULT);
        passWordField.setTransformationMethod(new PasswordTransformationMethod());

        countryField = (EditText) findViewById(R.id.editText_register_country);
        cityField = (EditText) findViewById(R.id.editText_register_city);
        firstNameField = (EditText) findViewById(R.id.editText_register_firstname);
        lastnameField = (EditText) findViewById(R.id.editText_register_lasstname);

        statusField = (TextView) findViewById(R.id.text_register_status);

        registerButton = (Button) findViewById(R.id.registeract_register);

        registerButton.setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View v){
                        resetHints();
                        try {
                            registerUser();
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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_register, menu);
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

    public void resetHints() {
        userNameField.setHint("");
        passWordField.setHint("");
        countryField.setHint("");
        cityField.setHint("");
        firstNameField.setHint("");
        lastnameField.setHint("");
    }

    public void registerUser() throws ExecutionException, InterruptedException, UnsupportedEncodingException {

        boolean validCharacters = true;

        User user = new User();

        userName = userNameField.getText().toString();
        password = passWordField.getText().toString();
        city = cityField.getText().toString();
        country = countryField.getText().toString();
        firstName = firstNameField.getText().toString();
        lastName = lastnameField.getText().toString();

        if (!userName.matches("[a-zA-Z0-9-_]*")){
            userNameField.setText("");
            userNameField.setHint(R.string.register_text_invalid_characters);
            userNameField.requestFocus();
            validCharacters = false;
        } else if (checkSize(userNameField)){
            validCharacters = false;
        }
        if (!password.matches("[a-zA-Z0-9-_]*")){
            passWordField.setText("");
            passWordField.setHint(R.string.register_text_invalid_characters);
            passWordField.requestFocus();
            validCharacters = false;
        } else if (checkSize(passWordField)){
            validCharacters = false;
        }
        if (!city.matches("[a-zA-Z0-9-_]*")){
            cityField.setText("");
            cityField.setHint(R.string.register_text_invalid_characters);
            validCharacters = false;
        } else if (checkSize(cityField)){
            validCharacters = false;
        }
        if (!country.matches("[a-zA-Z0-9-_]*")){
            countryField.setText("");
            countryField.setHint(R.string.register_text_invalid_characters);
            validCharacters = false;
        } else if (checkSize(countryField)){
            validCharacters = false;
        }
        if (!firstName.matches("[a-zA-Z0-9-_]*")){
            firstNameField.setText("");
            firstNameField.setHint(R.string.register_text_invalid_characters);
            validCharacters = false;
        } else if (checkSize(firstNameField)){
            validCharacters = false;
        }
        if (!lastName.matches("[a-zA-Z0-9-_]*")){
            lastnameField.setText("");
            lastnameField.setHint(R.string.register_text_invalid_characters);
            validCharacters = false;
        } else if (checkSize(lastnameField)){
            validCharacters = false;
        }

        setDefault();
        user.setInformation(userName, password, country, city, firstName, lastName);

        Calendar calendar = Calendar.getInstance();
        long millisec = calendar.getTimeInMillis();
        user.setFirst_login(millisec);
        System.out.println("    -   First login: " + millisec);

        if (validCharacters) {
            if (!userName.isEmpty()) {
                if (!password.isEmpty()) {
                    if (!userHandler.isExistingUser(userName)) {

                        String nameCheck = conn.checkForName(userName);

                        if (nameCheck.equals(conn.AVAILABLE)) {

                            String result = conn.createServerUser(user);
                            System.out.println("createServerUser result = " + result);

                            if (result.equals(conn.ADDED)) {
                                conn.createLocalUser(user);
                                System.out.println("    -   [Server] User added");
                                finish();
                            } else if (result.equals(conn.NOT_FOUND)) {
                                statusField.setText(conn.NOT_FOUND);
                                statusField.requestFocus();
                                userNameField.setText("");
                                //userNameField.setHint(R.string.regist);
                            } else if (result.equals(conn.FAILED)) {
                                statusField.setText(conn.FAILED);
                                statusField.requestFocus();
                                //statusView.setText(R.string.login_text_wrong);
                                System.out.println("    -   [Server] Connection failed");
                            }

                        } else if (nameCheck.equals(conn.TAKEN)) {
                            userNameField.setText("");
                            userNameField.setHint(R.string.register_text_exists);
                            userNameField.requestFocus();
                            System.out.println("    -   User already exists.");

                        } else if (nameCheck.equals(conn.FAILED)) {
                            statusField.setText(conn.FAILED);
                            statusField.requestFocus();
                            //statusView.setText(R.string.login_text_wrong);
                            System.out.println("    -   [Server] Connection failed");
                        }

                    } else {
                        userNameField.setText("");
                        userNameField.setHint(R.string.register_text_exists);
                        userNameField.requestFocus();
                        System.out.println("    -   User already exists.");
                    }
                } else {
                    passWordField.setText("");
                    passWordField.setHint(R.string.register_text_pass_needed);
                    passWordField.requestFocus();
                }
            } else {
                userNameField.setText("");
                userNameField.setHint(R.string.register_text_user_needed);
                userNameField.requestFocus();
            }
        }
    }

    private void setDefault() {
        if (country.isEmpty()) country = DEFAULT_ENTRY;
        if (city.isEmpty()) city = DEFAULT_ENTRY;
        if (firstName.isEmpty()) firstName = DEFAULT_ENTRY;
        if (lastName.isEmpty()) lastName = DEFAULT_ENTRY;
    }

    private boolean checkSize(EditText edit) {
        if (lastName.length() > 30) {
            edit.setText("");
            edit.setHint(R.string.register_text_too_long);
            edit.requestFocus();
            return true;
        } else {
            return false;
        }
    }
}
