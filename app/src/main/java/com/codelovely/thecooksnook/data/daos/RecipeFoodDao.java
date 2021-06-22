package com.codelovely.thecooksnook.data.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.codelovely.thecooksnook.data.entities.RecipeFood;
import com.codelovely.thecooksnook.models.Ingredient;

import java.util.List;

@Dao
public interface RecipeFoodDao {
    @Query("SELECT * FROM recipeFoods")
    public LiveData<List<RecipeFood>> getAllRecipeFoods();

    @Query("SELECT * FROM recipeFoods WHERE `Recipe code` = :recipeId")
    List<RecipeFood> getIngredientsByRecipeId(int recipeId);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(RecipeFood recipeFood);

    @Query("DELETE FROM recipeFoods WHERE `Recipe code` = :recipeId")
    void deleteRecipeById(int recipeId);
}

