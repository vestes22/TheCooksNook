package com.codelovely.thecooksnook.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface RecipeFoodDao {
    @Query("SELECT * FROM recipeFoods")
    public LiveData<List<RecipeFood>> getAllRecipeFoods();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(RecipeFood recipeFood);
}
