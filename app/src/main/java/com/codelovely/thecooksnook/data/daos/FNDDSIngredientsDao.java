package com.codelovely.thecooksnook.data.daos;

import androidx.room.Dao;
import androidx.room.Query;

import com.codelovely.thecooksnook.data.entities.FNDDSIngredients;

import java.util.List;

@Dao
public interface FNDDSIngredientsDao {
    @Query("SELECT * FROM FNDDSIngred")
    public List<FNDDSIngredients> getAll();
}
