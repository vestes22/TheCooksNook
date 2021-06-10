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
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

public class AddRecipeActivity extends AppCompatActivity implements SearchResultsAdapter.SearchResultsListener {
    EditText searchIngredientsText;
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

        searchIngredientsText = (TextInputEditText)findViewById(R.id.search_edit_text);
        chipGroup = (ChipGroup)findViewById(R.id.chipGroup);
        breakfastChip = (Chip)findViewById(R.id.breakfast_chip);
        lunchChip = (Chip)findViewById(R.id.lunch_chip);
        dinnerChip = (Chip)findViewById(R.id.dinner_chip);
        appetizersChip = (Chip)findViewById(R.id.appetizers_chip);
        mAddRecipeViewModel = new ViewModelProvider(this).get(AddRecipeViewModel.class);

        // The rest of this is setup code for our two RecyclerViews:
        // The one we use for our search results, and the one we use to store
        // the list of ingredients for our recipe.
        RecyclerView ingredientsListRv = findViewById(R.id.ingredients_list);
        RecyclerView searchResultsRv = findViewById(R.id.search_results);
        searchAdapter = new SearchResultsAdapter(new SearchResultsAdapter.SearchResultsDiff(), this);
        ingredientsAdapter = new IngredientsListAdapter(new IngredientsListAdapter.IngredientsDiff());
        searchResultsRv.setAdapter(searchAdapter);
        ingredientsListRv.setAdapter(ingredientsAdapter);
        searchResultsRv.setLayoutManager(new LinearLayoutManager(this));
        ingredientsListRv.setLayoutManager(new LinearLayoutManager(this));

        final Observer<List<FoodOption>> ingredientsListObserver = new Observer<List<FoodOption>>() {
            @Override
            public void onChanged(@Nullable final List<FoodOption> ingredientsList) {
                ingredientsAdapter.submitList(null);
                ingredientsAdapter.submitList(ingredientsList);
            }
        };

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
                else if (isChecked) {
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
                else if (isChecked) {
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
                else if (isChecked) {
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
                else if (isChecked) {
                    appetizersChip.setChipBackgroundColor(ColorStateList.valueOf(ContextCompat.getColor(context, R.color.colorAccent)));
                }
            }
        });
        mAddRecipeViewModel.getRecipeIngredients().observe(this, ingredientsListObserver);
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

        // So, this works. But I have qualms about creating an observer in this method...
        // It's recommended to observe LiveData in onCreate for a reason. We don't want to create
        // a billion observers, do we?
        // Maybe we can instantiate a global observer in onCreate, and simply attach it to the observed method here?
        // But global variables can be yucky.
        // My main question is.... Using observers when the method requires an argument. We get the user's query here.
        // We can't pass it to an observer in the onCreate method... or can we?
        // Is the answer onClickListeners?
        // ... It's onClickListeners, isn't it...

        final Observer<List<MainFoodDesc>> searchResultsObserver = new Observer<List<MainFoodDesc>>() {
            @Override
            public void onChanged(@Nullable final List<MainFoodDesc> searchResults) {
                searchAdapter.submitList(searchResults);
                if (searchResults == null) {
                    System.out.println("The results in onChanged are null.");
                }
            }
        };

        mAddRecipeViewModel.fetchIngredientByQuery(query).observe(this, searchResultsObserver);
    }

    /*
    After a user searches for ingredients, they can click on an item in the search results to add that ingredient to their recipe.
    Which is what we do here.
     */
    @Override
    public void onSearchResultClicked(int position) {
        List<MainFoodDesc> currentList = searchAdapter.getCurrentList();
        mAddRecipeViewModel.addRecipeIngredient(currentList.get(position));
    }

    public void saveRecipeButtonClicked(View view) {
    }
}
