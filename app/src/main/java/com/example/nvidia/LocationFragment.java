package com.example.nvidia;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LocationFragment extends Fragment {

    private static final String TAG = "LocationFragment";
    private static final String azureMapsSubscriptionKey = "BzyUJYQAFHBk1bQtzysagEUew5vdsN0OgDVDF3bCAtD9yuJPqHXzJQQJ99ALACYeBjFBZAt0AAAgAZMP3q2s"; // Replace with your Azure Maps key

    private FusedLocationProviderClient fusedLocationClient;
    private TextView locationText;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_location, container, false);

        // Initialize FusedLocationProviderClient
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity());

        // TextView to display location details
        locationText = view.findViewById(R.id.locationText);

        // Fetch location automatically when the page is opened
        fetchCurrentLocation();

        // Add listeners for boxes
        view.findViewById(R.id.atmBox).setOnClickListener(v -> startActivity(new Intent(getActivity(), ATMsNearby.class)));
        view.findViewById(R.id.pharmaciesBox).setOnClickListener(v -> startActivity(new Intent(getActivity(), PharmaciesNearby.class)));
        view.findViewById(R.id.hotelBox).setOnClickListener(v -> startActivity(new Intent(getActivity(), HotelNearby.class)));
        view.findViewById(R.id.restaurantsBox).setOnClickListener(v -> startActivity(new Intent(getActivity(), RestaurantsNearby.class)));

        // Add a fetch location button
        Button fetchLocationButton = view.findViewById(R.id.fetchLocationButton);
        fetchLocationButton.setOnClickListener(v -> fetchCurrentLocation());

        return view;
    }

    private void fetchCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            return;
        }

        fusedLocationClient.getLastLocation().addOnSuccessListener(requireActivity(), location -> {
            if (location != null) {
                fetchLocationDetails(location);
            } else {
                locationText.setText("Unable to fetch location.");
            }
        });
    }

    private void fetchLocationDetails(Location location) {
        String url = "https://atlas.microsoft.com/search/address/reverse/json?api-version=1.0&query="
                + location.getLatitude() + "," + location.getLongitude()
                + "&subscription-key=" + azureMapsSubscriptionKey;

        RequestQueue queue = Volley.newRequestQueue(requireContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> displayLocationDetails(response.toString()),
                error -> {
                    Log.e(TAG, "Error fetching location details: " + error.getMessage());
                    locationText.setText("Unable to fetch district and state.");
                });
        queue.add(jsonObjectRequest);
    }

    private void displayLocationDetails(String jsonResponse) {
        try {
            JSONObject jsonObject = new JSONObject(jsonResponse);
            JSONArray results = jsonObject.getJSONArray("addresses");
            if (results.length() > 0) {
                JSONObject address = results.getJSONObject(0).getJSONObject("address");
                String district = address.optString("municipality", "Unknown District");
                String state = address.optString("adminDistrict", "Unknown State");
                locationText.setText(String.format("%s, %s", district, state));
            } else {
                locationText.setText("Location details not found.");
            }
        } catch (JSONException e) {
            Log.e(TAG, "Error parsing location details: " + e.getMessage(), e);
            locationText.setText("Error parsing location details.");
        }
    }
}
