package edu.floridapoly.mobiledeviceapps.fall23.foogro_1;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_search_screen);

        context = this; // Store the context
        initializeViews();
    }

    private void initializeViews() {
        dbHelper = new DatabaseHelper(Item_Search_Screen.this);
        searchResults = findViewById(R.id.itemSearchResults);

        Button searchButton = findViewById(R.id.searchButton);
        Button backButton = findViewById(R.id.backButton);
        EditText searchEditText = findViewById(R.id.searchEditText);

        searchButton.setOnClickListener(view -> performSearch(searchEditText.getText().toString()));
        backButton.setOnClickListener(view -> finish());
    }

    private void performSearch(String query) {
        if (!query.isEmpty()) {
            showToast("Searching for: " + query);

            // Run database operation in a background thread to avoid blocking the UI
            new Thread(() -> {
                try {
                    Cursor cursor = dbHelper.searchProducts(query);
                    if (cursor != null) {
                        // Assuming you have a layout file 'list_item_layout.xml' with a TextView 'text1'
                        // to display product names. Adjust the layout and TextView IDs as per your actual layout.
                        String[] fromColumns = new String[]{"name"}; // column name to display
                        int[] toViews = new int[]{android.R.id.text1}; // The TextView in list_item_layout.xml

                        SimpleCursorAdapter adapter = new SimpleCursorAdapter(
                                context,
                                android.R.layout.simple_list_item_1, // Layout for individual rows
                                cursor,
                                fromColumns,
                                toViews,
                                0);

                        runOnUiThread(() -> searchResults.setAdapter(adapter));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    runOnUiThread(() -> showToast("Error: " + e.getMessage()));
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
