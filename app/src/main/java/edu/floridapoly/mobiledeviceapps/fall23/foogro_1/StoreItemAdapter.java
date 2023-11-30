package edu.floridapoly.mobiledeviceapps.fall23.foogro_1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class StoreItemAdapter extends ArrayAdapter<StoreItem> {
    public StoreItemAdapter(Context context, ArrayList<StoreItem> storeItems) {
        super(context, 0, storeItems);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        StoreItem storeItem = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_layout, parent, false);
        }

        TextView storeTextView = convertView.findViewById(R.id.storeTextView);
        Button addToCartButton = convertView.findViewById(R.id.addToCartButton);

        if (storeItem != null) {
            storeTextView.setText(storeItem.getStoreName() + ": $" + storeItem.getStorePrice());
        }

        // Set a click listener for the Add to Cart button
        addToCartButton.setOnClickListener(view -> {
            // Handle adding the item to the cart for the selected store
        });

        return convertView;
    }
}
