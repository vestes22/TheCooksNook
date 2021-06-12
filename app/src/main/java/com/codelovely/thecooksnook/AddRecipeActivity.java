package com.codelovely.thecooksnook;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import com.codelovely.thecooksnook.data.MainFoodDesc;
import com.codelovely.thecooksnook.models.FoodOption;
import com.codelovely.thecooksnook.models.Recipe;
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
    Observer<List<MainFoodDesc>> searchResultsObserver;
    SearchResultsAdapter searchAdapter;
    IngredientsListAdapter ingredientsAdapter;
    AddRecipeViewModel mAddRecipeViewModel;

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
        searchIngredientsText = (TextInputEditText)findViewById(R.id.search_edit_text);
        recipeNameText = (TextInputEditText)findViewById(R.id.name_edit_text);
        recipeDescriptionText = (TextInputEditText)findViewById(R.id.description_edit_text);
        recipeServingsText = (TextInputEditText)findViewById(R.id.num_servings_edit_text);
        recipeInstructionsText = (TextInputEditText)findViewById(R.id.instructions_edit_text);
        chipGroup = findViewById(R.id.chipGroup);
        breakfastChip = findViewById(R.id.breakfast_chip);
        lunchChip = findViewById(R.id.lunch_chip);
        dinnerChip = findViewById(R.id.dinner_chip);
        appetizersChip = findViewById(R.id.appetizers_chip);
        mAddRecipeViewModel = new ViewModelProvider(this).get(AddRecipeViewModel.class);

        // Setup code for our two RecyclerViews:
        // One we use for our search results, and one we use to store
        // the list of ingredients for our recipe.
        RecyclerView ingredientsListRv = findViewById(R.id.ingredients_list);
        RecyclerView searchResultsRv = findViewById(R.id.search_results);
        searchAdapter = new SearchResultsAdapter(new SearchResultsAdapter.SearchResultsDiff(), this);
        ingredientsAdapter = new IngredientsListAdapter(new IngredientsListAdapter.IngredientsDiff());
        searchResultsRv.setAdapter(searchAdapter);
        ingredientsListRv.setAdapter(ingredientsAdapter);
        searchResultsRv.setLayoutManager(new LinearLayoutManager(this));
        ingredientsListRv.setLayoutManager(new LinearLayoutManager(this));

        // Setup code for our observers, which we use to populate the RecyclerViews with updated data.
        final Observer<List<FoodOption>> ingredientsListObserver = new Observer<List<FoodOption>>() {
            @Override
            public void onChanged(@Nullable final List<FoodOption> ingredientsList) {
                ingredientsAdapter.submitList(null);
                ingredientsAdapter.updateIngredients(ingredientsList);
                ingredientsAdapter.submitList(ingredientsList);
            }
        };

        mAddRecipeViewModel.getRecipeIngredients().observe(this, ingredientsListObserver);

        searchResultsObserver = new Observer<List<MainFoodDesc>>() {
            @Override
            public void onChanged(@Nullable final List<MainFoodDesc> searchResults) {
                searchAdapter.submitList(searchResults);
                if (searchResults == null) {
                    System.out.println("The results in onChanged are null.");
                }
            }
        };


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

    /*
    This enables the user to search for ingredients to use in their recipe.
    Data retrieval follows the Android Guide to App Architecture:
    It asks the ViewModel for the data.
    The ViewModel gets the data from the Repository, which gets it from the Room database.

    I have a love/hate relationship with LiveData, which is what we use to store the results.
     */
    public void searchButtonClicked(View view) {
        String query = searchIngredientsText.getText().toString();
        mAddRecipeViewModel.fetchIngredientByQuery(query).observe(this, searchResultsObserver);
    }

    /*
    After a user searches for ingredients, they can click on an item in the search results to add that ingredient to their recipe.
    Which is what we do here.
     */
    @Override
    public void onSearchResultClicked(int position) {
        // Gets the ingredient that was clicked.
        List<MainFoodDesc> currentList = searchAdapter.getCurrentList();
        MainFoodDesc ingredient = currentList.get(position);

        // Compares the ID of the selected ingredient with the ingredients we already have added to our list.
        // If we already have the ingredient on our list, we do not want to add it again.
        // But if the ingredient is not on our list, we add it.
        List<FoodOption> ingredientsList = ingredientsAdapter.getCurrentList();
        int flag = -1;
        for (FoodOption food : ingredientsList) {
            if (food.getFoodId() == ingredient.getFoodId())
            {
                flag++;
            }
        }
        if (flag >= 0) {
            System.out.println("Ingredient already added.");
        }
        else {
            mAddRecipeViewModel.addRecipeIngredient(ingredient);
        }
    }


    public void saveRecipeButtonClicked(View view) {
        // Variables to hold the extracted values for our recipe.
        String recipeName;
        String recipeDescription;
        int numServings;
        String recipeCategory = "BREAKFAST";
        String recipeInstructions;
        // TODO - ensure at least one chip is selected

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
            recipeName = recipeNameText.getText().toString();
            recipeDescription = recipeDescriptionText.getText().toString();
            numServings = Integer.parseInt(recipeServingsText.getText().toString());
            recipeInstructions = recipeInstructionsText.getText().toString();
        } catch(Exception e) {
            e.printStackTrace();
        }
        recipeName = recipeNameText.getText().toString();
        recipeDescription = recipeDescriptionText.getText().toString();
        numServings = Integer.parseInt(recipeServingsText.getText().toString());
        recipeInstructions = recipeInstructionsText.getText().toString();
        List<FoodOption> ingredients = ingredientsAdapter.getIngredients();


        // TODO - input validation
        // Validate our inputs.

        // TODO - create recipe object
        Recipe recipe = new Recipe();
        recipe.setCategory(recipeCategory);
        recipe.setDescription(recipeDescription);
        recipe.setName(recipeName);
        recipe.setInstructions(recipeInstructions);
        recipe.setNumServings(numServings);

        // TODO - Set Recipe Ingredients

        // TODO - Save recipe object to Room Database - write method in ViewModel
        // May have to write additional columns in the RecipeFood entity to store values for quantity and portion

        // TODO - Save recipe object to Firebase - write method in ViewModel

        // TODO - Re-Route to cookbook home screen.

    }
}
