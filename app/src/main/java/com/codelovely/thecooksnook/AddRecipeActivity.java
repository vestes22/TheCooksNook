package com.codelovely.thecooksnook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import com.codelovely.thecooksnook.data.MainFoodDesc;
import com.codelovely.thecooksnook.viewmodels.AddRecipeViewModel;

import java.util.List;

public class AddRecipeActivity extends AppCompatActivity {
    private AddRecipeViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe);

        // Get the ViewModel
        viewModel = new ViewModelProvider(this).get(AddRecipeViewModel.class);

        // Create the observer, which updates the UI.
        final Observer<List<MainFoodDesc>> searchResultsObserver = new Observer<List<MainFoodDesc>>() {
            @Override
            public void onChanged(final List<MainFoodDesc> searchResults) {
                //TODO - Update the Recyclerview with the data from the search results.
            }
        };

        // Observe the LiveData, passing in this activity as the LifecycleOwner and the observer.
        // TODO - viewModel.search().observe(this, searchResultsObserver); May have something to do with recyclerview adapter... we'll see
    }
}
