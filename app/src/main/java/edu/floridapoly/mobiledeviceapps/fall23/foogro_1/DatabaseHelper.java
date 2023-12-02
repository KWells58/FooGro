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
    private static final int DATABASE_VERSION = 19;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "CREATE TABLE IF NOT EXISTS Products (" +
                        "id INTEGER PRIMARY KEY," +
                        "name TEXT," +
                        "category TEXT," +
                        "price REAL," +
                        "storeName TEXT," +
                        "storeID INTEGER," +
                        "description TEXT)"
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
    public void addProduct(int id, String name, String category, double price, String storeName, int storeID, String description) {
        SQLiteDatabase db = this.getWritableDatabase();


        ContentValues values = new ContentValues();

        values.put("id", id);
        values.put("name", name);
        values.put("category", category);
        values.put("price", price);
        values.put("storeName", storeName);
        values.put("storeID", storeID);
        values.put("description", description);

        // Inserting Row
        db.insert("Products", null, values);
        Log.d("DatabaseHelper", "Adding product: " + name);
        db.close(); // Closing database connection
}

    // Method to get a product
    public Cursor getProduct(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query("Products", new String[]{"id", "name", "category", "price", "storeName", "storeID","description"}, "id=?", new String[]{String.valueOf(id)}, null, null, null, null);
    }

    public Cursor searchProducts(String query) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {"id AS _id", "name", "category", "price", "storeName", "storeID","description"};
        String selection = "name LIKE ?";
        String[] selectionArgs = new String[]{"%" + query + "%"};
        String orderBy = "price ASC, name ASC";

        Cursor cursor = db.query("Products", columns, selection, selectionArgs, null, null, orderBy);

        // Log the results for debugging
        if (cursor != null) {
            int count = cursor.getCount();
            Log.d("DatabaseHelper", "searchProducts - Query: " + query + ", Result Count: " + count);
        }

        return cursor;
    }



    // Other methods for updating, deleting, and listing products added here
    public Cursor searchSingleProduct(String query, String storeName) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {"id AS _id", "name", "category", "price", "storeName", "storeID","description"};
        String selection = "name LIKE ?";
        String[] selectionArgs = new String[]{"%" + query + "%"};
        String orderBy = "price ASC, name ASC";

        Cursor cursor;

        if (storeName != null) {
            // If storeName is provided, filter the results
            selection += " AND storeName = ?";
            selectionArgs = new String[]{"%" + query + "%", storeName};
        }

        cursor = db.query("Products", columns, selection, selectionArgs, null, null, orderBy, "1");

        // Log the results for debugging
        if (cursor != null) {
            int count = cursor.getCount();
            Log.d("DatabaseHelper", "searchSingleProduct - Query: " + query + ", Result Count: " + count);
        }

        return cursor;
    }
    public void addToCart(int productId, String productName, double productPrice, int quantity) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("productId", productId);
        values.put("productName", productName);
        values.put("price", productPrice);
        values.put("quantity", quantity);

        // Inserting Row into Cart table (you need to create this table in your database)
        db.insert("Cart", null, values);
        Log.d("DatabaseHelper", "Added to cart: " + productName);
        db.close(); // Closing database connection
    }



}
