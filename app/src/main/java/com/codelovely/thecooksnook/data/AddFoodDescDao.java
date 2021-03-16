package com.codelovely.thecooksnook.data;

import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

@Dao
public interface AddFoodDescDao {
    @Query("SELECT * FROM addFoodDesc")
    public List<AddFoodDesc> getAll();
}
