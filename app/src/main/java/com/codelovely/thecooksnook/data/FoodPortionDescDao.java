package com.codelovely.thecooksnook.data;

import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

@Dao
public interface FoodPortionDescDao {
    @Query("SELECT * FROM foodPortionDesc")
    public List<FoodPortionDesc> getAll();
}
