package com.codelovely.thecooksnook;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.codelovely.thecooksnook.adapters.EditableIngredientsListAdapter;
import com.codelovely.thecooksnook.adapters.SearchResultsAdapter;
import com.codelovely.thecooksnook.models.IngredientModel;
import com.codelovely.thecooksnook.models.RecipeModel;
import com.codelovely.thecooksnook.models.restmodels.SearchResultFood;
import com.codelovely.thecooksnook.viewmodels.AddRecipeViewModel;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

public class AddRecipeActivity extends AppCompatActivity implements SearchResultsAdapter.SearchResultsListener {
    EditText searchIngredientsText;
    EditText recipeNameText;
    EditText recipeDescriptionText;
    EditText recipeServingsText;
    EditText recipeInstructionsText;
    Observer<List<SearchResultFood>> searchResultsObserver;
    SearchResultsAdapter searchAdapter;
    EditableIngredientsListAdapter ingredientsAdapter;
    AddRecipeViewModel mAddRecipeViewModel;
    ProgressBar progressBar;

    ChipGroup chipGroup;
    Chip breakfastChip;
    Chip lunchChip;
    Chip dinnerChip;
    Chip appetizersChip;
    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe);

        // Initializing the Views
        searchIngredientsText = (TextInputEditText)findViewById(R.id.addRecipe_searchIngredientsEditText);
        recipeNameText = (TextInputEditText)findViewById(R.id.addRecipe_recipeNameEditText);
        recipeDescriptionText = (TextInputEditText)findViewById(R.id.addRecipe_recipeDescriptionEditText);
        recipeServingsText = (TextInputEditText)findViewById(R.id.addRecipe_numServingsEditText);
        recipeInstructionsText = (TextInputEditText)findViewById(R.id.addRecipe_recipeInstructionsEditText);
        progressBar = findViewById(R.id.addRecipe_indeterminateProgressBar);
        chipGroup = findViewById(R.id.addRecipe_chipGroup);
        breakfastChip = findViewById(R.id.addRecipe_breakfastChip);
        lunchChip = findViewById(R.id.addRecipe_lunchChip);
        dinnerChip = findViewById(R.id.addRecipe_dinnerChip);
        appetizersChip = findViewById(R.id.addRecipe_appetizerChip);
        mAddRecipeViewModel = new ViewModelProvider(this).get(AddRecipeViewModel.class);

        progressBar.setVisibility(View.GONE);

        // Setup code for our SearchResults RecyclerViews:
        RecyclerView searchResultsRv = findViewById(R.id.addRecipe_searchResultsRecyclerView);
        searchAdapter = new SearchResultsAdapter(this);
        searchResultsRv.setAdapter(searchAdapter);
        searchResultsRv.setLayoutManager(new LinearLayoutManager(this));
        searchResultsObserver = new Observer<List<SearchResultFood>>() {
            @Override
            public void onChanged(List<SearchResultFood> searchResults) {
                if (searchResults != null) {
                    searchAdapter.setResults(searchResults);
                }
            }
        };
        mAddRecipeViewModel.getSearchResultsLiveData().observe(this, searchResultsObserver);


        // Setup code for our Ingredients RecyclerView:
        RecyclerView ingredientsListRv = findViewById(R.id.addRecipe_recipeIngredientsRecyclerView);
        ingredientsAdapter = new EditableIngredientsListAdapter(new EditableIngredientsListAdapter.IngredientsDiff());
        ingredientsListRv.setAdapter(ingredientsAdapter);
        ingredientsListRv.setLayoutManager(new LinearLayoutManager(this));
        final Observer<List<IngredientModel>> ingredientsListObserver = new Observer<List<IngredientModel>>() {
            @Override
            public void onChanged(@Nullable final List<IngredientModel> ingredientsList) {
                ingredientsAdapter.submitList(null);
                ingredientsAdapter.updateIngredients(ingredientsList);
                ingredientsAdapter.submitList(ingredientsList);
                progressBar.setVisibility(View.GONE);
            }
        };

       mAddRecipeViewModel.getSelectedIngredientsLiveData().observe(this, ingredientsListObserver);


        // Setup code for our chips and their listeners. The chips to allow the user to select a category for their recipe (i.e. Breakfast, or Dinner).
        breakfastChip.setCheckable(true);
        lunchChip.setCheckable(true);
        dinnerChip.setCheckable(true);
        appetizersChip.setCheckable(true);

        breakfastChip.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton view, boolean isChecked) {
                if (!isChecked) {
                    breakfastChip.setChipBackgroundColor(ColorStateList.valueOf(ContextCompat.getColor(context, R.color.colorPrimaryDark)));
                }
                else {
                    breakfastChip.setChipBackgroundColor(ColorStateList.valueOf(ContextCompat.getColor(context, R.color.colorAccent)));
                }
            }
        });

        lunchChip.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton view, boolean isChecked) {
                if (!isChecked) {
                    lunchChip.setChipBackgroundColor(ColorStateList.valueOf(ContextCompat.getColor(context, R.color.colorPrimaryDark)));
                }
                else {
                    lunchChip.setChipBackgroundColor(ColorStateList.valueOf(ContextCompat.getColor(context, R.color.colorAccent)));
                }
            }
        });

        dinnerChip.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton view, boolean isChecked) {
                if (!isChecked) {
                    dinnerChip.setChipBackgroundColor(ColorStateList.valueOf(ContextCompat.getColor(context, R.color.colorPrimaryDark)));
                }
                else {
                    dinnerChip.setChipBackgroundColor(ColorStateList.valueOf(ContextCompat.getColor(context, R.color.colorAccent)));
                }
            }
        });

        appetizersChip.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton view, boolean isChecked) {
                if (!isChecked) {
                    appetizersChip.setChipBackgroundColor(ColorStateList.valueOf(ContextCompat.getColor(context, R.color.colorPrimaryDark)));
                }
                else {
                    appetizersChip.setChipBackgroundColor(ColorStateList.valueOf(ContextCompat.getColor(context, R.color.colorAccent)));
                }
            }
        });
    }


    public void searchButtonClicked(View view) {
        String query = searchIngredientsText.getText().toString();
        mAddRecipeViewModel.searchFoodDataCentralByName(query);
    }


    @Override
    public void onSearchResultClicked(int position) {

        // Gets the ingredient that was clicked.
        List<SearchResultFood> currentList = searchAdapter.getResults();
        SearchResultFood ingredient = currentList.get(position);
        String dataType = ingredient.getDataType();

        // Compares the ID of the selected ingredient with the ingredients we already have added to our list to ensure we don't add the same ingredient more than once.
        List<IngredientModel> ingredientsList = ingredientsAdapter.getCurrentList();
        int flag = -1;
        for (IngredientModel food : ingredientsList) {
            if (food.getFdcId() == ingredient.getFdcId())
            {
                flag++;
            }
        }
        if (flag >= 0) {
            System.out.println("Ingredient already added.");
        }
        else {
            progressBar.setVisibility(View.VISIBLE);

            if (dataType.equals("Branded")) {
                System.out.println("Calling getBrandedFoodItemById...");
                mAddRecipeViewModel.getBrandedFoodItemById(ingredient.getFdcId());
            }
            else if (dataType.equals("Foundation")) {
                System.out.println("Calling getFoundationFoodItemById...");
                mAddRecipeViewModel.getFoundationFoodItemById(ingredient.getFdcId());
            }
        }




    }


    public void saveRecipeButtonClicked(View view) {
        // Variables to hold the extracted values for our recipe.
        String recipeName;
        String recipeDescription;
        int numServings;
        String recipeCategory = "BREAKFAST";
        String recipeInstructions;

        if (breakfastChip.isChecked()) {
            recipeCategory = "BREAKFAST";
        }

        if (lunchChip.isChecked()) {
            recipeCategory = "LUNCH";
        }

        if (dinnerChip.isChecked()) {
            recipeCategory = "DINNER";
        }

        if (appetizersChip.isChecked()) {
            recipeCategory = "APPETIZER";
        }

        try {
            // TODO - Validate Input
            recipeName = recipeNameText.getText().toString();
            recipeDescription = recipeDescriptionText.getText().toString();
            numServings = Integer.parseInt(recipeServingsText.getText().toString());
            recipeInstructions = recipeInstructionsText.getText().toString();
        } catch(Exception e) {
            // TODO - populate errors for TextFields
            e.printStackTrace();
        }


        recipeName = recipeNameText.getText().toString();
        recipeDescription = recipeDescriptionText.getText().toString();
        numServings = Integer.parseInt(recipeServingsText.getText().toString());
        recipeInstructions = recipeInstructionsText.getText().toString();
        List<IngredientModel> ingredients = ingredientsAdapter.getIngredients();

        // TODO - figure out how to validate all ingredients have a quantity

        // Create new recipe object
        RecipeModel recipe = new RecipeModel();
        recipe.setName(recipeName);
        recipe.setDescription(recipeDescription);
        recipe.setNumServings(numServings);
        recipe.setCategory(recipeCategory);
        recipe.setInstructions(recipeInstructions);
        recipe.setIngredients(ingredients);


        mAddRecipeViewModel.insertRecipe(recipe);

        Intent intent = new Intent(this, CookBookHomeActivity.class);
        startActivity(intent);

    }
}
