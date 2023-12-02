package edu.floridapoly.mobiledeviceapps.fall23.foogro_1;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import java.util.ArrayList;

public class StoreItemAdapter extends ArrayAdapter<StoreItem> {

    // ViewHolder static inner class for better performance in ListView.
    static class ViewHolder {
        TextView storeItemNameTextView;
        TextView storeItemPriceTextView;
        TextView storeItemDescriptionTextView;
        Button storeItemAddToCartButton;
    }

    public StoreItemAdapter(Context context, ArrayList<StoreItem> storeItems) {
        super(context, 0, storeItems);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_store, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.storeItemNameTextView = convertView.findViewById(R.id.storeItemNameTextView);
            viewHolder.storeItemPriceTextView = convertView.findViewById(R.id.storeItemPriceTextView);
            viewHolder.storeItemDescriptionTextView = convertView.findViewById(R.id.storeItemDescriptionTextView);
            viewHolder.storeItemAddToCartButton = convertView.findViewById(R.id.storeItemAddToCartButton);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        StoreItem storeItem = getItem(position);
        if (storeItem != null) {
            viewHolder.storeItemNameTextView.setText(storeItem.getStoreName());
            viewHolder.storeItemPriceTextView.setText(String.format("$%.2f", storeItem.getStorePrice()));
            viewHolder.storeItemDescriptionTextView.setText(storeItem.getDescription());

            viewHolder.storeItemAddToCartButton.setOnClickListener(view -> {
                addToCart(storeItem.getId(), storeItem.getStoreName(), storeItem.getStorePrice());
            });
        }

        return convertView;
    }

    private void addToCart(int itemId, String storeName, double price) {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("CartPreferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // Construct a unique key for this item. Assuming itemId is unique for each item.
        String itemKey = "cartItem_" + itemId;

        // Save item details. Consider using JSON to save more complex data.
        editor.putString(itemKey + "_storeName", storeName);
        editor.putFloat(itemKey + "_price", (float) price);

        // Commit changes to SharedPreferences
        editor.apply();
    }
}
