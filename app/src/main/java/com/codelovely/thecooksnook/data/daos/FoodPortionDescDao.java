package com.codelovely.thecooksnook.data.daos;

import androidx.room.Dao;
import androidx.room.Query;

import com.codelovely.thecooksnook.data.entities.FoodPortionDesc;

import java.util.List;

@Dao
public interface FoodPortionDescDao {
    @Query("SELECT * FROM foodPortionDesc")
    public List<FoodPortionDesc> getAll();
}
