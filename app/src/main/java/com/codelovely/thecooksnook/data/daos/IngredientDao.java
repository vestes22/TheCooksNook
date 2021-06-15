package com.codelovely.thecooksnook.data.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;
import com.codelovely.thecooksnook.models.Nutrient;

import java.util.List;

@Dao
public interface IngredientDao {
    @Query("SELECT mainFoodDesc.`Food code` AS foodCode, " +
            "MainFoodDesc.'Main food description' AS mainFoodDesc, " +
            "FoodWeights.`Portion weight` AS portionWeight, " +
            "FoodPortionDesc.`Portion code` AS portionCode, " +
            "FoodPortionDesc.`Portion description` AS portionDesc, " +
            "FNDDSNutVal.`Nutrient code` AS nutrientCode, " +
            "FNDDSNutVal.`Nutrient value` AS nutrientValue, " +
            "NutDesc.`Nutrient description` AS nutrientDescription, " +
            "NutDesc.Tagname, NutDesc.Unit, NutDesc.Decimals AS decimals " +
            "FROM NutDesc INNER JOIN FNDDSNutVal " +
            "ON NutDesc.`Nutrient code` = FNDDSNutVal.`Nutrient code` " +
            "INNER JOIN mainFoodDesc " +
            "ON FNDDSNutVal.`Food code` = mainFoodDesc.`Food code` " +
            "INNER JOIN foodWeights " +
            "ON mainFoodDesc.`Food code` = foodWeights.`Food code` " +
            "INNER JOIN foodPortionDesc " +
            "ON foodWeights.`Portion code` = foodPortionDesc.`Portion code` " +
            "            WHERE MainFoodDesc.[Food code] = :foodCode " +
            "            AND FoodPortionDesc.[Portion code] = :portionCode")
    List<Nutrient> getNutrition(int foodCode, int portionCode);

}
