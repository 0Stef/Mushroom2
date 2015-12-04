package com.mushroom.cwb1.mushroom2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class RankingAdapter extends ArrayAdapter<UserRanking> {

    public RankingAdapter(Context context, ArrayList<UserRanking> users) {
        super(context, 0, users);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        UserRanking userranking = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.ranking_layout, parent, false);
        }

        // Lookup view for data population
        TextView rName = (TextView) convertView.findViewById(R.id.rName);
        TextView rPoints = (TextView) convertView.findViewById(R.id.rPoints);
        TextView rNbRanking = (TextView) convertView.findViewById(R.id.rNbRanking);


        // Populate the data into the template view using the data object
        rName.setText(userranking.name);
        rPoints.setText(userranking.points);
        rNbRanking.setText(userranking.nbRank);

        // Return the completed view to render on screen
        return convertView;
    }
}
