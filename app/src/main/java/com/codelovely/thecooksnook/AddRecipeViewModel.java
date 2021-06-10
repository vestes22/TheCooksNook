package com.codelovely.thecooksnook;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.codelovely.thecooksnook.data.MainFoodDesc;
import com.codelovely.thecooksnook.data.NutritionInformationDatabase;
import com.codelovely.thecooksnook.models.FoodOption;
import com.codelovely.thecooksnook.models.FoodPortion;

import java.util.ArrayList;
import java.util.List;

public class AddRecipeViewModel extends AndroidViewModel {
    private DatabaseRepository mRepository;

    // The reason for two lists of ingredients:
    // It is not easy to manipulate list items when working with MutableLiveData. We are limited to postValue() or setValue() (as far as I know).
    // Instead, we use a regular ArrayList for data manipulations. Then, we can update the value of the MutableLiveData with the postValue() method.
    private List<FoodOption> _mRecipeIngredients = new ArrayList<FoodOption>();
    private MutableLiveData<List<FoodOption>> mRecipeIngredients = new MutableLiveData<List<FoodOption>>();

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
        final FoodOption foodOption = new FoodOption();
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

    public LiveData<List<FoodOption>> getRecipeIngredients() {
        return mRecipeIngredients;
    }
}

