package com.codelovely.thecooksnook.data.daos;

import androidx.room.Dao;
import androidx.room.Query;

import com.codelovely.thecooksnook.data.entities.FoodWeights;

import java.util.List;

@Dao
public interface FoodWeightsDao {
    @Query("SELECT * FROM foodWeights")
    public List<FoodWeights> getAll();
}
