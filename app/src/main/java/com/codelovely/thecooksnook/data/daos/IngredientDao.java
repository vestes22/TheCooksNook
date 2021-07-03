package com.codelovely.thecooksnook.data.daos;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.codelovely.thecooksnook.data.entities.Ingredient;

import java.util.List;

@Dao
public interface IngredientDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    long insert(Ingredient ingredient);

    @Query("SELECT * FROM ingredients WHERE `FDC ID` = :fdcId")
    Ingredient getIngredientById(int fdcId);

    @Query("SELECT EXISTS(SELECT * FROM ingredients WHERE `FDC ID` = :id)")
    boolean checkIfExists(int id);
}
