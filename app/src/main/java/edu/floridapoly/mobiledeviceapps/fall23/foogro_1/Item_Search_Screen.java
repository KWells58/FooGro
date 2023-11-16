package edu.floridapoly.mobiledeviceapps.fall23.foogro_1;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_search_screen);

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

            // Assuming listSearchedItem() method is correctly implemented in DatabaseHelper
            // Run database operation in a background thread to avoid blocking the UI
            new Thread(() -> {
                final SimpleCursorAdapter adapter = dbHelper.listSearchedItem(query);
                runOnUiThread(() -> searchResults.setAdapter(adapter));
            }).start();
        } else {
            showToast("Please enter a search query.");
        }
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
