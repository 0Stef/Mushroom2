package com.mushroom.cwb1.mushroom2;

import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.LinkedList;

public class RouteMapping extends FragmentActivity {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    private LinkedList list;
    private int nbRide;
    private String currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_mapping);

        //currentUser = getIntent().getStringExtra("username");
        currentUser = "stephan";
        DataBaseHandler2 handler = new DataBaseHandler2(getApplicationContext(), currentUser);
        nbRide=handler.getGreatestRideID();
        list = handler.getList(handler.getAllThisRide(nbRide));

        setUpMapIfNeeded();


    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p/>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {

        PolylineOptions mPolylineOptions = new PolylineOptions();

        if (list.size() != 0){
            for (int index = 1; index < list.size(); index++) {
                dbRow rowprev = (dbRow) list.get(index-1);
                dbRow row = (dbRow) list.get(index);
                mPolylineOptions.add(new LatLng(rowprev.getLatitude(),rowprev.getLongitude()), new LatLng(row.getLatitude(),row.getLongitude()));
            }
        }
        mPolylineOptions.width(5).color(Color.BLUE);
        mMap.addPolyline(mPolylineOptions);
        dbRow rowlast = (dbRow) list.get((list.size()-1)/2);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(rowlast.getLatitude(), rowlast.getLongitude()), 15.0f));
    }
}
