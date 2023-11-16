package edu.floridapoly.mobiledeviceapps.fall23.foogro_1;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.ListView;

public class Item_Search_Screen extends AppCompatActivity {
    private DatabaseHelper dbHelper;
    public ListView searchResults;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_search_screen);

        dbHelper = new DatabaseHelper(Item_Search_Screen.this);
        searchResults = findViewById(R.id.itemSearchResults);

        Button searchButton = findViewById(R.id.searchButton);
        Button backButton = findViewById(R.id.backButton);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText searchEditText = findViewById(R.id.searchEditText);
                String query = searchEditText.getText().toString();

                if (!query.isEmpty()) {
                    // Perform the search operation here (e.g., display search results).
                    showToast("Searching for: " + query);

                    SimpleCursorAdapter simpleCursorAdapter = dbHelper.listSearchedItem(item);
                    searchResults.setAdapter(simpleCursorAdapter);
                    Button addToCart = searchResults.findViewById(R.id.button_addToCart);
                    addToCart.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //Insert code for adding item to cart screen
                        }
                    });
                } else {
                    showToast("Please enter a search query.");
                }
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish(); // Close the SearchActivity and go back to the previous activity.
            }
        });
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
