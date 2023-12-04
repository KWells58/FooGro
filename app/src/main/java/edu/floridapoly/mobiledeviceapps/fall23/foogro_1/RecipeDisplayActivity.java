package edu.floridapoly.mobiledeviceapps.fall23.foogro_1;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

// RecipeDisplayActivity.java
public class RecipeDisplayActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_display);

        // Retrieve generated recipe from intent extras
        String generatedRecipe = getIntent().getStringExtra("generatedRecipe");

        // Set the text in the TextView
        TextView recipeTextView = findViewById(R.id.recipeTextView);
        recipeTextView.setText(generatedRecipe);
    }
}


