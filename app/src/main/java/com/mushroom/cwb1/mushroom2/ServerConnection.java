package com.mushroom.cwb1.mushroom2;

import android.content.Context;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class ServerConnection {

    private UserHandler userHandler;
    private DataBaseHandler2 dbHandler;

    private String dataToPut;
    private static ArrayList<String> serverCheckResult;

    public static final String NO_RESULT = "no results";
    public static final String ADDED = "existing user added";
    public static final String FAILED = "server check failed";


    public ServerConnection(Context context) {
        userHandler = new UserHandler(context);
        dbHandler = new DataBaseHandler2(context);
    }

    public String checkServer(String userName) throws ExecutionException, InterruptedException {
        dataToPut = userName;
        serverCheckResult =  new ArrayList<>();
        ArrayList<String> serverCheckResult = new PutAsyncTask().execute("http://mushroom.16mb.com/android/login_check_user.php").get();

        System.out.println("checkserver.size = " + serverCheckResult.size());


        if (serverCheckResult.get(0).equals("no results")) {
            System.out.println("--- checkServer no results ---" + serverCheckResult.get(0));
            return NO_RESULT;
        }
        else if (serverCheckResult.size() == 52){
            Map<String ,String> variabelenMap = new HashMap<String,String>();

            System.out.println("begin for");

            for(int l = 1; l < 52; l++){
                System.out.println(l + " - " + serverCheckResult.get(l));

                String rawString = serverCheckResult.get(l);
                String[] splitString = rawString.split("=");
                variabelenMap.put(splitString[0],splitString[1]);

                System.out.println("map" + variabelenMap.get(splitString[0]));
                System.out.println("einde for");

                User newuser = new User();

                String user_name = variabelenMap.get("username");
                String password = variabelenMap.get("password");
                String country = variabelenMap.get("country");
                String city = variabelenMap.get("city");
                String first_name = variabelenMap.get("first_name");
                String last_name = variabelenMap.get("last_name");
                newuser.setInformation(user_name, password, country, city, first_name, last_name);

                long first_login = Long.parseLong(variabelenMap.get("first_login"));
                long last_login = Long.parseLong(variabelenMap.get("last_login"));
                newuser.setLogin(first_login, last_login);

                float total_distance = Float.parseFloat(variabelenMap.get("total_distance"));
                long total_time = Long.parseLong(variabelenMap.get("total_time"));
                float highest_speed = Float.parseFloat(variabelenMap.get("highest_speed"));
                float highest_acceleration = Float.parseFloat(variabelenMap.get("highest_acceleration"));
                double highest_altitude_diff = Double.parseDouble(variabelenMap.get("highest_altitude_diff"));
                newuser.setStatistics(total_distance, total_time, highest_speed, highest_acceleration, highest_altitude_diff);

                int nb_won_challenges = Integer.parseInt(variabelenMap.get("nb_won_challenges"));
                int nb_days_biked = Integer.parseInt(variabelenMap.get("nb_days_biked"));
                int total_points = Integer.parseInt(variabelenMap.get("total_points"));
                int daily_points = Integer.parseInt(variabelenMap.get("daily_points"));
                int weekly_points = Integer.parseInt(variabelenMap.get("weekly_points"));
                newuser.setAmounts(nb_won_challenges, nb_days_biked, total_points, daily_points, weekly_points);

                int drive_1_km = Integer.parseInt(variabelenMap.get("drive_1_km"));
                int drive_5_km = Integer.parseInt(variabelenMap.get("drive_5_km"));
                int drive_10_km = Integer.parseInt(variabelenMap.get("drive_10_km"));
                int drive_50_km = Integer.parseInt(variabelenMap.get("drive_50_km"));
                int drive_100_km = Integer.parseInt(variabelenMap.get("drive_100_km"));
                int drive_250_km = Integer.parseInt(variabelenMap.get("drive_250_km"));
                int drive_500_km = Integer.parseInt(variabelenMap.get("drive_500_km"));
                int drive_1000_km = Integer.parseInt(variabelenMap.get("drive_1000_km"));
                int drive_5000_km = Integer.parseInt(variabelenMap.get("drive_5000_km"));
                newuser.setDrive(drive_1_km, drive_5_km, drive_10_km, drive_50_km, drive_100_km,
                        drive_250_km, drive_500_km, drive_1000_km, drive_5000_km);

                int topspeed_30 = Integer.parseInt(variabelenMap.get("topspeed_30"));
                int topspeed_35 = Integer.parseInt(variabelenMap.get("topspeed_35"));
                int topspeed_40 = Integer.parseInt(variabelenMap.get("topspeed_40"));
                int topspeed_45 = Integer.parseInt(variabelenMap.get("topspeed_45"));
                int topspeed_50 = Integer.parseInt(variabelenMap.get("topspeed_50"));
                newuser.setTopspeed(topspeed_30, topspeed_35, topspeed_40, topspeed_45, topspeed_50);

                int nb_challenge_1 = Integer.parseInt(variabelenMap.get("nb_challenge_1"));
                int nb_challenge_5 = Integer.parseInt(variabelenMap.get("nb_challenge_5"));
                int nb_challenge_10 = Integer.parseInt(variabelenMap.get("nb_challenge_10"));
                int nb_challenge_50 = Integer.parseInt(variabelenMap.get("nb_challenge_50"));
                int nb_challenge_200 = Integer.parseInt(variabelenMap.get("nb_challenge_200"));
                int nb_challenge_500 = Integer.parseInt(variabelenMap.get("nb_challenge_500"));
                newuser.setNb_challenge(nb_challenge_1, nb_challenge_5, nb_challenge_10, nb_challenge_50,
                        nb_challenge_200, nb_challenge_500);

                int biked_days_1 = Integer.parseInt(variabelenMap.get("biked_days_1"));
                int biked_days_2 = Integer.parseInt(variabelenMap.get("biked_days_2"));
                int biked_days_5 = Integer.parseInt(variabelenMap.get("biked_days_5"));
                int biked_days_7 = Integer.parseInt(variabelenMap.get("biked_days_7"));
                int biked_days_14 = Integer.parseInt(variabelenMap.get("biked_days_14"));
                int biked_days_31 = Integer.parseInt(variabelenMap.get("biked_days_31"));
                int biked_days_100 = Integer.parseInt(variabelenMap.get("biked_days_100"));
                newuser.setBiked_days(biked_days_1, biked_days_2, biked_days_5, biked_days_7, biked_days_14,
                        biked_days_31, biked_days_100);

                int alt_diff_10m = Integer.parseInt(variabelenMap.get("alt_diff_10m"));
                int alt_diff_25m = Integer.parseInt(variabelenMap.get("alt_diff_25m"));
                int alt_diff_50m = Integer.parseInt(variabelenMap.get("alt_diff_50m"));
                int alt_diff_100m = Integer.parseInt(variabelenMap.get("alt_diff_100m"));
                newuser.setAlt_diff(alt_diff_10m, alt_diff_25m, alt_diff_50m, alt_diff_100m);

                int start_the_game = Integer.parseInt(variabelenMap.get("start_the_game"));
                int get_all_achievements = Integer.parseInt(variabelenMap.get("get_all_achievements"));
                newuser.setExtra(start_the_game, get_all_achievements);

                userHandler.addUser(newuser);
                dbHandler.createTable(dbHandler.getWritableDatabase(), newuser.getUser_name());
                System.out.println("Bestaande user: " + newuser.toString() + " toegevoegd aan lokale db");
            }
            return ADDED;
        } else {
            System.out.println("checkserver niet gelukt geeft false terug");
            return FAILED;
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
}
