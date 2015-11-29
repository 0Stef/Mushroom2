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

import java.util.Calendar;

public class Register extends AppCompatActivity {

    private EditText userNameField;
    private EditText passWordField;
    private EditText countryField;
    private EditText cityField;

    private Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        userNameField = (EditText) findViewById(R.id.editText_register_username);
        passWordField = (EditText) findViewById(R.id.editText_register_password);
        passWordField.setTypeface(Typeface.DEFAULT);
        passWordField.setTransformationMethod(new PasswordTransformationMethod());

        countryField = (EditText) findViewById(R.id.editText_register_country);
        cityField = (EditText) findViewById(R.id.editText_register_city);

        registerButton = (Button) findViewById(R.id.registeract_register);

        registerButton.setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View v){
                        resetHints();
                        registerUser();
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
    }

    public void registerUser() {
        UserHandler userHandler = new UserHandler(getApplicationContext());
        DataBaseHandler2 dbHandler = new DataBaseHandler2(getApplicationContext());

        User user = new User();
        String userName = userNameField.getText().toString().replaceAll(" ", "_");
        String passWord = passWordField.getText().toString().replaceAll(" ", "_");

        user.setUser_name(userName);
        user.setPassword(passWord);
        user.setCountry(countryField.getText().toString());
        user.setCity(cityField.getText().toString());

        Calendar calendar = Calendar.getInstance();
        long millisec = calendar.getTimeInMillis();
        user.setFirst_login(millisec);

        if (!userName.isEmpty()) {
            if (!passWord.isEmpty()) {
                if (!userHandler.isExistingUser(userName)) {
                    userHandler.addUser(user);
                    dbHandler.createTable(dbHandler.getWritableDatabase(), userName);
                    System.out.println("    -   Created new user: " + userName + ", " + passWord);
                    finish();
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
