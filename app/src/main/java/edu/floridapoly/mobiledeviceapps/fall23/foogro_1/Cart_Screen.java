package edu.floridapoly.mobiledeviceapps.fall23.foogro_1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Cart_Screen extends AppCompatActivity {
    private CartItemAdapter cartItemAdapter;

    private StringBuilder recipeIngredients;

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


        // Register a listener for SharedPreferences changes
        SharedPreferences sharedPreferences = getSharedPreferences("CartPreferences", MODE_PRIVATE);
        sharedPreferences.registerOnSharedPreferenceChangeListener(new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                // Handle SharedPreferences changes here
                if (key.startsWith("cartItem_") && key.endsWith("_storeName")) {
                    // Refresh the data in onResume
                    if(cartItemAdapter != null) {
                        // Clear the adapter
                        cartItemAdapter.clear();

                        // Reload items from SharedPreferences
                        cartItemAdapter.addAll(loadCartItems());
                        cartItemAdapter.notifyDataSetChanged();
                    }
                }
            }
        });

        // Create Recipe Button Click Listener
        Button createRecipeButton = findViewById(R.id.createRecipeButton);
        createRecipeButton.setOnClickListener(view -> createRecipe());
    }

    @Override
    protected void onDestroy() {
        // Unregister the listener when the activity is destroyed
        SharedPreferences sharedPreferences = getSharedPreferences("CartPreferences", MODE_PRIVATE);
        sharedPreferences.unregisterOnSharedPreferenceChangeListener(new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                // Handle SharedPreferences changes here if needed
            }
        });

        super.onDestroy();
    }

    private void createRecipe() {
        // Get the list of items in the cart
        List<CartItem> cartItems = cartItemAdapter.getItems();

        // Create a string containing the names of items
        StringBuilder recipeIngredients = new StringBuilder("Recipe Ingredients:\n");
        for (CartItem item : cartItems) {
            recipeIngredients.append("- ").append(item.getItemName()).append("\n");
        }

        // Use the string to create a recipe with AI
        generateRecipeWithAI(recipeIngredients.toString());
    }

    private void generateRecipeWithAI(String recipeIngredients) {
        // Use the string to create a recipe with AI
        OpenAiRequest openAiRequest = new OpenAiRequest(this);
        openAiRequest.execute(recipeIngredients);
    }



    private List<CartItem> loadCartItems() {
        List<CartItem> cartItems = new ArrayList<>();
        SharedPreferences sharedPreferences = getSharedPreferences("CartPreferences", MODE_PRIVATE);

        Map<String, ?> allEntries = sharedPreferences.getAll();
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            if (entry.getKey().startsWith("cartItem_") && entry.getKey().endsWith("_storeName")) {
                int itemId = Integer.parseInt(entry.getKey().split("_")[1]);

                // Check if the item was removed (skip it in that case)
                if (sharedPreferences.contains("cartItem_" + itemId + "_itemName")) {
                    String storeName = (String) entry.getValue();
                    String itemName = sharedPreferences.getString("cartItem_" + itemId + "_itemName", "Unknown Item");

                    // Check if other necesary values are present
                    if(sharedPreferences.contains("cartItem_" + itemId + "_price")) {
                        float price = sharedPreferences.getFloat("cartItem_" + itemId + "_price", 0.0f);
                        cartItems.add(new CartItem(itemName, price, storeName, itemId));
                    }
                }
            }
        }


        // Iterate through existing adapter items and remove the ones not present
        List<CartItem> itemsToRemove = new ArrayList<>();
        for (CartItem existingItem : cartItemAdapter.getItems()) {
            if (!cartItems.contains(existingItem)) {
                itemsToRemove.add(existingItem);
            }
        }
        cartItemAdapter.removeAll(itemsToRemove);

        return cartItems;
    }


}
