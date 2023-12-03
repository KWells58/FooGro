package edu.floridapoly.mobiledeviceapps.fall23.foogro_1;

import android.content.Context; // Add this import statement
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class CartItemAdapter extends ArrayAdapter<CartItem> {
    public CartItemAdapter(Context context, List<CartItem> cartItems) {
        super(context, 0, cartItems);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_cart, parent, false);
        }
        // Get the current CartItem
        final CartItem cartItem = getItem(position);

        // Set data to the view in the layout
        if (cartItem != null) {
            TextView itemNameTextView = convertView.findViewById(R.id.itemNameTextView);
            TextView itemPriceTextView = convertView.findViewById(R.id.itemPriceTextView);
            TextView storeNameTextView = convertView.findViewById(R.id.storeNameTextView); // Changed the ID here

            Button removeButton = convertView.findViewById(R.id.removeButton1);

            itemNameTextView.setText(cartItem.getItemName());
            itemPriceTextView.setText(String.format("$%.2f", cartItem.getItemPrice()));
            storeNameTextView.setText(cartItem.getStoreName()); // Changed the method here

            // Handle remove button click here
            removeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    removeItem(position);
                }
            });
        }

        return convertView;
    }

    private void removeItem(int position) {
        try {
            // Get the item to be removed
            CartItem itemToRemove = getItem(position);

            if(itemToRemove != null) {
                Log.d("RemoveItem", "Removing Item: " + itemToRemove.getItemName());
                // Remove the item from SharedPreferences
                SharedPreferences sharedPreferences = getContext().getSharedPreferences("CartPreferences", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                // Assuming itemID is a unique identifier for each item which it is
                int itemId = itemToRemove.getItemId();

                // Log statements for debugging
                Log.d("RemoveItem", "Before removal: " + sharedPreferences.getAll());

                // Remove all entries related to the item from shared preferences
                editor.remove("cartItem_" + itemId + "_itemName");
                editor.remove("cartItem_" + itemId + "_price");
                editor.remove("cartItem_" + itemId + "_storeName");
                editor.remove("cartItem_" + itemId + "_description");


                // Commit the changes
                editor.apply();

                // Log statements for debugging
                Log.d("RemoveItem", "After removal: " + sharedPreferences.getAll());

                // Remove the item from the adapters data set
                remove(itemToRemove);

                // Notify the adapter to update the ui
                notifyDataSetChanged();
                Log.d("RemoveItem", "Item removed successfully");
            } else {
                Log.d("RemoveItem", "Item to remove is null");
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    // Method to get a unique identifier for a cart item
    private int getItemId(CartItem cartItem) {
        return cartItem.getItemId();
    }


    public List<CartItem> getItems() {
        int count = getCount();
        List<CartItem> items = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            items.add(getItem(i));
        }
        return items;
    }

    // Add this method to remove all items from the adapter
    public void removeAll(List<CartItem> items) {
        for (CartItem item : items) {
            remove(item);
        }
        notifyDataSetChanged(); // Notify the adapter that the data set has changed
    }

}
