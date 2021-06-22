package com.codelovely.thecooksnook.viewmodels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;

import com.codelovely.thecooksnook.DatabaseRepository;
import com.codelovely.thecooksnook.data.NutritionInformationDatabase;
import com.codelovely.thecooksnook.models.Recipe;

import java.util.ArrayList;
import java.util.List;


public class RecipeViewModel extends AndroidViewModel {
    private DatabaseRepository mRepository;

    public RecipeViewModel(Application application) {
        super(application);

        mRepository = new DatabaseRepository(application);
    }

    public Recipe getRecipeById(final int id) {
        final List<Recipe> recipe = new ArrayList<>();

        final Boolean[] condition = {false};
        final Object lockObject = new Object();


        NutritionInformationDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                synchronized (lockObject) {
                    Recipe newRecipe = mRepository.getRecipeById(id);
                    recipe.add(newRecipe);
                    condition[0] = true;
                    lockObject.notify();
                }
            }
        });


        synchronized (lockObject) {
            while (condition[0] == false) {
                try {
                    lockObject.wait();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return recipe.get(0);
    }

    public void delete(final int id) {
        NutritionInformationDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                mRepository.deleteRecipeById(id);
            }
        });
    }
}
