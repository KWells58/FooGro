package edu.floridapoly.mobiledeviceapps.fall23.foogro_1;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class Item_Search_Screen extends AppCompatActivity {
    private DatabaseHelper dbHelper;
    private ListView searchResults;
    private Context context; // To hold the context reference
    private String storeName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_search_screen);
        context = this; // Store the context
        initializeViews();

        //Retrieve the store name from the intent extras
        storeName = getIntent().getStringExtra("storeName");
        Log.d("ItemSearchScreen", "Store selected: " + storeName);

    }

    private void initializeViews() {
        dbHelper = new DatabaseHelper(Item_Search_Screen.this);
        searchResults = findViewById(R.id.itemSearchResults);

        Button searchButton = findViewById(R.id.searchButton);
        Button backButton = findViewById(R.id.backButton);
        EditText searchEditText = findViewById(R.id.searchEditText);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                performSearch(storeName, searchEditText.getText().toString());
                }
            });
        backButton.setOnClickListener(view -> finish());
    }

    private void performSearch(String storeName, String query) {
        if (!query.isEmpty()) {
            showToast("Searching for: " + query + " in " + storeName);
            // Run database operation in a background thread to avoid blocking the UI
            new Thread(() -> {
                Cursor cursor = dbHelper.searchSingleProduct(query, storeName);

                if (cursor != null) {
                    // Continue with the rest of the adapter setup
                    String[] fromColumns = new String[]{"name"}; // column name to display
                    int[] toViews = new int[]{android.R.id.text1}; // The TextView in simple_list_item_1.xml

                    SimpleCursorAdapter adapter = new SimpleCursorAdapter(
                            context,
                            android.R.layout.simple_list_item_1, // Layout for individual rows
                            cursor,
                            fromColumns,
                            toViews,
                            0);

                    searchResults.setOnItemClickListener((parent, view, position, id) -> {
                        Cursor clickedItemCursor = (Cursor) parent.getItemAtPosition(position);
                        if (clickedItemCursor != null && clickedItemCursor.moveToPosition(position)) {
                            String itemId = clickedItemCursor.getString(clickedItemCursor.getColumnIndexOrThrow("_id"));
                            Intent intent = new Intent(context, ItemDetailsActivity.class);
                            intent.putExtra("itemId", itemId);
                            intent.putExtra("storeName", storeName);
                            intent.putExtra("fromStandardSearch", true);
                            startActivity(intent);
                        }
                    });

                    runOnUiThread(() -> searchResults.setAdapter(adapter));
                } else {
                    runOnUiThread(() -> showToast("No results found"));
                }
            }).start();
        } else {
            showToast("Please enter a search query.");
        }
    }


    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
