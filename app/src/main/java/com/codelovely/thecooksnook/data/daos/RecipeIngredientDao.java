package com.codelovely.thecooksnook.data.daos;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.codelovely.thecooksnook.data.entities.RecipeIngredient;

import java.util.List;

@Dao
public interface RecipeIngredientDao {

    @Query("SELECT * FROM recipeIngredients WHERE `Recipe code` = :recipeId")
    List<RecipeIngredient> getIngredientsByRecipeId(int recipeId);

    @Insert
    void insert(RecipeIngredient recipeFood);

    @Query("DELETE FROM recipeIngredients WHERE `Recipe code` = :recipeId")
    void deleteRecipeById(int recipeId);

    @Query("SELECT EXISTS(SELECT * FROM recipeIngredients WHERE `FDC ID` = :fdcId AND `Recipe code` = :recipeCode)")
    boolean checkIfExists(int fdcId, int recipeCode);
}

