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

    @Insert
    void insert(UserRecipe userRecipe);
}
