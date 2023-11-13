package edu.floridapoly.mobiledeviceapps.fall23.foogro_1;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    // Instance of DatabaseHelper
    //test
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize DatabaseHelper
        dbHelper = new DatabaseHelper(this);

        // Check if first launch and add a sample product to the database
        addSampleProductToDatabase();

        Button profileHomeButton = findViewById(R.id.profile_home_button);
        Button groceryButton = findViewById(R.id.grocery_button);

        profileHomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("Profile/Home Button Clicked");
                // Start the Profile Screen
                Intent intent = new Intent(MainActivity.this, Profile_Screen.class);
                startActivity(intent);
            }
        });

        groceryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("Grocery Button Clicked");
                // Start the Grocery Screen
                Intent intent = new Intent(MainActivity.this, Grocery_Screen.class);
                startActivity(intent);
            }
        });
    }

    private void addSampleProductToDatabase() {
        // Add a product to the database HERE
        dbHelper.addProduct("Banana", "Fruit", 0.49, "Publix", 101);
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
