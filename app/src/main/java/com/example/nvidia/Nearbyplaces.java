package com.example.nvidia;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class Nearbyplaces extends Fragment implements OnMapReadyCallback {

    private GoogleMap mMap;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private static final int LOCATION_REQUEST_CODE = 100;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the fragment's layout
        return inflater.inflate(R.layout.activity_nearbyplaces, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireActivity());

        // Initialize the map
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;

        // Check location permissions
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_REQUEST_CODE);
            return;
        }

        // Enable current location
        mMap.setMyLocationEnabled(true);

        // Get the user's location
        fusedLocationProviderClient.getLastLocation().addOnSuccessListener(location -> {
            if (location != null) {
                LatLng currentLocation = new LatLng(location.getLatitude(), location.getLongitude());
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 14));

                // Add markers for nearby places
                addMarker(new LatLng(currentLocation.latitude + 0.01, currentLocation.longitude), "@location1");
                addMarker(new LatLng(currentLocation.latitude - 0.01, currentLocation.longitude), "@location2");
                addMarker(new LatLng(currentLocation.latitude + 0.01, currentLocation.longitude), "@location3");
                addMarker(new LatLng(currentLocation.latitude - 0.01, currentLocation.longitude), "@location4");
                addMarker(new LatLng(currentLocation.latitude + 0.01, currentLocation.longitude), "@location5");
                addMarker(new LatLng(currentLocation.latitude - 0.01, currentLocation.longitude), "@location6");
                addMarker(new LatLng(currentLocation.latitude + 0.01, currentLocation.longitude), "@location7");
                addMarker(new LatLng(currentLocation.latitude - 0.01, currentLocation.longitude), "@location8");
                addMarker(new LatLng(currentLocation.latitude + 0.01, currentLocation.longitude), "@location9");
                addMarker(new LatLng(currentLocation.latitude - 0.01, currentLocation.longitude), "@location10");
            }
        });

        // Handle marker info window click
        mMap.setOnInfoWindowClickListener(marker -> {
            // Open location in Google Maps app
            Uri gmmIntentUri = Uri.parse("geo:" + marker.getPosition().latitude + "," + marker.getPosition().longitude
                    + "?q=" + Uri.encode(marker.getTitle()));
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
            mapIntent.setPackage("com.google.android.apps.maps");
            startActivity(mapIntent);
        });
    }

    private void addMarker(LatLng position, String title) {
        mMap.addMarker(new MarkerOptions().position(position).title(title));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == LOCATION_REQUEST_CODE && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (mMap != null) {
                onMapReady(mMap);
            }
        }
    }
}
