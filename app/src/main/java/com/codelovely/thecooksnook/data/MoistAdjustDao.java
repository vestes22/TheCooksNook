package com.codelovely.thecooksnook.data;

import androidx.room.Dao;
import androidx.room.Query;
import java.util.List;

@Dao
public interface MoistAdjustDao {
    @Query("SELECT * FROM moistAdjust")
    public List<MoistAdjust> getAll();
}
