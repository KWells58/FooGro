package edu.floridapoly.mobiledeviceapps.fall23.foogro_1;

import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ItemDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details);

        DatabaseHelper dbHelper = new DatabaseHelper(this);
        String itemId = getIntent().getStringExtra("itemId");
        boolean fromStandardSearch = getIntent().getBooleanExtra("fromStandardSearch", false);

        Cursor cursor = dbHelper.getProduct(Integer.parseInt(itemId));
        if (cursor != null && cursor.moveToFirst()) {
            String itemName = cursor.getString(cursor.getColumnIndexOrThrow("name"));
            double itemPrice = cursor.getDouble(cursor.getColumnIndexOrThrow("price"));

            TextView itemNameTextView = findViewById(R.id.itemNameTextView);
            itemNameTextView.setText(itemName);

            TextView itemPriceTextView = findViewById(R.id.itemPriceTextView);
            itemPriceTextView.setText("Price: $" + itemPrice);

            if (fromStandardSearch) {
                // Fetch and display prices from other stores
                displayPricesFromOtherStores(itemName, dbHelper);
            }
        }
    }

    private void displayPricesFromOtherStores(String itemName, DatabaseHelper dbHelper) {
        ListView priceListView = findViewById(R.id.priceListView);

        // Query the database for the same product from different stores
        Cursor storesCursor = dbHelper.searchProducts(itemName);
        if (storesCursor != null) {
            ArrayList<StoreItem> storeItems = new ArrayList<>();

            while (storesCursor.moveToNext()) {
                String storeName = storesCursor.getString(storesCursor.getColumnIndexOrThrow("storeName"));
                double storePrice = storesCursor.getDouble(storesCursor.getColumnIndexOrThrow("price"));

                storeItems.add(new StoreItem(storeName, storePrice));
            }

            // Create an ArrayAdapter to bind data to the ListView
            StoreItemAdapter adapter = new StoreItemAdapter(this, storeItems);

            priceListView.setAdapter(adapter);

            // Set a click listener for the ListView
            priceListView.setOnItemClickListener((parent, view, position, id) -> {
                StoreItem selectedStoreItem = (StoreItem) parent.getItemAtPosition(position);
                if (selectedStoreItem != null) {
                    // Handle adding the item to the cart for the selected store
                    // You can access selectedStoreItem.getStoreName() and selectedStoreItem.getStorePrice()
                }
            });

            storesCursor.close();  // Close the cursor after you've done processing it
        }
    }

}
