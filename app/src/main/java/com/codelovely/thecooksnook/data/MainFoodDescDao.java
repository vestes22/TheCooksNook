package com.codelovely.thecooksnook.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

@Dao
public interface MainFoodDescDao {
    @Query("SELECT * FROM mainFoodDesc")
    public List<MainFoodDesc> getAll();

    @Query("SELECT * FROM mainFoodDesc JOIN mainFoodDesc_fts  " +
            "ON (mainFoodDesc.`Main food description` = mainFoodDesc_fts.`Main food description`) " +
            "WHERE mainFoodDesc_fts MATCH :query")
    public List<MainFoodDesc> search(String query);
}
