package edu.floridapoly.mobiledeviceapps.fall23.foogro_1;

import android.os.Bundle;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;

public class Cart_Screen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_screen);

        // Initialize the ListView
        ListView cartListView = findViewById(R.id.cartListView);

        // Create a list of CartItem objects (replace this with your actual data)
        List<CartItem> cartItems = new ArrayList<>();
        cartItems.add(new CartItem("Item 1", 10.00, "Supermarket 1"));
        cartItems.add(new CartItem("Item 2", 15.50, "Store A"));
        // Add more cart items as needed

        // Create and set the CartItemAdapter
        CartItemAdapter cartItemAdapter = new CartItemAdapter(this, cartItems);
        cartListView.setAdapter(cartItemAdapter);
    }
}


