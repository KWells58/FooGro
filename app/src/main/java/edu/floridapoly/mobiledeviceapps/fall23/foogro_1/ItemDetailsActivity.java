package edu.floridapoly.mobiledeviceapps.fall23.foogro_1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ItemDetailsActivity extends AppCompatActivity {

    private TextView itemNameTextView; // TextView for displaying the store name

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details);

        DatabaseHelper dbHelper = new DatabaseHelper(this);
        String itemId = getIntent().getStringExtra("itemId");
        String storeName = getIntent().getStringExtra("storeName");
        boolean fromStandardSearch = getIntent().getBooleanExtra("fromStandardSearch", false);

        itemNameTextView = findViewById(R.id.itemNameTextView); // Initialize the TextView
        itemNameTextView.setText(storeName); // Set the store name to the TextView

        Cursor cursor = dbHelper.getProduct(Integer.parseInt(itemId));
        if (cursor != null && cursor.moveToFirst()) {
            String itemName = cursor.getString(cursor.getColumnIndexOrThrow("name"));
            double itemPrice = cursor.getDouble(cursor.getColumnIndexOrThrow("price"));
            String description = cursor.getString(cursor.getColumnIndexOrThrow("description"));

            if (fromStandardSearch) {
                displayPricesFromOtherStores(itemName, storeName, dbHelper, description);
            }
        }
        // Ensure the cursor is closed after use
        if(cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
    }


    private void displayPricesFromOtherStores(String itemName, String storeName, DatabaseHelper dbHelper, String itemDescription) {
        ListView priceListView = findViewById(R.id.priceListView);
        ArrayList<StoreItem> storeItems = new ArrayList<>();
        Cursor storesCursor = null;
        try {
            storesCursor = dbHelper.searchProductInStore(itemName, storeName);
            while (storesCursor != null && storesCursor.moveToNext()) {
                int itemId = storesCursor.getInt(storesCursor.getColumnIndexOrThrow("_id")); // Retrieve the item ID
                double storePrice = storesCursor.getDouble(storesCursor.getColumnIndexOrThrow("price"));
                String description = storesCursor.getString(storesCursor.getColumnIndexOrThrow("description"));
                storeItems.add(new StoreItem(itemId, storeName, storePrice, description)); // Use the ID here
            }
            StoreItemAdapter adapter = new StoreItemAdapter(this, storeItems);
            priceListView.setAdapter(adapter);
        } finally {
            if (storesCursor != null) {
                storesCursor.close();
            }
        }
    }

    private void displayPricesFromAllStores(String itemName, DatabaseHelper dbHelper, String itemDescription) {
        ListView priceListView = findViewById(R.id.priceListView);
        ArrayList<StoreItem> storeItems = new ArrayList<>();
        Cursor storesCursor = null;
        try {
            storesCursor = dbHelper.searchProducts(itemName);
            while (storesCursor != null && storesCursor.moveToNext()) {
                int itemId = storesCursor.getInt(storesCursor.getColumnIndexOrThrow("_id")); // Retrieve the item ID
                String storeName = storesCursor.getString(storesCursor.getColumnIndexOrThrow("storeName"));
                double storePrice = storesCursor.getDouble(storesCursor.getColumnIndexOrThrow("price"));
                String description = storesCursor.getString(storesCursor.getColumnIndexOrThrow("description"));
                storeItems.add(new StoreItem(itemId, storeName, storePrice, description)); // Use the ID here
            }
            StoreItemAdapter adapter = new StoreItemAdapter(this, storeItems);
            priceListView.setAdapter(adapter);
        } finally {
            if (storesCursor != null) {
                storesCursor.close();
            }
        }
    }
}
