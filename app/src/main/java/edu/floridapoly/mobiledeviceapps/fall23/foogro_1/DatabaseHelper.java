package edu.floridapoly.mobiledeviceapps.fall23.foogro_1;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "GroceryStore.db";
    private static final int DATABASE_VERSION = 12;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create tables with the updated schema
        db.execSQL(
                "CREATE TABLE Products (" +
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
        db.execSQL("DROP TABLE IF EXISTS Products");
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

    // Method to add a new product
    public void addProduct(String name, String category, double price, String storeName, int storeID) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Check if the product already exists
        Cursor cursor = db.query("Products", new String[]{"id"}, "name = ?", new String[]{name}, null, null, null);
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
            db.insert("Products", null, values);
            Log.d("DatabaseHelper", "Adding product: " + name);
        }
        db.close(); // Closing database connection
    }

    // Method to get a product
    public Cursor getProduct(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query("Products", new String[]{"id", "name", "category", "price", "storeName", "storeID"}, "id=?", new String[]{String.valueOf(id)}, null, null, null, null);
    }

    public Cursor searchProducts(String query) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query("Products",
                new String[]{"id AS _id", "name", "category", "price", "storeName", "storeID"}, // Add alias for 'id'
                "name LIKE ?",
                new String[]{"%" + query + "%"},
                null, null, null, null);
    }


    // Other methods for updating, deleting, and listing products added here

    // Method to get prices from other stores for a given product
    public Cursor getStorePrices(int productId) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query("Products",
                new String[]{"storeName", "price"},
                "id=? AND storeID<>?",
                new String[]{String.valueOf(productId), "0"}, // Assuming 0 is a default/invalid store ID
                null, null, null, null);
    }

    public Cursor searchProductsByStore(String query, String storeName) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {"id AS _id", "name"};
        String selection = "name LIKE ? AND storeName = ?";
        String[] selectionArgs = new String[]{"%" + query + "%", storeName};
        String orderBy = "name ASC";

        return db.query("products", columns, selection, selectionArgs, null, null, orderBy);
    }

}
