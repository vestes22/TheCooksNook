package com.codelovely.thecooksnook.data.daos;

import androidx.room.Dao;
import androidx.room.Query;

import com.codelovely.thecooksnook.data.entities.RecommendedDailyValue;

import java.util.List;

@Dao
public interface RecommendedDailyValueDao {

    @Query("SELECT * FROM recommendedDailyValues")
    List<RecommendedDailyValue> getAllDailyValues();
}
