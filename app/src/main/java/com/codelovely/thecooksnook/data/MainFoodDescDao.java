package com.codelovely.thecooksnook.data;

import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

@Dao
public interface MainFoodDescDao {
    @Query("SELECT * FROM mainFoodDesc")
    public List<MainFoodDesc> getAll();
}
