package com.example.nvidia;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        // Initialize Firebase Authentication and Firestore
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        // Check if user is already logged in
        if (mAuth.getCurrentUser() != null) {
            // User is logged in, navigate directly to MainActivity
            navigateToMainActivity();
            return; // No need to show login screen
        }

        // UI components
        EditText phoneEditText = findViewById(R.id.phone);
        Button loginButton = findViewById(R.id.loginButton);

        // Set up button click listener
        loginButton.setOnClickListener(v -> {
            String phoneNumber = phoneEditText.getText().toString().trim();

            if (phoneNumber.isEmpty()) {
                Toast.makeText(LoginActivity.this, "Please enter your phone number", Toast.LENGTH_SHORT).show();
                return;
            }

            // Search Firestore for the phone number
            authenticateWithPhoneNumber(phoneNumber);
        });

        // Set window insets for Edge-to-Edge layout
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void authenticateWithPhoneNumber(String phoneNumber) {
        // Query Firestore to find the user by phone number
        db.collection("users")
                .whereEqualTo("phone", phoneNumber)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && !task.getResult().isEmpty()) {
                        // User exists, proceed to the main activity
                        navigateToMainActivity();
                    } else {
                        // No user found with this phone number
                        Toast.makeText(LoginActivity.this, "No account found with this phone number", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void navigateToMainActivity() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish(); // Finish the login activity so user can't go back
    }
}
