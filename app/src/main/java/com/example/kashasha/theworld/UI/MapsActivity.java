package com.example.kashasha.theworld.UI;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.example.kashasha.theworld.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private double lat;
    private double lng;
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra(getString(R.string.map_data));
        setLat(bundle.getDouble(getString(R.string.lat)));
        setLng(bundle.getDouble(getString(R.string.lng)));
        setName(bundle.getString(getString(R.string.name)));
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public double getLng() {
        return lng;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLat() {
        return lat;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng country = new LatLng(getLat(), getLng());
        mMap.addMarker(new MarkerOptions().position(country).title(getName()));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(country));
    }

}
