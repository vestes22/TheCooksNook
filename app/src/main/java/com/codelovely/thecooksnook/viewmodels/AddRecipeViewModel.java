package com.codelovely.thecooksnook.viewmodels;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.codelovely.thecooksnook.DatabaseRepository;
import com.codelovely.thecooksnook.data.NutritionInformationDatabase;
import com.codelovely.thecooksnook.models.IngredientModel;
import com.codelovely.thecooksnook.models.RecipeModel;
import com.codelovely.thecooksnook.models.restmodels.SearchResultFood;
import java.util.List;

public class AddRecipeViewModel extends AndroidViewModel {
    private DatabaseRepository mRepository;

    public AddRecipeViewModel(Application application) {
        super(application);

        mRepository = new DatabaseRepository(application);
    }

    public void insertRecipe(final RecipeModel recipe) {
        NutritionInformationDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
               mRepository.insertRecipe(recipe);
            }
        });
    }

    public void searchFoodDataCentralByName(String query) {
        mRepository.searchFoodDataCentralByName(query);
    }

    public LiveData<List<SearchResultFood>> getSearchResultsLiveData() {
        return mRepository.getSearchResultMutableLiveData();
    }

    public LiveData<List<IngredientModel>> getSelectedIngredientsLiveData() {
        return mRepository.getSelectedIngredientsLiveData();
    }

    public void getBrandedFoodItemById(int fdcId) {
        mRepository.getBrandedFoodItemById(fdcId);
    }

    public void getFoundationFoodItemById(int fdcId) {
        mRepository.getFoundationFoodItemById(fdcId);
    }
}

