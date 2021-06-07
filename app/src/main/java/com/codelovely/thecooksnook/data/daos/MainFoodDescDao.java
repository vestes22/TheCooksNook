package com.codelovely.thecooksnook.data.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import com.codelovely.thecooksnook.data.MainFoodDesc;

import java.util.List;

@Dao
public interface MainFoodDescDao {
    @Query("SELECT * FROM mainFoodDesc")
    LiveData<List<MainFoodDesc>> getAll();

    @Query("SELECT * FROM mainFoodDesc JOIN mainFoodDesc_fts  " +
            "ON (mainFoodDesc.`Main food description` = mainFoodDesc_fts.`Main food description`) " +
            "WHERE mainFoodDesc_fts MATCH :query")
    LiveData<List<MainFoodDesc>> search(String query);

    @Query("SELECT * FROM mainFoodDesc JOIN mainFoodDesc_fts  " +
            "ON (mainFoodDesc.`Main food description` = mainFoodDesc_fts.`Main food description`) " +
            "WHERE mainFoodDesc_fts MATCH :query")
    List<MainFoodDesc> searchTest(String query);
}
