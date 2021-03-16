package com.codelovely.thecooksnook.data;

import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

@Dao
public interface FoodWeightsDao {
    @Query("SELECT * FROM foodWeights")
    public List<FoodWeights> getAll();
}
