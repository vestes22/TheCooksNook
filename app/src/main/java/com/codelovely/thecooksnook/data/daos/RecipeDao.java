package com.codelovely.thecooksnook.data.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.codelovely.thecooksnook.data.entities.Recipe;

import java.util.List;

@Dao
public interface RecipeDao {
    @Query("SELECT * FROM recipes")
    public LiveData<List<Recipe>> getAll();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Recipe recipe);
}
