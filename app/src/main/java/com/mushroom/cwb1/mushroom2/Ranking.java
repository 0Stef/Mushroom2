package com.mushroom.cwb1.mushroom2;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;

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
    private Button Daily;
    private Button Weekly;
    private Button Alltime;
    private ProgressBar spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);

        Daily = (Button)findViewById(R.id.Daily);
        Weekly = (Button)findViewById(R.id.Weekly);
        Alltime = (Button)findViewById(R.id.Alltime);
        spinner = (ProgressBar)findViewById(R.id.progressBar);

        Alltime.setClickable(false);
        Alltime.setBackgroundColor(Color.rgb(156, 156, 156));

    }



    @Override
    public void onResume() {
        super.onResume();

        Daily.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        spinner.setVisibility(View.VISIBLE);
                        Daily.setClickable(false);
                        Weekly.setClickable(true);
                        Alltime.setClickable(true);
                        Daily.setBackgroundColor(Color.rgb(156, 156, 156));
                        Weekly.setBackgroundColor(Color.rgb(255, 191, 106));
                        Alltime.setBackgroundColor(Color.rgb(255, 191, 106));
                        BuildList("http://mushroom.16mb.com/android/ranglijst_top_dagelijks.php");
                        spinner.setVisibility(View.INVISIBLE);
                    }
                }
        );

        Weekly.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        spinner.setVisibility(View.VISIBLE);
                        Daily.setClickable(true);
                        Weekly.setClickable(false);
                        Alltime.setClickable(true);
                        Daily.setBackgroundColor(Color.rgb(255, 191, 106));
                        Weekly.setBackgroundColor(Color.rgb(156, 156, 156));
                        Alltime.setBackgroundColor(Color.rgb(255, 191, 106));
                        BuildList("http://mushroom.16mb.com/android/ranglijst_top_wekelijks.php");
                        spinner.setVisibility(View.INVISIBLE);
                    }
                }
        );

        Alltime.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        spinner.setVisibility(View.VISIBLE);
                        Daily.setClickable(true);
                        Weekly.setClickable(true);
                        Alltime.setClickable(false);
                        Daily.setBackgroundColor(Color.rgb(255, 191, 106));
                        Weekly.setBackgroundColor(Color.rgb(255, 191, 106));
                        Alltime.setBackgroundColor(Color.rgb(156, 156, 156));
                        BuildList("http://mushroom.16mb.com/android/ranglijst_top.php");
                        spinner.setVisibility(View.INVISIBLE);
                    }
                }
        );

        BuildList("http://mushroom.16mb.com/android/ranglijst_top.php");
        spinner.setVisibility(View.INVISIBLE);

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

    private void BuildList(String webaddress) {

        currentUser = ServerConnection.getActiveUser();

        try {
            System.out.println("getRanking() ");
            result = getRanking(webaddress);
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
            if (result.get(0).equals("Nothing to show...")) {
                System.out.print("Nothing to show...");
                UserRanking newUser = new UserRanking(getString(R.string.check_internet), "", "");
                adapter.add(newUser);
            }else if (result.get(0).equals("No scores yet")){
                System.out.print("No scores yet");
                UserRanking newUser = new UserRanking(getString(R.string.no_scores_yet), "", "");
                adapter.add(newUser);
            }else {
                System.out.print("Making ranking");
                for (int index = 0; index < result.size(); index++) {
                    String ResultUser = (String) result.get(index);
                    String[] splitString = ResultUser.split("=");
                    String name = splitString[0];
                    String points = splitString[1];
                    UserRanking newUser = new UserRanking(name, points, index + 1 + ".");
                    adapter.add(newUser);
                }

            }
        }

    }

    private ArrayList<String> getRanking(String webaddress) throws ExecutionException, InterruptedException {
        dataToPut = "";
        serverRankingResult = new ArrayList<>();
        ArrayList<String> serverRankingResult = new PutAsyncTaskone().execute(webaddress).get();

        System.out.println("checkserver.size = " + serverRankingResult.size());


        if (serverRankingResult.size() > 0) {
            System.out.println("checkserver.size = " + serverRankingResult.size());
            System.out.println("checkserver.get(0) = " + serverRankingResult.get(0));
            return serverRankingResult;
        } else {
            ArrayList<String> failedResult = new ArrayList<>();
            failedResult.add("Nothing to show...");
            return failedResult;
        }

    }

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

}
