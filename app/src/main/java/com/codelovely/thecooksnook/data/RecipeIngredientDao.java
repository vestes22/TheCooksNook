package com.codelovely.thecooksnook.data;


import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

@Dao
public interface RecipeIngredientDao {
    @Query("SELECT * FROM recipeIngredients")
    public List<RecipeIngredient> getAll();
}
