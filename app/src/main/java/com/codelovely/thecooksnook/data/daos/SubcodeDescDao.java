package com.codelovely.thecooksnook.data.daos;

import androidx.room.Dao;
import androidx.room.Query;

import com.codelovely.thecooksnook.data.entities.SubcodeDesc;

import java.util.List;

@Dao
public interface SubcodeDescDao {
    @Query("SELECT * FROM subcodeDesc")
    public List<SubcodeDesc> getAll();
}
