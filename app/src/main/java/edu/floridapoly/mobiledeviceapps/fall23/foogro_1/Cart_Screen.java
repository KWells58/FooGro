package edu.floridapoly.mobiledeviceapps.fall23.foogro_1;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Cart_Screen extends AppCompatActivity {
    private CartItemAdapter cartItemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_screen);

        // Initialize the ListView
        ListView cartListView = findViewById(R.id.cartListView);

        // Initialize the cartitemadaper
        cartItemAdapter = new CartItemAdapter(this, new ArrayList<>());

        // Load cat items from shared preferences
        List<CartItem> cartItems = loadCartItems();

        // Set the cartitem adapter
        cartListView.setAdapter(cartItemAdapter);

        // Add the loaded items to the adapter
        cartItemAdapter.addAll(cartItems);
    }


    @Override
    protected void onResume() {
        super.onResume();

        // Log the contents of SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("CartPreferences", MODE_PRIVATE);
        Map<String, ?> allEntries = sharedPreferences.getAll();
        Log.d("Cart_Screen", "SharedPreferences contents on resume: " + allEntries);

        // Refresh the data in onResume
        if(cartItemAdapter != null) {
            // Clear the adapter
            cartItemAdapter.clear();

            // Reload items from SharedPreferences after a delay
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    cartItemAdapter.addAll(loadCartItems());
                    cartItemAdapter.notifyDataSetChanged();
                }
            }, 500); // 500 milliseconds delay (adjust as needed)
        }

    }


    private List<CartItem> loadCartItems() {

        // Clear the adapter before adding items
        cartItemAdapter.clear();

        List<CartItem> cartItems = new ArrayList<>();
        SharedPreferences sharedPreferences = getSharedPreferences("CartPreferences", MODE_PRIVATE);

        Map<String, ?> allEntries = sharedPreferences.getAll();
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            if (entry.getKey().startsWith("cartItem_") && entry.getKey().endsWith("_itemName")) {
                String itemId = entry.getKey().split("_")[1];

                // Check if the item was removed (skip it in that case)
                if (sharedPreferences.contains("cartItem_" + itemId + "_itemName")) {
                    String storeName = (String) entry.getValue();
                    String itemName = sharedPreferences.getString("cartItem_" + itemId + "_storeName", "Unknown Item");
                    float price = sharedPreferences.getFloat("cartItem_" + itemId + "_price", 0.0f);
                    cartItems.add(new CartItem(itemName, price, storeName));
                }
            }
        }
        return cartItems;
    }


}
