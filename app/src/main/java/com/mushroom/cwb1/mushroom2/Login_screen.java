package com.mushroom.cwb1.mushroom2;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.ExecutionException;


public class Login_screen extends AppCompatActivity {

    private UserHandler userHandler;

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

        userHandler = new UserHandler(getApplicationContext());

        loginbutton = (Button) findViewById(R.id.button);
        registerbutton = (Button) findViewById(R.id.registerbutton);
        usernameEdit = (EditText) findViewById(R.id.editText);
        passwordEdit = (EditText) findViewById(R.id.editText2);
        debugView = (TextView) findViewById(R.id.debugView);
        statusView = (TextView) findViewById(R.id.wrong_password);

        usernameEdit.setSingleLine();
        passwordEdit.setSingleLine();
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

    public void login() throws ExecutionException, InterruptedException {
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
//                    DialogFragment prompt = new ServerDialogFragment(userName);
//                    prompt.show(getFragmentManager(), "serverPrompt");

                    //TODO checkServer 3 waarden laten returnen een voor als naam bestaat, niet bestaat maar ook als er een fout optreedt
                    if (checkServer(userName)) {
                        statusView.setText("Your account was found!");
                        passwordEdit.requestFocus();
                    } else {
                        usernameEdit.setText("");
                        usernameEdit.setHint("Username does not exist.");
                        registerbutton.setBackgroundColor(Color.RED);
                        registerbutton.requestFocus();
                        System.out.println("    -   Not existing username");
                    }

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

    private boolean checkServer(String userName) throws ExecutionException, InterruptedException {
        //Deze functie moet teruggeven of de gebruikersnaam bestaat of niet.
        //En de gebruikersgegevens installeren op de lokale opslag.


        dataToPut = userName;
        serverCheckResult =  new ArrayList<>();
        ArrayList<String> serverCheckResult = new PutAsyncTask().execute("http://mushroom.16mb.com/android/login_check_user.php").get();

        System.out.println("checkserver.size = "+serverCheckResult.size());


        if (serverCheckResult.get(0).equals("no results")) {
            System.out.println("--- checkServer no results ---"+serverCheckResult.get(0));
            return false;
        }
        else if (serverCheckResult.size() == 52){


            Map<String ,String> variabelenMap = new HashMap<String,String>();

            System.out.println("begin for");


        for(int l=1; l<52; l++){
            System.out.println(l + " - " + serverCheckResult.get(l));



            String rawString = serverCheckResult.get(l);


            String[] splitString = rawString.split("=");

            variabelenMap.put(splitString[0],splitString[1]);

            System.out.println("map" + variabelenMap.get(splitString[0]));

        }
            System.out.println("einde for");


            String user_name = variabelenMap.get("username");
            String password = variabelenMap.get("password");
            String country = variabelenMap.get("country");
            String city = variabelenMap.get("city");
            String first_name = variabelenMap.get("first_name");
            String last_name = variabelenMap.get("last_name");

            long first_login = Long.parseLong(variabelenMap.get("first_login"));
            long last_login = Long.parseLong(variabelenMap.get("last_login"));

            float total_distance = Float.parseFloat(variabelenMap.get("total_distance"));
            long total_time = Long.parseLong(variabelenMap.get("total_time"));

            float highest_speed = Float.parseFloat(variabelenMap.get("highest_speed"));
            float highest_acceleration = Float.parseFloat(variabelenMap.get("highest_acceleration"));

            double highest_altitude_diff = Double.parseDouble(variabelenMap.get("highest_altitude_diff"));

            int nb_won_challenges = Integer.parseInt(variabelenMap.get("nb_won_challenges"));
            int nb_days_biked = Integer.parseInt(variabelenMap.get("nb_days_biked"));
            int total_points = Integer.parseInt(variabelenMap.get("total_points"));
            int daily_points = Integer.parseInt(variabelenMap.get("daily_points"));
            int weekly_points = Integer.parseInt(variabelenMap.get("weekly_points"));

            int drive_1_km = Integer.parseInt(variabelenMap.get("drive_1_km"));
            int drive_5_km = Integer.parseInt(variabelenMap.get("drive_5_km"));
            int drive_10_km = Integer.parseInt(variabelenMap.get("drive_10_km"));
            int drive_50_km = Integer.parseInt(variabelenMap.get("drive_50_km"));
            int drive_100_km = Integer.parseInt(variabelenMap.get("drive_100_km"));
            int drive_250_km = Integer.parseInt(variabelenMap.get("drive_250_km"));
            int drive_500_km = Integer.parseInt(variabelenMap.get("drive_500_km"));
            int drive_1000_km = Integer.parseInt(variabelenMap.get("drive_1000_km"));
            int drive_5000_km = Integer.parseInt(variabelenMap.get("drive_5000_km"));

            int topspeed_30 = Integer.parseInt(variabelenMap.get("topspeed_30"));
            int topspeed_35 = Integer.parseInt(variabelenMap.get("topspeed_35"));
            int topspeed_40 = Integer.parseInt(variabelenMap.get("topspeed_40"));
            int topspeed_45 = Integer.parseInt(variabelenMap.get("topspeed_45"));
            int topspeed_50 = Integer.parseInt(variabelenMap.get("topspeed_50"));

            int nb_challenge_1 = Integer.parseInt(variabelenMap.get("nb_challenge_1"));
            int nb_challenge_5 = Integer.parseInt(variabelenMap.get("nb_challenge_5"));
            int nb_challenge_10 = Integer.parseInt(variabelenMap.get("nb_challenge_10"));
            int nb_challenge_50 = Integer.parseInt(variabelenMap.get("nb_challenge_50"));
            int nb_challenge_200 = Integer.parseInt(variabelenMap.get("nb_challenge_200"));
            int nb_challenge_500 = Integer.parseInt(variabelenMap.get("nb_challenge_500"));

            int biked_days_1 = Integer.parseInt(variabelenMap.get("biked_days_1"));
            int biked_days_2 = Integer.parseInt(variabelenMap.get("biked_days_2"));
            int biked_days_5 = Integer.parseInt(variabelenMap.get("biked_days_5"));
            int biked_days_7 = Integer.parseInt(variabelenMap.get("biked_days_7"));
            int biked_days_14 = Integer.parseInt(variabelenMap.get("biked_days_14"));
            int biked_days_31 = Integer.parseInt(variabelenMap.get("biked_days_31"));
            int biked_days_100 = Integer.parseInt(variabelenMap.get("biked_days_100"));

            int alt_diff_10m = Integer.parseInt(variabelenMap.get("alt_diff_10m"));
            int alt_diff_25m = Integer.parseInt(variabelenMap.get("alt_diff_25m"));
            int alt_diff_50m = Integer.parseInt(variabelenMap.get("alt_diff_50m"));
            int alt_diff_100m = Integer.parseInt(variabelenMap.get("alt_diff_100m"));

            int start_the_game = Integer.parseInt(variabelenMap.get("start_the_game"));
            int get_all_achievements = Integer.parseInt(variabelenMap.get("get_all_achievements"));



            User newuser = new User(user_name,password,country,city,first_name,last_name,first_login,last_login,total_distance,total_time,highest_speed,highest_acceleration,highest_altitude_diff,nb_won_challenges,nb_days_biked,total_points,daily_points,weekly_points,drive_1_km,drive_5_km,drive_10_km,drive_50_km,drive_100_km,drive_250_km,drive_500_km,drive_1000_km,drive_5000_km,topspeed_30,topspeed_35,topspeed_40,topspeed_45,topspeed_50,nb_challenge_1,nb_challenge_5,nb_challenge_10,nb_challenge_50,nb_challenge_200,nb_challenge_500,biked_days_1,biked_days_2,biked_days_5,biked_days_7,biked_days_14,biked_days_31,biked_days_100,alt_diff_10m,alt_diff_25m,alt_diff_50m,alt_diff_100m,start_the_game,get_all_achievements);
            userHandler.addUser(newuser);
            System.out.println("Bestaande user: "+newuser.toString()+" toegevoegd aan lokale db");


        return true;
        } else {
            System.out.println("checkserver niet gelukt geeft false terug");
            return false;
        }

    }


    public ArrayList<String> putDataToServer(String URL){
        ArrayList<String> status =  new ArrayList<>();
        try {

            java.net.URL url = new URL(URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");

            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            //conn.setRequestProperty("Content-Type", "application/json");

            //String input = dataToPut;
            String input = URLEncoder.encode("userName", "UTF-8") + "=" + URLEncoder.encode(dataToPut, "UTF-8");



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
    }

    /*public class ServerDialogFragment extends DialogFragment {
        private String userName;

        public ServerDialogFragment(String userName) {
            this.userName = userName;
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage("Do you want to check")
                    .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            if (checkServer(userName)) {
                                usernameEdit.setText(userName);

                            } else {
                                notFound();
                            }
                        }
                    })
                    .setNegativeButton("no", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            notFound();
                        }
                    });
            return builder.create();
        }
    }*/

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





