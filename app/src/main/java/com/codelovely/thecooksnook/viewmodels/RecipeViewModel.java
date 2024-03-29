package com.codelovely.thecooksnook.viewmodels;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;

import com.codelovely.thecooksnook.DatabaseRepository;
import com.codelovely.thecooksnook.data.NutritionInformationDatabase;
import com.codelovely.thecooksnook.models.RecipeModel;

import java.util.ArrayList;
import java.util.List;


public class RecipeViewModel extends AndroidViewModel {
    private DatabaseRepository mRepository;

    public RecipeViewModel(Application application) {
        super(application);

        mRepository = new DatabaseRepository(application);
    }

    public RecipeModel getRecipeById(final int id) {
        final List<RecipeModel> recipe = new ArrayList<>();
        final Boolean[] condition = {false};
        final Object lockObject = new Object();


        NutritionInformationDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                synchronized (lockObject) {
                    RecipeModel newRecipe = mRepository.getRecipeById(id);
                    recipe.add(newRecipe);
                    condition[0] = true;
                    lockObject.notify();
                }
            }
        });


        synchronized (lockObject) {
            while (!condition[0]) {
                try {
                    lockObject.wait();
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("ERROR", "The synchronization lock failed.");
                }
            }
        }
        return recipe.get(0);
    }
}
