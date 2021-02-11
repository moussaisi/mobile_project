    package com.example.exerciseform;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.net.URI;

public class UniversityFragment extends Fragment implements OnMapReadyCallback {
    private GoogleMap mMap;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_university, container, false);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        return view;
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
        // Add a marker in Sydney and move the camera
        LatLng isiDakar = new LatLng(14.6924827, -17.4545013);
        mMap.addMarker(new MarkerOptions().position(isiDakar)
                .title("ISI Keur massar").snippet("Contact: +221338224178 Site : groupeisi.com"));
        LatLng isiKm = new LatLng(14.786461, -17.2889386);
        mMap.addMarker(new MarkerOptions().position(isiKm)
                .title("ISI Keur Massar").snippet("Contact: +221338784349 Site : groupeisi.com"));

        LatLng ucad = new LatLng(14.6911552,-17.4728325);
        mMap.addMarker(new MarkerOptions().position(ucad)
                .title("UCAD").snippet("Contact: +221784946215 Site : ucad.sn"));

        //mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        CircleOptions co = new CircleOptions()
                .center(isiDakar)
                .radius(500)//Le nombre de mètres
                .fillColor(Color.BLUE)
                .strokeColor(Color.RED)
                .strokeWidth(5);
        mMap.addCircle(co);
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                if(marker.getTitle().equalsIgnoreCase("Ucad"))
                {
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:777437440"));
                    startActivity(intent);
                }
                if(marker.getTitle().equalsIgnoreCase("ISI Keur massar"))
                {
                    Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("sms:777437440"));
                    intent.putExtra("sms_body","Bonsoir les étudiants DITI5.");
                    startActivity(intent);
                }
                return false;
            }
        });
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(isiDakar,12));
    }
}