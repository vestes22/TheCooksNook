package com.codelovely.thecooksnook.viewmodels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.codelovely.thecooksnook.DatabaseRepository;
import com.codelovely.thecooksnook.data.NutritionInformationDatabase;
import com.codelovely.thecooksnook.models.RecipeModel;
import com.codelovely.thecooksnook.models.UserModel;

import java.util.List;

public class CookBookViewModel extends AndroidViewModel {
    private DatabaseRepository mRepository;
    private MutableLiveData<List<RecipeModel>> recipes;
    private UserModel user;


    public CookBookViewModel(Application application) {
        super(application);

        recipes = new MutableLiveData<>();
        mRepository = new DatabaseRepository(application);
    }

    public void setRecipesByCategory(final String category) {
        NutritionInformationDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                recipes.postValue(mRepository.getRecipesByCategory(user, category));
            }
        });
    }

    public void setUser(UserModel user) {
        this.user = user;
    }

    public LiveData<List<RecipeModel>> getRecipesByCategory() {
        return recipes;
    }


    public void setAllRecipes() {
        NutritionInformationDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                recipes.postValue(mRepository.getAllRecipes(user));
            }
        });
    }
}
