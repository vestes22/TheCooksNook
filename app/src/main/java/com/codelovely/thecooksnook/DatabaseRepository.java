package com.codelovely.thecooksnook;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.codelovely.thecooksnook.data.MainFoodDesc;
import com.codelovely.thecooksnook.data.daos.FoodPortionDao;
import com.codelovely.thecooksnook.data.daos.IngredientDao;
import com.codelovely.thecooksnook.data.daos.MainFoodDescDao;
import com.codelovely.thecooksnook.data.NutritionInformationDatabase;
import com.codelovely.thecooksnook.data.daos.RecipeDao;
import com.codelovely.thecooksnook.data.daos.RecipeFoodDao;
import com.codelovely.thecooksnook.data.entities.RecipeFood;
import com.codelovely.thecooksnook.models.FoodPortion;
import com.codelovely.thecooksnook.models.Ingredient;
import com.codelovely.thecooksnook.data.entities.Recipe;

import com.codelovely.thecooksnook.models.Nutrient;


import java.util.ArrayList;
import java.util.List;

public class DatabaseRepository {
    private MainFoodDescDao mMainFoodDescDao;
    private FoodPortionDao mFoodPortionDao;
    private LiveData<List<MainFoodDesc>> mAllFood;
    private IngredientDao mIngredientDao;
    private RecipeDao mRecipeDao;
    private RecipeFoodDao mRecipeFoodDao;

    public DatabaseRepository(Application application) {
        NutritionInformationDatabase db = NutritionInformationDatabase.getDatabase(application);
        mMainFoodDescDao = db.getMainFoodDescDao();
        mFoodPortionDao = db.getFoodPortionDao();
        mIngredientDao = db.getIngredientDao();
        mRecipeDao = db.getRecipeDao();
        mRecipeFoodDao = db.getRecipeFoodDao();
        mAllFood = mMainFoodDescDao.getAll();
    }

    public LiveData<List<MainFoodDesc>> getAllFood() {
        return mAllFood;
    }

    // Gets the available portion options for a specific food item.
    // For example, portion options for measuring walnuts might include cups, ounces, or number of nuts.
    public List<FoodPortion> getPortionOptions(final int id) {
        return mFoodPortionDao.getPortionOptions(id);
    }


    // Searches for ingredients in the Room database. Used for selecting ingredients to add to a user's recipe.
    public LiveData<List<MainFoodDesc>> search(final String query) {
        LiveData<List<MainFoodDesc>> searchResults = mMainFoodDescDao.search("*" + query + "*");
        return searchResults;
    }

    // Gets the nutritional values for a food item based on its portion.
    public List<Nutrient> getNutrition(int foodCode, int portionCode) {
        return mIngredientDao.getNutrition(foodCode, portionCode);
    }

    public void insertRecipe(com.codelovely.thecooksnook.models.Recipe recipe) {
        com.codelovely.thecooksnook.data.entities.Recipe dataRecipe = new Recipe();
        dataRecipe.setTitle(recipe.getName());
        dataRecipe.setDescription(recipe.getDescription());
        dataRecipe.setNumServings(recipe.getNumServings());
        dataRecipe.setInstructions(recipe.getInstructions());
        dataRecipe.setCategory(recipe.getCategory());
        int recipeId = (int) mRecipeDao.insert(dataRecipe);


        List<Ingredient> ingredients = recipe.getIngredients();
        for (Ingredient ingredient : ingredients) {
            RecipeFood recipeFood =  new RecipeFood();
            recipeFood.setRecipeId(recipeId);
            recipeFood.setFoodId(ingredient.getFoodId());
            recipeFood.setPortionCode(ingredient.getSelectedPortion().getPortionCode());
            recipeFood.setQuantity(ingredient.getQty());
            mRecipeFoodDao.insert(recipeFood);
        }
    }

    public List<com.codelovely.thecooksnook.models.Recipe> getRecipesByCategory(String category) {
        List<com.codelovely.thecooksnook.data.entities.Recipe> recipes = mRecipeDao.getRecipeByCategory(category);
        List<com.codelovely.thecooksnook.models.Recipe> modelRecipes = new ArrayList<>();
        for (com.codelovely.thecooksnook.data.entities.Recipe recipe : recipes) {
            com.codelovely.thecooksnook.models.Recipe modelRecipe = new com.codelovely.thecooksnook.models.Recipe();
            modelRecipe.setId(recipe.getRecipeId());
            modelRecipe.setName(recipe.getTitle());
            modelRecipe.setDescription(recipe.getDescription());
            modelRecipe.setNumServings(recipe.getNumServings());
            modelRecipe.setCategory(category);
            modelRecipe.setInstructions(recipe.getInstructions());

            /*
            List<Ingredient> ingredients = mRecipeFoodDao.getIngredientsByRecipeId(recipe.getRecipeId());
            // TODO - instead of having multiple nested for loops, I'm only going to get the list of ingredients and their subsequent nutrition as needed.
            // I'll do that when the Recipe screen loads, and I'll only do it for that one Recipe instead of all the recipes returned in the query.
            for (Ingredient ingredient : ingredients) {
                List<Nutrient> nutrients = mIngredientDao.getNutrition(ingredient.getFoodId(), ingredient.getSelectedPortion().getPortionCode());

            }
            modelRecipe.setIngredients(ingredients); */
        }
        return modelRecipes;
    }




}
