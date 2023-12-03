package edu.floridapoly.mobiledeviceapps.fall23.foogro_1;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Cart_Screen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_screen);

        // Initialize the ListView
        ListView cartListView = findViewById(R.id.cartListView);

        // Load cart items from SharedPreferences
        List<CartItem> cartItems = loadCartItems();

        // Create and set the CartItemAdapter
        CartItemAdapter cartItemAdapter = new CartItemAdapter(this, cartItems);
        cartListView.setAdapter(cartItemAdapter);
    }

    private List<CartItem> loadCartItems() {
        List<CartItem> cartItems = new ArrayList<>();
        SharedPreferences sharedPreferences = getSharedPreferences("CartPreferences", MODE_PRIVATE);

        Map<String, ?> allEntries = sharedPreferences.getAll();
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            if (entry.getKey().contains("cartItem_") && entry.getKey().endsWith("_storeName")) {
                String storeName = (String) entry.getValue();
                // Extract the item ID from the key
                String itemId = entry.getKey().split("_")[1];
                // Use the item ID to retrieve the price
                float price = sharedPreferences.getFloat("cartItem_" + itemId + "_price", 0.0f);
                cartItems.add(new CartItem(storeName, price, "")); // The location is set as an empty string for now
            }
        }
        return cartItems;
    }
}
