package edu.floridapoly.mobiledeviceapps.fall23.foogro_1;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ItemDetailsActivity extends AppCompatActivity {

    // Declare dbHelper and priceListView as class members
    private DatabaseHelper dbHelper;
    private ListView priceListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details);

        // Initialize dbHelper and priceListView
        dbHelper = new DatabaseHelper(this);
        priceListView = findViewById(R.id.priceListView);

        Intent intent = getIntent();
        // Retrieve the storeName and itemId from the intent
        String storeName = intent.getStringExtra("STORE_NAME");
        String itemId = intent.getStringExtra("itemId");

        if (itemId != null && storeName != null) {
            // Fetch and display the prices for the specific item from the selected store
            displayPricesForItem(storeName, itemId);
        }
    }

    private void displayPricesForItem(String storeName, String itemId) {
        try {
            int id = Integer.parseInt(itemId); // Convert itemId to integer
            Cursor cursor = dbHelper.getProduct(id);
            ArrayList<StoreItem> storeItems = new ArrayList<>();

            if (cursor != null && cursor.moveToFirst()) {
                String retrievedStoreName = cursor.getString(cursor.getColumnIndexOrThrow("storeName"));
                if (retrievedStoreName.equals(storeName)) {
                    do {
                        String itemName = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                        double price = cursor.getDouble(cursor.getColumnIndexOrThrow("price"));
                        String description = cursor.getString(cursor.getColumnIndexOrThrow("description"));
                        storeItems.add(new StoreItem(itemName, price, description));
                    } while (cursor.moveToNext());
                }
                cursor.close();
            }

            // Set up the adapter and assign it to the ListView
            StoreItemAdapter adapter = new StoreItemAdapter(this, storeItems);
            priceListView.setAdapter(adapter);
        } catch (NumberFormatException e) {
            // Handle the exception
            e.printStackTrace();
        }
    }
}
