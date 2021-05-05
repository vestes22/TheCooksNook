package com.codelovely.thecooksnook.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.codelovely.thecooksnook.data.MainFoodDesc;
import com.codelovely.thecooksnook.data.MainFoodDescDao;

import java.util.ArrayList;
import java.util.List;

public class RecipeViewModel extends ViewModel {
    MainFoodDescDao foodDao;
    private String recipeTitle;
    private String recipeDescription;
    private String instructions;
    private List<MainFoodDesc> ingredients;
    private MutableLiveData<LiveData<MainFoodDesc>> searchResults;

    public void setRecipeTitle(String recipeTitle) {
        this.recipeTitle = recipeTitle;
    }

    public void setRecipeDescription(String recipeDescription) {
        this.recipeDescription = recipeDescription;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public void addIngredient(MainFoodDesc ingredient) {
        ingredients.add(ingredient);
    }

    public void removeIngredient(MainFoodDesc ingredient) {
        ingredients.remove(ingredient);
    }

    public String getRecipeTitle() {
        return recipeTitle;
    }

    public String getRecipeDescription() {
        return recipeDescription;
    }

    public String getInstructions() {
        return instructions;
    }

    public List<MainFoodDesc> getIngredients() {
        return ingredients;
    }

    public void search(String query) {
        if(query.trim().isEmpty()) {
            searchResults.setValue((LiveData<MainFoodDesc>) foodDao.getAll());
        }
        else {
            searchResults.setValue((LiveData<MainFoodDesc>) foodDao.search("*" + query + "*"));
        }
    }

}

