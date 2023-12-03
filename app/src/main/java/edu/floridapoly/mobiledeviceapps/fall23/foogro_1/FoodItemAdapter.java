package edu.floridapoly.mobiledeviceapps.fall23.foogro_1;

import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class FoodItemAdapter extends RecyclerView.Adapter<FoodItemAdapter.ViewHolder> {

    private List<String> mFoodItems;
    private SparseBooleanArray itemStateArray = new SparseBooleanArray();

    public FoodItemAdapter(List<String> foodItems) {
        this.mFoodItems = foodItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_food, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return mFoodItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CheckBox checkBox;
        TextView textViewFoodItem;

        ViewHolder(View itemView) {
            super(itemView);
            checkBox = itemView.findViewById(R.id.checkbox);
            textViewFoodItem = itemView.findViewById(R.id.textViewFoodItem);
            itemView.setOnClickListener(v -> {
                int adapterPosition = getAdapterPosition();
                if (!itemStateArray.get(adapterPosition, false)) {
                    checkBox.setChecked(true);
                    itemStateArray.put(adapterPosition, true);
                } else {
                    checkBox.setChecked(false);
                    itemStateArray.put(adapterPosition, false);
                }
            });
            checkBox.setOnClickListener(v -> {
                int adapterPosition = getAdapterPosition();
                if (!itemStateArray.get(adapterPosition, false)) {
                    checkBox.setChecked(true);
                    itemStateArray.put(adapterPosition, true);
                } else {
                    checkBox.setChecked(false);
                    itemStateArray.put(adapterPosition, false);
                }
            });
        }

        void bind(int position) {
            // Set the text from the list to the TextView
            textViewFoodItem.setText(mFoodItems.get(position));
            // Use the state array to determine if this item is checked
            if (!itemStateArray.get(position, false)) {
                checkBox.setChecked(false);
            } else {
                checkBox.setChecked(true);
            }
        }
    }
}
