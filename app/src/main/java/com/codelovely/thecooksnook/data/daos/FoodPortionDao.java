package com.codelovely.thecooksnook.data.daos;

import androidx.room.Dao;
import androidx.room.Query;
import com.codelovely.thecooksnook.models.FoodPortion;

import java.util.List;

@Dao
public interface FoodPortionDao {
    @Query ("SELECT foodWeights.`Food code` AS foodCode, foodWeights.`Portion code` AS portionCode, foodPortionDesc.`Portion description` AS portionDesc " +
            "FROM FoodPortionDesc INNER JOIN FoodWeights ON FoodPortionDesc.[Portion code] = FoodWeights.[Portion code] " +
            "WHERE FoodWeights.[Food code] = :foodCode")
    List<FoodPortion> getPortionOptions(int foodCode);

    @Query ("SELECT foodWeights.`Food code` AS foodCode, foodWeights.`Portion code` AS portionCode, foodPortionDesc.`Portion description` AS portionDesc " +
            "FROM FoodPortionDesc INNER JOIN FoodWeights ON FoodPortionDesc.[Portion code] = FoodWeights.[Portion code] " +
            "WHERE FoodWeights.[Food code] = :foodCode " +
            "AND portionCode = :portionCode")
    FoodPortion getPortionById(int foodCode, int portionCode);
}
