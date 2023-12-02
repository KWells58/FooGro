package edu.floridapoly.mobiledeviceapps.fall23.foogro_1;

import android.database.Cursor;
import android.os.Bundle;
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
            // Assume you have added a "description" column to your Products table.
            String description = cursor.getString(cursor.getColumnIndexOrThrow("description"));





            if (fromStandardSearch) {
                // Fetch and display prices from other stores along with descriptions
                displayPricesFromOtherStores(itemName, dbHelper, description);
            }
        }
        // Ensure the cursor is closed after use
        if(cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
    }

    private void displayPricesFromOtherStores(String itemName, DatabaseHelper dbHelper, String itemDescription) {
        ListView priceListView = findViewById(R.id.priceListView);
        ArrayList<StoreItem> storeItems = new ArrayList<>();
        Cursor storesCursor = null;
        try {
            // Query the database for the same product from different stores
            storesCursor = dbHelper.searchProducts(itemName);
            while (storesCursor != null && storesCursor.moveToNext()) {
                String storeName = storesCursor.getString(storesCursor.getColumnIndexOrThrow("storeName"));
                double storePrice = storesCursor.getDouble(storesCursor.getColumnIndexOrThrow("price"));
                String description = storesCursor.getString(storesCursor.getColumnIndexOrThrow("description"));
                storeItems.add(new StoreItem(storeName, storePrice, description));
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
