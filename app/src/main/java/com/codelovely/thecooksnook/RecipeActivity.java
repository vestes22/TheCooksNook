package com.codelovely.thecooksnook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.codelovely.thecooksnook.adapters.NutrientListAdapter;
import com.codelovely.thecooksnook.adapters.SavedIngredientsListAdapter;
import com.codelovely.thecooksnook.models.Recipe;
import com.codelovely.thecooksnook.viewmodels.RecipeViewModel;

public class RecipeActivity extends AppCompatActivity {
    RecipeViewModel mRecipeViewModel;
    Recipe recipe;
    TextView recipeName;
    TextView recipeDescription;
    TextView recipeInstructions;
    SavedIngredientsListAdapter ingredientsAdapter;
    NutrientListAdapter nutrientAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        mRecipeViewModel = new ViewModelProvider(this).get(RecipeViewModel.class);

        Intent mIntent = getIntent();
        int recipeId = mIntent.getIntExtra("recipeId", 0);
        recipe = mRecipeViewModel.getRecipeById(recipeId);
        recipeName = (TextView) findViewById(R.id.recipe_recipeName);
        recipeDescription = (TextView) findViewById(R.id.recipe_recipeDescription);
        recipeInstructions = (TextView) findViewById(R.id.recipe_recipeInstructions);
        recipeName.setText(recipe.getName());
        recipeDescription.setText(recipe.getDescription());
        recipeInstructions.setText(recipe.getInstructions());

        RecyclerView ingredientsRv = findViewById(R.id.recipe_ingredientsListRecyclerView);
        ingredientsAdapter = new SavedIngredientsListAdapter(new SavedIngredientsListAdapter.IngredientsDiff(), this);
        ingredientsAdapter.submitList(recipe.getIngredients());
        ingredientsRv.setAdapter(ingredientsAdapter);
        ingredientsRv.setLayoutManager(new LinearLayoutManager(this));

        RecyclerView nutrientsRv = findViewById(R.id.recipe_recipeNutritionRecyclerView);
        nutrientAdapter = new NutrientListAdapter(new NutrientListAdapter.NutrientsDiff(), this);
        nutrientAdapter.submitList(recipe.getRecipeNutrientsPerServing());
        nutrientsRv.setAdapter(nutrientAdapter);
        nutrientsRv.setLayoutManager(new LinearLayoutManager(this));


    }

    public void addToMealPlanButtonClicked(View view) {
        // TODO
    }

    public void addToShoppingListButtonClicked(View view) {
        // TODO
    }

    public void deleteRecipeButtonClicked(View view) {
        int recipeId = recipe.getId();
        mRecipeViewModel.delete(recipeId);
        Intent intent = new Intent(this, CookBookHomeActivity.class);
        startActivity(intent);
    }
}
