package com.codelovely.thecooksnook.data.daos;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.codelovely.thecooksnook.data.entities.FoodNutrient;

import java.util.List;

@Dao
public interface FoodNutrientDao {

    @Query("SELECT EXISTS(SELECT * FROM foodNutrients WHERE `FDC ID` = :fdcId AND `Nutrient id` = :nutrientId)")
    boolean checkIfExists(int fdcId, int nutrientId);

    @Insert
    long insert(FoodNutrient foodNutrient);

    @Query("SELECT * FROM foodNutrients WHERE `FDC ID` = :fdcId")
    List<FoodNutrient> getFoodNutrientsByFdcId(int fdcId);
}
