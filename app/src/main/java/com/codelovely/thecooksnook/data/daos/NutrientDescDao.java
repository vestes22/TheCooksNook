package com.codelovely.thecooksnook.data.daos;

import androidx.room.Dao;
import androidx.room.Query;

import com.codelovely.thecooksnook.data.entities.NutrientDesc;

import java.util.List;

@Dao
public interface NutrientDescDao {
    @Query("SELECT * FROM nutDesc")
    public List<NutrientDesc> getAll();
}
