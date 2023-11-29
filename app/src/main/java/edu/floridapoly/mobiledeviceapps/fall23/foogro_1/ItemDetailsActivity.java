package edu.floridapoly.mobiledeviceapps.fall23.foogro_1;

import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

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

            if(fromStandardSearch) {
                // Fetch and display prices from other stores
                displayPricesFromOtherStores(itemName, dbHelper);
                cursor.close();
            }
        }
    }

    private void displayPricesFromOtherStores(String itemName, DatabaseHelper dbHelper) {
        LinearLayout pricesLayout = findViewById(R.id.pricesLayout);

        // Query the database for the same product from different stores
        Cursor storesCursor = dbHelper.searchProducts(itemName);
        if (storesCursor != null && storesCursor.moveToFirst()) {
            do {
                String storeName = storesCursor.getString(storesCursor.getColumnIndexOrThrow("storeName"));
                double storePrice = storesCursor.getDouble(storesCursor.getColumnIndexOrThrow("price"));

                // Create a TextView to display store name and price
                TextView storeTextView = new TextView(this);
                storeTextView.setText(storeName + ": $" + storePrice);

                // Create an Add to Cart button for each store
                Button addToCartButton = new Button(this);
                addToCartButton.setText("Add to Cart");
                addToCartButton.setOnClickListener(view -> {
                    // Add logic to add the item to the cart for this store
                    // You may want to store the selected store and item details in your cart
                });

                // Add the TextView and Button to the layout
                pricesLayout.addView(storeTextView);
                pricesLayout.addView(addToCartButton);

            } while (storesCursor.moveToNext());

            storesCursor.close();
        }
    }
}
