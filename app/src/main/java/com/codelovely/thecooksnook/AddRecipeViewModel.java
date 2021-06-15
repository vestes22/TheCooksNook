package com.codelovely.thecooksnook;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.codelovely.thecooksnook.data.MainFoodDesc;
import com.codelovely.thecooksnook.data.NutritionInformationDatabase;
import com.codelovely.thecooksnook.models.Ingredient;
import com.codelovely.thecooksnook.models.FoodPortion;
import com.codelovely.thecooksnook.models.Nutrient;
import com.codelovely.thecooksnook.models.Recipe;

import java.util.ArrayList;
import java.util.List;

public class AddRecipeViewModel extends AndroidViewModel {
    private DatabaseRepository mRepository;

    // The reason for two lists of ingredients:
    // It is not easy to manipulate list items when working with MutableLiveData. We are limited to postValue() or setValue() (as far as I know).
    // Instead, we use a regular ArrayList for data manipulations. Then, we can update the value of the MutableLiveData with the postValue() method.
    private List<Ingredient> _mRecipeIngredients = new ArrayList<Ingredient>();
    private MutableLiveData<List<Ingredient>> mRecipeIngredients = new MutableLiveData<List<Ingredient>>();

    public AddRecipeViewModel(Application application) {
        super(application);

        mRepository = new DatabaseRepository(application);
    }

    // Fetches a list of ingredients for the user based on their search criteria.
    // Allows user to search for ingredients to add to their recipe.
    public LiveData<List<MainFoodDesc>> fetchIngredientByQuery(final String query) {
        return mRepository.search(query);
    }

    public List<FoodPortion> getPortionOptions(int id) {
        return mRepository.getPortionOptions(id);
    }

    public void addRecipeIngredient(final MainFoodDesc ingredient) {
        final Ingredient foodOption = new Ingredient();
        foodOption.setFoodId(ingredient.getFoodId());
        foodOption.setFoodName(ingredient.getMainFoodDesc());
        NutritionInformationDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                foodOption.setFoodPortion(mRepository.getPortionOptions(ingredient.getFoodId()));
            }
        });
        _mRecipeIngredients.add(foodOption);
        mRecipeIngredients.postValue(_mRecipeIngredients);
    }

    public void removeRecipeIngredient(MainFoodDesc ingredient) {
       _mRecipeIngredients.remove(ingredient);
       mRecipeIngredients.postValue(_mRecipeIngredients);
    }

    public LiveData<List<Ingredient>> getRecipeIngredients() {
        return mRecipeIngredients;
    }

    public List<Nutrient> getNutrition(final int foodCode, final int portionCode) {
        final List<Nutrient> nutrients = new ArrayList<>();
        NutritionInformationDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                List<Nutrient> repoResults = mRepository.getNutrition(foodCode, portionCode);
                for (Nutrient nutrient : repoResults) {
                    nutrients.add(nutrient);
                }
            }
        });
        return mRepository.getNutrition(foodCode, portionCode);
    }

    public void insertRecipe(final Recipe recipe) {
        NutritionInformationDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
               mRepository.insertRecipe(recipe);
            }
        });
    }
}

