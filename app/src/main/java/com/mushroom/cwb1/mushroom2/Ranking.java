package com.mushroom.cwb1.mushroom2;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class Ranking extends AppCompatActivity {

    public String currentUser;
    public String dataToPut;
    public ArrayList serverRankingResult;
    public ArrayList result;
    public String userRanking;
    ListView listView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);

        UserHandler uHandler = new UserHandler(getApplicationContext());
        User user = uHandler.getUserInformation(currentUser);

        currentUser = ServerConnection.getActiveUser();

        try {
            System.out.println("getRanking() ");
            result = getRanking();
            //System.out.println("getUserRanking(currentUser)");
            //userRanking = getUserRanking(currentUser);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Construct the data source
        ArrayList<UserRanking> arrayOfUsers = new ArrayList<UserRanking>();
        // Create the adapter to convert the array to views
        RankingAdapter adapter = new RankingAdapter(this, arrayOfUsers);
        // Attach the adapter to a ListView
        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(adapter);

        if (result.size()>0) {
            if (result.get(0).equals("Nothing to show...")){
                UserRanking newUser = new UserRanking("Nothing to show... Please check your internet connection.","","",false);
                adapter.add(newUser);
            }            else{
            for (int index = 0; index < result.size(); index++) {
                String ResultUser = (String) result.get(index);
                String[] splitString = ResultUser.split("=");
                String name = splitString[0];
                String points = splitString[1];
                if (name.equals(currentUser)) {
                    UserRanking newUser = new UserRanking(name, points, index + 1 + ".", true);
                    adapter.add(newUser);
                } else {
                    UserRanking newUser = new UserRanking(name, points, index + 1 + ".", false);
                    adapter.add(newUser);
                }
            }

            }
        }


    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_ranking, menu);
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

    private ArrayList<String> getRanking() throws ExecutionException, InterruptedException {
        dataToPut = "";
        serverRankingResult = new ArrayList<>();
        ArrayList<String> serverRankingResult = new PutAsyncTaskone().execute("http://mushroom.16mb.com/android/ranglijst_top.php").get();

        System.out.println("checkserver.size = " + serverRankingResult.size());


        if (serverRankingResult.size() > 0) {
            System.out.println("checkserver.size = " + serverRankingResult.size());
            return serverRankingResult;
        } else {
            ArrayList<String> failedResult = new ArrayList<>();
            failedResult.add("Nothing to show...");
            return failedResult;
        }

    }


    /*private String getUserRanking(String userName) throws ExecutionException, InterruptedException {
        dataToPut = userName;
        String userRank;
        serverRankingResult = new ArrayList<>();
        ArrayList<String> serverRankingResult = new PutAsyncTasktwo().execute("http://mushroom.16mb.com/android/ranglijkst_user_position.php").get();

        System.out.println("checkserver.size = " + serverRankingResult.size());


        if (serverRankingResult.size() > 0) {
            System.out.println("checkserver.size = " + serverRankingResult.size());
            String rawString = serverRankingResult.get(0);
            String[] splitString = rawString.split("=");
            if (splitString[0].equals("error")){
                userRank = "-";
            }else{
               userRank = splitString[0];
            }

            return userRank;
        } else {

            return "-";
        }

    }*/

    public ArrayList<String> putDataToServerone(String URL){
        ArrayList<String> status =  new ArrayList<>();
        try {

            java.net.URL url = new URL(URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");

            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            String input = URLEncoder.encode("userName", "UTF-8") + " = " + URLEncoder.encode(dataToPut, "UTF-8");


            OutputStream os = conn.getOutputStream();
            os.write(input.getBytes());
            os.flush();


            //Read the acknowledgement message after putting data to server
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));
            String output;
            System.out.println("Output from Server .... \n");
            while ((output = br.readLine()) != null) {
                System.out.println(output);
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

    class PutAsyncTaskone extends AsyncTask<String, Void, ArrayList<String>> {

        @Override
        protected ArrayList<String> doInBackground(String... urls) {
            return putDataToServerone(urls[0]);
        }
        @Override
        protected void onPostExecute(ArrayList<String> result) {
            serverRankingResult = result;
        }
    }

   /* public ArrayList<String> putDataToServertwo(String URL){
        ArrayList<String> status =  new ArrayList<>();
        try {

            java.net.URL url = new URL(URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");

            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            String input = URLEncoder.encode("userName", "UTF-8") + " = " + URLEncoder.encode(dataToPut, "UTF-8");


            OutputStream os = conn.getOutputStream();
            os.write(input.getBytes());
            os.flush();


            //Read the acknowledgement message after putting data to server
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));
            String output;
            //System.out.println("Output from Server .... \n");
            while ((output = br.readLine()) != null) {
                System.out.println(output);
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

    class PutAsyncTasktwo extends AsyncTask<String, Void, ArrayList<String>> {

        @Override
        protected ArrayList<String> doInBackground(String... urls) {
            return putDataToServertwo(urls[0]);
        }
        @Override
        protected void onPostExecute(ArrayList<String> result) {
            serverRankingResult = result;
        }
    }*/




}
