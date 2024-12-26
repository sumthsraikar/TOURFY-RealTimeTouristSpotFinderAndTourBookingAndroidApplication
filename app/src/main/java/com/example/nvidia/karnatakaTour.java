package com.example.nvidia;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class karnatakaTour extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_karnataka_tour);

        // Set window insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.karnatakaTour), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Find buttons by ID and set OnClickListeners
        Button tourDetailsButton1 = findViewById(R.id.tourDetailsButton1);
        tourDetailsButton1.setOnClickListener(view -> {
            // Intent to navigate to karnatakaTourDetailsActivity
            Intent intent = new Intent(karnatakaTour.this, karnatakaTourDetailsActivity.class);
            startActivity(intent);
        });

        Button tourDetailsButton2 = findViewById(R.id.tourDetailsButton2);
        tourDetailsButton2.setOnClickListener(view -> {
            // Intent to navigate to karnatakaTourDetailsActivity2
            Intent intent = new Intent(karnatakaTour.this, karnatakaTourDetailsActivity2.class);
            startActivity(intent);
        });

        Button tourDetailsButton3 = findViewById(R.id.tourDetailsButton3);
        tourDetailsButton3.setOnClickListener(view -> {
            // Intent to navigate to karnatakaTourDetailsActivity3
            Intent intent = new Intent(karnatakaTour.this, karnatakaTourDetailsActivity3.class);
            startActivity(intent);
        });

        Button tourDetailsButton4 = findViewById(R.id.tourDetailsButton4);
        tourDetailsButton4.setOnClickListener(view -> {
            // Intent to navigate to karnatakaTourDetailsActivity4
            Intent intent = new Intent(karnatakaTour.this, karnatakaTourDetailsActivity4.class);
            startActivity(intent);
        });

        Button tourDetailsButton5 = findViewById(R.id.tourDetailsButton5);
        tourDetailsButton5.setOnClickListener(view -> {
            // Intent to navigate to karnatakaTourDetailsActivity5
            Intent intent = new Intent(karnatakaTour.this, karnatakaTourDetailsActivity5.class);
            startActivity(intent);
        });

        Button tourDetailsButton6 = findViewById(R.id.tourDetailsButton6);
        tourDetailsButton6.setOnClickListener(view -> {
            // Intent to navigate to karnatakaTourDetailsActivity6
            Intent intent = new Intent(karnatakaTour.this, karnatakaTourDetailsActivity7.class);
            startActivity(intent);
        });
    }
}
