package com.myapplication.view;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.myapplication.R;
import com.myapplication.models.Venue;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker m) {
                Venue selectedVenue = new Gson().fromJson(m.getTag().toString(),Venue.class);
                Intent i = new Intent(MapsActivity.this,DetailScreenActivity.class);
                i.putExtra("name", selectedVenue.getName());
                i.putExtra("icon_url", selectedVenue.getCategories().get(0).getIcon().getPrefix() + selectedVenue.getCategories().get(0).getIcon().getSuffix());
                i.putExtra("category", selectedVenue.getCategories().get(0).getName());
                i.putExtra("dist_from_seattle", selectedVenue.getDistFromSeattle());
                i.putExtra("url", selectedVenue.getUrl());
                i.putExtra("id", selectedVenue.getId());
                i.putExtra("latitude", String.valueOf(selectedVenue.getLocation().getLat()));
                i.putExtra("longitude", String.valueOf(selectedVenue.getLocation().getLng()));
                startActivity(i);
            }
        });

        JSONArray markersArray;
        try {
            markersArray = new JSONArray(getIntent().getStringExtra("venue_list"));
            LatLngBounds.Builder builder = new LatLngBounds.Builder();

            for(int i = 0 ; i < markersArray.length() ; i++ ) {
                JSONObject markerObj = markersArray.getJSONObject(i);
                LatLng latLng = new LatLng(markerObj.getJSONObject("location").getDouble("lat"), markerObj.getJSONObject("location").getDouble("lng"));

                Marker mk = mMap.addMarker(new MarkerOptions()
                        .position(latLng).anchor(0.5f, 0.5f)
                        .title(markersArray.getJSONObject(i).getString("name")));
                mk.setTag(markerObj.toString());
                builder.include(latLng);
            }
            CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(builder.build(), 0);
            mMap.moveCamera(cu);

        } catch (JSONException e) {
            e.printStackTrace();
        }



    }
}
