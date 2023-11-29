package edu.floridapoly.mobiledeviceapps.fall23.foogro_1;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    // Instance of DatabaseHelper
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Initialize DatabaseHelper
        dbHelper = new DatabaseHelper(this);
        JSONDataImporter importer = new JSONDataImporter(this);
        // Import data from Google Drive
        importer.importFromGoogleDrive();


        TextView textView = findViewById(R.id.textView5);
        //get the text
        String text = textView.getText().toString();

        //create a spannable string with the desired styles
        SpannableString spannableString = new SpannableString(text);
        int fooStart = text.indexOf("Foo");
        int fooEnd = fooStart + "Foo".length();

        //set the color for "Foo" to the primary color
        spannableString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.primary_color)),
                fooStart, fooEnd, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        //Make Foo bold
        spannableString.setSpan(new StyleSpan(Typeface.BOLD), fooStart, fooEnd, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        //Apply the spannable string to the text view
        textView.setText(spannableString);


        Button buttonStore1 = findViewById(R.id.storeOneButton);
        Button buttonStore2 = findViewById(R.id.storeTwoButton);
        Button buttonStore3 = findViewById(R.id.storeThreeButton);
        Button buttonStore4 = findViewById(R.id.storeFourButton);

        Button buttonStore5 = findViewById(R.id.storeFiveButton);
        Button buttonStore6 = findViewById(R.id.storeSixButton);
        Button buttonStore7 = findViewById(R.id.storeSevenButton);
        Button buttonStore8 = findViewById(R.id.storeEightButton);

        // Click listeners for the buttons
        buttonStore1.setOnClickListener(view ->openItemSearchScreen("Publix"));
        buttonStore2.setOnClickListener(view ->openItemSearchScreen("Walmart"));
        buttonStore3.setOnClickListener(view ->openItemSearchScreen("Target"));
        buttonStore4.setOnClickListener(view ->openItemSearchScreen("Kroger"));
        buttonStore5.setOnClickListener(view ->openItemSearchScreen("Store 1"));
        buttonStore6.setOnClickListener(view ->openItemSearchScreen("Store 1"));
        buttonStore7.setOnClickListener(view ->openItemSearchScreen("Store 1"));
        buttonStore8.setOnClickListener(view ->openItemSearchScreen("Store 1"));
    }

    private void openItemSearchScreen(String storeName) {
        Intent intent = new Intent(this, Item_Search_Screen.class);
        intent.putExtra("storeName", storeName);
        startActivity(intent);
    }



    private void addSampleProductToDatabase() {
        // Add a product to the database HERE
        dbHelper.addProduct("Banana", "Fruit", 0.49, "Publix", 101);
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void searchProducts(String query) {
        Cursor cursor = dbHelper.searchProducts(query);
        StringBuilder results = new StringBuilder();

        if (cursor != null) {
            while (cursor.moveToNext()) {
                String productName = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                String category = cursor.getString(cursor.getColumnIndexOrThrow("category"));
                double price = cursor.getDouble(cursor.getColumnIndexOrThrow("price"));
                String storeName = cursor.getString(cursor.getColumnIndexOrThrow("storeName"));
                int storeID = cursor.getInt(cursor.getColumnIndexOrThrow("storeID"));

                results.append("Name: ").append(productName)
                        .append(", Category: ").append(category)
                        .append(", Price: ").append(price)
                        .append(", Store Name: ").append(storeName)
                        .append(", Store ID: ").append(storeID)
                        .append("\n\n");
            }
            cursor.close();
        }
    }

}
