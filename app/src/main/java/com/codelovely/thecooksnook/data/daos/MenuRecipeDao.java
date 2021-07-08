package com.codelovely.thecooksnook.data.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.codelovely.thecooksnook.data.entities.MenuRecipe;

import java.util.List;

@Dao
public interface MenuRecipeDao {
    @Query("SELECT * FROM menuRecipes")
    LiveData<List<MenuRecipe>> getAll();

    @Query("SELECT EXISTS(SELECT * FROM menuRecipes WHERE `Menu code` = :menuCode AND `Recipe code` = :recipeCode)")
    boolean checkIfExists(int menuCode, int recipeCode);

    @Insert
    void insert(MenuRecipe menuRecipe);
}
