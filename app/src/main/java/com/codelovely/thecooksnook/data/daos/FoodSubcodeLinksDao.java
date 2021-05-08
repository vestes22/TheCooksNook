package com.codelovely.thecooksnook.data.daos;

import androidx.room.Dao;
import androidx.room.Query;

import com.codelovely.thecooksnook.data.entities.FoodSubcodeLinks;

import java.util.List;

@Dao
public interface FoodSubcodeLinksDao {
    @Query("SELECT * FROM foodSubcodeLinks")
    public List<FoodSubcodeLinks> getAll();
}
