package com.codelovely.thecooksnook.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.codelovely.thecooksnook.models.RecipeModel;
import com.codelovely.thecooksnook.models.restmodels.FoodNutrient;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NutritionProfileViewModel extends AndroidViewModel {
    private MutableLiveData<Map<Integer, FoodNutrient>> totalNutrients;
    private Map<Integer, FoodNutrient> _totalNutrients;
    private Map<String, RecipeModel> _recipes;

    public NutritionProfileViewModel(@NonNull Application application) {
        super(application);
        totalNutrients = new MutableLiveData<>();
        _totalNutrients = new HashMap<>();
        _recipes = new HashMap<>();
    }

    public void updateHashMap(RecipeModel recipe) {
        if (_recipes.containsKey(recipe.getCategory())) {
            _recipes.replace(recipe.getCategory(), recipe);
        }
        else{
            _recipes.put(recipe.getCategory(), recipe);
        }
        updateTotalNutrients();
    }

    private void updateTotalNutrients() {
        _totalNutrients.clear();

        for (Map.Entry<String, RecipeModel> entry : _recipes.entrySet()) {

            RecipeModel recipeModel = entry.getValue();
            List<FoodNutrient> recipeNutrients = recipeModel.getRecipeNutrientsPerServing();
            for (FoodNutrient foodNutrient : recipeNutrients) {
                int nutrientId = foodNutrient.getNutrient().getId();
                if (_totalNutrients.containsKey(nutrientId)) {
                    float addedAmount = foodNutrient.getAmount();
                    float currentAmount = _totalNutrients.get(nutrientId).getAmount();
                    _totalNutrients.get(nutrientId).setAmount(currentAmount + addedAmount);
                }
                else {
                    FoodNutrient newNutrient = new FoodNutrient();
                    newNutrient.setId(foodNutrient.getId());
                    newNutrient.setFdcId(foodNutrient.getFdcId());
                    newNutrient.setAmount(foodNutrient.getAmount());
                    newNutrient.setNutrient(foodNutrient.getNutrient());
                    _totalNutrients.put(nutrientId, newNutrient);
                }
            }
        }
        totalNutrients.postValue(_totalNutrients);
    }

    public LiveData<Map<Integer, FoodNutrient>> getTotalNutrients() {
        return totalNutrients;
    }
}
