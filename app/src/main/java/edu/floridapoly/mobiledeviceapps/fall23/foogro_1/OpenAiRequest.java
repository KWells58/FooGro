package edu.floridapoly.mobiledeviceapps.fall23.foogro_1;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import com.google.gson.Gson;

public class OpenAiRequest extends AsyncTask<String, Void, String> {

    private static final String API_KEY = "API_Key";
    private static final String OPENAI_API_URL = "https://api.openai.com/v1/engines/text-davinci-003/completions";
    private Context context;

    public OpenAiRequest(Context context) {
        this.context = context;
    }

    @Override
    protected String doInBackground(String... params) {
        if (params.length == 0) {
            return null;
        }

        String inputText = params[0];
        try {
            URL url = new URL(OPENAI_API_URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // Set the request method and headers
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Authorization", "Bearer " + API_KEY);
            connection.setRequestProperty("Content-Type", "application/json");

            connection.setDoOutput(true);
            System.out.println("The input text is : " + inputText);

            // Build the request payload using Gson library
            Gson gson = new Gson();
            String prompt = "Create a recipe using the following ingredients: " + inputText + ". Instructions:";
            String requestBody = gson.toJson(Map.of("prompt", prompt, "max_tokens", 200, "temperature", 0.7));


            try (DataOutputStream wr = new DataOutputStream(connection.getOutputStream())) {
                wr.writeBytes(requestBody);
            }
            System.out.println("Request Payload: " + requestBody);

            // Get the response from the API
            int responseCode = connection.getResponseCode();
            Log.d("WebService", "Response code: " + responseCode);

            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;

                while ((line = in.readLine()) != null) {
                    response.append(line);
                }
                in.close();
                return response.toString();
            } else {
                // Handle error response
                System.out.println("Error Response: " + responseCode);
                InputStream errorStream = connection.getErrorStream();
                if (errorStream != null) {
                    BufferedReader errorReader = new BufferedReader(new InputStreamReader(errorStream));
                    String errorLine;
                    StringBuilder errorResponse = new StringBuilder();
                    while ((errorLine = errorReader.readLine()) != null) {
                        errorResponse.append(errorLine);
                    }
                    errorReader.close();
                    System.out.println("Error Details: " + errorResponse.toString());
                }
                return "Error: " + responseCode;
            }
        } catch (Exception e) {
            return "Exception: " + e.getMessage();
        }
    }

    @Override
    protected void onPostExecute(String result) {
        if (result != null) {
            // Start the RecipeDisplayActivity and pass the generated recipe as an extra
            Intent intent = new Intent(context, RecipeDisplayActivity.class);
            intent.putExtra("generatedRecipe", result);
            context.startActivity(intent);
        } else {
            // Handle the case where the result is null (error or no response)
            Toast.makeText(context, "Error generating recipe", Toast.LENGTH_LONG).show();
        }
    }
}
