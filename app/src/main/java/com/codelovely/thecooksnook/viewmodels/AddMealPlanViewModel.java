package com.codelovely.thecooksnook.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.codelovely.thecooksnook.DatabaseRepository;
import com.codelovely.thecooksnook.data.NutritionInformationDatabase;
import com.codelovely.thecooksnook.models.MealPlan;
import com.codelovely.thecooksnook.models.RecipeModel;
import com.codelovely.thecooksnook.models.UserModel;
import com.codelovely.thecooksnook.models.restmodels.FoodNutrient;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddMealPlanViewModel extends AndroidViewModel {

    private MutableLiveData<Map<String, RecipeModel>> recipeMap;
    private Map<String, RecipeModel> _recipeMap;
    private DatabaseRepository mRepository;
    private UserModel user;

    public AddMealPlanViewModel(@NonNull Application application) {
        super(application);

        recipeMap = new MutableLiveData<Map<String, RecipeModel>>();
        _recipeMap = new HashMap<>();
        mRepository = new DatabaseRepository(application);
    }

    public void setUser(UserModel user) {
        this.user = user;
    }
    public LiveData<Map<String, RecipeModel>> getMealPlanRecipes() {
        return recipeMap;
    }

    public void updateHashMap(RecipeModel recipe) {
        if (_recipeMap.containsKey(recipe.getCategory())) {
            _recipeMap.replace(recipe.getCategory(), recipe);
        }
        else {
            _recipeMap.put(recipe.getCategory(), recipe);
        }
        recipeMap.postValue(_recipeMap);
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
                }
            }
        }
        return recipe.get(0);
    }
    public List<RecipeModel> getRecipes () {
        Collection<RecipeModel> mapValues = _recipeMap.values();
        List<RecipeModel> recipes = new ArrayList<>(mapValues);
        return recipes;
    }

    public void insertMealPlan(final MealPlan mealPlan) {
        NutritionInformationDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                mRepository.insertMenuItem(user, mealPlan);
            }
        });
    }
}
