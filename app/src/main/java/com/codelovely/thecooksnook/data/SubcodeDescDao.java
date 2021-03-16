package com.codelovely.thecooksnook.data;

import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

@Dao
public interface SubcodeDescDao {
    @Query("SELECT * FROM subcodeDesc")
    public List<SubcodeDesc> getAll();
}
