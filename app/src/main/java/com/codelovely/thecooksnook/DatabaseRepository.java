package com.codelovely.thecooksnook;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.codelovely.thecooksnook.data.MainFoodDesc;
import com.codelovely.thecooksnook.data.daos.FoodPortionDao;
import com.codelovely.thecooksnook.data.daos.MainFoodDescDao;
import com.codelovely.thecooksnook.data.NutritionInformationDatabase;
import com.codelovely.thecooksnook.models.FoodPortion;

import java.util.List;

public class DatabaseRepository {
    private MainFoodDescDao mMainFoodDescDao;
    private FoodPortionDao mFoodPortionDao;
    private LiveData<List<MainFoodDesc>> mAllFood;

    public DatabaseRepository(Application application) {
        NutritionInformationDatabase db = NutritionInformationDatabase.getDatabase(application);
        mMainFoodDescDao = db.getMainFoodDescDao();
        mFoodPortionDao = db.getFoodPortionDao();

        mAllFood = mMainFoodDescDao.getAll();
    }

    public LiveData<List<MainFoodDesc>> getAllFood() {
        return mAllFood;
    }

    // Gets the available portion options for a specific food item.
    // For example, portion options for measuring walnuts might include cups, ounces, or number of nuts.
    public LiveData<List<FoodPortion>> getPortionOptions(final int id) {
        return mFoodPortionDao.getPortionOptions(id);
    }


    // Searches for ingredients in the Room database. Used for selecting ingredients to add to a user's recipe.
    public LiveData<List<MainFoodDesc>> search(final String query) {
        LiveData<List<MainFoodDesc>> searchResults = mMainFoodDescDao.search("*" + query + "*");
        return searchResults;
    }




}
