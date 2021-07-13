package com.codelovely.thecooksnook;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codelovely.thecooksnook.models.RecipeModel;
import com.codelovely.thecooksnook.models.restmodels.SearchResultFood;
import com.codelovely.thecooksnook.viewmodels.AddMealPlanViewModel;

import java.util.List;


public class NutritionProfileFragment extends Fragment {

    AddMealPlanViewModel mAddMealPlanViewModel;
    Observer<RecipeModel> breakfastObserver;

    public NutritionProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAddMealPlanViewModel = new ViewModelProvider(this).get(AddMealPlanViewModel.class);
/*
        breakfastObserver = new Observer<List<SearchResultFood>>() {
            @Override
            public void onChanged(List<SearchResultFood> searchResults) {
                if (searchResults != null) {
                    searchAdapter.setResults(searchResults);
                }
            }
        };mAddRecipeViewModel.getSearchResultsLiveData().observe(this, searchResultsObserver);

 */


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_nutrition_profile, container, false);
    }
}
