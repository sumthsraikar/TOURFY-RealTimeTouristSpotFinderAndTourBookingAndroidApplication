package com.example.nvidia;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;
import android.widget.ViewFlipper;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class karnatakaTourDetailsActivity2 extends AppCompatActivity {

    private static final String TAG = "karnatakaTourDetailsActivity2";
    private ViewFlipper viewFlipper;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            // Enable edge-to-edge support
            EdgeToEdge.enable(this);
            setContentView(R.layout.activity_karnataka_tour_details2);

            // Setting up the toolbar
            Toolbar toolbar = findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

            // Enable back navigation with the default back arrow icon
            if (getSupportActionBar() != null) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setTitle("Tourfy"); // Set title explicitly
            }

            // Apply window insets for edge-to-edge display
            ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
                Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
                return insets;
            });

            // Initialize ViewFlipper and set up the image slider
            viewFlipper = findViewById(R.id.map_view);
            if (viewFlipper != null) {
                viewFlipper.setFlipInterval(3000); // Set flip interval to 3 seconds
                viewFlipper.setInAnimation(this, android.R.anim.slide_in_left);
                viewFlipper.setOutAnimation(this, android.R.anim.slide_out_right);
                viewFlipper.startFlipping(); // Start flipping images
            } else {
                Log.e(TAG, "ViewFlipper (map_view) not found in the layout");
            }

            // Find the "Book Now" button and set an OnClickListener
            Button bookNowButton = findViewById(R.id.booknowbuton2);
            if (bookNowButton != null) {
                bookNowButton.setOnClickListener(v -> {
                    Log.d(TAG, "Select Your Tour Package!");
                    Toast.makeText(karnatakaTourDetailsActivity2.this, "Select Your Tour Package", Toast.LENGTH_SHORT).show();

                    // Start the karnataka_tour_costActivity
                    Intent intent = new Intent(karnatakaTourDetailsActivity2.this, karnataka_tour_costActivity.class);
                    startActivity(intent);
                });
            } else {
                Log.e(TAG, "Book Now button (booknowbuton2) not found in the layout");
            }

            // Find the "cardView11" and set an OnClickListener
            CardView cardView11 = findViewById(R.id.cardView11);
            if (cardView11 != null) {
                cardView11.setOnClickListener(v -> {
                    Log.d(TAG, "CardView11 was clicked!");
                    Toast.makeText(karnatakaTourDetailsActivity2.this, "CardView11 is working now", Toast.LENGTH_SHORT).show();

                    // Start the karnataka_tour_costActivity
                    Intent intent = new Intent(karnatakaTourDetailsActivity2.this, karnataka_tour_costActivity.class);
                    startActivity(intent);
                });
            } else {
                Log.e(TAG, "CardView11 not found in the layout");
            }

        } catch (Exception e) {
            Log.e(TAG, "Error in onCreate", e);
        }
    }

    // Handle back button press to close the activity
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
