package edu.floridapoly.mobiledeviceapps.fall23.foogro_1;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AlertDialog;
import android.content.DialogInterface;

public class Item_Search_Screen extends AppCompatActivity {
    private DatabaseHelper dbHelper;
    private ListView searchResults;
    AlertDialog dialog;

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

        addItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
                dialog.show();
            }
        });
    }

    //Dialog box for adding items to database
    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Item_Search_Screen.this);
        builder.setTitle("Enter Item Information");
        View view = getLayoutInflater().inflate(R.layout.add_item_dialog, null);

        EditText itemNameET = view.findViewById(R.id.itemNameET);
        EditText itemCategoryET = view.findViewById(R.id.itemCategoryET);
        EditText itemPriceET = view.findViewById(R.id.itemPriceET);
        EditText itemStoreNameET = view.findViewById(R.id.itemStoreNameET);
        EditText itemStoreIdET = view.findViewById(R.id.itemStoreIdET);

        builder.setPositiveButton("Add Item", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int i) {
                String itemName = itemNameET.getText().toString();
                String itemCat = itemCategoryET.getText().toString();
                Double itemPrice = Double.parseDouble(itemPriceET.getText().toString());
                String itemStore = itemStoreNameET.getText().toString();
                Integer itemStoreID = Integer.parseInt(itemStoreIdET.getText().toString());
                
                if(itemName.equals("") || itemCat.equals("") || itemPrice.equals("") || itemStore.equals("") || itemStoreID.equals("")) {
                    Toast.makeText(Item_Search_Screen.this, "Please fill all fields.", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    SQLiteDatabase db = dbHelper.getWritableDatabase();
                    dbHelper.addProduct(db, itemName, itemCat, itemPrice, itemStore, itemStoreID);
                    dialog.dismiss();
                }

            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialog.cancel();
            }
        });
        builder.setView(view);
        dialog = builder.create();
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
