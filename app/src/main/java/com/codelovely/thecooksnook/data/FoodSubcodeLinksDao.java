package com.codelovely.thecooksnook.data;

import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

@Dao
public interface FoodSubcodeLinksDao {
    @Query("SELECT * FROM foodSubcodeLinks")
    public List<FoodSubcodeLinks> getAll();
}
