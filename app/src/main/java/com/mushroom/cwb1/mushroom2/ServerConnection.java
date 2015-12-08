package com.mushroom.cwb1.mushroom2;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
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
    private static Context context;

    private String dataToPut;
    private static ArrayList<String> serverCheckResult;

    private static final int COL = 52;

    public static final String ADDED = "user added";
    public static final String NOT_FOUND = "the requested user was not found";
    public static final String SUCCES = "operation was succesfull";
    public static final String FAILED = "server connection failed";

    public static final String AVAILABLE = "username available";
    public static final String TAKEN = "username taken";

    //Constructor ----------------------------------------------------------------------------------

    public ServerConnection(Context context) {
        userHandler = new UserHandler(context);
        dbHandler = new DataBaseHandler2(context);
        this.context = context;
    }

    //Active User ----------------------------------------------------------------------------------

    public static String getActiveUser() {
        SharedPreferences settings = context.getSharedPreferences(context.getString(R.string.app_save_file), 0);
        String userName = settings.getString(context.getString(R.string.app_user_name), null);

        return userName;
    }

    public static void setActiveUser(String userName) {
        SharedPreferences settings = context.getSharedPreferences(context.getString(R.string.app_save_file), 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(context.getString(R.string.app_user_name), userName);

        editor.commit();
    }

    //Check user name ------------------------------------------------------------------------------

    public String checkForName(String userName) throws ExecutionException, InterruptedException, UnsupportedEncodingException {
        //AVAILABLE, TAKEN, FAILED
        dataToPut = userName;
        serverCheckResult =  new ArrayList<>();
        String input = URLEncoder.encode("userName", "UTF-8") + "=" + URLEncoder.encode(dataToPut, "UTF-8");
        serverCheckResult = new PutAsyncTask(input).execute("http://mushroom.16mb.com/android/register_check_username.php").get();

        if (serverCheckResult.size() < 1) {
            return FAILED;
        } else if (serverCheckResult.get(0).equals("taken")) {
            return TAKEN;
        } else if (serverCheckResult.get(0).equals("available")) {
            return AVAILABLE;
        } else {
            return FAILED;
        }
    }

    //Install user from server locally -------------------------------------------------------------

    public String installUser(String userName) throws ExecutionException, InterruptedException, UnsupportedEncodingException {
        //NOT_FOUND, ADDED, FAILED
        dataToPut = userName;
        serverCheckResult =  new ArrayList<>();
        String input = URLEncoder.encode("userName", "UTF-8") + "=" + URLEncoder.encode(dataToPut, "UTF-8");
        serverCheckResult = new PutAsyncTask(input).execute("http://mushroom.16mb.com/android/login_check_user.php").get();


        if (serverCheckResult.size() < 1) {
            return FAILED;
        } else if (serverCheckResult.get(0).equals("no results")) {
            return NOT_FOUND;
        } else if (serverCheckResult.size() == COL) {
            createLocalUser(createUserInstance(serverCheckResult));
            return ADDED;
        } else {
            return FAILED;
        }
    }

    private User createUserInstance(ArrayList<String> serverCheckResult) {
        Map<String ,String> variabelenMap = new HashMap<String,String>();
        User newuser = new User();

        for(int l = 1; l < COL; l++){
            String rawString = serverCheckResult.get(l);
            String[] splitString = rawString.split("=");

            if (1 == splitString.length){
                variabelenMap.put(splitString[0], " ");
            }else{
                variabelenMap.put(splitString[0], splitString[1]);
            }
        }

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

        return newuser;
    }

    public void createLocalUser(User user) {
        userHandler.addUser(user);
        dbHandler.createTable(dbHandler.getWritableDatabase(), user.getUser_name());
    }

    //Register user on server ----------------------------------------------------------------------

    public String createServerUser(User user) throws ExecutionException, InterruptedException, UnsupportedEncodingException {
        //NOT_FOUND, ADDED, FAILED
        serverCheckResult =  new ArrayList<>();
        String input = URLEncoder.encode("userName", "UTF-8") + "=" + URLEncoder.encode(user.getUser_name(), "UTF-8")
                + "&" + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(user.getPassword(), "UTF-8")
                + "&" + URLEncoder.encode("city", "UTF-8") + "=" + URLEncoder.encode(user.getCity(), "UTF-8")
                + "&" + URLEncoder.encode("country", "UTF-8") + "=" + URLEncoder.encode(user.getCountry(), "UTF-8")
                + "&" + URLEncoder.encode("firstName", "UTF-8") + "=" + URLEncoder.encode(user.getFirst_name(), "UTF-8")
                + "&" + URLEncoder.encode("lastName", "UTF-8") + "=" + URLEncoder.encode(user.getLast_name(), "UTF-8");
        serverCheckResult = new PutAsyncTask(input).execute("http://mushroom.16mb.com/android/register_user.php").get();

        if (serverCheckResult.size() < 1){
            return FAILED;
        } else if (serverCheckResult.get(0).equals("error")) {
            return NOT_FOUND;
        } else if (serverCheckResult.get(0).equals("succes")){
            return ADDED;
        } else {
            return FAILED;
        }
    }

    //Update user information ----------------------------------------------------------------------

    public String updateUser(String userName) throws UnsupportedEncodingException, ExecutionException, InterruptedException {
        //NOT_FOUND, SUCCES, FAILED
        dataToPut = userName;
        serverCheckResult =  new ArrayList<>();
        String input = URLEncoder.encode("userName", "UTF-8") + "=" + URLEncoder.encode(dataToPut, "UTF-8");
        serverCheckResult = new PutAsyncTask(input).execute("http://mushroom.16mb.com/android/login_check_user.php").get();

        if (serverCheckResult.size() < 1) {
            return FAILED;
        } else if (serverCheckResult.get(0).equals("no results")) {
            return NOT_FOUND;
        } else if (serverCheckResult.size() == COL) {
            User localUser = userHandler.getUserInformation(userName);
            User serverUser = createUserInstance(serverCheckResult);
            User updatedUser = getUpdatedUser(serverUser, localUser);

            writeToServer(updatedUser);
            writeToLocal(updatedUser);
            return SUCCES;
        } else {
            return FAILED;
        }
    }

    private User getUpdatedUser(User serverUser, User localUser) {
        User updatedUser = serverUser;

        updatedUser.setFirst_login(Math.min(serverUser.getFirst_login(), localUser.getFirst_login()));
        updatedUser.setLast_login(Math.max(serverUser.getLast_login(), localUser.getLast_login()));

        updatedUser.setTotal_distance(Math.max(serverUser.getTotal_distance(), localUser.getTotal_distance()));
        updatedUser.setTotal_time(Math.max(serverUser.getTotal_time(), localUser.getTotal_time()));
        updatedUser.setHighest_speed(Math.max(serverUser.getHighest_speed(), localUser.getHighest_speed()));
        updatedUser.setHighest_acceleration(Math.max(serverUser.getHighest_acceleration(), localUser.getHighest_acceleration()));
        updatedUser.setHighest_altitude_diff(Math.max(serverUser.getHighest_altitude_diff(), localUser.getHighest_altitude_diff()));

        updatedUser.setNb_won_challenges(Math.max(serverUser.getNb_won_challenges(), localUser.getNb_won_challenges()));
        updatedUser.setNb_days_biked(Math.max(serverUser.getNb_days_biked(), localUser.getNb_days_biked()));
        updatedUser.setTotal_points(Math.max(serverUser.getTotal_points(), localUser.getTotal_points()));
        updatedUser.setDaily_points(Math.max(serverUser.getDaily_points(), localUser.getDaily_points()));
        updatedUser.setWeekly_points(Math.max(serverUser.getWeekly_points(), localUser.getWeekly_points()));

        updatedUser.setDrive_1_km(Math.max(serverUser.getDrive_1_km(), localUser.getDrive_1_km()));
        updatedUser.setDrive_5_km(Math.max(serverUser.getDrive_5_km(), localUser.getDrive_5_km()));
        updatedUser.setDrive_10_km(Math.max(serverUser.getDrive_10_km(), localUser.getDrive_10_km()));
        updatedUser.setDrive_50_km(Math.max(serverUser.getDrive_50_km(), localUser.getDrive_50_km()));
        updatedUser.setDrive_100_km(Math.max(serverUser.getDrive_100_km(), localUser.getDrive_100_km()));
        updatedUser.setDrive_250_km(Math.max(serverUser.getDrive_250_km(), localUser.getDrive_250_km()));
        updatedUser.setDrive_500_km(Math.max(serverUser.getDrive_500_km(), localUser.getDrive_500_km()));
        updatedUser.setDrive_1000_km(Math.max(serverUser.getDrive_1000_km(), localUser.getDrive_1000_km()));
        updatedUser.setDrive_5000_km(Math.max(serverUser.getDrive_5000_km(), localUser.getDrive_5000_km()));

        updatedUser.setTopspeed_30(Math.max(serverUser.getTopspeed_30(), localUser.getTopspeed_30()));
        updatedUser.setTopspeed_35(Math.max(serverUser.getTopspeed_35(), localUser.getTopspeed_35()));
        updatedUser.setTopspeed_40(Math.max(serverUser.getTopspeed_40(), localUser.getTopspeed_40()));
        updatedUser.setTopspeed_45(Math.max(serverUser.getTopspeed_45(), localUser.getTopspeed_45()));
        updatedUser.setTopspeed_50(Math.max(serverUser.getTopspeed_50(), localUser.getTopspeed_50()));

        updatedUser.setNb_challenge_1(Math.max(serverUser.getNb_challenge_1(), localUser.getNb_challenge_1()));
        updatedUser.setNb_challenge_5(Math.max(serverUser.getNb_challenge_5(), localUser.getNb_challenge_5()));
        updatedUser.setNb_challenge_10(Math.max(serverUser.getNb_challenge_10(), localUser.getNb_challenge_10()));
        updatedUser.setNb_challenge_50(Math.max(serverUser.getNb_challenge_50(), localUser.getNb_challenge_50()));
        updatedUser.setNb_challenge_200(Math.max(serverUser.getNb_challenge_200(), localUser.getNb_challenge_200()));
        updatedUser.setNb_challenge_500(Math.max(serverUser.getNb_challenge_500(), localUser.getNb_challenge_500()));

        updatedUser.setBiked_days_1(Math.max(serverUser.getBiked_days_1(), localUser.getBiked_days_1()));
        updatedUser.setBiked_days_2(Math.max(serverUser.getBiked_days_2(), localUser.getBiked_days_2()));
        updatedUser.setBiked_days_5(Math.max(serverUser.getBiked_days_5(), localUser.getBiked_days_5()));
        updatedUser.setBiked_days_7(Math.max(serverUser.getBiked_days_7(), localUser.getBiked_days_7()));
        updatedUser.setBiked_days_14(Math.max(serverUser.getBiked_days_14(), localUser.getBiked_days_14()));
        updatedUser.setBiked_days_31(Math.max(serverUser.getBiked_days_31(), localUser.getBiked_days_31()));
        updatedUser.setBiked_days_100(Math.max(serverUser.getBiked_days_100(), localUser.getBiked_days_100()));

        updatedUser.setAlt_diff_10m(Math.max(serverUser.getAlt_diff_10m(), localUser.getAlt_diff_10m()));
        updatedUser.setAlt_diff_25m(Math.max(serverUser.getAlt_diff_25m(), localUser.getAlt_diff_25m()));
        updatedUser.setAlt_diff_50m(Math.max(serverUser.getAlt_diff_50m(), localUser.getAlt_diff_50m()));
        updatedUser.setAlt_diff_100m(Math.max(serverUser.getAlt_diff_100m(), localUser.getAlt_diff_100m()));

        updatedUser.setStart_the_game(Math.max(serverUser.getStart_the_game(), localUser.getStart_the_game()));
        updatedUser.setGet_all_achievements(Math.max(serverUser.getGet_all_achievements(), localUser.getGet_all_achievements()));

        return updatedUser;
    }

    private void writeToLocal(User user) {
        userHandler.overWrite(user);
    }

    private void writeToServer(User user) throws UnsupportedEncodingException, ExecutionException, InterruptedException {
        System.out.println("    -   Write to server started.");

        updateGeneralInfo(user);
        updateAchievements(user);

        System.out.println("    -   Write to server ended.");
    }

    public void updateGeneralInfo(User user) throws UnsupportedEncodingException {
        String input = URLEncoder.encode("userName", "UTF-8") + "=" + URLEncoder.encode(user.getUser_name(), "UTF-8")
                + "&" + URLEncoder.encode("last_login", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(user.getLast_login()), "UTF-8")
                + "&" + URLEncoder.encode("total_distance", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(user.getTotal_distance()), "UTF-8")
                + "&" + URLEncoder.encode("total_time", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(user.getTotal_time()), "UTF-8")
                + "&" + URLEncoder.encode("highest_speed", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(user.getHighest_speed()), "UTF-8")
                + "&" + URLEncoder.encode("highest_acceleration", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(user.getHighest_acceleration()), "UTF-8")
                + "&" + URLEncoder.encode("highest_altitude_diff", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(user.getHighest_altitude_diff()), "UTF-8")
                + "&" + URLEncoder.encode("nb_won_challenges", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(user.getNb_won_challenges()), "UTF-8")
                + "&" + URLEncoder.encode("nb_days_biked", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(user.getNb_days_biked()), "UTF-8")
                + "&" + URLEncoder.encode("total_points", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(user.getTotal_points()), "UTF-8")
                + "&" + URLEncoder.encode("daily_points", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(user.getDaily_points()), "UTF-8")
                + "&" + URLEncoder.encode("weekly_points", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(user.getWeekly_points()), "UTF-8");
        new PutAsyncTask(input).execute("http://mushroom.16mb.com/android/update_algemene_gegevens.php");
    }

    public void updateAchievements(User user) throws UnsupportedEncodingException {
        String input = URLEncoder.encode("userName", "UTF-8") + "=" + URLEncoder.encode(user.getUser_name(), "UTF-8")
                + "&" + URLEncoder.encode("drive_1_km", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(user.getDrive_1_km()), "UTF-8")
                + "&" + URLEncoder.encode("drive_5_km", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(user.getDrive_5_km()), "UTF-8")
                + "&" + URLEncoder.encode("drive_10_km", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(user.getDrive_10_km()), "UTF-8")
                + "&" + URLEncoder.encode("drive_50_km", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(user.getDrive_50_km()), "UTF-8")
                + "&" + URLEncoder.encode("drive_100_km", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(user.getDrive_100_km()), "UTF-8")
                + "&" + URLEncoder.encode("drive_250_km", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(user.getDrive_250_km()), "UTF-8")
                + "&" + URLEncoder.encode("drive_500_km", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(user.getDrive_500_km()), "UTF-8")
                + "&" + URLEncoder.encode("drive_1000_km", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(user.getDrive_1000_km()), "UTF-8")
                + "&" + URLEncoder.encode("drive_5000_km", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(user.getDrive_5000_km()), "UTF-8")

                + "&" + URLEncoder.encode("topspeed_30", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(user.getTopspeed_30()), "UTF-8")
                + "&" + URLEncoder.encode("topspeed_35", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(user.getTopspeed_35()), "UTF-8")
                + "&" + URLEncoder.encode("topspeed_40", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(user.getTopspeed_40()), "UTF-8")
                + "&" + URLEncoder.encode("topspeed_45", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(user.getTopspeed_45()), "UTF-8")
                + "&" + URLEncoder.encode("topspeed_50", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(user.getTopspeed_50()), "UTF-8")

                + "&" + URLEncoder.encode("nb_challenge_1", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(user.getNb_challenge_1()), "UTF-8")
                + "&" + URLEncoder.encode("nb_challenge_5", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(user.getNb_challenge_5()), "UTF-8")
                + "&" + URLEncoder.encode("nb_challenge_10", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(user.getNb_challenge_10()), "UTF-8")
                + "&" + URLEncoder.encode("nb_challenge_50", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(user.getNb_challenge_50()), "UTF-8")
                + "&" + URLEncoder.encode("nb_challenge_200", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(user.getNb_challenge_200()), "UTF-8")
                + "&" + URLEncoder.encode("nb_challenge_500", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(user.getNb_challenge_500()), "UTF-8")

                + "&" + URLEncoder.encode("biked_days_1", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(user.getBiked_days_1()), "UTF-8")
                + "&" + URLEncoder.encode("biked_days_2", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(user.getBiked_days_2()), "UTF-8")
                + "&" + URLEncoder.encode("biked_days_5", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(user.getBiked_days_5()), "UTF-8")
                + "&" + URLEncoder.encode("biked_days_7", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(user.getBiked_days_7()), "UTF-8")
                + "&" + URLEncoder.encode("biked_days_14", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(user.getBiked_days_14()), "UTF-8")
                + "&" + URLEncoder.encode("biked_days_31", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(user.getBiked_days_31()), "UTF-8")
                + "&" + URLEncoder.encode("biked_days_100", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(user.getBiked_days_100()), "UTF-8")

                + "&" + URLEncoder.encode("alt_diff_10m", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(user.getAlt_diff_10m()), "UTF-8")
                + "&" + URLEncoder.encode("alt_diff_25m", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(user.getAlt_diff_25m()), "UTF-8")
                + "&" + URLEncoder.encode("alt_diff_50m", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(user.getAlt_diff_50m()), "UTF-8")
                + "&" + URLEncoder.encode("alt_diff_100m", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(user.getAlt_diff_100m()), "UTF-8")

                + "&" + URLEncoder.encode("start_the_game", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(user.getStart_the_game()), "UTF-8")
                + "&" + URLEncoder.encode("get_all_achievements", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(user.getGet_all_achievements()), "UTF-8");
        new PutAsyncTask(input).execute("http://mushroom.16mb.com/android/update_achievements.php");
    }

    //Functionality for challenges. ----------------------------------------------------------------

    public String createChallenge(Challenge challenge) {
        //ADDED, FAILED

        return null;
    }

    public ArrayList<Challenge> downloadChallenge(String userName) {
        ArrayList<Challenge> list = new ArrayList<>();

        //When problems occur: add new challenge with Challenge.FAILED as status.
        //The list is empty: add new challenge with Challenge.NOT_ACTIVE
        //With other words... the list should not be empty!

        return list;
    }

    public String updateChallenge(Challenge challenge) {
        //SUCCES, FAILED

        return null;
    }

    public String deleteChallenge(Challenge challenge) {
        //SUCCES, FAILED

        return null;
    }

    //Extra functions ------------------------------------------------------------------------------

    public ArrayList<String> putDataToServer(String URL, String input){
        ArrayList<String> status =  new ArrayList<>();
        try {

            java.net.URL url = new URL(URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");

            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            //conn.setRequestProperty("Content-Type", "application/json");

            OutputStream os = conn.getOutputStream();
            os.write(input.getBytes());
            os.flush();

            //Read the acknowledgement message after putting data to server
            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

            String output;
            while ((output = br.readLine()) != null) {
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
        private String input;

        public PutAsyncTask(String input) {
            this.input = input;
        }

        @Override
        protected ArrayList<String> doInBackground(String... urls) {
            return putDataToServer(urls[0], input);
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