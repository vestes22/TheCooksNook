package com.codelovely.thecooksnook.data;

import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

@Dao
public interface RecipeFoodDao {
    @Query("SELECT * FROM recipeFoods")
    public List<RecipeFood> getAllRecipeFoods();
}
