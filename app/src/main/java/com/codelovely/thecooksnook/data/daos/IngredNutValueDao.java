package com.codelovely.thecooksnook.data.daos;

import androidx.room.Dao;
import androidx.room.Query;

import com.codelovely.thecooksnook.data.entities.IngredNutValue;

import java.util.List;

@Dao
public interface IngredNutValueDao {
    @Query("SELECT * FROM ingredNutVal")
    public List<IngredNutValue> getAll();
}
