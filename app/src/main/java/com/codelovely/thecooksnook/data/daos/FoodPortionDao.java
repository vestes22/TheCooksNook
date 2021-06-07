package com.codelovely.thecooksnook.data.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;
import com.codelovely.thecooksnook.models.FoodPortion;

import java.util.List;

@Dao
public interface FoodPortionDao {
    @Query ("SELECT foodWeights.`Food code` AS foodCode, foodWeights.`Portion code` AS portionCode, foodPortionDesc.`Portion description` AS portionDesc " +
            "FROM FoodPortionDesc INNER JOIN FoodWeights ON FoodPortionDesc.[Portion code] = FoodWeights.[Portion code] " +
            "WHERE FoodWeights.[Food code] = :foodCode")
    LiveData<List<FoodPortion>> getPortionOptions(int foodCode);
}
