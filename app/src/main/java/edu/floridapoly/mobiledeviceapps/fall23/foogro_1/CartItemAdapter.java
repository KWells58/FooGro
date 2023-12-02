package edu.floridapoly.mobiledeviceapps.fall23.foogro_1;

import android.content.Context; // Add this import statement
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
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

        CartItem cartItem = getItem(position);
        if (cartItem != null) {
            TextView itemNameTextView = convertView.findViewById(R.id.itemNameTextView);
            TextView itemPriceTextView = convertView.findViewById(R.id.itemPriceTextView);
            TextView itemLocationTextView = convertView.findViewById(R.id.itemLocationTextView);

            itemNameTextView.setText(cartItem.getItemName());
            itemPriceTextView.setText(String.format("$%.2f", cartItem.getItemPrice()));
            itemLocationTextView.setText("Location: " + cartItem.getItemLocation());
        }

        return convertView;
    }
}
