package edu.floridapoly.mobiledeviceapps.fall23.foogro_1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

public class Cart_Screen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_screen);

        SharedPreferences sharedPreferences = getSharedPreferences("CartPreferences", MODE_PRIVATE);
        String itemId = sharedPreferences.getString("itemId", "");
        String storeName = sharedPreferences.getString("storeName", "");
        // Retrieve other details as stored

        // Update UI with these details
        TextView itemNameTextView = findViewById(R.id.itemName1); // Replace with your TextView's ID
        itemNameTextView.setText(storeName); // Example of setting store name
        // Update other UI elements as needed
    }
}
