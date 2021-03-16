package com.codelovely.thecooksnook.data;

import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

@Dao
public interface FNDDSIngredientsDao {
    @Query("SELECT * FROM FNDDSIngred")
    public List<FNDDSIngredients> getAll();
}
