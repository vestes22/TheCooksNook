package com.codelovely.thecooksnook;

import android.app.Application;

import com.codelovely.thecooksnook.data.MainFoodDesc;
import com.codelovely.thecooksnook.data.daos.MainFoodDescDao;
import com.codelovely.thecooksnook.data.NutritionInformationDatabase;

import java.util.List;

public class DatabaseRepository {
    private MainFoodDescDao mMainFoodDescDao;
    private List<MainFoodDesc> mAllFood;

    public DatabaseRepository(Application application) {
        NutritionInformationDatabase db = NutritionInformationDatabase.getDatabase(application);
        mMainFoodDescDao = db.getMainFoodDescDao();
        mAllFood = mMainFoodDescDao.getAll();
    }

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    public List<MainFoodDesc> getAllFood() {
        return mAllFood;
    }

    public List<MainFoodDesc> search(String query) {
        return mMainFoodDescDao.search(query);
    }
}
