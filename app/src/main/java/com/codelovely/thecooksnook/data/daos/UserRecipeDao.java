package com.codelovely.thecooksnook.data.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.codelovely.thecooksnook.data.entities.UserRecipe;

import java.util.List;

@Dao
public interface UserRecipeDao {
    @Query("SELECT * FROM userRecipes")
    LiveData<List<UserRecipe>> getAll();

    @Query("SELECT 'Recipe code' FROM userRecipes WHERE `User code` = :userCode")
    List<Integer> getUserRecipes(String userCode);

    @Query("SELECT EXISTS(SELECT * FROM userRecipes WHERE `User code` = :userCode AND `Recipe code` = :recipeCode )")
    boolean checkIfExists(String userCode, int recipeCode);

    @Insert
    void insert(UserRecipe userRecipe);
}
