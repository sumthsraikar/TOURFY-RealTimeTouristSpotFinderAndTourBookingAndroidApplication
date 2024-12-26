package com.example.nvidia;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

public class PharmaciesNearby extends AppCompatActivity {

    private static final String TAG = "PharmaciesNearby";
    private static final String azureMapsSubscriptionKey = "BzyUJYQAFHBk1bQtzysagEUew5vdsN0OgDVDF3bCAtD9yuJPqHXzJQQJ99ALACYeBjFBZAt0AAAgAZMP3q2s";

    private TextView locationText;
    private FusedLocationProviderClient fusedLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pharmacies_nearby);

        // Initialize views
        locationText = findViewById(R.id.location_text);
        LinearLayout pharmaciesContainer = findViewById(R.id.pharmacies_container);

        // Initialize FusedLocationProviderClient
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        // Fetch current location
        fetchCurrentLocation(pharmaciesContainer);
    }

    private void fetchCurrentLocation(LinearLayout pharmaciesContainer) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            return;
        }

        fusedLocationClient.getLastLocation().addOnSuccessListener(this, location -> {
            if (location != null) {
                fetchLocationDetails(location);
                fetchNearbyPharmacies(location, pharmaciesContainer);
            } else {
                locationText.setText("Unable to fetch location.");
            }
        });
    }

    private void fetchLocationDetails(Location location) {
        String url = "https://atlas.microsoft.com/search/address/reverse/json?api-version=1.0&query="
                + location.getLatitude() + "," + location.getLongitude()
                + "&subscription-key=" + azureMapsSubscriptionKey;

        RequestQueue queue = Volley.newRequestQueue(this);
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
                String state = address.optString("adminDistrict", "Karnataka");
                locationText.setText(String.format("%s, %s", district, state));
            } else {
                locationText.setText("Location details not found.");
            }
        } catch (JSONException e) {
            Log.e(TAG, "Error parsing location details: " + e.getMessage(), e);
            locationText.setText("Error parsing location details.");
        }
    }

    private void fetchNearbyPharmacies(Location location, LinearLayout pharmaciesContainer) {
        String url = "https://atlas.microsoft.com/search/poi/json?api-version=1.0&query=pharmacy&lat="
                + location.getLatitude() + "&lon=" + location.getLongitude()
                + "&radius=5000&subscription-key=" + azureMapsSubscriptionKey;

        RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> displayNearbyPharmacies(response.toString(), pharmaciesContainer),
                error -> {
                    Log.e(TAG, "Error fetching pharmacies: " + error.getMessage());
                });
        queue.add(jsonObjectRequest);
    }

    private void displayNearbyPharmacies(String jsonResponse, LinearLayout pharmaciesContainer) {
        try {
            pharmaciesContainer.removeAllViews(); // Clear previous entries

            JSONObject jsonObject = new JSONObject(jsonResponse);
            JSONArray results = jsonObject.getJSONArray("results");

            for (int i = 0; i < Math.min(5, results.length()); i++) {
                JSONObject pharmacy = results.getJSONObject(i);
                JSONObject poiDetails = pharmacy.optJSONObject("poi");

                // Extract pharmacy details
                String name = poiDetails != null ? poiDetails.optString("name", "Unknown Name") : "Unknown Name";
                String phone = poiDetails != null ? poiDetails.optString("phone", "No Phone Number") : "No Phone Number";
                String timing = poiDetails != null ? poiDetails.optString("openingHours", "Timing Not Available") : "Timing Not Available";
                double latitude = pharmacy.optJSONObject("position").optDouble("lat", 0.0);
                double longitude = pharmacy.optJSONObject("position").optDouble("lon", 0.0);

                // Create a clickable box for each pharmacy
                View pharmacyView = getLayoutInflater().inflate(R.layout.pharmacy_item, pharmaciesContainer, false);
                TextView pharmacyName = pharmacyView.findViewById(R.id.pharmacy_name);
                TextView pharmacyDetails = pharmacyView.findViewById(R.id.pharmacy_details);

                pharmacyName.setText(name);
                pharmacyDetails.setText(String.format("Phone: %s\nTiming: %s", phone, timing));

                // Add click listener to open Google Maps
                pharmacyView.setOnClickListener(v -> {
                    Uri gmmIntentUri = Uri.parse("google.navigation:q=" + latitude + "," + longitude);
                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                    mapIntent.setPackage("com.google.android.apps.maps");
                    startActivity(mapIntent);
                });

                pharmaciesContainer.addView(pharmacyView);
            }
        } catch (JSONException e) {
            Log.e(TAG, "Error parsing pharmacy response: " + e.getMessage(), e);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            fetchCurrentLocation(findViewById(R.id.pharmacies_container));
        } else {
            locationText.setText("Permission denied.");
        }
    }
}
