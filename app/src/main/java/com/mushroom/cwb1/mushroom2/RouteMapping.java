package com.mushroom.cwb1.mushroom2;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.LinkedList;
import java.util.Random;

public class RouteMapping extends FragmentActivity {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    private LinkedList list;
    private int nbRide;
    private String currentUser;
    private Random r = new Random();
    public int polylineColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_mapping);

        randomColor();

        currentUser = getIntent().getStringExtra("username");
        nbRide = getIntent().getIntExtra("nbRide", nbRide);

        DataBaseHandler2 handler = new DataBaseHandler2(getApplicationContext(), currentUser);
        list = handler.getList(handler.getAllThisRide(nbRide));

        setUpMapIfNeeded();

    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    public void randomColor(){

        int colorNb = r.nextInt(4);

        if (colorNb == 0) {
            polylineColor  = Color.RED;
        }else if (colorNb == 1){
            polylineColor = Color.GREEN;
        }else if (colorNb == 2){
            polylineColor = Color.MAGENTA;
        }else if (colorNb == 3) {
            polylineColor = Color.YELLOW;
        }else{
            polylineColor  = Color.BLUE;
        }

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
        FrameLayout mapLayout = (FrameLayout)findViewById(R.id.map);
        mapLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                LatLngBounds.Builder builder = new LatLngBounds.Builder();

                PolylineOptions mPolylineOptions = new PolylineOptions();

                if (list.size() != 0){
                    for (int index = 1; index < list.size(); index++) {
                        dbRow rowprev = (dbRow) list.get(index-1);
                        dbRow row = (dbRow) list.get(index);
                        mPolylineOptions.add(new LatLng(rowprev.getLatitude(), rowprev.getLongitude()), new LatLng(row.getLatitude(), row.getLongitude()));
                        builder.include(new LatLng(rowprev.getLatitude(), rowprev.getLongitude()));
                    }
                }

                mPolylineOptions.width(5).color(polylineColor);
                mMap.addPolyline(mPolylineOptions);

                LatLngBounds bounds = builder.build();
                int padding = 30; // offset from edges of the map in pixels
                CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);
                mMap.animateCamera(cu);
            }
        });
    }
}