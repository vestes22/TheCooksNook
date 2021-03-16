package com.codelovely.thecooksnook.data;

import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

@Dao
public interface FNDDSNutritionValueDao {
    @Query("SELECT * FROM FNDDSNutVal")
    public List<FNDDSNutritionValue> getAll();
}
