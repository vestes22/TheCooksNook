package com.codelovely.thecooksnook.data;

import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

@Dao
public interface NutrientDescDao {
    @Query("SELECT * FROM nutDesc")
    public List<NutrientDesc> getAll();
}
