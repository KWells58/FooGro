package edu.floridapoly.mobiledeviceapps.fall23.foogro_1;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    // Instance of DatabaseHelper
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Initialize DatabaseHelper
        dbHelper = new DatabaseHelper(this);
        JSONDataImporter importer = new JSONDataImporter(this);
        // Import data from Google Drive
        importer.importFromGoogleDrive();

        EditText searchText = findViewById(R.id.editTextText);
        Button searchButton = findViewById(R.id.search_button);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String query = searchText.getText().toString();
                searchProducts(query);
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

    private void searchProducts(String query) {
        TextView resultsView = findViewById(R.id.tvResults);
        Cursor cursor = dbHelper.searchProducts(query);
        StringBuilder results = new StringBuilder();

        if (cursor != null) {
            while (cursor.moveToNext()) {
                String productName = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                String category = cursor.getString(cursor.getColumnIndexOrThrow("category"));
                double price = cursor.getDouble(cursor.getColumnIndexOrThrow("price"));
                String storeName = cursor.getString(cursor.getColumnIndexOrThrow("storeName"));
                int storeID = cursor.getInt(cursor.getColumnIndexOrThrow("storeID"));

                results.append("Name: ").append(productName)
                        .append(", Category: ").append(category)
                        .append(", Price: ").append(price)
                        .append(", Store Name: ").append(storeName)
                        .append(", Store ID: ").append(storeID)
                        .append("\n\n");
            }
            cursor.close();
        }

        resultsView.setText(results.toString());
    }

}
