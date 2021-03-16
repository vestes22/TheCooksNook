package com.codelovely.thecooksnook.data;

import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

@Dao
public interface DerivDescDao {
    @Query("SELECT * FROM derivDesc")
    public List<DerivDesc> getAll();
}
