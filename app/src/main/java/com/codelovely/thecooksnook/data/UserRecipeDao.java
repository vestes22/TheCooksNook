package com.codelovely.thecooksnook.data;

import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

@Dao
public interface UserRecipeDao {
    @Query("SELECT * FROM userRecipes")
    public List<UserRecipe> getAll();
}
