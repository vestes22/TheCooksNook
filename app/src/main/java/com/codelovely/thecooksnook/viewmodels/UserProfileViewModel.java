package com.codelovely.thecooksnook.viewmodels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.codelovely.thecooksnook.DatabaseRepository;
import com.codelovely.thecooksnook.data.NutritionInformationDatabase;
import com.codelovely.thecooksnook.models.MealPlan;
import com.codelovely.thecooksnook.models.RecipeModel;
import com.codelovely.thecooksnook.models.UserModel;
import com.codelovely.thecooksnook.models.restmodels.FoodNutrient;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserProfileViewModel extends AndroidViewModel {
    private DatabaseRepository mRepository;
    private UserModel user;
    private MutableLiveData<List<MealPlan>> mealsInDateRange;
    private List<MealPlan> _mealsInDateRange;
    private MutableLiveData<Map<Integer, FoodNutrient>> averageDailyNutrients;
    private Map<Integer, FoodNutrient> _averageDailyNutrients;
    private MutableLiveData<List<RecipeModel>> recipes;


    public UserProfileViewModel(Application application) {
        super(application);
        mealsInDateRange = new MutableLiveData<>();
        _mealsInDateRange = new ArrayList<>();
        averageDailyNutrients = new MutableLiveData<>();
        _averageDailyNutrients = new HashMap<>();
        recipes = new MutableLiveData();
        mRepository = new DatabaseRepository(application);
    }

    public void setUser(UserModel user) {
        this.user = user;
    }


    public void getUserMenuRecipesInDateRange(LocalDate startDate,LocalDate endDate) {
        NutritionInformationDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                _mealsInDateRange.clear();
                _mealsInDateRange = mRepository.getUserMenusInDateRange(user, startDate, endDate);
                mealsInDateRange.postValue(_mealsInDateRange);
                updateAverageDailyNutrients();
            }
        });
    }

    public void updateAverageDailyNutrients() {
        _averageDailyNutrients.clear();

        List<RecipeModel> totalRecipes = new ArrayList<>();

        for(MealPlan meal : _mealsInDateRange) {
            totalRecipes.addAll(meal.getRecipes());
        }

        for (RecipeModel recipeModel : totalRecipes) {

            List<FoodNutrient> recipeNutrients = recipeModel.getRecipeNutrientsPerServing();
            for (FoodNutrient foodNutrient : recipeNutrients) {
                int nutrientId = foodNutrient.getNutrient().getId();
                if (_averageDailyNutrients.containsKey(nutrientId)) {
                    float addedAmount = foodNutrient.getAmount();
                    float currentAmount = _averageDailyNutrients.get(nutrientId).getAmount();
                    _averageDailyNutrients.get(nutrientId).setAmount(currentAmount + addedAmount);
                }
                else {
                    FoodNutrient newNutrient = new FoodNutrient();
                    newNutrient.setId(foodNutrient.getId());
                    newNutrient.setFdcId(foodNutrient.getFdcId());
                    newNutrient.setAmount(foodNutrient.getAmount());
                    newNutrient.setNutrient(foodNutrient.getNutrient());
                    _averageDailyNutrients.put(nutrientId, newNutrient);
                }
            }
        }

        for (Map.Entry mapItem : _averageDailyNutrients.entrySet()) {
            FoodNutrient foodNutrient = (FoodNutrient) mapItem.getValue();
            float averageDailyValue = foodNutrient.getAmount() / _mealsInDateRange.size();
            foodNutrient.setAmount(averageDailyValue);
            _averageDailyNutrients.replace((int)mapItem.getKey(), foodNutrient);
        }

        averageDailyNutrients.postValue(_averageDailyNutrients);

    }

    public LiveData<Map<Integer, FoodNutrient>> getAverageDailyNutrients() {
        return averageDailyNutrients;
    }

    public LiveData<List<RecipeModel>> getRecipes() {
        return recipes;
    }

    public List<MealPlan> getMealsInDateRange() {
        return _mealsInDateRange;
    }

    public void setRecipesByCategory(final String category) {
        NutritionInformationDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                recipes.postValue(mRepository.getFullRecipesByCategory(user, category));
            }
        });
    }
}