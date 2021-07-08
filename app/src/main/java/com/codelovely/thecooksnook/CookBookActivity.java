package com.codelovely.thecooksnook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.codelovely.thecooksnook.adapters.RecipeAdapter;
import com.codelovely.thecooksnook.models.RecipeModel;
import com.codelovely.thecooksnook.viewmodels.CookBookViewModel;

import java.util.List;

public class
CookBookActivity extends AppCompatActivity implements RecipeAdapter.RecipeListener {
    CookBookViewModel mCookBookViewModel;
    RecipeAdapter recipeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cook_book);

        mCookBookViewModel = new ViewModelProvider(this).get(CookBookViewModel.class);

        // Gets the category passed from the previous intent.
        Bundle extras = getIntent().getExtras();
        assert extras != null;
        String category = extras.getString("category");
        // TODO - Write functionality for "View All" recipe button.
        // If "category == "ALL" then write another function in view model for all recipes.

        List<RecipeModel> recipes =  mCookBookViewModel.getRecipesByCategory(category);

        RecyclerView recipeRv = findViewById(R.id.cookBook_recipeRecyclerview);
        recipeAdapter = new RecipeAdapter(new RecipeAdapter.RecipeDiff(), this);
        recipeAdapter.submitList(recipes);
        recipeRv.setAdapter(recipeAdapter);
        recipeRv.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onRecipeClicked(int position) {
        List<RecipeModel> currentList =  recipeAdapter.getCurrentList();
        RecipeModel recipe = currentList.get(position);
        Intent intent = new Intent(this, RecipeActivity.class);
        intent.putExtra("recipeId", recipe.getId());
        startActivity(intent);
    }


}
