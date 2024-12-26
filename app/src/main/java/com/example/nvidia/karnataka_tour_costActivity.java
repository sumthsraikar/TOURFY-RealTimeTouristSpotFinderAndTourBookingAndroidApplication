package com.example.nvidia;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class karnataka_tour_costActivity extends AppCompatActivity {

    private Spinner tourPackageSpinner, tourDateSpinner;
    private EditText adultsInput, childrenWithBedInput, childrenWithoutBedInput, infantsInput;
    private TextView subtotalText, gstText, totalCostText;
    private Button saveButton;

    private final double gstRate = 0.05;
    private double selectedTourPrice = 0;
    private String selectedTourName = "";
    private String selectedTourDate = "";

    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_karnataka_tour_cost);

        // Initialize Firebase
        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        // Initialize input fields
        tourPackageSpinner = findViewById(R.id.tourPackageSpinner);
        tourDateSpinner = findViewById(R.id.tourDateSpinner);
        adultsInput = findViewById(R.id.adultsInput);
        childrenWithBedInput = findViewById(R.id.childrenWithBedInput);
        childrenWithoutBedInput = findViewById(R.id.childrenWithoutBedInput);
        infantsInput = findViewById(R.id.infantsInput);

        // Initialize result fields
        subtotalText = findViewById(R.id.subtotalText);
        gstText = findViewById(R.id.gstText);
        totalCostText = findViewById(R.id.totalCostText);

        // Initialize save button
        saveButton = findViewById(R.id.saveButton);

        // Set up Tour Packages Spinner
        List<String> tourPackages = new ArrayList<>();
        tourPackages.add("Dakshina Kannada Tour - ₹10500");
        tourPackages.add("Badami-Bijapur Tour - ₹12000");
        tourPackages.add("Dandeli Tour  - ₹6000");
        tourPackages.add("Jog falls Tour - ₹2600");
        tourPackages.add("Chikkamangaluru Tour - ₹9000");
        tourPackages.add("Mysore-Coorg Tour  - ₹9000");
        tourPackages.add("Shirdi Tour - ₹20,000");
        tourPackages.add("Munnar - Alleppey Tour - ₹14000");
        tourPackages.add("Tamilnadu Tour - ₹21,600");
        tourPackages.add("Goa Dudhsagar Tourr - ₹12,500");
        tourPackages.add("Mantralaya Srishailam Tour  - ₹12,500");


        ArrayAdapter<String> packageAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, tourPackages);
        packageAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tourPackageSpinner.setAdapter(packageAdapter);

        tourPackageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String selectedItem = (String) parentView.getItemAtPosition(position);
                selectedTourName = selectedItem.split(" - ")[0];
                selectedTourPrice = Double.parseDouble(selectedItem.split(" - ₹")[1]);
                calculateTotals();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                selectedTourName = "";
                selectedTourPrice = 0;
                calculateTotals();
            }
        });

        // Set up Tour Dates Spinner
        List<String> tourDates = new ArrayList<>();
        String[] months = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
        int[] days = {7, 21, 4, 18, 11, 25, 8, 22, 6, 20, 3, 24, 8, 22, 5, 19, 9, 23, 7, 21, 4, 18, 9, 23};

        for (int i = 0; i < days.length; i++) {
            tourDates.add(months[i / 2] + " - " + days[i]);
        }

        ArrayAdapter<String> dateAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, tourDates);
        dateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tourDateSpinner.setAdapter(dateAdapter);

        tourDateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedTourDate = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedTourDate = "";
            }
        });

        // Add listeners to input fields to recalculate totals on input change
        adultsInput.addTextChangedListener(inputWatcher);
        childrenWithBedInput.addTextChangedListener(inputWatcher);
        childrenWithoutBedInput.addTextChangedListener(inputWatcher);
        infantsInput.addTextChangedListener(inputWatcher);

        // Save button click listener
        saveButton.setOnClickListener(view -> saveDataToFirebase());
    }

    private final TextWatcher inputWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            calculateTotals();
        }
    };

    private void calculateTotals() {
        int adults = parseInput(adultsInput);
        int childrenWithBed = parseInput(childrenWithBedInput);
        int childrenWithoutBed = parseInput(childrenWithoutBedInput);
        int infants = parseInput(infantsInput);

        int subtotal = (adults * (int) selectedTourPrice) +
                (childrenWithBed * (int) (selectedTourPrice * 0.8)) +  // Assuming discount for children with bed
                (childrenWithoutBed * (int) (selectedTourPrice * 0.5)) +  // Assuming discount for children without bed
                (infants * (int) (selectedTourPrice * 0.2));  // Assuming discount for infants

        int gst = (int) (subtotal * gstRate);
        int totalCost = subtotal + gst;

        subtotalText.setText("sub total ₹" + subtotal);
        gstText.setText("gst ₹" + gst);
        totalCostText.setText("total cost₹" + totalCost);
    }

    private int parseInput(EditText editText) {
        String input = editText.getText().toString();
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    private void saveDataToFirebase() {
        if (firebaseAuth.getCurrentUser() == null) {
            Toast.makeText(this, "No user is logged in.", Toast.LENGTH_SHORT).show();
            return;
        }

        int adults = parseInput(adultsInput);
        int childrenWithBed = parseInput(childrenWithBedInput);
        int childrenWithoutBed = parseInput(childrenWithoutBedInput);
        int infants = parseInput(infantsInput);

        int subtotal = (adults * (int) selectedTourPrice) +
                (childrenWithBed * (int) (selectedTourPrice * 0.8)) +
                (childrenWithoutBed * (int) (selectedTourPrice * 0.5)) +
                (infants * (int) (selectedTourPrice * 0.2));

        int gst = (int) (subtotal * gstRate);
        int totalCost = subtotal + gst;

        Map<String, Object> data = new HashMap<>();
        data.put("Tour Package", selectedTourName);
        data.put("Tour Date", selectedTourDate);
        data.put("Adults", adults);
        data.put("Children with Bed", childrenWithBed);
        data.put("Children without Bed", childrenWithoutBed);
        data.put("Infants", infants);
        data.put("Subtotal", subtotal);
        data.put("GST", gst);
        data.put("Total Cost", totalCost);

        firestore.collection("Tourbooking")
                .document(firebaseAuth.getCurrentUser().getUid())
                .collection("Bookings")
                .add(data)
                .addOnSuccessListener(documentReference -> {
                    Toast.makeText(karnataka_tour_costActivity.this, "Your booking was successful. Our team will contact you soon ", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(karnataka_tour_costActivity.this, "Failed to save booking.", Toast.LENGTH_SHORT).show();
                });
    }
}
