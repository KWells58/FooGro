//package edu.floridapoly.mobiledeviceapps.fall23.foogro_1;
//
//import android.os.AsyncTask;
//import android.util.Log;
//import okhttp3.*;
//
//import java.io.IOException;
//
//public class RecipeGenerator {
//
//    private static final String OPENAI_API_URL = "https://api.openai.com/v1/engines/davinci-codex/completions";
//    private static final String API_KEY = "YOUR_API_KEY";
//
//    public void generateRecipeWithAI(String ingredients) {
//        new GenerateRecipeTask().execute(ingredients);
//    }
//
//    private class GenerateRecipeTask extends AsyncTask<String, Void, String> {
//
//        @Override
//        protected String doInBackground(String... params) {
//            if (params.length == 0) {
//                return null;
//            }
//
//            String inputIngredients = params[0];
//
//            OkHttpClient client = new OkHttpClient();
//
//            RequestBody requestBody = new FormBody.Builder()
//                    .add("prompt", "Create a recipe using the following ingredients: " + inputIngredients)
//                    .build();
//
//            Request request = new Request.Builder()
//                    .url(OPENAI_API_URL)
//                    .post(requestBody)
//                    .addHeader("Authorization", "Bearer " + API_KEY)
//                    .build();
//
//            try {
//                Response response = client.newCall(request).execute();
//                if (response.isSuccessful()) {
//                    return response.body().string();
//                } else {
//                    Log.e("RecipeGenerator", "Error: " + response.code() + " - " + response.message());
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(String result) {
//            if (result != null) {
//                // Handle the generated recipe here (e.g., display it in your app)
//                Log.d("RecipeGenerator", "Generated Recipe: " + result);
//            } else {
//                Log.e("RecipeGenerator", "Failed to generate recipe");
//            }
//        }
//    }
//}
//
