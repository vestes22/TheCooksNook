package com.codelovely.thecooksnook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class HomeScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
    }

    public void cookBookButtonClicked(View view) {
        Intent intent = new Intent(this, CookBookHomeActivity.class);
        startActivity(intent);
    }


    public void recommendedRecipeClicked(View view) {
        Intent intent = new Intent(this, RecipeActivity.class);
        startActivity(intent);
    }

    public void shoppingListClicked(View view) {
        // TODO
    }

    public void mealPlannerClicked(View view) {
        Intent intent = new Intent(this, MealPlannerActivity.class);
        startActivity(intent);
    }
}
