package com.codelovely.thecooksnook.viewmodels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;

import com.codelovely.thecooksnook.DatabaseRepository;
import com.codelovely.thecooksnook.data.NutritionInformationDatabase;
import com.codelovely.thecooksnook.models.Recipe;

import java.util.ArrayList;
import java.util.List;

public class CookBookViewModel extends AndroidViewModel {
    private DatabaseRepository mRepository;

    public CookBookViewModel(Application application) {
        super(application);

        mRepository = new DatabaseRepository(application);
    }

    public List<Recipe> getRecipesByCategory(final String category) {
        final List<Recipe> recipes = new ArrayList<>();
        NutritionInformationDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                List<Recipe> dbRecipes = mRepository.getRecipesByCategory(category);
                for (Recipe recipe : dbRecipes) {
                    System.out.println(recipe.getName());
                    recipes.add(recipe);
                }
            }
        });
        return recipes;
    }
}
