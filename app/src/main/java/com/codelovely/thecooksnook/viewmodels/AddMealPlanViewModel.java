package com.codelovely.thecooksnook.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.codelovely.thecooksnook.DatabaseRepository;
import com.codelovely.thecooksnook.models.RecipeModel;

import java.util.HashMap;
import java.util.Map;

public class AddMealPlanViewModel extends AndroidViewModel {

    private MutableLiveData<Map<String, RecipeModel>> recipeMap;
    private Map<String, RecipeModel> _recipeMap;
    private MutableLiveData<RecipeModel> breakfastRecipe;
    private MutableLiveData<RecipeModel> lunchRecipe;
    private MutableLiveData<RecipeModel> dinnerRecipe;
    private MutableLiveData<RecipeModel> appetizerRecipe;
    private DatabaseRepository mRepository;

    public AddMealPlanViewModel(@NonNull Application application) {
        super(application);

        _recipeMap = new HashMap();
        mRepository = new DatabaseRepository(application);
    }

    public LiveData<RecipeModel> getBreakfastRecipe() {
        return breakfastRecipe;
    }

    public void setBreakfastRecipe(RecipeModel breakfastRecipe) {
        this.breakfastRecipe.postValue(breakfastRecipe);
    }

    public LiveData<RecipeModel> getLunchRecipe() {
        return lunchRecipe;
    }

    public void setLunchRecipe(RecipeModel lunchRecipe) {
        this.lunchRecipe.postValue(lunchRecipe);
    }

    public LiveData<RecipeModel> getDinnerRecipe() {
        return dinnerRecipe;
    }

    public void setDinnerRecipe(RecipeModel dinnerRecipe) {
        this.dinnerRecipe.postValue(dinnerRecipe);
    }

    public LiveData<RecipeModel> getAppetizerRecipe() {
        return appetizerRecipe;
    }

    public void setAppetizerRecipe(RecipeModel appetizerRecipe) {
        this.appetizerRecipe.postValue(appetizerRecipe);
    }
}
