package com.codelovely.thecooksnook.data.daos;

import androidx.room.Dao;
import androidx.room.Query;

import com.codelovely.thecooksnook.data.entities.MoistAdjust;

import java.util.List;

@Dao
public interface MoistAdjustDao {
    @Query("SELECT * FROM moistAdjust")
    public List<MoistAdjust> getAll();
}
