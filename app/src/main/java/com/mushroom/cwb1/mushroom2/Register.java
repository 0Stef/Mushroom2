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

    private EditText userNameField;
    private EditText passWordField;
    private EditText countryField;
    private EditText cityField;
    private EditText firstnameField;
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
        firstnameField = (EditText) findViewById(R.id.editText_register_firstname);
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
    }

    public void registerUser() throws ExecutionException, InterruptedException, UnsupportedEncodingException {

        User user = new User();

        userName = userNameField.getText().toString().replaceAll("[^a-zA-Z0-9] + ", "");
        password = passWordField.getText().toString().replaceAll("[^a-zA-Z0-9] + ", "");
        city = cityField.getText().toString().replaceAll("[^a-zA-Z0-9] + ", "");
        country = countryField.getText().toString().replaceAll("[^a-zA-Z0-9] + ", "");
        firstName = firstnameField.getText().toString().replaceAll("[^a-zA-Z0-9] + ", "");
        lastName = lastnameField.getText().toString().replaceAll("[^a-zA-Z0-9] + ", "");



        user.setInformation(userName, password, country, city, firstName, lastName);

        Calendar calendar = Calendar.getInstance();
        long millisec = calendar.getTimeInMillis();
        user.setFirst_login(millisec);

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

                    } else if (nameCheck.equals(conn.TAKEN)){
                        userNameField.setText("");
                        userNameField.setHint(R.string.register_text_exists);
                        userNameField.requestFocus();
                        System.out.println("    -   User already exists.");

                    } else if (nameCheck.equals(conn.FAILED)){
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


    /*public String usernameServerUnique(String username) throws ExecutionException, InterruptedException {

        serverCheckResult =  new ArrayList<>();
        serverCheckResult = new PutAsyncTask().execute("http://mushroom.16mb.com/android/register_check_username.php").get();

        if (serverCheckResult.size()<1){
            System.out.println("checkserver niet gelukt");
            return FAILED;
        } else if (serverCheckResult.get(0).equals("taken")) {
            //System.out.println("--- checkServer no results --- " + serverCheckResult.get(0));
            return TAKEN;
        } else if (serverCheckResult.get(0).equals("available")){
            return AVAILABLE;
        } else {
            return FAILED;
        }




    }*/








    /*public String createServerUser(User user) throws ExecutionException, InterruptedException {

        serverCheckResult =  new ArrayList<>();
        serverCheckResult = new PutAsyncTask().execute("http://mushroom.16mb.com/android/register_user.php").get();

        if (serverCheckResult.size()<1){
            System.out.println("checkserver niet gelukt geeft false terug");
            return FAILED;
        } else if (serverCheckResult.get(0).equals("error")) {
            System.out.println("--- checkServer no results --- " + serverCheckResult.get(0));
            return NO_RESULT;
        } else if (serverCheckResult.get(0).equals("succes")){


            System.out.println("online registreren gelukt lokaal aanmaken");
            createLocalUser(user);
            System.out.println("    -   Created new user: " + userName + ", " + password);

            return ADDED;

        } else {

            return FAILED;
        }


    }*/

    /*public ArrayList<String> putDataToServer(String URL){
        ArrayList<String> status =  new ArrayList<>();
        try {

            java.net.URL url = new URL(URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");

            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            //conn.setRequestProperty("Content-Type", "application/json");

            //String input = dataToPut;
            String input = URLEncoder.encode("userName", "UTF-8") + "=" + URLEncoder.encode(userName, "UTF-8")
                   + "&" + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8")
            + "&" + URLEncoder.encode("city", "UTF-8") + "=" + URLEncoder.encode(city, "UTF-8")
            + "&" + URLEncoder.encode("country", "UTF-8") + "=" + URLEncoder.encode(country, "UTF-8")
            + "&" + URLEncoder.encode("firstName", "UTF-8") + "=" + URLEncoder.encode(firstName, "UTF-8")
            + "&" + URLEncoder.encode("lastName", "UTF-8") + "=" + URLEncoder.encode(lastName, "UTF-8");


            OutputStream os = conn.getOutputStream();
            os.write(input.getBytes());
            os.flush();


            //Read the acknowledgement message after putting data to server
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));
            String output;
            //System.out.println("Output from Server .... \n");
            while ((output = br.readLine()) != null) {
                //System.out.println(output);
                status.add(output);
            }
            conn.disconnect();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return status;
    }

    class PutAsyncTask extends AsyncTask<String, Void, ArrayList<String>> {

        @Override
        protected ArrayList<String> doInBackground(String... urls) {
            return putDataToServer(urls[0]);
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(ArrayList<String> result) {
            //textView_1.setText(result.get(0)+" - "+result.get(1));
            //super.onPostExecute(result);
            serverCheckResult = result;
            //debugView.setText("post exec"+result.get(0));
        }
    }*/
}
