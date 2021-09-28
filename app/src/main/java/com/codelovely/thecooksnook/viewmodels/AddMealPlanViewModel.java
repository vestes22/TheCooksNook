package com.codelovely.thecooksnook.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.codelovely.thecooksnook.DatabaseRepository;
import com.codelovely.thecooksnook.RecipeCategory;
import com.codelovely.thecooksnook.data.NutritionInformationDatabase;
import com.codelovely.thecooksnook.models.MealPlan;
import com.codelovely.thecooksnook.models.RecipeModel;
import com.codelovely.thecooksnook.models.UserModel;

import java.util.ArrayList;
import java.util.List;

public class AddMealPlanViewModel extends AndroidViewModel {

    private MutableLiveData<List<RecipeModel>> breakfastList;
    private MutableLiveData<List<RecipeModel>> lunchList;
    private MutableLiveData<List<RecipeModel>> dinnerList;
    private MutableLiveData<List<RecipeModel>> appetizerList;
    private MutableLiveData<List<RecipeModel>> dessertList;
    private MutableLiveData<List<RecipeModel>> drinkList;

    private List<RecipeModel> _breakfastList;
    private List<RecipeModel> _lunchList;
    private List<RecipeModel> _dinnerList;
    private List<RecipeModel> _appetizerList;
    private List<RecipeModel> _dessertList;
    private List<RecipeModel> _drinkList;


    private DatabaseRepository mRepository;
    private UserModel user;

    public AddMealPlanViewModel(@NonNull Application application) {
        super(application);

        breakfastList = new MutableLiveData<>();
        lunchList = new MutableLiveData<>();
        dinnerList = new MutableLiveData<>();
        appetizerList = new MutableLiveData<>();
        dessertList = new MutableLiveData<>();
        drinkList = new MutableLiveData<>();

        _breakfastList = new ArrayList<>();
        _lunchList = new ArrayList<>();
        _dinnerList = new ArrayList<>();
        _appetizerList = new ArrayList<>();
        _dessertList = new ArrayList<>();
        _drinkList = new ArrayList<>();

        mRepository = new DatabaseRepository(application);
    }

    public void setUser(UserModel user) {
        this.user = user;
    }

    public LiveData<List<RecipeModel>> getBreakfastRecipes() {
        return breakfastList;
    }

    public LiveData<List<RecipeModel>> getLunchRecipes() {
        return lunchList;
    }

    public LiveData<List<RecipeModel>> getDinnerRecipes() {
        return dinnerList;
    }

    public LiveData<List<RecipeModel>> getAppetizerRecipes() {
        return appetizerList;
    }

    public LiveData<List<RecipeModel>> getDessertRecipes() {
        return dessertList;
    }

    public LiveData<List<RecipeModel>> getDrinkRecipes() {
        return drinkList;
    }

    public void updateHashMap(RecipeModel recipe) {
        if (recipe.getCategory().equals(RecipeCategory.BREAKFAST.toString())) {
            _breakfastList.add(recipe);
            breakfastList.postValue(_breakfastList);
        }
        else if (recipe.getCategory().equals(RecipeCategory.LUNCH.toString())) {
            _lunchList.add(recipe);
            lunchList.postValue(_lunchList);
        }
        else if (recipe.getCategory().equals(RecipeCategory.DINNER.toString())) {
            _dinnerList.add(recipe);
            dinnerList.postValue(_dinnerList);
        }
        else if (recipe.getCategory().equals(RecipeCategory.APPETIZER.toString())) {
            _appetizerList.add(recipe);
            appetizerList.postValue(_appetizerList);
        }
        else if (recipe.getCategory().equals(RecipeCategory.DESSERT.toString())) {
            _dessertList.add(recipe);
            dessertList.postValue(_dessertList);
        }
        else if (recipe.getCategory().equals(RecipeCategory.DRINK.toString())) {
            _drinkList.add(recipe);
            drinkList.postValue(_drinkList);
        }
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
        List<RecipeModel> totalRecipes = new ArrayList<>();
        totalRecipes.addAll(_breakfastList);
        totalRecipes.addAll(_lunchList);
        totalRecipes.addAll(_dinnerList);
        totalRecipes.addAll(_appetizerList);
        totalRecipes.addAll(_dessertList);
        totalRecipes.addAll(_drinkList);

        return totalRecipes;
    }

    public void removeBreakfast(String breakfastName) {
        for (RecipeModel recipe : _breakfastList) {
            if (breakfastName.equals(recipe.getName())) {
                _breakfastList.remove(recipe);
                break;
            }
        }
        breakfastList.postValue(_breakfastList);
    }

    public void removeLunch(String lunchName) {
        for (RecipeModel recipe : _lunchList) {
            if (lunchName.equals(recipe.getName())) {
                _lunchList.remove(recipe);
                break;
            }
        }
        lunchList.postValue(_lunchList);
    }

    public void removeDinner(String dinnerName) {
        for (RecipeModel recipe : _dinnerList) {
            if (dinnerName.equals(recipe.getName())) {
                _dinnerList.remove(recipe);
                break;
            }
        }
        dinnerList.postValue(_dinnerList);
    }

    public void removeAppetizer(String appetizerName) {
        for (RecipeModel recipe : _appetizerList) {
            if (appetizerName.equals(recipe.getName())) {
                _appetizerList.remove(recipe);
                break;
            }
        }
        appetizerList.postValue(_appetizerList);
    }

    public void removeDessert(String dessertName) {
        for (RecipeModel recipe : _dessertList) {
            if (dessertName.equals(recipe.getName())) {
                _dessertList.remove(recipe);
                break;
            }
        }
        dessertList.postValue(_dessertList);
    }

    public void removeDrink(String drinkName) {
        for (RecipeModel recipe : _drinkList) {
            if (drinkName.equals(recipe.getName())) {
                _drinkList.remove(recipe);
                break;
            }
        }
        drinkList.postValue(_drinkList);
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
