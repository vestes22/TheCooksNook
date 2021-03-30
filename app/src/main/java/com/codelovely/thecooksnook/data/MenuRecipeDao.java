package com.codelovely.thecooksnook.data;

import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

@Dao
public interface MenuRecipeDao {
    @Query("SELECT * FROM menuRecipes")
    public List<MenuRecipe> getAll();
}
