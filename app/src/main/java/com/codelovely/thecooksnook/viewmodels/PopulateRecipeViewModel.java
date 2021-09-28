package com.codelovely.thecooksnook.viewmodels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;

import com.codelovely.thecooksnook.DatabaseRepository;
import com.codelovely.thecooksnook.models.RecipeModel;

public class PopulateRecipeViewModel extends AndroidViewModel {
    private DatabaseRepository mRepository;

    public PopulateRecipeViewModel(Application application) {
        super(application);
        mRepository = new DatabaseRepository(application);
    }

    public void insertPreliminaryRecipe(RecipeModel recipeModel) {
        mRepository.insertPreliminaryRecipe(recipeModel);
    }
}
