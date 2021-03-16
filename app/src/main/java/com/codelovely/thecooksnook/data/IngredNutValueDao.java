package com.codelovely.thecooksnook.data;

import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

@Dao
public interface IngredNutValueDao {
    @Query("SELECT * FROM ingredNutVal")
    public List<IngredNutValue> getAll();
}
