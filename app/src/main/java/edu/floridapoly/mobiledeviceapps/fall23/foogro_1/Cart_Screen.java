package edu.floridapoly.mobiledeviceapps.fall23.foogro_1;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import org.json.JSONException;
import org.json.JSONObject;

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

        // Retrieve all keys and values from SharedPreferences
        Map<String, ?> allEntries = sharedPreferences.getAll();
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            if (entry.getKey().contains("cartItem_")) {
                // Splitting the key to extract the ID part
                String[] keyParts = entry.getKey().split("_");
                if (keyParts.length > 1 && keyParts[2].equals("storeName")) {
                    // Using the ID to retrieve the price
                    float price = sharedPreferences.getFloat("cartItem_" + keyParts[1] + "_price", 0.0f);
                    cartItems.add(new CartItem(entry.getValue().toString(), price, ""));
                }
            }
        }
        return cartItems;
    }

}
