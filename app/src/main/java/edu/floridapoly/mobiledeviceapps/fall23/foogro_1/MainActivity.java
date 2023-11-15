package edu.floridapoly.mobiledeviceapps.fall23.foogro_1;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {

    // Instance of DatabaseHelper
    //test
    //apples
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize DatabaseHelper
        dbHelper = new DatabaseHelper(this);

        Button profileHomeButton = findViewById(R.id.profile_home_button);
        Button groceryButton = findViewById(R.id.grocery_button);

        profileHomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("Profile/Home Button Clicked");
                // Start the Profile Screen
                Intent intent = new Intent(MainActivity.this, Profile_Screen.class);
                startActivity(intent);
            }
        });

        groceryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("Grocery Button Clicked");
                // Start the Grocery Screen
                Intent intent = new Intent(MainActivity.this, Grocery_Screen.class);
                startActivity(intent);
            }
        });
        //Download and parse JSON in the background
        new DownloadJsonTask().execute("https://drive.google.com/uc?export=download&id=12cjOCNo3D1cUv7sJeEly_2cIQt4D1SCe");
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private class DownloadJsonTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            try {
                return downloadJson(urls[0]);
            } catch(IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String result) {
            if (result != null) {
                try {
                    // Log the received JSON for debugging
                    Log.d("Received JSON", result);

                    // Parse JSON and add products to the database
                    dbHelper.addProductsFromJson(MainActivity.this, result);
                } catch (Exception e) {
                    e.printStackTrace();
                    showToast("An unexpected error occurred in onPostExecute");
                    throw new RuntimeException("Unexpected error", e);
                }
            } else {
                showToast("Failed to download JSON");
            }
        }

        private String downloadJson(String urlString) throws IOException {
            HttpURLConnection urlConnection = null;
            try {
                URL url = new URL(urlString);
                urlConnection = (HttpURLConnection) url.openConnection();
                InputStream in = urlConnection.getInputStream();

                Scanner scanner = new Scanner(in);
                scanner.useDelimiter("\\A");
                return scanner.hasNext() ? scanner.next() : null;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
            }
        }
    }
}
