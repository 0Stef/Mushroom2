package com.mushroom.cwb1.mushroom2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class Ranking extends AppCompatActivity {

    public String currentUser;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);

        currentUser = getIntent().getStringExtra("username");

        TextView points1 = (TextView)findViewById(R.id.points1);
        TextView points2 = (TextView)findViewById(R.id.points2);
        TextView points3 = (TextView)findViewById(R.id.points3);
        TextView points4 = (TextView)findViewById(R.id.points4);
        TextView points5 = (TextView)findViewById(R.id.points5);
        TextView points6 = (TextView)findViewById(R.id.points6);
        TextView points7 = (TextView)findViewById(R.id.points7);
        TextView points8 = (TextView)findViewById(R.id.points8);
        TextView points9 = (TextView)findViewById(R.id.points9);
        TextView points10 = (TextView)findViewById(R.id.points10);
        TextView points11 = (TextView)findViewById(R.id.points11);
        TextView points12 = (TextView)findViewById(R.id.points12);
        TextView points13 = (TextView)findViewById(R.id.points13);
        TextView points14 = (TextView)findViewById(R.id.points14);
        TextView points15 = (TextView)findViewById(R.id.points15);

        TextView name1 = (TextView)findViewById(R.id.name1);
        TextView name2 = (TextView)findViewById(R.id.name2);
        TextView name3 = (TextView)findViewById(R.id.name3);
        TextView name4 = (TextView)findViewById(R.id.name4);
        TextView name5 = (TextView)findViewById(R.id.name5);
        TextView name6 = (TextView)findViewById(R.id.name6);
        TextView name7 = (TextView)findViewById(R.id.name7);
        TextView name8 = (TextView)findViewById(R.id.name8);
        TextView name9 = (TextView)findViewById(R.id.name9);
        TextView name10 = (TextView)findViewById(R.id.name10);
        TextView name11 = (TextView)findViewById(R.id.name11);
        TextView name12 = (TextView)findViewById(R.id.name12);
        TextView name13 = (TextView)findViewById(R.id.name13);
        TextView name14 = (TextView)findViewById(R.id.name14);
        TextView name15 = (TextView)findViewById(R.id.name15);

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
}
