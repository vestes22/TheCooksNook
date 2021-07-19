package com.codelovely.thecooksnook.data.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.codelovely.thecooksnook.data.entities.Recipe;

import java.util.List;

@Dao
public interface RecipeDao {
    @Query("SELECT * FROM recipes")
    List<Recipe> getAll();

    @Query("SELECT * FROM recipes WHERE category = :category")
    List<Recipe> getRecipeByCategory(String category);

    @Query("SELECT userRecipes.`User code`, userRecipes.`Recipe code`, recipes.title, recipes.description, recipes.`Number of servings`, recipes.instructions, recipes.category " +
            "FROM userRecipes JOIN recipes ON userRecipes.`Recipe code` =  recipes.`Recipe code` " +
            "WHERE `User code` = :userId AND category = :category")
    List<Recipe> getUserRecipeByCategory(String userId, String category);

    @Query("SELECT * FROM recipes WHERE `Recipe code` = :recipeCode")
    Recipe getRecipeById(int recipeCode);

    @Insert
    long insert(Recipe recipe);

    @Query("DELETE FROM recipes WHERE `Recipe code` = :recipeId")
    void deleteByRecipeId(int recipeId);
}
