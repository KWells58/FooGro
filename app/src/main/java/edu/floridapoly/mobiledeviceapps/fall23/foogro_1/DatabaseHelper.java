package edu.floridapoly.mobiledeviceapps.fall23.foogro_1;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "GroceryStore.db";
    private static final int DATABASE_VERSION = 5;
    Context context;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create tables with the updated schema
        db.execSQL(
                "CREATE TABLE groceryStoreItems (" +
                        "id INTEGER PRIMARY KEY," +
                        "name TEXT," +
                        "category TEXT," +
                        "price REAL," +
                        "storeName TEXT," +
                        "storeID INTEGER)"
        );
        // Add more table creation statements as needed
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS groceryStoreItems");
        // Create tables again
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            // Enable foreign key constraints
            db.execSQL("PRAGMA foreign_keys=ON;");
        }
    }
    // Method to get a product
    public Cursor getProduct(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query("groceryStoreItems", new String[]{"id", "name", "category", "price", "storeName", "storeID"}, "id=?", new String[]{String.valueOf(id)}, null, null, null, null);
    }

    public Cursor searchProducts(String query) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query("groceryStoreItems",
                new String[]{"id", "name", "category", "price", "storeName", "storeID"},
                "name LIKE ?",
                new String[]{"%" + query + "%"},
                null, null, null, null);
    }

    //Function searches for given item and displays results in a ListView
    //Untested
    public SimpleCursorAdapter listSearchedItem(String item) {
        SQLiteDatabase db = this.getReadableDatabase();
        String columns[] = {"name", "price", "storeName"};
        Cursor cursor = db.query("groceryStoreItems", columns, "name LIKE ?",
                new String[]{"%" + item + "%"}, null, null, "price", null);
        int listviewIDs[] = new int[] {R.id.item_name, R.id.price, R.id.store};
        SimpleCursorAdapter listAdapter = new SimpleCursorAdapter(
                context, R.layout.item_display, cursor, columns, listviewIDs);
        return listAdapter;
    }

    // Other methods for updating, deleting, and listing products added here
    public void addProductsFromJson(Context context, String jsonString) {
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray jsonArray = jsonObject.getJSONArray("groceryStoreItems");

            SQLiteDatabase db = this.getWritableDatabase();
            db.beginTransaction();

            try {
                // Loop through the array and add products to the database
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject productObject = jsonArray.getJSONObject(i);

                    String name = productObject.getString("name");
                    String category = productObject.getString("category");
                    double price = productObject.getDouble("price");
                    String storeName = productObject.getString("storeName");
                    int storeID = productObject.getInt("storeID");

                    addProduct(db, name, category, price, storeName, storeID);
                }

                db.setTransactionSuccessful();
            } finally {
                db.endTransaction();
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("addProductsFromJson", "Error parsing JSON", e);
        }
    }

    private void addProduct(SQLiteDatabase db, String name, String category, double price, String storeName, int storeID) {
        // Check if the product already exists
        Cursor cursor = db.query("groceryStoreItems", new String[]{"id"}, "name = ?", new String[]{name}, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            // Product exists, you can update it or skip adding
            cursor.close();
        } else {
            // Product does not exist, add new product
            ContentValues values = new ContentValues();
            values.put("name", name);
            values.put("category", category);
            values.put("price", price);
            values.put("storeName", storeName);
            values.put("storeID", storeID);

            // Inserting Row
            db.insert("groceryStoreItems", null, values);
        }
    }

}
