package com.codelovely.thecooksnook;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import com.codelovely.thecooksnook.data.MainFoodDesc;

import java.util.List;

public class AddRecipeActivity extends AppCompatActivity implements SearchResultsAdapter.SearchResultsListener {
    EditText searchIngredientsText;
    SearchResultsAdapter searchAdapter;
    IngredientsListAdapter ingredientsAdapter;
    AddRecipeViewModel mAddRecipeViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe);

        searchIngredientsText = findViewById(R.id.searchText);
        mAddRecipeViewModel = new ViewModelProvider(this).get(AddRecipeViewModel.class);

        // The rest of this is setup code for our two RecyclerViews:
        // The one we use for our search results, and the one we use to store
        // the list of ingredients for our recipe.
        RecyclerView ingredientsListRv = findViewById(R.id.ingredients_list);
        RecyclerView searchResultsRv = findViewById(R.id.search_results);
        searchAdapter = new SearchResultsAdapter(new SearchResultsAdapter.SearchResultsDiff(), this);
        // I'm just going to see if I can use the same DiffUtil callback class thingy for both adapters.
        // Currently I have an inner DiffUtil class created in SearchResultsAdapter, but not for IngredientsListAdapter.
        // I can't see why the same class wouldn't work for both!
        ingredientsAdapter = new IngredientsListAdapter(new SearchResultsAdapter.SearchResultsDiff());
        searchResultsRv.setAdapter(searchAdapter);
        ingredientsListRv.setAdapter(ingredientsAdapter);
        searchResultsRv.setLayoutManager(new LinearLayoutManager(this));
        ingredientsListRv.setLayoutManager(new LinearLayoutManager(this));
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
        System.out.println("Search result clicked!");
        List<MainFoodDesc> currentList = searchAdapter.getCurrentList();
        mAddRecipeViewModel.addRecipeIngredient(currentList.get(position));
        System.out.println("I clicked " + currentList.get(position).getMainFoodDesc());
        // TODO - When searchResults recyclerview item is clicked.
    }
}
