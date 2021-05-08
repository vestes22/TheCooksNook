package com.codelovely.thecooksnook.data.daos;

import androidx.room.Dao;
import androidx.room.Query;

import com.codelovely.thecooksnook.data.entities.FNDDSNutritionValue;

import java.util.List;

@Dao
public interface FNDDSNutritionValueDao {
    @Query("SELECT * FROM FNDDSNutVal")
    public List<FNDDSNutritionValue> getAll();
}
