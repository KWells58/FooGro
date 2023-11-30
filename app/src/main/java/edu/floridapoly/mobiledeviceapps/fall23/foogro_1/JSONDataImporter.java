package edu.floridapoly.mobiledeviceapps.fall23.foogro_1;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class JSONDataImporter {

    private DatabaseHelper dbHelper;
    private Context context;

    public JSONDataImporter(Context context) {
        this.context = context;
        dbHelper = new DatabaseHelper(context);
    }

    public void importFromGoogleDrive() {
        new ImportTask(dbHelper).execute("https://drive.google.com/uc?export=download&id=12cjOCNo3D1cUv7sJeEly_2cIQt4D1SCe");
    }

    private static class ImportTask extends AsyncTask<String, Void, String> {
        private DatabaseHelper dbHelper;

        ImportTask(DatabaseHelper dbHelper) {
            this.dbHelper = dbHelper;
        }

        @Override
        protected String doInBackground(String... urls) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;
            try {
                URL url = new URL(urls[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setConnectTimeout(15000); // 15 seconds
                connection.setReadTimeout(15000); // 15 seconds

                int responseCode = connection.getResponseCode();
                if (responseCode != HttpURLConnection.HTTP_OK) {
                    Log.e("HTTP Error", "Response Code: " + responseCode);
                    return null;
                }

                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                return stringBuilder.toString();
            } catch (Exception e) {
                Log.e("JSON Import Error", "Error: " + e.getMessage(), e);
                return null;
            } finally {
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (Exception e) {
                        Log.e("Stream Closing Error", "Error closing stream", e);
                    }
                }
                if (connection != null) {
                    connection.disconnect();
                }
            }
        }

        @Override
        protected void onPostExecute(String json) {
            if (json != null) {
                try {
                    // Parse the JSON string into a JSONObject
                    JSONObject jsonObject = new JSONObject(json);

                    // Access the array inside the outer object
                    JSONArray jsonArray = jsonObject.getJSONArray("groceryStoreItems"); // Replace "groceryStoreItems" with the actual key name

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject item = jsonArray.getJSONObject(i);

                        // Extract data from each object in the array
                        int id = item.getInt("id");
                        String name = item.getString("name");
                        String category = item.getString("category");
                        double price = item.getDouble("price");
                        String storeName = item.getString("storeName");
                        int storeID = item.getInt("storeID"); // Assuming storeID is an integer in the JSON

                        // Add product to the database
                        Cursor cursor = dbHelper.getProduct(id);
                        if(cursor != null && cursor.moveToFirst()) {
                            //Product exists you can upade it or skip adding\
                            cursor.close();
                        } else {
                            // Products does not exist, add new product
                            dbHelper.addProduct(name, category, price, storeName, storeID);
                        }
                    }

                } catch (Exception e) {
                    Log.e("JSON Parsing/DB Insertion Error", "Error occurred", e);
                }
            } else {
                Log.e("JSONDataImporter", "JSON String is null");
            }
        }


    }
}
