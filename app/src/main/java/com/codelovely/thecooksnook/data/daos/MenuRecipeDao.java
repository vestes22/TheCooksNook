package com.codelovely.thecooksnook.data.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.codelovely.thecooksnook.data.entities.MenuRecipe;

import java.util.List;

@Dao
public interface MenuRecipeDao {
    @Query("SELECT * FROM menuRecipes")
    public LiveData<List<MenuRecipe>> getAll();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(MenuRecipe menuRecipe);
}
