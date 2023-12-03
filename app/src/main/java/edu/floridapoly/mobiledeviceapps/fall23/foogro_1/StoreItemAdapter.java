package edu.floridapoly.mobiledeviceapps.fall23.foogro_1;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import java.util.ArrayList;

public class StoreItemAdapter extends ArrayAdapter<StoreItem> {
    private boolean fromStandardSearch;

    static class ViewHolder {
        TextView storeItemNameTextView;
        TextView storeItemPriceTextView;
        TextView storeItemDescriptionTextView;
        Button storeItemAddToCartButton;
    }

    public StoreItemAdapter(Context context, ArrayList<StoreItem> storeItems) {
        super(context, 0, storeItems);
        this.fromStandardSearch = fromStandardSearch;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
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
                addToCart(storeItem); // Pass the storeItem object directly

                if (getContext() instanceof ItemDetailsActivity) {
                    ((ItemDetailsActivity) getContext()).openCartScreen();
                }
            });
        }

        return convertView;
    }

    private void addToCart(StoreItem storeItem) {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("CartPreferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // Use the store name associated with the clicked item
        String storeName = storeItem.getStoreName() != null ? storeItem.getStoreName() : "DefaultStore";
        Log.d("ItemDetailsActivity", "Adding to cart - Item: " + storeItem.getItemName() + ", Store: " + storeName);

        editor.putString("cartItem_" + storeItem.getId() + "_itemName", storeItem.getItemName());
        editor.putString("cartItem_" + storeItem.getId() + "_storeName", storeName);
        editor.putFloat("cartItem_" + storeItem.getId() + "_price", (float) storeItem.getStorePrice());
        editor.putString("cartItem_" + storeItem.getId() + "_description", storeItem.getDescription()); // Storing description

        editor.apply();
    }





}
